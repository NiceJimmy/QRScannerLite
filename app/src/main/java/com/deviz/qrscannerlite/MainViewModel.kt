package com.deviz.qrscannerlite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _isToggleBtnSelected = MutableLiveData(true)
    val isToggleBtnSelected: LiveData<Boolean> = _isToggleBtnSelected

    fun onScanClick() {
        _isToggleBtnSelected.value = true
    }

    fun onGenerateClick() {
        _isToggleBtnSelected.value = false
    }
}