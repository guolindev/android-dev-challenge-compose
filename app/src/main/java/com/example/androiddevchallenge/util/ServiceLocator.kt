package com.example.androiddevchallenge.util

import com.example.androiddevchallenge.logic.DataHelper
import com.example.androiddevchallenge.logic.Repository

/**
 * ServiceLocator to provide instances that no one is responsible to create.
 *
 * @author Lin Guo
 * @since 2020/2/26
 */
object ServiceLocator {

    /**
     * Provide the Repository instance that ViewModel should depend on.
     */
    fun provideRepository() = Repository(provideDataHelper())

    /**
     * Provide the DataHelper instance that Repository should depend on.
     */
    private fun provideDataHelper() = DataHelper()

}