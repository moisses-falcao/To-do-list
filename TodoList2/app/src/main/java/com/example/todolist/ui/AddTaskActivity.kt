package com.example.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.*
import android.widget.Toast
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.example.todolist.localdatabase.TaskDao
import com.example.todolist.localdatabase.TaskDatabase
import com.example.todolist.model.TaskModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var taskDatabase: TaskDatabase
    private lateinit var taskDao: TaskDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        voltar()

        this.taskDatabase = TaskDatabase.getInstance(this)
        this.taskDao = this.taskDatabase.taskDao()
    }

    private fun voltar() {
        binding.btBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H).build()

            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "${hour}:${minute}"
            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.btCancelar.setOnClickListener {
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btCriar.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val result = saveTask(
                    binding.tilTask.text.toString(),
                    binding.tilDate.text.toString(),
                    binding.tilHour.text.toString(),
                    binding.tilDetail.text.toString()
                )
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        this@AddTaskActivity,
                        if(result) "Tarefa salva!" else "Erro ao salvar",
                        Toast.LENGTH_LONG
                    ).show()
                    if(result){
                        val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }


        /*if(intent.hasExtra(TASK_ID)) {
            binding.btUpdate.visibility = VISIBLE
            binding.btCriar.visibility = GONE
            binding.btCancelar.visibility = GONE

            var taskId = intent.getIntExtra(TASK_ID, 0)

            binding.btUpdate.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    var item = taskDao.search(taskId)

                    item.title = binding.tilTask.text
                    item.date = binding.tilDate.text
                    item.hour = binding.tilHour.text
                    item.description = binding.tilDetail.text

                    var result = updateTask(
                        item.title,
                        item.date,
                        item.hour,
                        item.description
                    )
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddTaskActivity,
                            if (result) "Tarefa atualizada!" else "Erro ao editar tarefa",
                            Toast.LENGTH_LONG
                        ).show()
                        if (result) {
                            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }*/
    }

    private suspend fun saveTask(title: String, hour: String, date: String, description: String?): Boolean {
        if (title.isBlank() || title.isEmpty()){
            return false
        }
        if (hour.isBlank() || hour.isEmpty()){
            return false
        }
        if (date.isBlank() || date.isEmpty()){
            return false
        }

        this.taskDao.insert(TaskModel(title, date, hour, description))
        return true
    }

    private suspend fun updateTask(title: String, hour: String, date: String, description: String?): Boolean {
        if (title.isBlank() || title.isEmpty()){
            return false
        }
        if (hour.isBlank() || hour.isEmpty()){
            return false
        }
        if (date.isBlank() || date.isEmpty()){
            return false
        }

        val task = TaskModel(title, date, hour, description)

        this.taskDao.insert(TaskModel(title, date, hour, description))

        return true
    }

    companion object{
        const val TASK_ID = "task_id"
    }

}