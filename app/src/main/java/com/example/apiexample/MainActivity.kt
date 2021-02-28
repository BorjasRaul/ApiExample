package com.example.apiexample

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//raul
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener{
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: DogsAdapter
    private val dogImage= mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.schview.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = DogsAdapter(dogImage)
        binding.rcv.layoutManager = LinearLayoutManager(this)
        binding.rcv.adapter=adapter

    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun searchName(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            var call=getRetrofit().create(APIService::class.java).getDogs("$query/images")

            var pupies=call.body()
            runOnUiThread{

                if(call.isSuccessful){

                    val images: List<String> = pupies?.image ?: emptyList()
                    dogImage.clear()
                    dogImage.addAll(images)
                    adapter.notifyDataSetChanged()

                }else{
                    showError()
                }
                hidekeyboard()
            }

        }
    }

    private fun hidekeyboard() {
        val imm: InputMethodManager= getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewb.windowToken,0)
    }

    private fun showError() {
        Toast.makeText(this,"Ha ocurrido un error",Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            searchName(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}