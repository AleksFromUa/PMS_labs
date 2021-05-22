package ua.kpi.comsys.IO8120.feature_films.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.kpi.comsys.IO8120.feature_films.core.datasource.FilmDataSource
import ua.kpi.comsys.IO8120.feature_films.core.domain.model.Film

internal class FilmViewModel : ViewModel() {
    val state = MutableLiveData<State>(State.List)
    var ds: FilmDataSource? = null

    sealed class State {
        object List: State()
        data class ShowFilm(val film: Film): State()
        data class AddFilm(val film: Film? = null): State()
    }
}
