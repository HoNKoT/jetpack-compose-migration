package com.example.jetpackcomposemigration.usecase

import com.example.jetpackcomposemigration.models.binding.DataBindingModel
import com.example.jetpackcomposemigration.repositories.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend fun getBindingModels(): List<DataBindingModel> = withContext(Dispatchers.IO) {
        return@withContext dataRepository.findAll().map { DataBindingModel(it) }
    }
}
