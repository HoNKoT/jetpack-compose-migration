package com.example.jetpackcomposemigration.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposemigration.models.binding.DataBindingModel
import com.example.jetpackcomposemigration.usecase.DataUseCase
import com.shopify.livedataktx.PublishLiveDataKtx
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val dataUseCase: DataUseCase
) : ViewModel() {

    private val _displayBindingModels = MutableLiveData<List<DataBindingModel>>()
    val displayBindingModels: LiveData<List<DataBindingModel>> = _displayBindingModels
    val onAddedModels = PublishLiveDataKtx<DataBindingModel>()
    val onRemovedModels = PublishLiveDataKtx<DataBindingModel>()

    fun initialize() = viewModelScope.launch {
        _displayBindingModels.value = dataUseCase.getBindingModels()
    }

    fun addNewModel() = viewModelScope.launch {
        onAddedModels.value = dataUseCase.generateNewModel()
    }

    fun removeModel(dataBindingModel: DataBindingModel) = viewModelScope.launch {
        dataUseCase.removeModel(dataBindingModel)
        onRemovedModels.value = dataBindingModel
    }
}
