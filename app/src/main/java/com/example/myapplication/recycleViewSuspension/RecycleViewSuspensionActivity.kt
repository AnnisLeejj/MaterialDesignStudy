package com.example.myapplication.recycleViewSuspension

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.squareup.picasso.Picasso

class RecycleViewSuspensionActivity : AppCompatActivity() {
    private var mFeedList: RecyclerView? = null
    private var mSuspensionBar: RelativeLayout? = null
    private var mSuspensionTv: TextView? = null
    private var mSuspensionIv: ImageView? = null

    private var mCurrentPosition = 0
    private var mSuspensionBarHeight: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view_suspension)

        mSuspensionBar = findViewById<RelativeLayout>(R.id.suspension_bar)
        mSuspensionTv = findViewById<TextView>(R.id.tv_nickname)
        mSuspensionIv = findViewById<ImageView>(R.id.iv_avatar)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        toolbar.inflateMenu(R.menu.menu_main)
//        toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener() {
//            fun onMenuItemClick(item: MenuItem): Boolean {
//                if (item.itemId == R.id.item_jump) {
//                    val intent = Intent(this@MainActivity, MultiActivity::class.java)
//                    startActivity(intent)
//                }
//                return false
//            }
//        })

        val linearLayoutManager = LinearLayoutManager(this)
        val adapter = FeedAdapter()

        mFeedList = findViewById<RecyclerView>(R.id.feed_list)
        mFeedList!!.layoutManager = linearLayoutManager
        mFeedList!!.adapter = adapter
        mFeedList!!.setHasFixedSize(true)


        mFeedList!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mSuspensionBarHeight = mSuspensionBar!!.height
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //获取当前位置后面的一个 View
                val view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1)
                //layout的位置 中的top
                val top = view?.top ?: 0
                if (top < mSuspensionBarHeight) {
                    mSuspensionBar?.y = (-(mSuspensionBarHeight - top)).toFloat()
                } else {
                    mSuspensionBar?.y = 0f
                }
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()

                Log.d(
                    "HHHH",
                    "view.getTop(): $top     mCurrentPosition:$mCurrentPosition    firstVisibleItemPosition:$firstVisibleItemPosition"
                )
                //如果 当前位置不是  mCurrentPosition
                //设置 mSuspensionBar 复位
                if (mCurrentPosition != firstVisibleItemPosition) {
                    mCurrentPosition = firstVisibleItemPosition

                    //刚好到临界值时 mSuspensionBar 的UI更新了,而位置没有更新
                    //                    mSuspensionBar.setY(0);
                    //                    updateSuspensionBar();
                    mSuspensionBar?.y = (-mSuspensionBarHeight).toFloat()
                    updateSuspensionBar()
                }
            }
        })

        updateSuspensionBar()

    }

    private fun updateSuspensionBar() {
        Log.d("HHHH", "updateSuspensionBar: $mCurrentPosition")
        Picasso.with(this@RecycleViewSuspensionActivity)
            .load(getAvatarResId(mCurrentPosition))
            .centerInside()
            .fit()
            .into(mSuspensionIv)

        mSuspensionTv!!.text = "Taeyeon $mCurrentPosition"
    }

    private fun getAvatarResId(position: Int): Int {
        when (position % 4) {
            0 -> return R.drawable.avatar1
            1 -> return R.drawable.avatar2
            2 -> return R.drawable.avatar3
            3 -> return R.drawable.avatar4
        }
        return 0
    }
}