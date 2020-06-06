package com.example.mysampleroomdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mysampleroomdb.R
import com.example.mysampleroomdb.async.DeleteEmployeeAsyncTask
import com.example.mysampleroomdb.databinding.EmployeeRowBinding
import com.example.mysampleroomdb.db.EmployeeDAO
import com.example.mysampleroomdb.models.Employee
import com.example.mysampleroomdb.ui.AddDialog

class EmployeeAdapter(private val context: Context, private val employeeDAO: EmployeeDAO, private val list: MutableList<Employee>?) : RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding: EmployeeRowBinding = DataBindingUtil.inflate(inflater, R.layout.employee_row,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if(list == null){
            return 0
        }
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val getEmployee = list?.get(position)
        holder.itemBinding.employee = getEmployee

        holder.itemBinding.imgEdit.setOnClickListener {
            AddDialog.showDialog(employeeDAO,context,true,getEmployee,position)
        }

        holder.itemBinding.imgDelete.setOnClickListener {
            getEmployee?.let { it1 -> DeleteEmployeeAsyncTask(context,employeeDAO, it1).execute() }
        }

    }

    class MyViewHolder(val itemBinding: EmployeeRowBinding) : RecyclerView.ViewHolder(itemBinding.root){

        private var binding : EmployeeRowBinding? = null

        init {
            this.binding = itemBinding
        }

    }

}