package com.example.pia_sismov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pia_sismov.fragments.HomeFragment
import com.example.pia_sismov.fragments.MainPostsFragment
import kotlinx.android.synthetic.main.activity_main.*

interface IFragManager{
    fun launchActivity(intent: Intent)
    fun changeFragment(containerViewId: Int,fragment: Fragment, tag: String)
}

class MainActivity : AppCompatActivity(),IFragManager {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(R.style.AppTheme)
        //changeFragment(R.id.mainFrameContainer,MainPostsFragment(this,this),"MainPostsFragment")
        changeFragment(R.id.mainFrameContainer,HomeFragment(this),"HomeFragment")
    }

    override fun changeFragment(containerViewId: Int, fragment: Fragment, tag: String) {
        val currentFragment = supportFragmentManager.findFragmentByTag(tag)
        if (currentFragment == null || currentFragment.isVisible.not()) {
            supportFragmentManager.beginTransaction()
                .replace(containerViewId, fragment, tag)
                .commit()
        }
    }

    override fun launchActivity(intent: Intent) {
        startActivity(intent)
    }
}