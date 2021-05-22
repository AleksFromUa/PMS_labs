package ua.kpi.comsys.IO8120.feature_films.core.datasource

import com.github.michaelbull.result.Result
import ua.kpi.comsys.IO8120.feature_films.core.domain.model.Film

internal interface FilmDataSource {
    suspend fun getFilms(request: String): Result<List<Film>, Exception>
    suspend fun getFilm(id: String): Result<Film, Exception>
    suspend fun addFilm(film: Film): Result<Unit, Exception>
    suspend fun updateFilm(film: Film): Result<Unit, Exception>
    suspend fun removeFilm(film: Film): Result<Unit, Exception>
}