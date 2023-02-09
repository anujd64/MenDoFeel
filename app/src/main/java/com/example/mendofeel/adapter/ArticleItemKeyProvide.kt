package com.example.mendofeel.adapter

import androidx.recyclerview.selection.ItemKeyProvider

class ArticleItemKeyProvide(private val adapter: ArticleAdapter) :
    ItemKeyProvider<String>(SCOPE_MAPPED) {

    override fun getKey(position: Int): String {
        return adapter.articles[position].title
    }

    override fun getPosition(key: String): Int {
        return adapter.articles.indexOfFirst { it.title == key }
    }
}