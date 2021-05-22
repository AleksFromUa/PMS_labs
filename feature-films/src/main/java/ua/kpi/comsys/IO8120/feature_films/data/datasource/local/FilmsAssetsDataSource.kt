package ua.kpi.comsys.IO8120.feature_films.data.datasource.local

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ua.kpi.comsys.IO8120.feature_films.core.datasource.FilmDataSource
import ua.kpi.comsys.IO8120.feature_films.core.domain.model.Film
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.charset.Charset

/*internal class FilmsAssetsDataSource(
    private val assetManager: AssetManager,
    private val fileName: String = FILMS_FILE
) : FilmDataSource {
    private val assetsFilms by lazy {
        val text = assetManager.open(fileName)
            .bufferedReader(Charset.defaultCharset())
            .readText()
        Json.decodeFromString<FilmData>(text).data
    }
    private val localFilms = mutableListOf<Film>()
    private val localRemoved = mutableListOf<Film>()

    override fun getFilms() = assetsFilms + localFilms - localRemoved

    override fun getPoster(film: Film): Drawable? {
        if (film.poster.isBlank()) return null
        return try {
            Drawable.createFromStream(
                assetManager.open(film.poster),
                null
            )
        } catch (e: IOException) {
            return null
        }
    }

    override fun getFilm(id: String): Film? = try {
        val text = assetManager.open("$id.txt")
            .bufferedReader(Charset.defaultCharset())
            .readText()

        Json.decodeFromString(text)
    } catch (e: FileNotFoundException) {
        getFilms().find { it.imdbID == id }
    }

    override fun addFilm(film: Film) {
        localFilms.add(film.copy(imdbID = "local" + localFilms.size))
    }

    override fun removeFilm(film: Film) {
        localRemoved.add(film)
    }

    @Serializable
    private data class FilmData(@SerialName("Search") val data: List<Film>)

    companion object {
        private const val FILMS_FILE = "MoviesList.txt"
    }
}*/
