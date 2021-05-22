package ua.kpi.comsys.IO8120.feature_gallery.data.datasource.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import ua.kpi.comsys.IO8120.feature_gallery.core.domain.model.ImageCollection

internal interface PixabayApi {
    @GET("/api/")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("per_page") count: String,
        @Query("image_type") type: String = "photo",
    ): ImageCollection
}