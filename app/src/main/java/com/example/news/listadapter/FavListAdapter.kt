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
import com.example.news.model.db.NewsFavEntity
import com.example.news.utils.calcularDiferenciaTemporal

class FavListAdapter(private val miBorradorDeNoticias: MiBorradorDeNoticias): ListAdapter<NewsFavEntity, NewsFavViewHolder>(NewsFavCompardor()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFavViewHolder {
        return NewsFavViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsFavViewHolder, position: Int) {
        val favorito = getItem(position)

        with(holder.binding) {

            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            tvTituloRow.text = favorito.titulo.substringBefore("-")
            tvfechaRow.text = favorito.fecha.toString()
            tvfuenteRow.text = favorito.fuente

            holder.unidorimagen(favorito.imagenUrl)

            tvfechaRow.text = calcularDiferenciaTemporal(favorito.fecha)

            cardView2.setOnClickListener {
               // miBorradorDeNoticias.alClick(favorito)

                Navigation.findNavController(holder.itemView).navigate(R.id.action_favFragment_to_detailsFragment)
            }


        }

    }

    interface MiBorradorDeNoticias{
        fun alClick(favorito: NewsFavEntity)
    }
}

class NewsFavCompardor : DiffUtil.ItemCallback<NewsFavEntity>() {
    override fun areItemsTheSame(oldItem: NewsFavEntity, newItem: NewsFavEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NewsFavEntity, newItem: NewsFavEntity): Boolean {
        return oldItem.fecha == newItem.fecha
    }

}



class NewsFavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = ItemRowBinding.bind(itemView)

    companion object {
        fun create(parent: ViewGroup): NewsFavViewHolder {
            val layoutInflaterB = LayoutInflater.from(parent.context)
            val binding = ItemRowBinding.inflate(layoutInflaterB, parent, false)

            return NewsFavViewHolder(binding.root)


        }


    }

    fun unidorimagen(url: String) {
        Glide.with(itemView)
            .load(url)
            .into(binding.imageView)
    }


}
