package tn.org.tasksearch.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tn.org.tasksearch.data.local.TaskDatabase
import tn.org.tasksearch.data.local.TaskEntity
import tn.org.tasksearch.data.remote.TaskClient
import tn.org.tasksearch.data.remote.TaskResponse
import tn.org.tasksearch.search.State
import tn.org.tasksearch.utils.default_query

class TasksDataSource(private val context: Context) {

    fun getAllTasks(): Flow<State> = flow {
        emit(State.Loading)
        val dao = TaskDatabase.getInstance(context).taskDao()
        val remote = TaskClient.getInstance()
        try {
            val response = remote.searchTask(default_query)
            if (response.isSuccessful && response.body() != null) {
                val entities = response.body()!!.results.asTaskEntity()
                dao.insertTasks(entities)
            }
            emit(State.Content(dao.getAllTasks()))
        } catch (ex: Exception) {
            emit(State.Error)
        }
    }

    fun searchByName(name: String): Flow<State> = flow {
        emit(State.Loading)
        val dao = TaskDatabase.getInstance(context).taskDao()
        val result = dao.searchByName("$name%")
        emit(State.Content(result))
    }

    fun searchByState(state: Boolean): Flow<State> = flow {
        emit(State.Loading)
        val dao = TaskDatabase.getInstance(context).taskDao()
        val result = dao.searchByState(state)
        emit(State.Content(result))
    }

    fun search(name: String, state: Boolean): Flow<State> = flow {
        emit(State.Loading)
        val dao = TaskDatabase.getInstance(context).taskDao()
        val isArchived = if (state) 1 else 0
        val result = dao.searchByNameState("$name%", isArchived)
        emit(State.Content(result))
    }

    private fun TaskResponse.Results.asTaskEntity(): List<TaskEntity> {
        val tasks = mutableListOf<TaskEntity>()

        this.tasks.map { task ->
            val sectionId = this.sections.find { section ->
                section.id == task.sectionId
            }?.projectId

            val projectName = this.projects.find { project ->
                project.id == sectionId
            }?.name

            tasks.add(
                TaskEntity(
                    taskId = task.id,
                    taskName = task.name,
                    projectName = projectName ?: "",
                    isArchived = task.status == 18
                )
            )
        }
        return tasks
    }
}