package me.smoothhacker.swampsploit.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HostScannerViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is hostScanner Fragment"
    }
    val text: LiveData<String> = _text
}