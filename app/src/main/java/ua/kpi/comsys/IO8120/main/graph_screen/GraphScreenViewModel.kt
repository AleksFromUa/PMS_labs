package ua.kpi.comsys.IO8120.main.graph_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GraphScreenViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is chart Fragment"
    }
    val text: LiveData<String> = _text
}
