package com.example.reelstar

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reelstar.databinding.ActivityLoginBinding
import com.example.reelstar.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAuth.getInstance().currentUser?.let {
            //user is there//
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        binding=ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.goToSingInBtn.setOnClickListener {
            startActivity(Intent(this,Sign_Up_Activity::class.java))
            finish()
        }
        binding.submitbtn.setOnClickListener{
            login()
        }

    }
    fun login(){
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
        LoginWithFireBase(email, password)


    }
    fun LoginWithFireBase(email:String,password:String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnSuccessListener {
            Toast.makeText(applicationContext, "Login SuccessFully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "${it.localizedMessage?:"Something went wrong"}", Toast.LENGTH_SHORT).show()
        }
    }
}