package com.macrosystems.sixtapp.utils.mapadapters

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.gms.maps.SupportMapFragment

class CustomMapView : SupportMapFragment() {

    private var mListener: OnTouchListener? = null

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = super.onCreateView(inflater, parent, savedInstanceState)
        val frameLayout = TouchableWrapper(activity)
        frameLayout.setBackgroundColor(resources.getColor(R.color.transparent))
        (layout as ViewGroup?)!!.addView(frameLayout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        return layout
    }

    fun setListener(listener: OnTouchListener?) {
        mListener = listener
    }

    interface OnTouchListener {
        fun onTouch()
    }

    inner class TouchableWrapper(context: Context?) : FrameLayout(
        context!!
    ) {
        override fun dispatchTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> mListener!!.onTouch()
                MotionEvent.ACTION_UP -> mListener!!.onTouch()
            }
            return super.dispatchTouchEvent(event)
        }
    }
}