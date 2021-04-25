package ua.kpi.compsys.IO8120.ui.film

import com.fasterxml.jackson.annotation.JsonProperty


class Search {
    @JsonProperty("Title")
    var title: String? = null

    @JsonProperty("Year")
    var year: String? = null

    @JsonProperty("imdbID")
    var imdbID: String? = null

    @JsonProperty("Type")
    var type: String? = null

    @JsonProperty("Poster")
    var poster: String? = null
}

