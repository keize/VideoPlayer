package com.example.videoapplication.model

import com.google.gson.annotations.SerializedName

public class VideoModel {

    @SerializedName("items")
    var items: List<Items>? = null


    class MyDefault {
        @SerializedName("url")
        var videoImage: String? = null
    }

    class Thumbnails {
        @SerializedName("medium")
        var mmedium: MyDefault? = null
    }

    class Snippet {
        @SerializedName("title")
        var videoTitle: String? = null

        @SerializedName("thumbnails")
        var thumbnails: Thumbnails? = null
    }

    class Id {
        @SerializedName("videoId")
        var videoId: String? = null
    }


    class Items {

        @SerializedName("id")
        var id: Id? = null

        @SerializedName("snippet")
        var snippet: Snippet? = null
    }
}