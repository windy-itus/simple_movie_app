package com.example.simple_movie_app.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_details")
data class MovieDetails(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @Embedded(prefix = "collection_")
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Collection?,

    @SerializedName("budget")
    val budget: Long,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("imdb_id")
    val imdbId: String?,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("revenue")
    val revenue: Long,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,

    @SerializedName("status")
    val status: String,

    @SerializedName("tagline")
    val tagline: String?,

    @SerializedName("title")
    val title: String,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "cached_at")
    val cachedAt: Long = System.currentTimeMillis()
)

data class Collection(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?
)

data class Genre(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)

data class ProductionCompany(
    @SerializedName("id")
    val id: Int,

    @SerializedName("logo_path")
    val logoPath: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("origin_country")
    val originCountry: String
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val isoCode: String,

    @SerializedName("name")
    val name: String
)

data class SpokenLanguage(
    @SerializedName("iso_639_1")
    val isoCode: String,

    @SerializedName("name")
    val name: String
)
