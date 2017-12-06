package com.mini.skproto.imageloader

import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.widget.ImageView

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午6:02
 * 描述:
 */
object SImage {
    private var sImageLoader: SImageLoader? = null

    private val imageLoader: SImageLoader?
        get() {
            if (sImageLoader == null) {
                synchronized(SImage::class.java) {
                    if (sImageLoader == null) {
                        if (isClassExists("org.xutils.x")) {
                            sImageLoader = SXUtilsImageLoader()
                        } else if (isClassExists("com.bumptech.glide.Glide")) {
                            sImageLoader = SGlideImageLoader()
                        } else if (isClassExists("com.squareup.picasso.Picasso")) {
                            sImageLoader = SPicassoImageLoader()
                        } else if (isClassExists("com.nostra13.universalimageloader.core.ImageLoader")) {
                            sImageLoader = SUILImageLoader()
                        } else {
                            throw RuntimeException("必须在你的 build.gradle 文件中配置「Glide、Picasso、universal-image-loader、XUtils3」中的某一个图片加载库的依赖,或者检查是否添加了图库的混淆配置")
                        }
                    }
                }
            }
            return sImageLoader
        }

    private fun isClassExists(classFullName: String): Boolean {
        try {
            Class.forName(classFullName)
            return true
        } catch (e: ClassNotFoundException) {
            return false
        }

    }

    fun displayImage(activity: Activity, imageView: ImageView, path: String, @DrawableRes loadingResId: Int, @DrawableRes failResId: Int, width: Int, height: Int, delegate: SImageLoader.SDisplayImageListener) {
        imageLoader!!.displayImage(activity, imageView, path, loadingResId, failResId, width, height, delegate)
    }

    fun downloadImage(context: Context, path: String, delegate: SImageLoader.SDownloadImageListener) {
        imageLoader!!.downloadImage(context, path, delegate)
    }
}