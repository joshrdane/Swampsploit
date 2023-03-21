package me.smoothhacker.swampsploit.ui.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReportsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is reports Fragment"
    }
    val text: LiveData<String> = _text
}