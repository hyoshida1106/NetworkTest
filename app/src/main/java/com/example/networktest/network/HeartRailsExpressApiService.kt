package com.example.networktest.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HeartRailsExpressApiService {
    // エリア情報取得
    @GET("json?method=getAreas")
    suspend fun getArea():
            HeartRailsExpressProperty<AreaResponse>

    // 都道府県情報取得
    @GET("json?method=getPrefectures")
    suspend fun getPrefectures(@Query("area") area: String):
            HeartRailsExpressProperty<PrefecturesResponse>

    //路線情報取得
    @GET("json?method=getLines")
    suspend fun getLines(@Query("area") area: String, @Query("prefecture") prefecture: String):
        HeartRailsExpressProperty<LinesResponse>

    //駅名情報取得
    @GET("json?method=getStations")
    suspend fun getStations(@Query("line") line: String):
        HeartRailsExpressProperty<StationsResponse>
}

object HeartRailsExpressApi {
    private const val BASE_URL = "https://express.heartrails.com/api/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService : HeartRailsExpressApiService by lazy {
        retrofit.create(HeartRailsExpressApiService::class.java) }
}