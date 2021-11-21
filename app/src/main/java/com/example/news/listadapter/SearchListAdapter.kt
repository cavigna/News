package com.example.news.listadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.databinding.ItemRowBinding
import com.example.news.model.Article
import com.example.news.utils.calcularDiferenciaTemporal
import com.example.news.utils.formateameLaFecha

class SearchListAdapter(private val miExtractorBusqueda: MiExtractorBusqueda): ListAdapter<Article, MySearchViewHolder> (ArticleComparator()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySearchViewHolder {
        return MySearchViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MySearchViewHolder, position: Int) {
        val articulo = getItem(position)

        with(holder.binding){
            //imageView.scaleType = Ima

            tvTituloRow.text = articulo.title.substringBefore("-")
            tvfechaRow.text = calcularDiferenciaTemporal(formateameLaFecha(articulo.publishedAt)!!)
            tvfuenteRow.text = articulo.source.name
        }
        holder.unidorimagenSearch(articulo.urlToImage)

        holder.binding.cardView2.setOnClickListener {
            miExtractorBusqueda.itemBuscado(articulo)
            Navigation.findNavController(holder.itemView).navigate(R.id.action_searchFragment_to_detailsSearchFragment)
        }
    }

    interface MiExtractorBusqueda{
        fun itemBuscado(article: Article)
    }
}

class MySearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = ItemRowBinding.bind(itemView)

    companion object{
        fun create(parent: ViewGroup):MySearchViewHolder{
            val layoutInflaterB = LayoutInflater.from(parent.context)
            val binding = ItemRowBinding.inflate(layoutInflaterB, parent, false)

            return MySearchViewHolder(binding.root)
        }
    }

    fun unidorimagenSearch(url: String) {
        Glide.with(itemView)
            .load(url)
            .into(binding.imageView)
    }


}

class ArticleComparator :DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem== newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.publishedAt == newItem.publishedAt
    }

}
