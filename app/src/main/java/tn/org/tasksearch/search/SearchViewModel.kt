package tn.org.tasksearch.search

import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tn.org.tasksearch.data.TasksDataSource

class SearchViewModel(private val dataSource: TasksDataSource) : ViewModel() {

    var loading = true

    private var _tasks = MutableLiveData<State>()
    val tasks: LiveData<State> = _tasks
    private var selection = 1
    private var query = ""

    var testing = ChipGroup.OnCheckedChangeListener { _, checkedId ->
        selection = checkedId
        search()
    }

    fun textChange(query: CharSequence) {
        this.query = query.toString()
        search()
    }

    private fun search() {
        loading = true
        viewModelScope.launch {
            delay(400)
            when (selection) {
                1 -> {
                    if (query.isEmpty()) {
                        searchTasks(SearchType.All)
                    } else {
                        searchTasks(SearchType.Name(query))
                    }
                }
                2 -> {
                    if (query.isEmpty()) {
                        searchTasks(SearchType.State(false))
                    } else {
                        searchTasks(SearchType.Both(query, false))
                    }
                }
                3 -> {
                    if (query.isEmpty()) {
                        searchTasks(SearchType.State(true))
                    } else {
                        searchTasks(SearchType.Both(query, true))
                    }
                }
            }
        }
    }

    private fun searchTasks(searchType: SearchType) = viewModelScope.launch {
        when (searchType) {
            is SearchType.Both -> {
                Log.e("TAG", "searchTasks: Both")
                dataSource.search(searchType.name, searchType.state).collect {
                    _tasks.value = it
                    loading = false
                }
            }
            is SearchType.Name -> {
                Log.e("TAG", "searchTasks: name")
                dataSource.searchByName(searchType.name).collect {
                    _tasks.value = it
                    loading = false
                }
            }
            is SearchType.State -> {
                Log.e("TAG", "searchTasks: state")
                dataSource.searchByState(searchType.state).collect {
                    _tasks.value = it
                    loading = false
                }
            }
            SearchType.All -> {
                Log.e("TAG", "searchTasks: All")
                dataSource.getAllTasks().collect {
                    _tasks.value = it
                    loading = false
                }
            }
        }
    }

    fun loadAllTasks() = searchTasks(SearchType.All)
}