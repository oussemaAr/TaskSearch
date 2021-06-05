package tn.org.tasksearch.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(task: List<TaskEntity>)

    @Query("SELECT * FROM task_table")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM task_table WHERE is_task_archived = :state")
    suspend fun searchByState(state: Boolean): List<TaskEntity>

    @Query("SELECT * FROM TASK_TABLE WHERE task_name like :name")
    suspend fun searchByName(name: String): List<TaskEntity>

    @Query("SELECT * FROM TASK_TABLE WHERE task_name like :name AND is_task_archived = :state")
    suspend fun searchByNameState(name: String, state: Int): List<TaskEntity>
}