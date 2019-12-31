package com.ahmad.sportview

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmad.sportview.network.SportsItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ahmad.sportview.overview.PhotoGridAdapter
import com.ahmad.sportview.overview.SportApiStatus


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<SportsItem>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    if (!data.isNullOrEmpty())
        adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl:String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}

@BindingAdapter("sportApiStatus")
fun bindStatus(statusImageView: ImageView, status: SportApiStatus?){
    when(status){
        SportApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        SportApiStatus.ERROR ->{
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        SportApiStatus.DONE ->{
            statusImageView.visibility = View.GONE
        }
    }
}