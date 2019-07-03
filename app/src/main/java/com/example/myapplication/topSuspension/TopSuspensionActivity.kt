package com.example.myapplication.topSuspension

import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import com.example.myapplication.R

class TopSuspensionActivity : AppCompatActivity(), ObservableScrollView.ScrollViewListener {

    private var scrollView: ObservableScrollView? = null
    private var topBtn1: Button? = null
    private var topBtn2: Button? = null
    private var middleBtn1: Button? = null
    private var middleBtn2: Button? = null
    private var topPanel: View? = null
    private var middlePanel: View? = null
    private var topHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_suspension)
        initViews()
        initListeners()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        val frame = Rect()
        window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top//状态栏高度

        val titleBarHeight =
            (window.findViewById(Window.ID_ANDROID_CONTENT) as View).top//标题栏高度
        topHeight = titleBarHeight + statusBarHeight
    }

    private fun initViews() {
        scrollView = findViewById<ObservableScrollView>(R.id.scrollView)
        topPanel = findViewById(R.id.topPanel)
        topBtn1 = topPanel!!.findViewById(R.id.button1) as Button
        topBtn2 = topPanel!!.findViewById(R.id.button2) as Button

        middlePanel = findViewById(R.id.middlePanel)
        middleBtn1 = middlePanel!!.findViewById(R.id.button1) as Button
        middleBtn2 = middlePanel!!.findViewById(R.id.button2) as Button
    }

    private fun initListeners() {
        topBtn1!!.setOnClickListener {
            middleBtn1!!.setBackgroundColor(Color.WHITE)
            topBtn1!!.setBackgroundColor(Color.WHITE)
        }

        middleBtn1!!.setOnClickListener {
            middleBtn1!!.setBackgroundColor(Color.BLUE)
            topBtn1!!.setBackgroundColor(Color.BLUE)
        }
        scrollView!!.setScrollViewListener(this)
    }

    override fun onScrollChanged(
        scrollView: ObservableScrollView,
        x: Int,
        y: Int,
        oldx: Int,
        oldy: Int
    ) {
        val location = IntArray(2)
        middleBtn1!!.getLocationOnScreen(location)
        val locationY = location[1]
        Log.e("locationY", "$locationY   topHeight的值是：$topHeight")

        if (locationY <= topHeight && (topPanel!!.visibility == View.GONE || topPanel!!.visibility == View.INVISIBLE)) {
            topPanel!!.visibility = View.VISIBLE
        }

        if (locationY > topHeight && topPanel!!.visibility == View.VISIBLE) {
            topPanel!!.visibility = View.GONE
        }
    }
}