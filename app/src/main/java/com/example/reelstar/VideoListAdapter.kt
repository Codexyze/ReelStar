package com.example.reelstar

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reelstar.databinding.VideoItemRowBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

//class VideoListAdapter(options: FirestoreRecyclerOptions<VideoModel>) : FirestoreRecyclerAdapter<VideoModel, VideoListAdapter.VideViewHolder>(options) {
//
//    inner class VideViewHolder(private val binding: VideoItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bindVideo(videoModel: VideoModel) {
//            binding.videoView.apply {
//                setVideoPath(videoModel.url)
//                setOnPreparedListener { it ->
//                    it.start()
//                    it.isLooping = true
//                }
//                setOnClickListener {
//                    if (isPlaying) {
//                        pause()
//                        binding.pauseIcon.visibility = View.VISIBLE
//                    } else {
//                        start() // Changed from resume() to start()
//                        binding.pauseIcon.visibility = View.GONE
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideViewHolder {
//        val binding = VideoItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return VideViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: VideViewHolder, position: Int, model: VideoModel) {
//        holder.bindVideo(model)
//    }
//}
class VideoListAdapter(options: FirestoreRecyclerOptions<VideoModel>) :
    FirestoreRecyclerAdapter<VideoModel, VideoListAdapter.VideoViewHolder>(options) {

    inner class VideoViewHolder(private val binding: VideoItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindVideo(videoModel: VideoModel) {
            val videoUri = Uri.parse(videoModel.url)
            binding.videoView.setVideoURI(videoUri)
            binding.videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.start()
            }
            binding.videoView.setOnErrorListener { _, what, extra ->
                Log.e("VideoListAdapter", "Error playing video: what=$what, extra=$extra")
                true // Return true if error is handled
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideoItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, model: VideoModel) {
        holder.bindVideo(model)
    }
}

