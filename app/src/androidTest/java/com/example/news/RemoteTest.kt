package com.example.news

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.news.db.BaseDeDatos
import com.example.news.db.NewsDao
import com.example.news.model.Article
import com.example.news.model.Source
import com.example.news.network.ApiService
import com.example.news.repository.Repositorio
import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class RemoteTest {

    @get: Rule

    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000
    private lateinit var repositorioTest: Repositorio
    private lateinit var repositorio: Repositorio

    private lateinit var database: BaseDeDatos
    private lateinit var dao: NewsDao

    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    @Before
    fun setup() {
        mockWebServer.start(MOCK_WEBSERVER_PORT)
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, BaseDeDatos::class.java)
            .allowMainThreadQueries()
            .build()

        dao = database.dao()

        val retrofitClientTest by lazy {
            Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }


        val retrofitClient by lazy {
            Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

        }



        repositorioTest = Repositorio(retrofitClientTest, dao)
        repositorio = Repositorio(retrofitClient, dao)
    }

    @After
    fun teardown(){
        mockWebServer.shutdown()
        database.close()
    }

    @Test
    fun noticiaComparador(){
        val articuloOriginal = Article(
            "Hernán Cappiello",
            "La Corte Suprema de Justicia dejó firme una condena a 3 años y seis meses de prisión contra el exfuncionario Germán Kammerath por negociaciones incompatibles con la función pública cuando fue intende… [+3581 chars]",
            "Es por negociaciones incompatibles con la función pública en 2000; hubo disidencias de los jueces Rosenkrantz y Maqueda porque dicen que se violó el plazo razonable para el proceso",
            "2021-10-28T19:52:30Z",
            Source("la-nacion","La Nacion"),
            "La Corte dejó firme una sentencia a 3 años y 6 meses contra el ex funcionario Germán Kammerath",
            "https://www.lanacion.com.ar/politica/la-corte-dejo-firme-una-sentencia-a-3-anos-y-6-meses-contra-el-ex-funcionario-german-kammerath-nid28102021/",
            "https://resizer.glanacion.com/resizer/uWI-9SusBymhJT-RI2p5q_7WNLE=/768x0/filters:quality(80)/cloudfront-us-east-1.images.arcpublishing.com/lanacionar/CSTROHTH5JHIXO56DLK4ZAW4MM.jpg"
        )

        mockWebServer.apply{
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        FileReader.readStringFromFile("mock_response.json")
                    )
            )
        }

        testScope.launch {
            val articulo = repositorioTest.buscarNoticia("germán kammerath").articles[0]
            assertThat(articuloOriginal).isEqualTo(articulo)
        }

    }

    @Test
    fun buscarNoticiaTest(){
        mockWebServer.apply {
            MockResponse()
                .setBody(
                    FileReader.readStringFromFile("mock_response.json")
                )
        }

        testScope.launch {
            val noticiaApi = repositorio.buscarNoticia("germán kammerath").articles
            val noticiaMock = repositorioTest.buscarNoticia("germán kammerath").articles

            assertThat(noticiaApi).isEqualTo(noticiaMock)
        }
    }
}