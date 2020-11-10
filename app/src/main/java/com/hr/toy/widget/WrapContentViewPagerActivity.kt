package com.hr.toy.widget

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hr.toy.R
import com.hr.toy.widget.wcvp.ObjectAtPositionPagerAdapter
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import java.util.*
import kotlin.collections.ArrayList

class WrapContentViewPagerActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wcvp)
        viewPager = findViewById(R.id.viewpager)
        tabs = findViewById(R.id.tablayout)
        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.setCurrentItem(tab!!.position, false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        initTextViewsAdapter()

        // Create global configuration and initialize ImageLoader with this config
        val config = ImageLoaderConfiguration.Builder(this).build()
        ImageLoader.getInstance().init(config)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sample, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        //noinspection SimplifiableIfStatement
        when (id) {
            R.id.action_textviews -> {
                initTextViewsAdapter()
                return true
            }
            R.id.action_imageviews -> initImageViewsAdapter()
            R.id.action_imageviews_async -> initAsyncImageViewsAdapter()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initTextViewsAdapter() {
        val stringArrayList = ArrayList<String>()
        stringArrayList.add(getString(R.string.lorem_short))
        stringArrayList.add(getString(R.string.lorem_medium))
        stringArrayList.add(getString(R.string.lorem_long))
        stringArrayList.add(getString(R.string.lorem_medium))
        stringArrayList.add(getString(R.string.lorem_short))

        val adapter = TextViewPagerAdapter(stringArrayList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }

    private fun initImageViewsAdapter() {
        val resArrayList = ArrayList<Int>()
        resArrayList.add(R.drawable.a100)
        resArrayList.add(R.drawable.a200)
        resArrayList.add(R.drawable.a300)
        resArrayList.add(R.drawable.a400)
        resArrayList.add(R.drawable.a200)
        resArrayList.add(R.drawable.a300)
        resArrayList.add(R.drawable.a100)

        val adapter = ImageViewPagerAdapter(resArrayList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun initAsyncImageViewsAdapter() {
        val resArrayList = java.util.ArrayList<String>()
        resArrayList.add("http://dummyimage.com/500x400/000/fff")
        resArrayList.add("http://dummyimage.com/500x300/040/fff")
        resArrayList.add("http://dummyimage.com/500x200/006/fff")
        resArrayList.add("http://dummyimage.com/500x400/700/fff")
        resArrayList.add("http://dummyimage.com/500x100/006/fff")

        val adapter = AsyncImageViewPagerAdapter(resArrayList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    class TextViewPagerAdapter(strings: ArrayList<String>) : ObjectAtPositionPagerAdapter() {

        private var strings: ArrayList<String> = strings

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view.equals(`object`)
        }

        override fun instantiateItemObject(container: ViewGroup?, position: Int): Any {
            val view = View.inflate(container!!.context, android.R.layout.test_list_item, null)
            (view.findViewById<View>(android.R.id.text1) as TextView).text = strings[position]
            container.addView(view)

            // set Random background
            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            view.setBackgroundColor(color)

            return view
        }

        override fun destroyItemObject(container: ViewGroup?, position: Int, `object`: Any?) {
            container!!.removeView(`object` as View)
        }

        override fun getCount(): Int {
            return strings.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return "View$position"
        }

    }

    class ImageViewPagerAdapter(images: ArrayList<Int>) : ObjectAtPositionPagerAdapter() {

        val images: ArrayList<Int> = images

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItemObject(container: ViewGroup, position: Int): Any {
            val view = ImageView(container.context)
            view.scaleType = ImageView.ScaleType.CENTER_CROP
            view.setImageResource(images[position])
            container.addView(view)
            return view
        }

        override fun destroyItemObject(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return "View$position"
        }

        override fun getCount(): Int {
            return images.size
        }
    }


    class AsyncImageViewPagerAdapter(images: ArrayList<String>) : ObjectAtPositionPagerAdapter() {

        val images: ArrayList<String> = images

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItemObject(container: ViewGroup, position: Int): Any {
            val view = ImageView(container.context)
            view.scaleType = ImageView.ScaleType.CENTER_CROP
            container.addView(view)
            ImageLoader.getInstance().displayImage(images[position], view)
            return view
        }

        override fun destroyItemObject(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return "View$position"
        }

        override fun getCount(): Int {
            return images.size
        }
    }
}