package com.example.mendofeel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mendofeel.adapter.ArticleAdapter
import com.example.mendofeel.databinding.ActivityMainBinding
import com.example.mendofeel.model.NewsArticles
import com.example.mendofeel.adapter.ArticleDetailsLookup
import com.example.mendofeel.adapter.ArticleItemKeyProvide
import com.example.mendofeel.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsRecyclerView: RecyclerView
    private var adapter: ArticleAdapter = ArticleAdapter()
    private var tracker: SelectionTracker<String>? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker?.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            tracker?.onRestoreInstanceState(savedInstanceState)
        }

        initComponents()

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.articles.observe(this) {
            adapter.updateData(it.articles)
        }

        binding.searchBar.setOnQueryTextListener(this)

    }

    private fun initComponents() {

        newsRecyclerView = binding.newsRV
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = adapter

        tracker = SelectionTracker.Builder(
            "mySelectionId",
            newsRecyclerView,
            ArticleItemKeyProvide(adapter),
            ArticleDetailsLookup(newsRecyclerView),
            StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        adapter.tracker = tracker
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newQuery: String?): Boolean {
        adapter.filter.filter(newQuery)
        return false
    }
}

