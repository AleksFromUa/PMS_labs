package ua.kpi.comsys.IO8120.main.navigation_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    val state = MutableLiveData(State.Info)

    enum class State {
        Info, Graph, Films, Gallery
    }
}
