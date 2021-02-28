package com.example.apiexample

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.apiexample.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class DogsViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding= ItemDogBinding.bind(view)
    fun bind(image:String){
        Picasso.get().load(image).into(binding.imgdog)

    }
}