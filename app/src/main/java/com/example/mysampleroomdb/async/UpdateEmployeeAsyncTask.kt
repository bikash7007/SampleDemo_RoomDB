package com.example.mysampleroomdb.async

import android.app.Dialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.mysampleroomdb.MainActivity
import com.example.mysampleroomdb.db.EmployeeDAO
import com.example.mysampleroomdb.models.Employee

class UpdateEmployeeAsyncTask(var context: Context, var empDao: EmployeeDAO, var employee: Employee, var pos: Int, val dialog: Dialog) :
    AsyncTask<Void, Void?, Boolean?>() {

    override fun doInBackground(vararg params: Void?): Boolean {
        empDao.updateEmployee(employee)
        return true
    }

    override fun onPostExecute(result: Boolean?) {
        if (result!!) {
            Toast.makeText(context, "Updated to Database", Toast.LENGTH_LONG).show()
            val activity = context as MainActivity
            activity.updateEmployee(employee,pos)
            dialog.dismiss()
        }
    }

}