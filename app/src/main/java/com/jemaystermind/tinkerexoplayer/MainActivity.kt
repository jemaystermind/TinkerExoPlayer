package com.jemaystermind.tinkerexoplayer

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.android.exoplayer2.DefaultControlDispatcher
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.player
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    private lateinit var exoPlayer: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bandwidthMeter = DefaultBandwidthMeter()
        val agent = Util.getUserAgent(this, "TinkerExoPlayer")
//        val dataSourceFactory = DefaultDataSourceFactory(this, agent, bandwidthMeter)
        val httpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        val dataSourceFactory = OkHttpDataSourceFactory(httpClient, agent, bandwidthMeter)

        val uri = Uri.parse("https://cdn.arnellebalane.com/videos/original-video.mp4")
        val cfSampleUri =
            Uri.parse("https://d3heg6bx5jbtwp.cloudfront.net/video/enc/Tc7RwJjtXrsoCCuZyutbwd-360.webm")
        val source = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(cfSampleUri)

//        val clippingMediaSource = ClippingMediaSource(source, 2000, C.TIME_END_OF_SOURCE)


//        val bMapper = Function<A, B> { B(it.int) }
//        val (b, c) = A(1, "").
//        val mappedB = bMapper.apply(A(1, "ad"))
//        Function<> {  }
//
//        listOf<String>().map {  }

        val handler = Handler()
        val videoTrackSelectFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectFactory)

//        val a = A(1, "")
//        with(a) { bMapper.apply(this) }
//
//
//        val lists = listOf<Int>()
//
//        for (i in lists.indices) {
//
//        }

        // Prepare player!
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        player.player = exoPlayer
        exoPlayer.prepare(source)
        exoPlayer.playWhenReady = true

//        exo_overlay.visibility = View.GONE

        player.setControlDispatcher(object : DefaultControlDispatcher() {
            override fun dispatchSetPlayWhenReady(p: Player?, playWhenReady: Boolean): Boolean {
                Log.e("Main", "play=$playWhenReady")

//                player.overlay

                return super.dispatchSetPlayWhenReady(p, playWhenReady)
            }
        })

//        report.setOnClickListener {
//            Log.e("Report", "Report clicked!")
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}
