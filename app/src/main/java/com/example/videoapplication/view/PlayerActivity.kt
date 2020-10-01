package com.example.videoapplication.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.videoapplication.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {

    private val exoPlayer by lazy { ExoPlayerFactory.newSimpleInstance(this) }
    private var mediaSource: ExtractorMediaSource? = null
    private var videoCode = ""
    private var title = ""


    val NOTIFY_ID = 101
    val CHANNEL_ID = "Creating notifications"

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)


        videoCode = intent.getStringExtra("videoId")
        title = intent.getStringExtra("videoTitle")

        Log.d("videoCodeFromList", videoCode)

        connectWithExtractor()

        playerView.player = exoPlayer
        createNotificationChannel()


    }

    //create url for play and auto play video after opening activity
    private fun connectWithExtractor() {
        object : YouTubeExtractor(this!!) {
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
                val iTag = 18
                if (ytFiles?.get(iTag) != null && ytFiles?.get(iTag).url != null) {
                    val downloadUrl = ytFiles.get(iTag).url
                    val dataSourceFactory = DefaultDataSourceFactory(
                        this@PlayerActivity,
                        Util.getUserAgent(this@PlayerActivity, "cansu")
                    )
                    mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(downloadUrl))

                    exoPlayer.prepare(mediaSource!!, true, false)
                    exoPlayer.playWhenReady = true
                    addNotification()


                } else {
                    val toast =
                        Toast.makeText(
                            this@PlayerActivity,
                            "Can't play video, try again later",
                            Toast.LENGTH_LONG
                        )
                    toast.show()
                }
            }
        }.extract("https://www.youtube.com/watch?v=$videoCode", true, true)
    }


    override fun onPause() {
        super.onPause()
        if (exoPlayer != null)
            exoPlayer.playWhenReady = false


    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()

    }


    fun addNotification() {

        // Create PendingIntent

        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra("something", NOTIFY_ID)
        val pIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val mBuilder = NotificationCompat.Builder(this)
            .setContentTitle(title)
            .setLargeIcon(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.icon_logo
                    ), 200, 200, false
                )
            )
            .setSmallIcon(R.drawable.icon_logo)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(videoCode)
            )
            //  .addAction(getNotificationIcon(), "Action Button", pIntent)
            .setContentIntent(pIntent)
            .setContentText("Продолжайте смотреть видео")
            .setColor(resources.getColor(R.color.colorPrimary))
            .setOngoing(true)

        val intentx = Intent(this, PlayerActivity::class.java)
        intentx.putExtra("videoId", videoCode)
        intentx.putExtra("videoTitle", title)


        val notificationmanager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationmanager.notify(NOTIFY_ID, mBuilder.build())


    }


    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.app_name)
            val description = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(PlayerActivity().CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )!!
            notificationManager.createNotificationChannel(channel)
        }
    }


}



