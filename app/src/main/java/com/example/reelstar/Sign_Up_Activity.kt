package com.example.reelstar

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reelstar.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class Sign_Up_Activity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.submitbtn.setOnClickListener {
            SignUp()
        }
        binding.goToLoginBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }
    fun SignUp(){
        val email=binding.email.text.toString()
        val password=binding.password.text.toString()
        val confirmPassword=binding.confirmPassword.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.setError("Email invalid")
            return
        }
        if (password.length<6){
            binding.password.setError("minimum 6 Characters")
            return
        }
        if (password!=confirmPassword){
            binding.confirmPassword.setError("Enter exact Password")
            return
        }
        SignUpWithFireBase(email,password)
    }
    fun SignUpWithFireBase( email:String, password:String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
               it.user?.let {user->
                   val userModel=UserModel(user.uid,email,email.substringBefore("@"))
                   Firebase.firestore.collection("users").document(user.uid)
                       .set(userModel).addOnSuccessListener {
                           Toast.makeText(applicationContext, "Successful", Toast.LENGTH_SHORT).show()
                           startActivity(Intent(this,MainActivity::class.java))
                           finish()
                       }
               }
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "${it.localizedMessage?:"Something went wrong"}", Toast.LENGTH_SHORT).show()
            }
    }
}
