package com.example.news

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import cl.duoc.ejemploroom.getOrAwaitValue
import com.example.news.db.BaseDeDatos
import com.example.news.db.NewsDao
import com.example.news.model.db.NewsEntity
import com.example.news.model.db.NewsFavEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LocalDBTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: BaseDeDatos
    private  lateinit var dao : NewsDao

    private val noticiaFavorita = NewsFavEntity(
        "la nación",
        "Algun título escandaloso",
        "aca va un contenido bien amarrillista",
        fecha = Calendar.getInstance().time
    )

    private val noticiaABorrar = NewsFavEntity(
        "la nación",
        "Algun título escandaloso a borrar",
        "aca va un contenido bien amarrillista, pero como es tan amarillo, lo borramos",
        fecha = Calendar.getInstance().time
    )


    private val noticia = NewsEntity(
        "la nación",
        "Algun título escandaloso",
        "aca va un contenido bien amarrillista",
        fecha = Calendar.getInstance().time,
        imagenUrl = "http algo",
        contenido = "Contenido muy noticioso"
    )

    private val noticiaDos = NewsEntity(
        "la nación",
        "Algun título escandaloso a borrar",
        "aca va un contenido bien amarrillista, pero como es tan amarillo, lo borramos",
        fecha = Calendar.getInstance().time,
        imagenUrl = "http algo",
        contenido = "Contenido muy noticioso"
    )






    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, BaseDeDatos::class.java)
            .allowMainThreadQueries()
            .build()

        dao =  database.dao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun agergarListadoUltimasNoticiasTest() {
        val listadoNoticiasApi = listOf(noticia, noticiaDos)
        runBlockingTest {


            dao.agregarListadoNews(listadoNoticiasApi)

            val listadoNoticiasLD = dao.listarUltimasNoticias().asLiveData().getOrAwaitValue()

            assertThat(listadoNoticiasLD).contains(noticia)
            assertThat(listadoNoticiasLD).contains(noticiaDos)

        }
    }



    @Test
    fun agergarFavoritoTest() {

        runBlockingTest {


            dao.agregarFavNews(noticiaFavorita)

            val listadoNoticiasTest = dao.listarFavoritos().asLiveData().getOrAwaitValue()

            assertThat(listadoNoticiasTest).contains(noticiaFavorita)
        }
    }

    @Test
    fun eliminarFavoritoTest() = runBlockingTest {

        dao.agregarFavNews(noticiaFavorita)
        dao.borrarFavorito(noticiaABorrar)

        val listadoNoticias = dao.listarFavoritos().asLiveData().getOrAwaitValue()

        assertThat(listadoNoticias).doesNotContain(noticiaABorrar)
    }
}