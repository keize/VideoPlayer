package com.example.videoapplication.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoapplication.R
import com.example.videoapplication.YouTubePlayListAdapter
import com.example.videoapplication.model.VideoModel
import com.example.videoapplication.network.ApiClient
import com.example.videoapplication.network.ApiInterface
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    val API_KEY = "AIzaSyD-cAVJIENVA9tqmoB-DQb44DdU6AxQrg0"
    val CHANNEL_ID = "UC101o-vQ2iOj9vr00JUlyKw"

    private val MAX_RESULTS: Int = 10

    var data: List<VideoModel.Items>? = null
    var adapter: YouTubePlayListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val apiInterface = ApiClient?.getRestEngine().create(ApiInterface::class.java)
        val apiCall = apiInterface?.getList(CHANNEL_ID, API_KEY, MAX_RESULTS)


        apiCall?.enqueue(object : Callback<VideoModel> {
            override fun onFailure(call: Call<VideoModel>, t: Throwable) {

                Log.e("error", t.toString())

            }

            override fun onResponse(call: Call<VideoModel>, response: Response<VideoModel>) {

                Log.e("playlist", response.message())

                data = response.body()?.items
                adapter = YouTubePlayListAdapter(data)
                recyclerView.adapter = adapter;
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)


            }


        })
    }


}

