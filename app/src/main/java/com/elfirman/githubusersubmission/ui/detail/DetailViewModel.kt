package com.elfirman.githubusersubmission.ui.detail

import android.app.Application
import androidx.lifecycle.*
import com.elfirman.githubusersubmission.data.Repository
import com.elfirman.githubusersubmission.data.Resource
import com.elfirman.githubusersubmission.data.remote.RetrofitService
import com.elfirman.githubusersubmission.model.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    val repository = Repository(application)

    suspend fun getDetailUser(username: String) = repository.getDetailUser(username)

    fun insertFavoriteUser(user: User) = viewModelScope.launch {
        repository.insertFavoriteUser(user)
    }

    fun deleteFavoriteUser(user: User) = viewModelScope.launch {
        repository.deleteFavoriteUser(user)
    }
}