package com.example.crudapplication.repository

import com.example.crudapplication.model.ProductModel
import com.example.crudapplication.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductRepositoryImpl : ProductRepository {

    private val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference : DatabaseReference = database.reference.child("products")

    override fun addProduct(productModel: ProductModel, callback: (Boolean, String) -> Unit) {
        var id = reference.push().key.toString()
        reference.child(id).setValue(productModel).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Product created successfully")
            }else{
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun updateProduct(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(productId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true, "Product updated successfully")
            }else{
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {
        reference.child(productId).removeValue().addOnCompleteListener {
            if (it.isSuccessful){
                callback(true, "Product deleted successfully")
            }else{
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun getProductById(
        productId: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {
        reference.child(productId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val model = snapshot.getValue(ProductModel::class.java)
                    callback(model, true, "Product fetched successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun getAllProduct(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val productList = mutableListOf<ProductModel>()
                    for (data in snapshot.children){
                        val model = snapshot.getValue(ProductModel::class.java)
                        if(model != null){
                            productList.add(model)
                        }
                    }
                    callback(productList, true, "Product fetched successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList(), false, error.message)
            }
        })
    }
}