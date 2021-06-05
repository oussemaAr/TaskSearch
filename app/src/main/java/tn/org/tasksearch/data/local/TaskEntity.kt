package tn.org.tasksearch.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey @ColumnInfo(name = "task_id") val taskId: Int,
    @ColumnInfo(name = "task_name") val taskName: String,
    @ColumnInfo(name = "project_name") val projectName: String,
    @ColumnInfo(name = "is_task_archived") val isArchived: Boolean,
)