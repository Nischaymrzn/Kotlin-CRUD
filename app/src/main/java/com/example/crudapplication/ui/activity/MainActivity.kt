package com.example.crudapplication.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.crudapplication.R
import com.example.crudapplication.databinding.ActivityMainBinding
import com.example.crudapplication.ui.fragment.HomeFragment
import com.example.crudapplication.ui.fragment.NotificationFragment
import com.example.crudapplication.ui.fragment.ProfileFragment
import com.example.crudapplication.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager : FragmentManager = supportFragmentManager

        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame,fragment)

        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

            binding= ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)


            replaceFragment(HomeFragment())

            //bottomView -> bottomNavigationView ko id
            binding.bottomView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.navHome -> replaceFragment(HomeFragment())
                    R.id.navSearch -> replaceFragment(SearchFragment())
                    R.id.navNotification -> replaceFragment(NotificationFragment())
                    R.id.navProfile -> replaceFragment(ProfileFragment())
                    else -> replaceFragment(HomeFragment())
                }
                true
            }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}