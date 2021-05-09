package ua.kpi.compsys.IO8120.ui.film

import com.fasterxml.jackson.annotation.JsonProperty

class DetailedFilm {
    @JsonProperty("Title")
    var title: String? = null

    @JsonProperty("Year")
    var year: String? = null

    @JsonProperty("Rated")
    var rated: String? = null

    @JsonProperty("Released")
    var released: String? = null

    @JsonProperty("Runtime")
    var runtime: String? = null

    @JsonProperty("Genre")
    var genre: String? = null

    @JsonProperty("Director")
    var director: String? = null

    @JsonProperty("Actors")
    var actors: String? = null

    @JsonProperty("Plot")
    var plot: String? = null

    @JsonProperty("Country")
    var country: String? = null

    @JsonProperty("Awards")
    var awards: String? = null

    @JsonProperty("imdbRating")
    var imdbRating: String? = null

    @JsonProperty("imdbVotes")
    var imdbVotes: String? = null

    @JsonProperty("Production")
    var production: String? = null

    @JsonProperty("imdbID")
    var imdbID: String? = null

    @JsonProperty("Type")
    var type: String? = null

    @JsonProperty("Poster")
    var poster: String? = null

    @JsonProperty("Writer")
    var writer: String? = null

    @JsonProperty("Language")
    var language: String? = null
}