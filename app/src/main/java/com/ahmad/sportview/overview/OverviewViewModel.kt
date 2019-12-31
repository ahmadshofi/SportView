package com.ahmad.sportview.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmad.sportview.network.SportApi
import com.ahmad.sportview.network.SportApiFilter
import com.ahmad.sportview.network.SportsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class SportApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel: ViewModel(){

    private val _respones = MutableLiveData<String>()

    val response:LiveData<String>
    get() = _respones

    private val _status = MutableLiveData<SportApiStatus>()
    val status: LiveData<SportApiStatus>
    get() = _status

    private val _properties = MutableLiveData<List<SportsItem?>?>()
    val properties: LiveData<List<SportsItem?>?>
    get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<SportsItem>()

    val navigateToSelectedProperty: LiveData<SportsItem>
        get() = _navigateToSelectedProperty

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getSportProperties(SportApiFilter.SHOW_ALL)
    }


    private fun getSportProperties(filter: SportApiFilter){
        coroutineScope.launch {
            val getPropertiesDeferred = SportApi.retrofitService.getProperties()
            try {
                _status.value = SportApiStatus.LOADING
                val listResult = getPropertiesDeferred.await()

                if (filter != SportApiFilter.SHOW_ALL) {
                    _properties.value = listResult.sports?.filter { sportsItem -> sportsItem?.strFormat == filter.value }
                }
                else {
                    _properties.value = listResult.sports
                }
                _status.value =SportApiStatus.DONE
                 listResult.sports
            }catch (e: Exception){
                _status.value = SportApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayPropertyDetails(sportProperty: SportsItem) {
        _navigateToSelectedProperty.value = sportProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun updateFilter(filter: SportApiFilter) {
        getSportProperties(filter)
    }
}