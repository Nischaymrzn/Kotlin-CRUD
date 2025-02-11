package com.example.crudapplication.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudapplication.R
import com.example.crudapplication.databinding.ActivitySignupBinding
import com.example.crudapplication.model.UserModel
import com.example.crudapplication.repository.UserRepositoryImpl
import com.example.crudapplication.utils.Loader
import com.example.crudapplication.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private lateinit var userViewModel: UserViewModel
    private lateinit var loadingUtils : Loader

    @SuppressLint("UnsafeIntentLaunch")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = Loader(this@SignupActivity)

        val userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)

        binding.sumbitBtn.setOnClickListener{
            loadingUtils.show()
            val email : String = binding.signupEmailText.text.toString()
            val password : String = binding.signupPasswordText.text.toString()
            val address : String= binding.signupAddressText.text.toString()
            val fullName : String = binding.signupFnameText.text.toString()
            val phoneNumber : String = binding.signupNumberText.text.toString()

            userViewModel.signup(email,password){ success, message, userId ->
                if (success) {
                    val userModel = UserModel(
                        userId,
                        fullName, email, phoneNumber, address
                    )
                    addUserToDatabase(userModel)
                }else{
                    loadingUtils.dismiss()
                    Snackbar.make(binding.main,message,Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        binding.alreadyAccountText.setOnClickListener{
            intent=Intent(this@SignupActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addUserToDatabase(userModel: UserModel){
        userViewModel.addUserToDatabase(userModel){
                _, message ->
                    loadingUtils.dismiss()
                    Toast.makeText(this@SignupActivity,message,Toast.LENGTH_SHORT).show()
        }
    }
}
