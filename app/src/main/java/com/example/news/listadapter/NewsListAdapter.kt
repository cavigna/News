package com.example.news.listadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide

import com.example.news.databinding.ItemRowBinding
import com.example.news.model.db.NewsEntity


class NewsListAdapter(): ListAdapter<NewsEntity, NewsViewHolder> (NewsComparator()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = getItem(position)

        with(holder.binding){
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            tvTituloRow.text = currentNews.titulo

            imageView.load(currentNews.url)

            Log.e("prueba", currentNews.imagenUrl)

            tvfechaRow.text = currentNews.fecha.toString()


        }




    }
}

class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    val binding = ItemRowBinding.bind(itemView)

    companion object{
        fun create(parent: ViewGroup):NewsViewHolder{
            val layoutInflaterB = LayoutInflater.from(parent.context)
            val binding = ItemRowBinding.inflate(layoutInflaterB, parent, false)

            return NewsViewHolder(binding.root)


        }


    }
    fun unidorimagen(){
        Glide.with(itemView)
            .load("https://www.infobae.com/new-resizer/wXrH1WUz825r7GLGsjAnkUmwB5U=/1200x628/filters:format(jpg):quality(85)//cloudfront-us-east-1.images.arcpublishing.com/infobae/BZLVAIQZ5VARPBUKZZCXGZZTLM.jpg")
            .into(binding.imageView)
    }


}

class NewsComparator: DiffUtil.ItemCallback<NewsEntity>() {
    override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
        return  oldItem.fecha == newItem.fecha
    }

}
