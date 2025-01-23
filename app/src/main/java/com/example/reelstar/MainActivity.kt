package com.example.reelstar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reelstar.databinding.ActivityLoginBinding
import com.example.reelstar.databinding.ActivityMainBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore

//class MainActivity : AppCompatActivity() {
//    lateinit var binding: ActivityMainBinding
//    lateinit var adapter: VideoListAdapter
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        FirebaseApp.initializeApp(this)
//        binding= ActivityMainBinding.inflate(layoutInflater)
//        enableEdgeToEdge()
//        setContentView(binding.root)
//        binding.bottomNavigationView.setOnItemSelectedListener {menuitem->
//            when(menuitem.itemId){
//                R.id.bottom_menu_home->{
//                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
//                }
//                R.id.bottom_menu_addvdeo->{
//                   startActivity(Intent(this,VideoUploadActivity::class.java))
//
//                }
//                R.id.bottom_menu_profile->{
//                    Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show()
//                }
//            }
//            false
//
//        }
//        setViewPager()
//
//    }
//    private fun setViewPager(){
//     val options= FirestoreRecyclerOptions.Builder<VideoModel>().setQuery(
//         Firebase.firestore.collection("videos"),VideoModel::class.java
//     ).build()
//        adapter= VideoListAdapter(options)
//        binding.viewPager.adapter=adapter
//    }
//
////   override fun onStart() {
////       super.onStart()
////        adapter.startListening()
////}
////    override fun onStop() {
////        super.onStop()
////        adapter.stopListening()
////    }
//}
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: VideoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { menuitem ->
            when (menuitem.itemId) {
                R.id.bottom_menu_home -> {
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.bottom_menu_addvdeo -> {
                    startActivity(Intent(this, VideoUploadActivity::class.java))
                }
                R.id.bottom_menu_profile -> {
                    Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }

        setViewPager()
    }

    private fun setViewPager() {
        val options = FirestoreRecyclerOptions.Builder<VideoModel>()
            .setQuery(Firebase.firestore.collection("videos"), VideoModel::class.java)
            .build()
        adapter = VideoListAdapter(options)
        binding.viewPager.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
