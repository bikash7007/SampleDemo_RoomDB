package com.example.mysampleroomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Dialog
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysampleroomdb.adapters.EmployeeAdapter
import com.example.mysampleroomdb.async.GetAllEmployeesAsyncTask
import com.example.mysampleroomdb.databinding.ActivityMainBinding
import com.example.mysampleroomdb.db.AppDataBase
import com.example.mysampleroomdb.db.EmployeeDAO
import com.example.mysampleroomdb.models.Employee
import com.example.mysampleroomdb.ui.AddDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var employeeDAO: EmployeeDAO
    private lateinit var employeesList: MutableList<Employee>
    private lateinit var adapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = setContentView(this@MainActivity, R.layout.activity_main)
        binding.recyclerMain.layoutManager = LinearLayoutManager(this@MainActivity)

        setSupportActionBar(app_bar)

        employeeDAO = AppDataBase.getDatabase(this).getDAO()

        employeesList = mutableListOf()

        adapter = EmployeeAdapter(
            this, employeeDAO,
            employeesList
        )
        binding.recyclerMain.adapter = adapter

        GetAllEmployeesAsyncTask(this, employeeDAO).execute()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId

        if (id == R.id.action_add) {
            AddDialog.showDialog(employeeDAO, this, false, null,0)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun getAllEmployees(list: MutableList<Employee>) {
        employeesList.addAll(list)
        adapter.notifyDataSetChanged()
    }

    fun addEmployee(employee: Employee) {
        employeesList.add(employee)
        adapter.notifyDataSetChanged()
    }

    fun updateEmployee(employee: Employee,pos: Int) {
        employeesList.set(pos, employee)
        adapter.notifyDataSetChanged()
    }

    fun removeEmployee(employee: Employee) {
        employeesList.remove(employee)
        adapter.notifyDataSetChanged()
    }

}

