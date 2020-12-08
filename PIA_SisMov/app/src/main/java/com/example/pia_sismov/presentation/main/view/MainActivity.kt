package com.example.pia_sismov.presentation.main.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pia_sismov.R
import com.example.pia_sismov.presentation.account.view.LoginActivity
import com.example.pia_sismov.presentation.main.IMainContract
import com.example.pia_sismov.presentation.main.presenter.MainPresenter
import com.example.pia_sismov.presentation.posts.view.*
import com.google.android.material.tabs.TabLayoutMediator
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<IMainContract.IView, MainPresenter>(),IMainContract.IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        main_view_pager_container.adapter = ViewPager2Adapter(this,this)
        TabLayoutMediator(main_tab_layout, main_view_pager_container) { tab, position ->
            when (position) {
                0 -> {
                    // tab.text = "Inicio"
                    tab.setIcon(R.drawable.ic_inicio)
                }
                1 -> {
                    //  tab.text ="Mis Posts"
                    tab.setIcon(R.drawable.ic_publicaciones)
                }
                2 -> {
                    //  tab.text ="Guardados"
                    tab.setIcon(R.drawable.ic_guardados)
                }
                3 -> {
                    //   tab.text ="Perfil"
                    tab.setIcon(R.drawable.ic_perfil)
                }
                4 -> {
                    tab.text = " "
                }

            }
            main_view_pager_container.setCurrentItem(tab.position, true)
        }.attach()
        main_tab_layout.getTabAt(4)?.view?.isClickable = false

        btn_add_new_post.setOnClickListener{
            onAddNewPostClick()
        }
    }

    override fun onAddNewPostClick(){
        val intent = Intent(this, NewPostActivity::class.java)
        startActivity(intent)
    }

    override fun finishActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



    override fun getLayout() = R.layout.activity_main

    override fun instantiatePresenter() = MainPresenter()

    internal class ViewPager2Adapter(activity: AppCompatActivity,parentView: IMainContract.IView) : FragmentStateAdapter(activity) {
        private val fragments = ArrayList<Fragment>()

        init {
            fragments.add(MainHomeFragment(activity))
            fragments.add(MainPostsFragment(activity))
            fragments.add(MainDraftsFragment(activity))
            fragments.add(MainProfileFragment(activity,parentView))
            fragments.add(MainProfileFragment(activity,parentView))
        }

        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }

}