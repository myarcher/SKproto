//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mini.skproto.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Handler
import android.os.Message
import android.os.Parcel
import android.os.Parcelable
import android.os.Handler.Callback
import android.os.Parcelable.Creator
import android.support.annotation.NonNull
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Scroller
import android.widget.ImageView.ScaleType
import org.jetbrains.anko._RelativeLayout
import org.jetbrains.anko.internals.AnkoInternals
import java.io.Serializable
import java.util.ArrayList

open class BannerLayout : _RelativeLayout {
    private var pager: ViewPager? = null
    private var indicatorContainer: LinearLayout? = null
    private var unSelectedDrawable: Drawable? = null
    private var selectedDrawable: Drawable? = null
    private val WHAT_AUTO_PLAY: Int
    private var isAutoPlay: Boolean = false
    private var itemCount: Int = 0
    public var selectedIndicatorColor: Int = 0
    public var unSelectedIndicatorColor: Int = 0
    public var indicatorShape: BannerLayout.Shape? = null
    public var selectedIndicatorHeight: Int = 0
    public var selectedIndicatorWidth: Int = 0
    public var unSelectedIndicatorHeight: Int = 0
    public var unSelectedIndicatorWidth: Int = 0
    public var indicatorPosition: BannerLayout.Position? = null
    public var autoPlayDuration: Int = 0
    public var scrollDuration: Int = 0
    public var indicatorSpace: Int = 0
    public var indicatorMargin: Int = 0
    private var currentPosition: Int = 0
    private var imageLoader: BannerLayout.ImageLoader? = null
    private var onBannerItemClickListener: BannerLayout.OnBannerItemClickListener? = null
    private var handlers: Handler? = null

    constructor(context: Context, ints: BannerLayout.() -> Unit) : super(context) {
        this.WHAT_AUTO_PLAY = 1000
        this.isAutoPlay = true
        this.selectedIndicatorColor = -65536;
        this.unSelectedIndicatorColor = -2004318072;
        this.indicatorShape = BannerLayout.Shape.oval;
        this.selectedIndicatorHeight = 6;
        this.selectedIndicatorWidth = 6;
        this.unSelectedIndicatorHeight = 6;
        this.unSelectedIndicatorWidth = 6;
        this.indicatorPosition = BannerLayout.Position.centerBottom;
        this.autoPlayDuration = 4000;
        this.scrollDuration = 900;
        this.indicatorSpace = 3;
        this.indicatorMargin = 10;
        this.handlers = Handler(object : Callback {
            override fun handleMessage(msg: Message): Boolean {
                if (msg.what == WHAT_AUTO_PLAY && pager != null && isAutoPlay) {
                    pager!!.setCurrentItem(pager!!.getCurrentItem() + 1, true);
                    handler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, autoPlayDuration as Long);
                }

                return false
            }
        })
        ints()
    }

    init {
        inits()
    }

    public fun inits() {
        val shape = indicatorShape
        val position = BannerLayout.Shape.values()
        val unSelectedLayerDrawable = position.size

        var selectedLayerDrawable: Int
        selectedLayerDrawable = 0
        while (selectedLayerDrawable < unSelectedLayerDrawable) {
            val unSelectedGradientDrawable = position[selectedLayerDrawable]
            if (unSelectedGradientDrawable.ordinal == shape!!.ordinal) {
                this.indicatorShape = unSelectedGradientDrawable
                break
            }
            ++selectedLayerDrawable
        }

        val var10 = indicatorPosition
        val var11 = BannerLayout.Position.values()
        selectedLayerDrawable = var11.size

        for (var14 in 0 until selectedLayerDrawable) {
            val selectedGradientDrawable = var11[var14]
            if (var10!!.ordinal == selectedGradientDrawable.ordinal) {
                this.indicatorPosition = selectedGradientDrawable
            }
        }

        val var15 = GradientDrawable()
        val var16 = GradientDrawable()
        when (this.indicatorShape!!.ordinal) {
            1 -> {
                var15.shape = 0
                var16.shape = 0
            }
            2 -> {
                var15.shape = 1
                var16.shape = 1
            }
        }

        var15.setColor(this.unSelectedIndicatorColor)
        var15.setSize(this.unSelectedIndicatorWidth, this.unSelectedIndicatorHeight)
        val var12 = LayerDrawable(arrayOf<Drawable>(var15))
        this.unSelectedDrawable = var12
        var16.setColor(this.selectedIndicatorColor)
        var16.setSize(this.selectedIndicatorWidth, this.selectedIndicatorHeight)
        val var13 = LayerDrawable(arrayOf<Drawable>(var16))
        this.selectedDrawable = var13
    }

    public fun setViewUrls(urls: List<String>) {
        var views: ArrayList<View> = ArrayList()
        this.itemCount = urls.size
        if (this.itemCount < 1) {
            throw  IllegalStateException("item count not equal zero")
        } else {
            if (this.itemCount < 2) {
                views.add(getImageView(urls.get(0), 0))
                views.add(this.getImageView(urls.get(0), 0))
                views.add(this.getImageView(urls.get(0), 0))
            } else if (this.itemCount < 3) {
                views.add(this.getImageView(urls.get(0), 0))
                views.add(this.getImageView(urls.get(1), 1))
                views.add(this.getImageView(urls.get(0), 0))
                views.add(this.getImageView(urls.get(1), 1))
            } else {
                for (i: Int in 0..urls.size) {
                    views.add(this.getImageView(urls.get(i), i));
                }
            }

            setViews(views)
        }
    }

    @NonNull
    private fun getImageView(url: String, position: Int): ImageView {
        var imageView = ImageView(this.getContext())
        imageView.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener!!.onItemClick(position);
                }
            }
        })
        imageView.setScaleType(ScaleType.CENTER_CROP);
        this.imageLoader!!.displayImage(this.getContext(), url, imageView);
        return imageView
    }

    fun setImageLoader(imageLoader: BannerLayout.ImageLoader) {
        this.imageLoader = imageLoader
    }

    fun getPager(): ViewPager? {
        return if (this.pager != null) this.pager else null
    }

    fun setViews(views: List<View>) {
        this.pager = ViewPager(this.context)
        this.addView(this.pager)
        this.setSliderTransformDuration(this.scrollDuration)
        this.indicatorContainer = LinearLayout(this.context)
        this.indicatorContainer!!.setGravity(16)
        val params = LayoutParams(-2, -2)
        when (indicatorPosition!!.ordinal) {
            1 -> {
                params.addRule(14)
                params.addRule(12)
            }
            2 -> {
                params.addRule(14)
                params.addRule(10)
            }
            3 -> {
                params.addRule(9)
                params.addRule(12)
            }
            4 -> {
                params.addRule(9)
                params.addRule(10)
            }
            5 -> {
                params.addRule(11)
                params.addRule(12)
            }
            6 -> {
                params.addRule(11)
                params.addRule(10)
            }
        }

        params.setMargins(this.indicatorMargin, this.indicatorMargin, this.indicatorMargin, this.indicatorMargin)
        this.addView(this.indicatorContainer, params)

        for (pagerAdapter in 0 until this.itemCount) {
            val targetItemPosition = ImageView(this.context)
            targetItemPosition.layoutParams = android.view.ViewGroup.LayoutParams(-2, -2)
            targetItemPosition.setPadding(this.indicatorSpace, this.indicatorSpace, this.indicatorSpace, this.indicatorSpace)
            targetItemPosition.setImageDrawable(this.unSelectedDrawable)
            this.indicatorContainer!!.addView(targetItemPosition)
        }

        val var5 = BannerLayout.LoopPagerAdapter(views)
        this.pager!!.setAdapter(var5)
        val var6 = 1073741823 - 1073741823 % this.itemCount
        this.currentPosition = var6
        this.pager!!.setCurrentItem(var6)
        this.switchIndicator(var6 % this.itemCount)
        this.pager!!.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                this@BannerLayout.currentPosition = position
                this@BannerLayout.switchIndicator(position % this@BannerLayout.itemCount)
            }
        })
        if (this.isAutoPlay) {
            this.startAutoPlay()
        }

    }

    fun setSliderTransformDuration(duration: Int) {
        try {
            val e = ViewPager::class.java.getDeclaredField("mScroller")
            e.isAccessible = true
            val scroller = BannerLayout.FixedSpeedScroller(this.pager!!.getContext(), null as Interpolator, duration)
            e.set(this.pager, scroller)
        } catch (var4: Exception) {
            var4.printStackTrace()
        }

    }


    private fun startAutoPlay() {
        this.stopAutoPlay()
        if (this.isAutoPlay) {
            this.handler.sendEmptyMessageDelayed(this.WHAT_AUTO_PLAY, autoPlayDuration as Long)
        }

    }

    override protected fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == 0) {
            startAutoPlay()
        } else {
            this.stopAutoPlay()
        }

    }

    private fun stopAutoPlay() {
        if (this.isAutoPlay) {
            this.handler.removeMessages(this.WHAT_AUTO_PLAY);
            if (this.pager != null) {
                pager!!.setCurrentItem(pager!!.getCurrentItem(), false);
            }
        }

    }

    public fun setAutoPlay(autoPlay: Boolean) {
        this.isAutoPlay = autoPlay
    }

    override public fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.getAction() == 0) {
            this.stopAutoPlay()
        } else if (ev.action == 1) {
        } else if (ev.action == 2) {
        } else if (ev.action == 3) {
            this.startAutoPlay()
        }
        return super.dispatchTouchEvent(ev);
    }

    private fun switchIndicator(currentPosition: Int) {
        for (i in 0 until this.indicatorContainer!!.getChildCount()) {
            (this.indicatorContainer!!.getChildAt(i) as ImageView).setImageDrawable(if (i == currentPosition) this.selectedDrawable else this.unSelectedDrawable)
        }

    }

    public fun setOnBannerItemClickListener(onBannerItemClickListener: BannerLayout.OnBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    override public fun onRestoreInstanceState(state: Parcelable) {
        var savedState = state as (BannerLayout.SavedState)
        super.onRestoreInstanceState(savedState.getSuperState());
        this.currentPosition = savedState.currentPosition;
        this.requestLayout()
    }

    override public fun onSaveInstanceState(): Parcelable {
        var superState = super.onSaveInstanceState();
        var savedState = BannerLayout.SavedState(superState);
        savedState.currentPosition = this.currentPosition;
        return savedState
    }

    public interface ImageLoader : Serializable {
        fun displayImage(var1: Context, var2: String, var3: ImageView)
    }

    public class FixedSpeedScroller : Scroller {
        private var mDuration: Int = 0;

        public constructor (context: Context) : super(context) {
            this.mDuration = 1000
        }

        public constructor (context: Context, interpolator: Interpolator) : super(context, interpolator) {
            this.mDuration = 1000
        }

        public constructor (context: Context, interpolator: Interpolator, duration: Int) : super(context, interpolator) {
            this.mDuration = duration;
        }

        override public fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, this.mDuration)
        }

        override public fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
            super.startScroll(startX, startY, dx, dy, this.mDuration);
        }
    }

    private class LoopPagerAdapter : PagerAdapter {
        private var views: List<View>

        constructor(views: List<View>) {
            this.views = views
        }

        override public fun getCount(): Int {
            return 2147483647
        }

        override public fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj;
        }

        override public fun instantiateItem(container: ViewGroup, position: Int): Any? {
            if (this.views.size > 0) {
                var view = this.views.get(position % this.views.size)
                if (container.equals(view.getParent())) {
                    container.removeView(view)
                }

                container.addView(view)
                return view
            } else {
                return null
            }
        }

        override public fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        }
    }

    private class SavedState : BaseSavedState {
        internal var currentPosition: Int = 0

        internal constructor(superState: Parcelable) : super(superState) {}

        private constructor(`in`: Parcel) : super(`in`) {
            this.currentPosition = `in`.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(this.currentPosition)
        }

        companion object {
            val CREATOR: Creator<BannerLayout.SavedState> = object : Creator<BannerLayout.SavedState> {
                override fun createFromParcel(ins: Parcel): BannerLayout.SavedState {
                    return BannerLayout.SavedState(ins)
                }

                override fun newArray(size: Int): Array<BannerLayout.SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }


    public interface OnBannerItemClickListener {
        fun onItemClick(var1: Int)
    }

    enum class Position constructor() {
        centerBottom,
        rightBottom,
        leftBottom,
        centerTop,
        rightTop,
        leftTop
    }

    enum class Shape constructor() {
        rect,
        oval
    }
    
}


