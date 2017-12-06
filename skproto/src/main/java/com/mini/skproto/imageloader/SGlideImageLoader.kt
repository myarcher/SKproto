package com.mini.skproto.imageloader

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午6:51
 * 描述:
 */
class SGlideImageLoader : SImageLoader() {
    override fun downloadImage(context: Context, finalPath: String, listener: SDownloadImageListener) {
        Glide.with(context.applicationContext).load(finalPath).asBitmap().into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                listener?.onSuccess(finalPath, resource)
            }

            override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                listener?.onFailed(finalPath)
            }
        })
    }

    override fun displayImage(activity: Activity, imageView: ImageView, finalPath: String, loadingResId: Int, failResId: Int, width: Int, height: Int, listener: SDisplayImageListener) {
        Glide.with(activity).load(finalPath).asBitmap().placeholder(loadingResId).error(failResId).override(width, height).diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(object : RequestListener<String, Bitmap> {
            override fun onException(e: Exception, model: String, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Bitmap, model: String, target: Target<Bitmap>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                if (listener != null) {
                    val drawable = BitmapDrawable(resource)
                    listener.onSuccess(imageView, finalPath, drawable)
                }
                return false
            }
        }).into(imageView) }
    
}