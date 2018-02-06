package com.jemaystermind.tinkerexoplayer

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.player

class MainActivity : AppCompatActivity() {

    lateinit var exoPlayer: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bandwidthMeter = DefaultBandwidthMeter()
        val agent = Util.getUserAgent(this, "TinkerExoPlayer")
        val dataSourceFactory = DefaultDataSourceFactory(this, agent, bandwidthMeter)

        val uri = Uri.parse("https://cdn.arnellebalane.com/videos/fragmented-video.mp4")
        val source = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)

        val handler = Handler()
        val videoTrackSelectFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectFactory)

        // Prepare player!
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        player.player = exoPlayer
        exoPlayer.prepare(source)
        exoPlayer.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}
