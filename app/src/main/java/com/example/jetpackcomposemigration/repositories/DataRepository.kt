package com.example.jetpackcomposemigration.repositories

import com.example.jetpackcomposemigration.dao.DataDao
import com.example.jetpackcomposemigration.models.local.Data
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val dataDao: DataDao
) {
    fun findAll(): List<Data> = dataDao.relation().selector().toList()
}
