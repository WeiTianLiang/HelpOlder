package com.example.tools.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.example.tools.R
import com.example.tools.view.MyViewPagerAdapter
import com.example.tools.view.addBottomBar
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        addBottomBar(bottomBar)

        val pagerAdapter = MyViewPagerAdapter(getFragmentList(), supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 0
        viewPager.offscreenPageLimit = FRAGMENTNUMBER

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(p0: Int) {
                bottomBar.selectTab(p0)
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageScrollStateChanged(p0: Int) {
            }

        })

        bottomBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabReselected(position: Int) {
            }
        })

        onBaseCreate()
    }

    companion object {
        const val FRAGMENTNUMBER = 3
    }

    protected abstract fun getFragmentList(): ArrayList<Fragment>

    protected abstract fun onBaseCreate()
}
