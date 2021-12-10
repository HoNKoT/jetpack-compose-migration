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
//        if (!dataRepository.hasAny()) initializeData()
        return@withContext dataRepository.findAll().map {
            DataBindingModel(
                id = it.localId,
                message = it.message,
                thumbnail = it.imageThumbnail
            )
        }
    }

//    private fun initializeData() {
//        (0..SAMPLE_COUNT).forEachIndexed { index, _ ->
//            dataRepository.store(
//                remoteId = index + 1L,
//                imageThumbnail = IMAGE_UTA_URL
//            )
//        }
//    }

    suspend fun generateNewModel(): DataBindingModel = withContext(Dispatchers.IO) {
        return@withContext dataRepository.store(
            remoteId = 200,
            imageThumbnail = IMAGE_UTA_URL
        ).let {
            DataBindingModel(
                id = it.localId,
                message = it.message,
                thumbnail = it.imageThumbnail
            )
        }
    }

    suspend fun removeModel(dataBindingModel: DataBindingModel) = withContext(Dispatchers.IO) {
        dataRepository.removeById(dataBindingModel.id)
    }

    companion object {
        private const val SAMPLE_COUNT = 100
        private const val IMAGE_UTA_URL = "https://github.com/SenriTaro.png"
    }
}
