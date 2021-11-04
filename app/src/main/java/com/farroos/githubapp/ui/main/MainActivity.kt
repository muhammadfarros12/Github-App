package com.farroos.githubapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farroos.githubapp.data.model.User
import com.farroos.githubapp.databinding.ActivityMainBinding
import com.farroos.githubapp.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

        showRecyclerView()

        binding.apply {
            btnSearch.setOnClickListener {
                searchUser()
            }

            edtQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // command
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

        }

        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

    }

    private fun showRecyclerView() {
        binding.rcvUser.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rcvUser.adapter = adapter
        binding.rcvUser.setHasFixedSize(true)
    }

    private fun searchUser() {
        binding.apply {
            val query = edtQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUser(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}