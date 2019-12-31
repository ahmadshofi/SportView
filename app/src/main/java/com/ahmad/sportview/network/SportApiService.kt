package com.ahmad.sportview.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"
enum class SportApiFilter(val value: String) { SHOW_TEAMVSTEAM("TeamvsTeam"), SHOW_EVENSPORT("EventSport"), SHOW_ALL("all") }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface SportApiService {
    @GET("all_sports.php")
    fun getProperties():
            Deferred<SportResponse>
}

object SportApi{
    val retrofitService : SportApiService by lazy { retrofit.create(SportApiService::class.java) }
}