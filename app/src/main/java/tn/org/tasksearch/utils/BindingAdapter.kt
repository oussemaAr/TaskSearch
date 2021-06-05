package tn.org.tasksearch.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.ChipGroup


@BindingAdapter("onCheckedChanged")
fun bindChangeListener(chipGroup: ChipGroup, listener: ChipGroup.OnCheckedChangeListener) {
    chipGroup.setOnCheckedChangeListener(listener)
}
