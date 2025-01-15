package com.example.crudapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.crudapplication.R
import com.example.crudapplication.databinding.FragmentProfileBinding
import com.example.crudapplication.repository.UserRepositoryImpl
import com.example.crudapplication.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Toast.makeText(requireContext(),"Hello World",Toast.LENGTH_SHORT).show()

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        var currentUser = userViewModel.getCurrentUser()

        currentUser.let{
            userViewModel.getDataFromDatabase(currentUser?.uid.toString())
        }

        userViewModel.userData.observe(requireActivity()){users->
            binding.fullName.text=users?.fullName
            binding.emailAddress.text=users?.email
        }
    }

}