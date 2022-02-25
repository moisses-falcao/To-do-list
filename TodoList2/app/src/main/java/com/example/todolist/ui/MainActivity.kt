package com.example.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.todolist.adapter.ListTaskAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.localdatabase.TaskDao
import com.example.todolist.localdatabase.TaskDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskDatabase: TaskDatabase
    private lateinit var taskDao: TaskDao
    val adapter by lazy { ListTaskAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        irParaTelaDeCriar()

        this.taskDatabase = TaskDatabase.getInstance(this)
        this.taskDao = this.taskDatabase.taskDao()
    }


    override fun onStart() {
        super.onStart()

        loadTasks()
        deleteTask()
    }


    private fun deleteTask() {
        adapter.listenerDelete = {
            CoroutineScope(Dispatchers.IO).launch {
                taskDao.delete(it)
                finish()
                overridePendingTransition(0, 0)
                startActivity(getIntent())
                overridePendingTransition(0, 0)
            }
        }
    }

    private fun loadTasks() {
        CoroutineScope(Dispatchers.IO).launch {
            var taskList = taskDao.getAllTasks()
            val recy = binding.rvListRecyclerView
            recy.adapter = adapter
            adapter.taskList = taskList

            if(taskList.size != 0){
                binding.gvPandinha.visibility = GONE
                binding.ivBalaodefala.visibility = GONE
                binding.tvNenhumaTarefaCriada.visibility = GONE
                binding.gvArrow.visibility = GONE
                binding.tvCliqueaqui.visibility = GONE
                binding.gvPandinhajogando.visibility = VISIBLE
            }
        }
    }


    private fun irParaTelaDeCriar() {
        binding.btFloatActionButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}