package com.example.test_lab_week_13

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Calendar

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    init {
        fetchPopularMovies()
    }

    // StateFlow untuk list film
    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    // StateFlow untuk error
    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
                .catch { exception ->
                    _error.value = "An exception occurred: ${exception.message}"
                }
                .collect { movies ->
                    // --- ASSIGNMENT LOGIC START ---
                    // 1. Ambil tahun saat ini
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

                    // 2. Lakukan Filter (Tahun ini) & Sorting (Popularitas Tertinggi)
                    val filteredAndSortedMovies = movies
                        .filter { movie ->
                            movie.releaseDate?.startsWith(currentYear) == true
                        }
                        .sortedByDescending { it.popularity }

                    // 3. Masukkan data yang sudah diproses ke StateFlow
                    _popularMovies.value = filteredAndSortedMovies
                    // --- ASSIGNMENT LOGIC END ---
                }
        }
    }
}