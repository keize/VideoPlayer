package com.example.videoapplication

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoapplication.model.VideoModel
import com.example.videoapplication.view.PlayerActivity
import kotlinx.android.synthetic.main.video_item.view.*


class YouTubePlayListAdapter(var videoList: List<VideoModel.Items>?) :
    RecyclerView.Adapter<YouTubePlayListAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        //set data to views

        var textViewPlayListTitle = itemView.itemTitle
        var imagePlayList = itemView.itemImage
        var itemVideoId = itemView.itemVideoId


        fun setData(play: VideoModel.Items?, position: Int) {


            // play?.snippet?.thumbnails?.medium.url - my way in JSON model

            textViewPlayListTitle.text = play?.snippet?.videoTitle
            itemVideoId.text = play?.id?.videoId
            val videoId = itemVideoId.text.toString()

            Glide
                .with(itemView.context)
                .load(play?.snippet?.thumbnails?.mmedium?.videoImage)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(imagePlayList)


            itemView.setOnClickListener {

                // send videoURLIntent + open new activity

                val intent = Intent(itemView.context, PlayerActivity::class.java)
                val videoId = itemVideoId.text.toString()
                val titleTextForNot = textViewPlayListTitle.text.toString()

                intent.putExtra("videoId", videoId)
                intent.putExtra("videoTitle", titleTextForNot)

                itemView.context.startActivity(intent)
                Log.d("clicksend", videoId)  // check my intentsend info

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // set item layout

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //set data
        val play = videoList?.get(position)
        holder.setData(play, position)
    }

    override fun getItemCount(): Int {

        return videoList?.size!!

    }
}

