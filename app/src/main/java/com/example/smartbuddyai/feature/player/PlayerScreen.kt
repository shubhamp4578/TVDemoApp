package com.example.smartbuddyai.feature.player

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.tv.material3.Text
import com.example.smartbuddyai.data.model.VideoProgress
import com.example.smartbuddyai.data.model.VideoSource
import com.example.smartbuddyai.feature.player.components.PlayerControls
import kotlinx.coroutines.delay

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    videoId: String,
    onVideoEnd: (String) -> Unit
) {
    val context = LocalContext.current
    val viewModel: PlayerViewModel = viewModel(
        factory = PlayerViewModelFactory()
    )

    var isPlaying by remember { mutableStateOf(true) }
    var showControls by remember { mutableStateOf(true) }
    var currentPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    val videoFocusRequester = remember { FocusRequester() }
    var savedProgress by remember { mutableStateOf<VideoProgress?>(null) }
    var isUserSeeking by remember { mutableStateOf(false) }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }



    LaunchedEffect(videoId) {
        viewModel.loadVideo(videoId)
        savedProgress = viewModel.getProgress(videoId)
    }
    val video = viewModel.video
    val videoUrl = video?.videoSource?.let {
        (it as? VideoSource.Remote)?.url
    } ?: ""

    LaunchedEffect(videoUrl) {
        if (videoUrl.isNotEmpty()){
            exoPlayer.setMediaItem(MediaItem.fromUri(videoUrl))
            exoPlayer.prepare()
        }
    }

    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
    }

    LaunchedEffect(exoPlayer) {
        exoPlayer.let { player ->
            player.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        onVideoEnd(videoId)
                    }
                }
            })
            while (true) {
                currentPosition = player.currentPosition
                duration = if (player.duration == C.TIME_UNSET) 0 else player.duration
                if (player.isPlaying || isUserSeeking) {
                    viewModel.saveProgress(
                        VideoProgress(
                            videoId = videoId,
                            position = player.currentPosition,
                            duration = player.duration
                        )
                    )
                    isUserSeeking = false
                }
                delay(2000)
            }
        }
    }

    LaunchedEffect(exoPlayer, savedProgress) {
        if (savedProgress != null) {
            exoPlayer.seekTo(savedProgress!!.position)
        }
        exoPlayer.playWhenReady = true
    }

    DisposableEffect((exoPlayer)) {
        onDispose {
            exoPlayer.apply {
                playWhenReady = false
                pause()
                release()
            }
        }
    }
    LaunchedEffect(Unit) {
        videoFocusRequester.requestFocus()
    }


    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .focusRequester(videoFocusRequester)
                .focusable()
                .onKeyEvent {
                    if (it.type == KeyEventType.KeyDown) {
                        if (it.key == Key.Enter || it.key == Key.DirectionCenter) {
                            showControls = true
                        }
                        when (it.key) {
                            Key.Enter, Key.DirectionCenter -> {
                                isPlaying = !isPlaying
                                true
                            }

                            Key.DirectionRight -> {
                                exoPlayer.seekTo(exoPlayer.currentPosition.plus(10000))
                                showControls = true
                                isUserSeeking = true
                                true
                            }

                            Key.DirectionLeft -> {
                                isUserSeeking = true
                                exoPlayer.seekTo(
                                    (exoPlayer.currentPosition.minus(10000)).coerceAtLeast(0)
                                )
                                showControls = true
                                true
                            }

                            Key.Back -> {
                                false
                            }

                            else -> false
                        }
                    } else false
                }
        ) {
            if (videoUrl.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Select a video from Home ", color = Color.White)
                }
            } else {
                exoPlayer.let { player ->
                    AndroidView(
                        factory = {
                            PlayerView(it).apply {
                                this.player = player
                                layoutParams =
                                    ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                                useController = false
                                controllerAutoShow = false

                                setShutterBackgroundColor(
                                    android.graphics.Color.BLACK
                                )
                                resizeMode =
                                    androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                            }
                        },
                        modifier = Modifier.fillMaxSize(),
                        update = {
                            it.player = exoPlayer
                        }
                    )
                    LaunchedEffect(showControls) {
                        if (showControls) {
                            delay(3000)
                            showControls = false
                        }
                    }
                    if (showControls) {
                        PlayerControls(
                            isPlaying = isPlaying,
                            currentPosition = currentPosition,
                            duration = duration,
                            onPlayPause = {
                                isPlaying = !isPlaying
                            }
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xAA000000)
                            )
                        )
                    )
            )
        }
    }
}
