package com.example.androiddevchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.logic.Repository
import com.example.androiddevchallenge.model.Dog
import com.example.androiddevchallenge.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * MainViewModel is the bridge to communicate between data layer and ui layer.
 *
 * @author Lin Guo
 * @since 2021/2/26
 */
class MainViewModel(private val repository: Repository) : ViewModel() {

    /**
     * The LiveData variable to observe Contact object list.
     */
    val dogsLiveData: LiveData<Resource<List<Dog>>>
        get() = _dogsLiveData

    private val _dogsLiveData = MutableLiveData<Resource<List<Dog>>>()

    /**
     * The handler to handle coroutine exceptions and notify to the observer.
     */
    private val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _dogsLiveData.value = Resource.error(throwable.message ?: "Uncaught exception happens")
    }

    /**
     * Start to read and parse the dogs and notify the result to the observer.
     */
    fun readAndParseData() = viewModelScope.launch(handler) {
        _dogsLiveData.value = Resource.loading()
        val dogs = repository.getDogs()
        _dogsLiveData.value = Resource.success(dogs)
    }

}