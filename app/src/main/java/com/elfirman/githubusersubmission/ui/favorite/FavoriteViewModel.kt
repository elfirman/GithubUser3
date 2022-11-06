package com.elfirman.githubusersubmission.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.elfirman.githubusersubmission.data.Repository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    suspend fun getFavoriteList() = repository.getFavoriteList()
}