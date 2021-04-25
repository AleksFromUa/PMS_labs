package ua.kpi.compsys.IO8120.ui.film

import com.fasterxml.jackson.annotation.JsonProperty

class SearchContainer {
    @JsonProperty("Search")
    var search: MutableList<Search> = ArrayList()
}

