package com.turdroid.library.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.turdroid.library.base.BaseApp


object LoadImageUtils {

    fun loadImage(view: ImageView,bitmap: Bitmap?){
        bitmap?.let {
            Glide.with(view)
                .load(bitmap)
                .into(view)
        }
    }

//    fun loadImage(view: AppCompatImageView,bitmap: Bitmap?){
//        bitmap?.let {
//            Glide.with(view)
//                .load(bitmap)
//                .into(view)
//        }
//    }

    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view)
            .asDrawable()
            .load(url)
            .into(view)
    }

    fun loadImage(view: ImageView, url: String?,width: Int,height: Int) {
        Glide.with(view)
            .asDrawable()
            .load(url)
            .override(width, height)
            .into(view)
    }

    fun loadImage(
        view: ImageView,
        url: String,
        @RawRes @DrawableRes @androidx.annotation.Nullable errorRes: Int
    ) {
        Glide.with(view)
            .asDrawable()
            .load(url)
            .error(errorRes)
            .into(view)
    }

    fun loadImage(
        view: ImageView,
        @RawRes @DrawableRes @androidx.annotation.Nullable resourceId: Int
    ) {
        Glide.with(view)
            .asDrawable()
            .load(resourceId)
            .into(view)
    }

    fun loadImage(
        view: ImageView,
        @RawRes @DrawableRes @androidx.annotation.Nullable resourceId: Int,
        @RawRes @DrawableRes @androidx.annotation.Nullable errorRes: Int
    ) {
        Glide.with(view)
            .asDrawable()
            .load(resourceId)
            .error(errorRes)
            .into(view)
    }

    fun loadCircleImage(view: ImageView, url: String?) {
        Glide.with(view)
            .asDrawable()
            .load(url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(view)
    }

    fun loadCircleImage(view: ImageView, url: String?,width:Int,height:Int) {
        Glide.with(view)
            .asDrawable()
            .load(url)
            .override(width, height)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(view)
    }

    fun loadCircleImage(
        view: ImageView,
        url: String?,
        @RawRes @DrawableRes @androidx.annotation.Nullable errorRes: Int
    ) {
        Glide.with(view)
            .asDrawable()
            .load(url)
            .error(errorRes)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(view)
    }

    fun loadCircleImage(
        view: ImageView,
        @RawRes @DrawableRes @androidx.annotation.Nullable resourceId: Int
    ) {
        Glide.with(view)
            .asDrawable()
            .load(resourceId)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(view)
    }

    fun loadCircleImage(
        view: ImageView,
        @RawRes @DrawableRes @androidx.annotation.Nullable resourceId: Int,
        @RawRes @DrawableRes @androidx.annotation.Nullable errorRes: Int
    ) {
        Glide.with(view)
            .asDrawable()
            .load(resourceId)
            .error(errorRes)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(view)
    }

    fun setImageBackground(view: View, resId: Int) {
        Glide.with(view)
            .asBitmap()
            .load(resId)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    val drawable: Drawable =
                        BitmapDrawable(view.resources, resource)
                    view.background = drawable

                }
            })
    }

    fun loadCircleImageBackground(view: View, resId: Int){
        Glide.with(view)
            .asBitmap()
            .load(resId)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(object : CustomTarget<Bitmap?>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    val drawable: Drawable =
                        BitmapDrawable(view.resources, resource)
                    view.background = drawable

                }
            })
    }

    fun loadRoundImage(view: ImageView, url: String?,radius: Int) {
        Glide.with(view)
            .asDrawable()
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
            .into(view)
    }

    fun loadRoundImage(view: ImageView, @RawRes @DrawableRes @androidx.annotation.Nullable resourceId: Int,radius: Int) {
        Glide.with(view)
            .asDrawable()
            .load(resourceId)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
            .into(view)
    }

    fun loadCornerImage(
        view: ImageView,
        url: String?,
        radius: Int,
        leftTop:Boolean,
        rightTop:Boolean,
        leftBottom: Boolean,
        rightBottom:Boolean){
        val transform = RoundedCornersTransform(BaseApp.instance, radius.toFloat())
        transform.setNeedCorner(leftTop,rightTop,leftBottom, rightBottom)
        val options: RequestOptions = RequestOptions().transform(transform)
        Glide.with(BaseApp.instance)
            .asDrawable()
            .load(url)
            .apply(options)
            .into(view)
    }

    fun loadCornerImage(
        view: ImageView,
        resId: Int,
        radius: Int,
        leftTop:Boolean,
        rightTop:Boolean,
        leftBottom: Boolean,
        rightBottom:Boolean){
        val transform = RoundedCornersTransform(BaseApp.instance, radius.toFloat())
        transform.setNeedCorner(leftTop,rightTop,leftBottom, rightBottom)
        val options: RequestOptions = RequestOptions().transform(transform)
        Glide.with(BaseApp.instance)
            .asDrawable()
            .load(resId)
            .apply(options)
            .into(view)
    }

    fun loadCornerImage(
        view: ImageView,
        url: String?,
        radius: Int,
        leftTop:Boolean,
        rightTop:Boolean,
        leftBottom: Boolean,
        rightBottom:Boolean,
        width:Int,height:Int){
        val transform = RoundedCornersTransform(BaseApp.instance, radius.toFloat())
        transform.setNeedCorner(leftTop,rightTop,leftBottom, rightBottom)
        val options: RequestOptions = RequestOptions().transform(transform)
        Glide.with(BaseApp.instance)
            .asDrawable()
            .load(url)
            .apply(options)
            .override(width, height)
            .into(view)
    }

    fun resumeRequest(view: View){
        Glide.with(view.context).resumeRequests();
    }

    fun pauseRequest(view: View){
        Glide.with(view.context).pauseRequests()
    }
}