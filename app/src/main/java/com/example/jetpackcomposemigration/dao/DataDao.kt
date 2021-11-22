package com.example.jetpackcomposemigration.dao

import com.example.jetpackcomposemigration.models.local.Data_Relation
import com.example.jetpackcomposemigration.models.local.OrmaDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataDao @Inject constructor(
    private val ormaDatabase: OrmaDatabase
) {
    fun relation(): Data_Relation = ormaDatabase.relationOfData()
}
