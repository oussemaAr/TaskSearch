package tn.org.tasksearch.data.remote


import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("paging")
    val paging: Paging,
    @SerializedName("results")
    val results: Results
) {
    data class Paging(
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("results_per_page")
        val resultsPerPage: Int,
        @SerializedName("total_pages")
        val totalPages: Int,
        @SerializedName("total_results")
        val totalResults: Int
    )

    data class Results(
        @SerializedName("projects")
        val projects: List<Project>,
        @SerializedName("sections")
        val sections: List<Section>,
        @SerializedName("tasks")
        val tasks: List<Task>
    ) {
        data class Project(
            @SerializedName("id")
            val id: Int,
            @SerializedName("color")
            val color: String,
            @SerializedName("name")
            val name: String,
        )

        data class Section(
            @SerializedName("id")
            val id: Int,
            @SerializedName("project_id")
            val projectId: Int,
        )

        data class Task(
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("section_id")
            val sectionId: Int,
            @SerializedName("status")
            val status: Int,
        )
    }
}