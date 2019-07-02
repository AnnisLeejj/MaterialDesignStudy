package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.RecycleViewSuspension.RecycleViewSuspensionActivity
import com.example.myapplication.scrolling.ScrollingActivity
import kotlinx.android.synthetic.main.activity_guide.*
import kotlinx.android.synthetic.main.guide_scrolling.*

class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        mainToolbar.title = "Material Design Study"
        setSupportActionBar(mainToolbar)


        main_app_bar.setExpanded(false)
        main_app_bar.setOnDragListener { v, event ->
            Toast.makeText(this, "setOnDragListener", Toast.LENGTH_SHORT).show()
            false
        }
        main_app_bar.setOnHoverListener { v, event ->
            Toast.makeText(this, "setOnHoverListener", Toast.LENGTH_SHORT).show()
            false
        }
        main_app_bar.setOnSystemUiVisibilityChangeListener {
            Toast.makeText(this, "setOnSystemUiVisibilityChangeListener", Toast.LENGTH_SHORT).show()
        }
        click()
    }

    private fun click() {
        _2_toolbar.setOnClickListener {
            Intent(this, ToolbarStudyActivity::class.java).run {
                startActivity(this)
            }
        }
        _2_scrolling.setOnClickListener {
            Intent(this, ScrollingActivity::class.java).run {
                startActivity(this)
            }
        }
        _2_recycle_view_suspension.setOnClickListener {
            Intent(this, RecycleViewSuspensionActivity::class.java).run {
                startActivity(this)
            }
        }
    }
}
