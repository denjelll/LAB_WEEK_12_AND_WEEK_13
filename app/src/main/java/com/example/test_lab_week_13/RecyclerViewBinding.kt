package com.example.test_lab_week_13

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test_lab_week_13.model.Movie

@BindingAdapter("list")
fun bindMovies(view: RecyclerView, movies: List<Movie>?) {
    // Mengambil adapter dari RecyclerView dan memastikannya bertipe MovieAdapter
    val adapter = view.adapter as? MovieAdapter

    // Jika adapter ada, update datanya
    // Jika movies null, kirim list kosong agar tidak error
    adapter?.addMovies(movies ?: emptyList())
}