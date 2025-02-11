package com.example.crudapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.crudapplication.model.UserModel
import com.example.crudapplication.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo : UserRepository) {

    fun login(email : String, password: String, callback:(Boolean, String) -> Unit){
        repo.login(email,password,callback)
    }

    fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repo.signup(email,password,callback)
    }

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email, callback)
    }

    fun addUserToDatabase(userModel: UserModel, callback: (Boolean, String) -> Unit){
        repo.addUserToDatabase(userModel, callback)
    }

    fun getCurrentUser() : FirebaseUser? {
        return repo.getCurrentUser()
    }

    private var _userData=MutableLiveData<UserModel?>()
    var userData = MutableLiveData<UserModel?>()
        get() = _userData

    fun getDataFromDatabase(userId : String){
        repo.getDataFromDatabase(userId){
            userModel, success, message ->
            if(success){
                _userData.value = userModel
            }else{
                _userData.value = null
            }
        }
    }

    fun logout(callback: (Boolean, String) -> Unit){
        repo.logout(callback)
    }
    fun editProfile(userId: String, data : MutableMap<String, Any>, callback: (Boolean, String) -> Unit){
        repo.editProfile(userId,data,callback)
    }
}