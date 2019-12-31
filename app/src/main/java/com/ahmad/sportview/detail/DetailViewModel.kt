package com.ahmad.sportview.detail

import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ahmad.sportview.R
import com.ahmad.sportview.network.SportsItem

class DetailViewModel (sportProperty: SportsItem, app: Application) : AndroidViewModel(app){
    private val _selectedProperty = MutableLiveData<SportsItem>()
    val selectedProperty: LiveData<SportsItem>
        get() = _selectedProperty

    init {
        _selectedProperty.value = sportProperty
    }

    val displayPropertyType = Transformations.map(selectedProperty){
        app.applicationContext.getString(
            if (it.strFormat.equals("TeamvsTeam")){
                R.string.type_TeamvsTeam
            }
            else {
                R.string.type_EventSport
            }
        )
    }
}