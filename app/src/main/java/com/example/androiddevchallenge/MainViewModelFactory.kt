package com.example.androiddevchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androiddevchallenge.util.ServiceLocator

/**
 * The ViewModel Factory to create MainViewModel instance and pass a Repository instance as parameter
 * which provided by ServiceLocator.
 *
 * @author Lin Guo
 * @since 2022/2/26
 */
class MainViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(ServiceLocator.provideRepository()) as T
    }

}