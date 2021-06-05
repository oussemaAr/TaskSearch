package tn.org.tasksearch.search

import tn.org.tasksearch.data.local.TaskEntity

sealed class State {
    object Loading : State()
    class Content(val data: List<TaskEntity>) : State()
    object Error : State()
}

sealed class SearchType {
    object All : SearchType()
    class State(val state: Boolean) : SearchType()
    class Name(val name: String) : SearchType()
    class Both(val name: String, val state: Boolean) : SearchType()
}
