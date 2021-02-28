package ua.kpi.compsys.IO8120.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Павловець Олександр \n Група ІО-81 \n НЗК ІО-8120"
    }
    val text: LiveData<String> = _text
}