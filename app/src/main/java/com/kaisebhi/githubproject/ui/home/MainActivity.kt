package com.kaisebhi.githubproject.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kaisebhi.githubproject.R
import com.kaisebhi.githubproject.adapter.GithubRepoAdapter
import com.kaisebhi.githubproject.databinding.ActivityMainBinding
import com.kaisebhi.githubproject.utils.ApplicationClass
import com.kaisebhi.githubproject.utils.ResponseHandler

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val TAG = "MainActivity.kt"
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val app = application as ApplicationClass
        viewModel =
            ViewModelProvider(this, MainViewModelFactory(app.mainRepo))[MainViewModel::class.java]
        viewModel.getRepo("Dushyant-sharma")

        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.addRepoBtn.setOnClickListener(View.OnClickListener {
            val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null)
            val bottomSheetDialog = BottomSheetDialog(this@MainActivity)
            bottomSheetDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            bottomSheetDialog.setContentView(view)
            val addBtn = view.findViewById<CardView>(R.id.addCard)
            val ownerEdit = view.findViewById<EditText>(R.id.ownerEdit)
            val repoEdit = view.findViewById<EditText>(R.id.repoEdit)
            val bar = view.findViewById<ProgressBar>(R.id.bar)
            val addTv = view.findViewById<TextView>(R.id.addTv)
            addBtn.setOnClickListener(View.OnClickListener {
                bar.visibility = View.VISIBLE
                addTv.visibility = View.INVISIBLE
                if (!ownerEdit.text.isNullOrEmpty() && !repoEdit.text.isNullOrEmpty()) {
                    viewModel.addRepo(ownerEdit.text.toString(), repoEdit.text.toString())
                    Log.d(TAG, "setListeners: ${repoEdit.text.toString()}")
                    bottomSheetDialog.dismiss()
                } else {
                    bar.visibility = View.GONE
                    addTv.visibility = View.VISIBLE
                    Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
                }
            })

            bottomSheetDialog.show()
        })
    }

    private fun setObservers() {
        viewModel.liveData.observe(this@MainActivity, Observer {
            when (it) {
                is ResponseHandler.Success -> {
                    if (it.responseList?.size == 0) {
                        Log.d(TAG, "setObservers1: ${it.responseList?.size}")
                        binding.noDataLabel.setVisibility(View.VISIBLE)
                        binding.recyclerView.setVisibility(View.GONE)
                    } else {
                        Log.d(TAG, "setObservers2: ${it.responseList?.size}")
                        binding.noDataLabel.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        val adapter = GithubRepoAdapter(this@MainActivity, viewModel)
                        adapter.submitList(it.responseList)
                        binding.recyclerView.adapter = adapter
                        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                    Log.d(TAG, "setObservers: ${it.responseList.toString()}")
                }
                is ResponseHandler.Error -> {
                    binding.noDataLabel.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    Toast.makeText(this, "${it.errorMessage}", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.d(TAG, "setObservers: error")
                }
            }
        })
    }
}