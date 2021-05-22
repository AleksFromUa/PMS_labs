package ua.kpi.comsys.IO8120.feature_films.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import ua.kpi.comsys.IO8120.core_ui.MainFragment
import ua.kpi.comsys.IO8120.feature_films.R
import ua.kpi.comsys.IO8120.feature_films.databinding.FragmentFilmBinding

internal class FilmFragment : MainFragment() {
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FilmViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(FilmViewModel::class.java)
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmId = arguments?.getString(BUNDLE_FILM_ID) ?: error("Film not provided")
        val film = viewModel.ds?.getFilm(filmId) ?: error("Film not found")
        val noData = getString(R.string.no_data)

        with(binding) {
            title.text = film.title
            poster.setImageDrawable(viewModel.ds?.getPoster(film) ?: ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_film_no_photo
            ))
            actors.text = getString(R.string.actors, film.actors ?: noData)
            if (film.rated == null) {
                rated.isVisible = false
            } else {
                rated.text = film.rated
            }

            year.text = getString(R.string.year, film.year)
            genre.text = getString(R.string.genre, film.genre ?: noData)
            released.text = getString(R.string.released, film.released ?: noData)
            imdbId.text = getString(R.string.imdbid, film.imdbID)
            imdbIdRating.text = getString(R.string.imdbrating, film.imdbRating ?: noData)
            imdbIdVotes.text = getString(R.string.imdbvotes, film.imdbVotes ?: noData)
            type.text = getString(R.string.type, film.type)
            runtime.text = getString(R.string.runtime, film.runtime ?: noData)
            director.text = getString(R.string.director, film.director ?: noData)
            writer.text = getString(R.string.writer, film.writer ?: noData)
            actors.text = getString(R.string.actors, film.actors ?: noData)
            plot.text = getString(R.string.plot, film.plot ?: noData)
            language.text = getString(R.string.language, film.language ?: noData)
            country.text = getString(R.string.country, film.country ?: noData)
            awards.text = getString(R.string.awards, film.awards ?: noData)
            production.text = getString(R.string.production, film.production ?: noData)
        }
    }

    override fun onBackPressed() {
        viewModel.state.value = FilmViewModel.State.List
        super.onBackPressed()
    }

    companion object {
        const val BUNDLE_FILM_ID = "BUNDLE_FILM_ID"
    }
}