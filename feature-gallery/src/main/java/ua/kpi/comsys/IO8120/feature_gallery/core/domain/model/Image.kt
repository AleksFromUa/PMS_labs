package ua.kpi.comsys.IO8120.feature_gallery.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val largeImageURL: String
)

@Serializable
data class ImageCollection(
    val hits: List<Image>
)
