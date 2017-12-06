package com.mini.skproto.imageloader

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView
import org.xutils.common.Callback
import org.xutils.image.ImageOptions
import org.xutils.x

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午6:55
 * 描述:
 */
class SXUtilsImageLoader : SImageLoader() {
    override fun displayImage(activity: Activity, imageView: ImageView, finalPath: String, loadingResId: Int, failResId: Int, width: Int, height: Int, listener: SDisplayImageListener) {
        x.Ext.init(activity.application)
        val options = ImageOptions.Builder()
                .setLoadingDrawableId(loadingResId)
                .setFailureDrawableId(failResId)
                .setSize(width, height)
                .build()

        x.image().bind(imageView, finalPath, options, object : Callback.CommonCallback<Drawable> {
            override fun onSuccess(result: Drawable) {
                listener?.onSuccess(imageView, finalPath, result)
            }

            override fun onError(ex: Throwable, isOnCallback: Boolean) {}

            override fun onCancelled(cex: Callback.CancelledException) {}

            override fun onFinished() {}
        }) }

    override fun downloadImage(context: Context, finalPath: String, listener: SDownloadImageListener) {
        x.Ext.init(context.applicationContext as Application)

        x.image().loadDrawable(finalPath, ImageOptions.Builder().build(), object : Callback.CommonCallback<Drawable> {
            override fun onSuccess(result: Drawable) {
                listener?.onSuccess(finalPath, (result as BitmapDrawable).bitmap)
            }

            override fun onError(ex: Throwable, isOnCallback: Boolean) {
                listener?.onFailed(finalPath)
            }

            override fun onCancelled(cex: Callback.CancelledException) {}

            override fun onFinished() {}
        })
    }

   

   

}
