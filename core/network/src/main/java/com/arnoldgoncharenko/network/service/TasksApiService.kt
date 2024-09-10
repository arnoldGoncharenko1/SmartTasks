package com.arnoldgoncharenko.network.service

import com.arnoldgoncharenko.network.models.TaskList
import retrofit2.http.GET
import retrofit2.http.Url

interface TasksApiService {
    @GET("http://demo1414406.mockable.io/")
    suspend fun getTasks(): TaskList
}