package me.smoothhacker.swampsploit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }

    private val _librarySize = MutableLiveData<String>().apply {
        value = "4 Exploits are loaded in the library"
    }

    private val _payloadSize = MutableLiveData<String>().apply {
        value = "3 payloads are loaded in the library"
    }

    private val _registeredTargets = MutableLiveData<String>().apply {
        value = "12 registered targets are available for possible exploitation"
    }

    private val _pastAttempts = MutableLiveData<String>().apply {
        value = "6 past attempts at exploitation in this engagement"
    }

    val text: LiveData<String> = _text
    val librarySize: LiveData<String> = _librarySize
    val payloadSize: LiveData<String> = _payloadSize
    val registeredTargets: LiveData<String> = _registeredTargets
    val pastAttempts: LiveData<String> = _pastAttempts
}
