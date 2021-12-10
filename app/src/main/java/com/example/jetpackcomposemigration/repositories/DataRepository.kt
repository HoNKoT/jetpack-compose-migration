package com.example.jetpackcomposemigration.repositories

import com.example.jetpackcomposemigration.dao.DataDao
import com.example.jetpackcomposemigration.models.local.Data
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val dataDao: DataDao
) {
    fun store(
        remoteId: Long,
        message: String? = null,
        imageThumbnail: String
    ): Data {
        val data = Data().also {
            it.remoteId = remoteId
            it.message = message ?: Integer.toHexString(it.hashCode())
            it.imageThumbnail = imageThumbnail
        }
        val id = dataDao.relation().inserter().execute(data)
        return data.also { it.localId = id }
    }

    fun findAll(): List<Data> = dataDao.relation().selector().toList()

    fun hasAny(): Boolean = !dataDao.relation().isEmpty

    fun removeById(id: Long) = dataDao.relation().localIdEq(id).deleter().execute()
}
