package ru.easycode.zerotoheroandroidtdd.data.cloud

import retrofit2.http.GET
import retrofit2.http.Url

interface SimpleService {

    @GET
    suspend fun fetch(@Url url : String) : SimpleResponse
}