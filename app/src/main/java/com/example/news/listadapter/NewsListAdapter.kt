package com.example.news.listadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R

import com.example.news.databinding.ItemRowBinding
import com.example.news.model.db.NewsEntity
import com.example.news.utils.calcularDiferenciaTemporal


class NewsListAdapter(private val miExtractorNoticas: ExtractorNoticas) : ListAdapter<NewsEntity, NewsViewHolder>(NewsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = getItem(position)

        with(holder.binding) {

            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            tvTituloRow.text = currentNews.titulo.substringBefore("-")
            tvfechaRow.text = currentNews.fecha.toString()
            tvfuenteRow.text = currentNews.fuente

            holder.unidorimagen(currentNews.imagenUrl)

           tvfechaRow.text = calcularDiferenciaTemporal(currentNews.fecha)

            cardView2.setOnClickListener {
                miExtractorNoticas.alClick(currentNews)

                Navigation.findNavController(holder.itemView).navigate(R.id.action_homeFragment_to_detailsFragment)
            }


        }


    }

    interface ExtractorNoticas{
        fun alClick(news: NewsEntity)
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = ItemRowBinding.bind(itemView)

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val layoutInflaterB = LayoutInflater.from(parent.context)
            val binding = ItemRowBinding.inflate(layoutInflaterB, parent, false)

            return NewsViewHolder(binding.root)


        }


    }

    fun unidorimagen(url: String) {
        Glide.with(itemView)
            .load(url)
            .into(binding.imageView)
    }


}

class NewsComparator : DiffUtil.ItemCallback<NewsEntity>() {
    override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
        return oldItem.fecha == newItem.fecha
    }

}
