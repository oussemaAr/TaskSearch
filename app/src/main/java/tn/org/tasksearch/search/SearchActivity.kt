package tn.org.tasksearch.search

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import tn.org.tasksearch.R
import tn.org.tasksearch.adapters.TaskAdapter
import tn.org.tasksearch.data.TasksDataSource
import tn.org.tasksearch.databinding.ActivityMainBinding

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(TasksDataSource(this@SearchActivity))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        viewModel.tasks.observe(this) { state ->
            when (state) {
                is State.Content -> {
                    with(binding) {
                        val list = state.data.map {
                            TaskUIConfig(
                                projectName = it.projectName,
                                taskName = it.taskName
                            )
                        }
                        recycler.layoutManager = LinearLayoutManager(this@SearchActivity)
                        recycler.adapter = TaskAdapter(list) {
                            MaterialAlertDialogBuilder(this@SearchActivity)
                                .setTitle(it.projectName)
                                .setMessage("${it.taskName} will be done")
                                .setNeutralButton(getString(R.string.close)) { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .setPositiveButton(getString(R.string.done)) { dialog, _ ->
                                    dialog.dismiss()
                                }.show()
                        }
                        if (list.isEmpty()) {
                            emptyState.visibility = View.VISIBLE
                            recycler.visibility = View.GONE
                        } else {
                            emptyState.visibility = View.GONE
                            recycler.visibility = View.VISIBLE
                        }
                        loading.visibility = View.GONE
                    }
                }
                State.Error -> {
                    Snackbar.make(binding.root, "Error come to surface", Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.reload)) {
                            viewModel.loadAllTasks()
                        }
                        .show()

                }
                State.Loading -> {
                    with(binding) {
                        recycler.visibility = View.GONE
                        loading.visibility = View.VISIBLE
                        emptyState.visibility = View.GONE
                    }
                }
            }
        }
        viewModel.loadAllTasks()
    }
}