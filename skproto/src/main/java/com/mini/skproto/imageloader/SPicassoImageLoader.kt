package com.mini.skproto.imageloader

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午6:57
 * 描述:
 */
class SPicassoImageLoader : SImageLoader() {
    override fun displayImage(activity: Activity, imageView: ImageView, finalPath: String, loadingResId: Int, failResId: Int, width: Int, height: Int, listener: SDisplayImageListener) {
        Picasso.with(activity).load(finalPath).placeholder(loadingResId).error(failResId).resize(width, height).centerCrop().into(imageView, object : Callback.EmptyCallback() {
            override fun onSuccess() {
                listener?.onSuccess(imageView, finalPath, null as Drawable)
            }
        })
    }

    override fun downloadImage(context: Context, finalPath: String, listener: SDownloadImageListener) {
        Picasso.with(context.applicationContext).load(finalPath).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                listener?.onSuccess(finalPath, bitmap)
            }

            override fun onBitmapFailed(errorDrawable: Drawable) {
                listener?.onFailed(finalPath)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
        })
    }


   

}
