package com.mini.skproto.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.util.Linkify
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.*

/**
 *
 *
 * @author
 * @date    2017/10/11
 * @version 1.0
 */
/**
 * 自定义的viewholder，用于处理列表的item
 */
class ViHolder(var mContext: SActivity, val convertView: View) : RecyclerView.ViewHolder(convertView) {
    private val mViews: SparseArray<View>

    constructor(context: SActivity, layoutId: Int) : this(context, LayoutInflater.from(context).inflate(layoutId, null)) {}

    init {
        mViews = SparseArray()
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = convertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    fun getItemView(): View {
        return convertView
    }


    /****以下为辅助方法 */

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    fun setText(viewId: Int, text: String): ViHolder {
        val tv = getView<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): ViHolder {
        val view = getView<ImageView>(viewId)
        view.setImageResource(resId)
        return this
    }

    fun setImageBitmap(viewId: Int, bitmap: Bitmap): ViHolder {
        val view = getView<ImageView>(viewId)
        view.setImageBitmap(bitmap)

        return this
    }

    fun setImageDrawable(viewId: Int, drawable: Drawable): ViHolder {
        val view = getView<ImageView>(viewId)
        view.setImageDrawable(drawable)
        return this
    }

    fun setImageUrl(viewId: Int, url: String, w: Int, h: Int, default_img: Int): ViHolder {
        val view = getView<ImageView>(viewId)
        //Units.loadImage(mContext, url, w, h, view, default_img)//加载网络图片（因为返回的url格式不固定需要处理在进行加载）
        return this
    }


    fun setBackgroundColor(viewId: Int, color: Int): ViHolder {
        val view = getView<View>(viewId)
        view.setBackgroundColor(color)
        return this
    }

    fun setBackgroundRes(viewId: Int, backgroundRes: Int): ViHolder {
        val view = getView<View>(viewId)
        view.setBackgroundResource(backgroundRes)
        return this
    }

    fun setTextColor(viewId: Int, textColor: Int): ViHolder {
        val view = getView<TextView>(viewId)
        view.setTextColor(textColor)
        return this
    }

    fun setTextColorRes(viewId: Int, textColorRes: Int): ViHolder {
        val view = getView<TextView>(viewId)
        view.setTextColor(mContext.getResources().getColor(textColorRes))
        return this
    }

    @SuppressLint("NewApi")
    fun setAlpha(viewId: Int, value: Float): ViHolder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView<View>(viewId).alpha = value
        } else {
            // Pre-honeycomb hack to set Alpha value
            val alpha = AlphaAnimation(value, value)
            alpha.duration = 0
            alpha.fillAfter = true
            getView<View>(viewId).startAnimation(alpha)
        }
        return this
    }

    fun setVisible(viewId: Int, visible: Boolean): ViHolder {
        val view = getView<View>(viewId)
        view.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun setVisible(viewId: Int, visible: Int): ViHolder {
        val view = getView<View>(viewId)
        view.visibility = visible
        return this
    }

    fun linkify(viewId: Int): ViHolder {
        val view = getView<TextView>(viewId)
        Linkify.addLinks(view, Linkify.ALL)
        return this
    }

    fun setTypeface(typeface: Typeface, vararg viewIds: Int): ViHolder {
        for (viewId in viewIds) {
            val view = getView<TextView>(viewId)
            view.typeface = typeface
            view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        }
        return this
    }

    fun setProgress(viewId: Int, progress: Int): ViHolder {
        val view = getView<ProgressBar>(viewId)
        view.progress = progress
        return this
    }

    fun setProgress(viewId: Int, progress: Int, max: Int): ViHolder {
        val view = getView<ProgressBar>(viewId)
        view.max = max
        view.progress = progress
        return this
    }

    fun setMax(viewId: Int, max: Int): ViHolder {
        val view = getView<ProgressBar>(viewId)
        view.max = max
        return this
    }

    fun setRating(viewId: Int, rating: Float): ViHolder {
        val view = getView<RatingBar>(viewId)
        view.rating = rating
        return this
    }

    fun setRating(viewId: Int, rating: Float, max: Int): ViHolder {
        val view = getView<RatingBar>(viewId)
        view.max = max
        view.rating = rating
        return this
    }

    fun setTag(viewId: Int, tag: Any): ViHolder {
        val view = getView<View>(viewId)
        view.tag = tag
        return this
    }

    fun setTag(viewId: Int, key: Int, tag: Any): ViHolder {
        val view = getView<View>(viewId)
        view.setTag(key, tag)
        return this
    }

    fun setChecked(viewId: Int, checked: Boolean): ViHolder {
        val view = getView<View>(viewId) as Checkable
        view.isChecked = checked
        return this
    }

    /**
     * 关于事件的
     */
    fun setOnClickListener(viewId: Int, listener: View.OnClickListener): ViHolder {
        val view = getView<View>(viewId)
        view.setOnClickListener(listener)
        return this
    }

    fun setOnTouchListener(viewId: Int, listener: View.OnTouchListener): ViHolder {
        val view = getView<View>(viewId)
        view.setOnTouchListener(listener)
        return this
    }

    fun setOnLongClickListener(viewId: Int, listener: View.OnLongClickListener): ViHolder {
        val view = getView<View>(viewId)
        view.setOnLongClickListener(listener)
        return this
    }

    fun getBitmap(context: Context, re: Int): BitmapDrawable {
        val opt = BitmapFactory.Options()
        opt.inPreferredConfig = Bitmap.Config.RGB_565
        opt.inPurgeable = true
        opt.inInputShareable = true
        val `is` = context.resources.openRawResource(re)
        val bm = BitmapFactory.decodeStream(`is`, null, opt)
        return BitmapDrawable(context.resources, bm)
    }

    companion object {
        fun createViewHolder(context: SActivity, itemView: View): ViHolder {
            return ViHolder(context, itemView)
        }

        fun createViewHolder(context: SActivity, parent: ViewGroup, layoutId: Int): ViHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return ViHolder(context, itemView)
        }
    }

    fun bindItemHolder(holder: ViHolder, data: Map<String, Any>, position: Int,mAdapter:CustomAdapter) {
        
    }
}