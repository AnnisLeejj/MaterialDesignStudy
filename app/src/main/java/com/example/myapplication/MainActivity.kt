package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.AccessibleObject.setAccessible



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.toolbar.title = "Title"
        this.toolbar.subtitle = "subTitle"
//        this.toolbar.setLogo(R.mipmap.ic_launcher_round)
        setSupportActionBar(toolbar)
    }

    var mSearchView: SearchView? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        val findItem = menu?.findItem(R.id.app_bar_search)
        mSearchView = findItem?.actionView as SearchView?
        setSearchView()
        return true
    }

    private fun setSearchView() {
        /*------------------ SearchView有三种默认展开搜索框的设置方式，区别如下： ------------------*/
        //设置搜索框直接展开显示。左侧有放大镜(在搜索框中) 右侧有叉叉 可以关闭搜索框
//        mSearchView?.isIconified = false
        //设置搜索框直接展开显示。左侧有放大镜(在搜索框外) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
//        mSearchView?.setIconifiedByDefault(false)
        //设置搜索框直接展开显示。左侧有无放大镜(在搜索框中) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
//        mSearchView?.onActionViewExpanded()


        //设置最大宽度
//        mSearchView?.maxWidth = 10_00
        //设置是否显示搜索框展开时的提交按钮
        mSearchView?.isSubmitButtonEnabled = true
        //设置输入框提示语
        mSearchView?.queryHint = "hint"
        mSearchView?.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, "onQueryTextSubmit:$query", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(this@MainActivity, "onQueryTextChange:$newText", Toast.LENGTH_SHORT).show()
                return false
            }
        })
        mSearchView?.setOnSearchClickListener {
            Toast.makeText(this, "Open", Toast.LENGTH_SHORT).show()
        }
    }
    // 让菜单同时显示图标和文字
    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
        if (menu != null) {
            if (menu.javaClass.simpleName.equals("MenuBuilder", ignoreCase = true)) {
                try {
                    val method = menu.javaClass.getDeclaredMethod(
                        "setOptionalIconsVisible",
                        java.lang.Boolean.TYPE
                    )
                    method.isAccessible = true
                    method.invoke(menu, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        return super.onMenuOpened(featureId, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Toast.makeText(this, "${item?.title} 被点击", Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
}
