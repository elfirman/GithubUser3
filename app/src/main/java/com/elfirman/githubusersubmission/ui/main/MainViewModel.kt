package com.elfirman.githubusersubmission.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elfirman.githubusersubmission.data.Repository
import com.elfirman.githubusersubmission.data.Resource
import com.elfirman.githubusersubmission.data.remote.ApiService
import com.elfirman.githubusersubmission.data.remote.RetrofitService
import com.elfirman.githubusersubmission.model.SearchResponse
import com.elfirman.githubusersubmission.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun searchUser(query: String) = repository.searchUser(query)
}