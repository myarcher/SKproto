package com.mini.skproto.imageloader

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.view.View
import android.widget.ImageView

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午5:58
 * 描述:
 */
public abstract class SImageLoader {

    abstract fun displayImage(activity: Activity, imageView: ImageView, path: String, @DrawableRes loadingResId: Int, @DrawableRes failResId: Int, width: Int, height: Int, displayImageListener: SDisplayImageListener)

    abstract fun downloadImage(context: Context, path: String, downloadImageListener: SDownloadImageListener)

    interface SDisplayImageListener {
        fun onSuccess(view: View, path: String, drawable: Drawable)
    }

    interface SDownloadImageListener {
        fun onSuccess(path: String, bitmap: Bitmap)

        fun onFailed(path: String)
    }
}