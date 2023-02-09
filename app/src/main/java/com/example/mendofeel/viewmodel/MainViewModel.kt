package com.example.mendofeel.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mendofeel.model.NewsArticles
import com.example.mendofeel.repository.ArticleRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    init {
        getData()
    }
     val articles = MutableLiveData<NewsArticles>()

    private fun getData(){

        viewModelScope.launch {
            ArticleRepository.getArticles()
                .catch {
                    Log.d("viewModel",it.toString() )
                }
                .collect{
                        response -> articles.value = response
                }
        }

    }
}