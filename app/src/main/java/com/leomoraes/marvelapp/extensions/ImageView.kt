package com.leomoraes.marvelapp.extensions

import android.widget.ImageView
import com.leomoraes.marvelapp.R
import com.leomoraes.marvelapp.data.model.HeroImage
import com.squareup.picasso.Picasso

fun ImageView.loadSmallImage(image: HeroImage) {
    loadImage(this, image, "standard_medium")
}

fun ImageView.loadLargeImage(image: HeroImage) {
    loadImage(this, image, "portrait_uncanny")
}

private fun loadImage(imageView: ImageView, imageData: HeroImage, size: String) {
    val url = "${imageData.path}/$size.${imageData.extension}"
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_loading)
        .error(R.drawable.ic_error)
        .into(imageView)
}