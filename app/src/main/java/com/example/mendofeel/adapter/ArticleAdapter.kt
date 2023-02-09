package com.example.mendofeel.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate.NightMode
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mendofeel.R
import com.example.mendofeel.databinding.ItemArticleBinding
import com.example.mendofeel.model.Article
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*
import kotlin.collections.ArrayList

class ArticleAdapter() : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(), Filterable {

    init {
        setHasStableIds(true)
    }

    var tracker: SelectionTracker<String>? = null
    var articles = emptyList<Article>()
    var articleListFiltered= emptyList<Article>()


    fun updateData(newData: List<Article>) {
        articles = newData.map { it }
        articleListFiltered = articles.map { it }
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = articleListFiltered.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = articleListFiltered[position]

        tracker?.let {
            holder.bind(item, it.isSelected(articleListFiltered[position].title))
        }

    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) : ViewHolder(binding.root) {


        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): String = articles[adapterPosition].title
            }


        fun bind(article: Article, isActivated: Boolean = false) {

            binding.title.text = article.title
            binding.content.text = article.content
            binding.description.text = article.description

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val date = sdf.parse(article.publishedAt)
            val sdf2 = SimpleDateFormat("MMM dd, yyyy 'at' h:m a", Locale.getDefault()).format(date!!)

            binding.publishedAt.text = sdf2.toString()

            binding.sourceName.text = article.source.name
            Glide.with(itemView.context)
                .load(article.urlToImage)
                .into(binding.urlToImage)

            itemView.isActivated = isActivated

            if (isActivated) {
                binding.cardItem.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.blue
                    )
                )
            }
            else {
                binding.cardItem.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.cardBackgroundColor
//                        R.color.white
                    ))
            }
        }
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString()?.lowercase() ?: ""
                if (charString.isEmpty()) articleListFiltered = articles else {
                    val filteredList = ArrayList<Article>()
                    articles
                        .filter {
                            (it.title.lowercase().contains(constraint!!))
                        }
                        .forEach { filteredList.add(it) }
                    articleListFiltered = filteredList

                    Log.e("performFiltering: t1: ", filteredList.size.toString())

                }
                return FilterResults().apply { values = articleListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                articleListFiltered =
                    if (results?.values == null)
                        ArrayList()
                    else
                        results.values as List<Article>

                notifyDataSetChanged()

                Log.e("performFiltering: t2 ", "called " + articleListFiltered.size)

            }

        }

    }


}




