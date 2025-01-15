package com.example.crudapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.crudapplication.model.ProductModel
import com.example.crudapplication.repository.ProductRepository

class ProductViewModel (private val repository: ProductRepository) {

    fun addProduct(productModel: ProductModel, callback:(Boolean, String) -> Unit){
        repository.addProduct(productModel, callback)
    }

    fun updateProduct(productId : String, data:MutableMap<String,Any>, callback:(Boolean,String) -> Unit){
        repository.updateProduct(productId, data, callback)
    }

    fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit){
        repository.deleteProduct(productId, callback)
    }

    private var _productData = MutableLiveData<ProductModel?>()
    var productData = MutableLiveData<ProductModel?>()
        get() = _productData

    fun getProductById(productId: String){
        repository.getProductById(productId) {
                productModel, success, _ ->
            if(success){
                _productData.value = productModel
            }else{
                _productData.value = null
            }
        }
    }

    private var _allProductsData = MutableLiveData<List<ProductModel>?>()
    var allProductsData = MutableLiveData<List<ProductModel>?>()
        get() = _allProductsData

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading = MutableLiveData<Boolean>()
        get() = _isLoading

    fun getAllProduct(){
        isLoading.value = true
        repository.getAllProduct(){
            allProducts, success, _ ->
            if (success){
                _allProductsData.value = allProducts
                _isLoading.value = false
            }else{
                _isLoading.value = false
                _allProductsData.value = null
            }
        }
    }

}