package com.mini.skproto.imageloader

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.annotation.DrawableRes
import android.view.View
import android.widget.ImageView
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageSize
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午6:54
 * 描述:
 */
class SUILImageLoader : SImageLoader() {
    override fun downloadImage(context: Context, finalPath: String, listener: SDownloadImageListener) {
        initImageLoader(context)

        ImageLoader.getInstance().loadImage(finalPath, object : SimpleImageLoadingListener() {
            override
            fun onLoadingComplete(imageUri: String, view: View, loadedImage: Bitmap) {
                listener?.onSuccess(imageUri, loadedImage)
            }

            override
            fun onLoadingFailed(imageUri: String, view: View, failReason: FailReason) {
                listener?.onFailed(imageUri)
            }
        })
    }

    override fun displayImage(activity: Activity, imageView: ImageView, finalPath: String, loadingResId: Int, failResId: Int, width: Int, height: Int, listener: SDisplayImageListener) {
        initImageLoader(activity)
        val options = DisplayImageOptions.Builder()
                .showImageOnLoading(loadingResId)
                .showImageOnFail(failResId)
                .cacheInMemory(true)
                .build()
        val imageSize = ImageSize(width, height)

        ImageLoader.getInstance().displayImage(finalPath, ImageViewAware(imageView), options, imageSize, object : SimpleImageLoadingListener() {
            override
            fun onLoadingComplete(imageUri: String, view: View, loadedImage: Bitmap) {
                if (listener != null) {
                    val drawable = BitmapDrawable(loadedImage)
                    listener.onSuccess(view, imageUri, drawable)
                }
            }
        }, null)
    }

    private fun initImageLoader(context: Context) {
        if (!ImageLoader.getInstance().isInited()) {
            val options = DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build()
            val config = ImageLoaderConfiguration.Builder(context.applicationContext).threadPoolSize(3).defaultDisplayImageOptions(options).build()
            ImageLoader.getInstance().init(config)
        }
    }


}