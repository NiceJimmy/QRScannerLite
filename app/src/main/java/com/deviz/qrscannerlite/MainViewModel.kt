package com.deviz.qrscannerlite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _isScanSelected = MutableLiveData(true)
    val isScanSelected: LiveData<Boolean> = _isScanSelected

    private val _isGenerateSelected = MutableLiveData(false)
    val isGenerateSelected: LiveData<Boolean> = _isGenerateSelected

    fun onScanClick() {
        _isScanSelected.value = true
        _isGenerateSelected.value = false
    }

    fun onGenerateClick() {
        _isScanSelected.value = false
        _isGenerateSelected.value = true
    }
}