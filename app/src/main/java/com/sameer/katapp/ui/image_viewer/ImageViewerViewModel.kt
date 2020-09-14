package com.sameer.katapp.ui.image_viewer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sameer.katapp.repository.CatRepository
import kotlinx.coroutines.launch
import com.sameer.katapp.data.Result

class ImageViewerViewModel @ViewModelInject constructor(val catRepository: CatRepository): ViewModel() {

    private val _catResult = MutableLiveData<CatImageResult>()
    val catResult: LiveData<CatImageResult> = _catResult

    fun getNewImage() {
        viewModelScope.launch {
            val result = catRepository.getRandomCatImage()
            if (result is Result.Success){
                _catResult.value = CatImageResult(imageUrl = result.data.imageUrl)
            }
        }
    }
}