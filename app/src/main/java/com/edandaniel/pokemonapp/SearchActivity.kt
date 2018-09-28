package com.edandaniel.pokemonapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.edandaniel.pokemonapp.api.PokemonAPI
import com.edandaniel.pokemonapp.model.Pokemon
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        btSearch.setOnClickListener{
            val okhttp = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://pokeapi.co")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp)
                .build()

            val api = retrofit.create(PokemonAPI::class.java)

            api.getPokemonBy(etID.text.toString().toInt())
                .enqueue(object : Callback<Pokemon>{
                    override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {
                        Toast.makeText(this@SearchActivity,
                                t?.message,
                                Toast.LENGTH_LONG)
                    }

                    override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {
                        if(response?.isSuccessful == true){
                            val pokemon = response.body()
                            tvPokemon.text = pokemon?.name

                            Picasso.get()
                                    .load(pokemon?.sprites?.frontDefault)
                                    .placeholder(R.drawable.pikachu_loading)
                                    .error(R.drawable.missingno)
                                    .into(ivPokemon)
                        }else{
                            Toast.makeText(this@SearchActivity,
                                    "Oops, could not connect to the PokéServer",
                                    Toast.LENGTH_LONG)
                        }
                    }

                })
        }
    }
}
