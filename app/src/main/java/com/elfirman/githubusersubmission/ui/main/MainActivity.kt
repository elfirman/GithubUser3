package com.elfirman.githubusersubmission.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.elfirman.githubusersubmission.R
import com.elfirman.githubusersubmission.data.Resource
import com.elfirman.githubusersubmission.databinding.ActivityMainBinding
import com.elfirman.githubusersubmission.model.User
import com.elfirman.githubusersubmission.ui.adapter.UserAdapter
import com.elfirman.githubusersubmission.ui.favorite.FavoriteActivity
import com.elfirman.githubusersubmission.ui.setting.SettingsActivity
import com.elfirman.githubusersubmission.util.ViewStateCallback


class MainActivity : AppCompatActivity(), ViewStateCallback<List<User>> {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userQuery: String
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        userAdapter = UserAdapter()
        mainBinding.includeMainSearch.rvListUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        mainBinding.searchView.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userQuery = query.toString()
                    clearFocus()
                    viewModel.searchUser(userQuery).observe(this@MainActivity, {
                        when (it) {
                            is Resource.Error -> onFailed(it.message)
                            is Resource.Loading -> onLoading()
                            is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                        }
                    })
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }
            R.id.menu_favorite -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }

    override fun onSuccess(data: List<User>) {
        userAdapter.setAllData(data)
        mainBinding.includeMainSearch.apply {
            ivSearchIcon.visibility = invisible
            tvMessage.visibility = invisible
            mainProgressBar.visibility = invisible
            rvListUser.visibility = visible
        }
    }

    override fun onLoading() {
        mainBinding.includeMainSearch.apply {
            ivSearchIcon.visibility = invisible
            tvMessage.visibility = invisible
            mainProgressBar.visibility = visible
            rvListUser.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        mainBinding.includeMainSearch.apply {
            //User yang dicari tidak ditemukan
            if (message == null) {
                ivSearchIcon.apply {
                    setImageResource(R.drawable.ic_search_empty)
                    visibility = visible
                }
                tvMessage.apply {
                    text = resources.getString(R.string.user_not_found)
                    visibility = visible
                }
                //Memunculkan error pada exception
            } else {
                ivSearchIcon.apply {
                    setImageResource(R.drawable.ic_search_failed)
                    visibility = visible
                }
                tvMessage.apply {
                    text = message
                    visibility = visible
                }
            }
            mainProgressBar.visibility = invisible
            rvListUser.visibility = invisible
        }
    }
}