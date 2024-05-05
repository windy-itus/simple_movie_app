package com.example.simple_movie_app.data.database.converter

import androidx.room.TypeConverter
import com.example.simple_movie_app.data.database.entity.Genre
import com.example.simple_movie_app.data.database.entity.Movie
import com.example.simple_movie_app.data.database.entity.ProductionCompany
import com.example.simple_movie_app.data.database.entity.ProductionCountry
import com.example.simple_movie_app.data.database.entity.SpokenLanguage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromMovieList(movies: List<Movie>?): String? {
        if (movies == null) return null
        val type = object : TypeToken<List<Movie>>() {}.type
        return gson.toJson(movies, type)
    }

    @TypeConverter
    fun toMovieList(movieString: String?): List<Movie>? {
        if (movieString == null) return null
        val type = object : TypeToken<List<Movie>>() {}.type
        return gson.fromJson(movieString, type)
    }

    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String? {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun toGenreList(genreJson: String?): List<Genre>? {
        return genreJson?.let {
            val type = object : TypeToken<List<Genre>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromProductionCompanyList(companies: List<ProductionCompany>?): String? {
        return gson.toJson(companies)
    }

    @TypeConverter
    fun toProductionCompanyList(companyJson: String?): List<ProductionCompany>? {
        return companyJson?.let {
            val type = object : TypeToken<List<ProductionCompany>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromSpokenLanguageList(languages: List<SpokenLanguage>?): String? {
        return gson.toJson(languages)
    }

    @TypeConverter
    fun toSpokenLanguageList(languageJson: String?): List<SpokenLanguage>? {
        return languageJson?.let {
            val type = object : TypeToken<List<SpokenLanguage>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromProductionCountryList(countries: List<ProductionCountry>?): String? {
        return gson.toJson(countries)
    }

    @TypeConverter
    fun toProductionCountryList(countryJson: String?): List<ProductionCountry>? {
        return countryJson?.let {
            val type = object : TypeToken<List<ProductionCountry>>() {}.type
            gson.fromJson(it, type)
        }
    }
}