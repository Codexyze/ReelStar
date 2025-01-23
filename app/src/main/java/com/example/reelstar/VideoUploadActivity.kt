package com.example.reelstar

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.reelstar.databinding.ActivityVideoUploadBinding
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

class VideoUploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoUploadBinding
    private var selectedVideoUri: Uri?=null
    lateinit var videoLauncher:ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityVideoUploadBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        videoLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode== RESULT_OK){
                //video selected//
                selectedVideoUri=result.data?.data
                Toast.makeText(this, "Got Video"+selectedVideoUri.toString(), Toast.LENGTH_SHORT).show()
                showPostView()
            }
        }
        binding.uploadVideo.setOnClickListener {
           checkPermissionAndOpenVideoPicker()
        }
        binding.submitPostBtn.setOnClickListener {
            postVideo()
        }
        binding.cancelPostBtn.setOnClickListener {
            finish()
        }
    }
    private fun showPostView(){

        selectedVideoUri?.let {uri->
            binding.postView.visibility=View.VISIBLE
            binding.uploadVideo.visibility=View.GONE
            Glide.with(binding.postThumbnailView).load(uri).into(binding.postThumbnailView)
        }

    }
   private fun checkPermissionAndOpenVideoPicker(){
       var readExternalVideo:String=""
       if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
//greater than android 13//
           readExternalVideo=android.Manifest.permission.READ_MEDIA_VIDEO
       }else{
           readExternalVideo=android.Manifest.permission.READ_EXTERNAL_STORAGE
       }
       if(ContextCompat.checkSelfPermission(this,readExternalVideo)==PackageManager.PERMISSION_GRANTED){
           openVideoPicker()
       }else{
           ActivityCompat.requestPermissions(this, arrayOf(readExternalVideo),100)
       }

    }
    private fun openVideoPicker(){
       //get video from User//
        var intent =Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.type="video/*"
        videoLauncher.launch(intent)
    }
    private fun postVideo(){
       if( binding.postCaption.text.toString().isEmpty()){
           binding.postCaption.setError("write caption")
           return
       }
        selectedVideoUri?.apply {
            //store in firebase cloud//
            //video model store in firebase firestore//
           val videoRef=  FirebaseStorage.getInstance().reference
               .child("videos/"+this
                   .lastPathSegment)

            videoRef.putFile(this).addOnSuccessListener {
                videoRef.downloadUrl.addOnSuccessListener { downloaduri->
                    //video model store in firestore//
                       postToFireStore(downloaduri.toString())
                }
            }
        }
    }
    private fun postToFireStore(url:String){
     val videoModel=VideoModel(
          FirebaseAuth.getInstance().currentUser?.uid!! + "_" + Timestamp.now().toString(),
         binding.postCaption.text.toString(),
         url,FirebaseAuth.getInstance().currentUser?.uid!!,
         Timestamp.now())
        Firebase.firestore.collection("videos").document(videoModel.videoid).set(videoModel)
            .addOnSuccessListener {
                Toast.makeText(this, "Video uploaded", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "failed to upload", Toast.LENGTH_SHORT).show()

            }
    }
}