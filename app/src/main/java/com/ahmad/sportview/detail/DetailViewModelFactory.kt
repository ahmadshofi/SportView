package com.ahmad.sportview.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmad.sportview.network.SportsItem

class DetailViewModelFactory (
    private val sportProperty : SportsItem,
    private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(sportProperty,application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
