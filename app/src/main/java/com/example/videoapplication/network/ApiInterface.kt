package com.example.videoapplication.network

import com.example.videoapplication.model.VideoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    // https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCXoJ8kY9zpLBEz-8saaT3ew&maxResults=10&key=AIzaSyCxlRAhM1tlNVJ5GXTd4ofqm9mFMOtPVow

    //get request with urlkeys

    @GET("search?part=snippet")
    fun getList(
        @Query("channelId") channelID: String,
        @Query("key") apiKey: String,
        @Query("maxResults") limit: Int
    )
            : Call<VideoModel>
}

