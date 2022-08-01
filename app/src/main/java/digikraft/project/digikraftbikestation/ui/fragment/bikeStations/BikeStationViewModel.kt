package digikraft.project.digikraftbikestation.ui.fragment.bikeStations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import digikraft.project.digikraftbikestation.data.BikeStations
import digikraft.project.digikraftbikestation.data.ResultWrapper
import digikraft.project.digikraftbikestation.repo.bikestationrepo.BikeStationRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class BikeStationViewModel @Inject constructor(private val bikeStationRepo: BikeStationRepo) : ViewModel(){


    private val _searchResult = MutableLiveData<ResultWrapper<BikeStations>>()
    val searchResult : LiveData<ResultWrapper<BikeStations>> = _searchResult


    fun getBikeStations(mtype: String, co: String) {
        viewModelScope.launch {
            bikeStationRepo
                .getBikeStation(mtype, co)
                .map {
                    // add item indexed number manulay
                    it.features.forEachIndexed { index, _ ->
                        it.features[index].count = index+1
                    }
                    it
                }
                .onStart {
                    _searchResult.postValue(ResultWrapper.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        _searchResult.postValue(ResultWrapper.Error(it))
                    }
                }
                .collect { _bikeList ->
                        _searchResult.postValue(ResultWrapper.Success(_bikeList))
                    }
                }
        }



}