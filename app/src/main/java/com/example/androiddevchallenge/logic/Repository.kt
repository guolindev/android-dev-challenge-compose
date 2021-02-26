package com.example.androiddevchallenge.logic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository to communicate with MainViewModel and DataHelper.
 *
 * @author Lin Guo
 * @since 2021/2/26
 */
class Repository(private val dataHelper: DataHelper) {

    /**
     * Get the dogs by DataHelper and return them.
     */
    suspend fun getDogs() = withContext(Dispatchers.IO) {
        dataHelper.getDogs()
    }

}