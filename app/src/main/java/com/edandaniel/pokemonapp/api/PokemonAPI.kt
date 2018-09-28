package com.edandaniel.pokemonapp.api

import com.edandaniel.pokemonapp.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI{
    @GET("/api/v2/pokemon/{id}")
    fun getPokemonBy(@Path("id") PokemonId: Int):Call<Pokemon>
}