package com.example.smartgym.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.R
import com.example.smartgym.model.Exercise
import com.example.smartgym.model.ExerciseLog

class WorkoutLogAdapter(
    private val logs: List<ExerciseLog>,
    private val targets: List<Exercise>
) : RecyclerView.Adapter<WorkoutLogAdapter.LogViewHolder>() {

    class LogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvExName)
        val tvTarget = view.findViewById<TextView>(R.id.tvExTarget)
        val etWeight = view.findViewById<EditText>(R.id.etLogWeight)
        val etReps = view.findViewById<EditText>(R.id.etLogReps)
        val cbDone = view.findViewById<CheckBox>(R.id.cbCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout_log, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val log = logs[position]
        val target = targets.find { it.name == log.exerciseName }
        
        holder.tvName.text = log.exerciseName
        holder.tvTarget.text = "Target: ${target?.sets ?: 0} Sets x ${target?.reps ?: 0} Reps"
        
        holder.etWeight.setText(log.weight)
        holder.etReps.setText(log.repsDone)
        holder.cbDone.isChecked = log.completed

        holder.etWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { log.weight = s.toString() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        holder.etReps.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { log.repsDone = s.toString() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        holder.cbDone.setOnCheckedChangeListener { _, isChecked -> log.completed = isChecked }
    }

    override fun getItemCount() = logs.size
}
