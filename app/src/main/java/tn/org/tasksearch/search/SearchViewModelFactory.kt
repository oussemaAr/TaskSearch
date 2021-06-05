package tn.org.tasksearch.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tn.org.tasksearch.data.TasksDataSource

class SearchViewModelFactory(private val dataSource: TasksDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Wrong ViewModel Class")
    }
}