package tn.org.tasksearch.data.remote

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface TaskService {

    @POST("search")
    suspend fun searchTask(
        @Query("filter") filter: String,
        @Query("response_format") format: String = "object"
    ): Response<TaskResponse>
}