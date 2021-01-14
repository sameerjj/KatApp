package com.sameer.katapp.ui.image_viewer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sameer.katapp.data.Resource
import com.sameer.katapp.model.CatImage
import com.sameer.katapp.repository.CatRepository
import kotlinx.coroutines.launch

class ImageViewerViewModel @ViewModelInject constructor(val catRepository: CatRepository): ViewModel() {

    private val _catResult = MutableLiveData<Resource<CatImage>> ()
    val catResult: LiveData<Resource<CatImage>> = _catResult

    fun getNewImage() {
        viewModelScope.launch {
            _catResult.value = catRepository.getRandomCatImage()
        }
    }
}