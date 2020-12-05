package com.example.pia_sismov.presentation.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pia_sismov.R
import com.example.pia_sismov.presentation.posts.view.MainDraftsFragment
import com.example.pia_sismov.presentation.posts.view.MainHomeFragment
import com.example.pia_sismov.presentation.posts.view.MainPostsFragment
import com.example.pia_sismov.presentation.posts.view.MainProfileFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(R.style.AppTheme)


        main_view_pager_container.adapter =  ViewPager2Adapter(this)
        TabLayoutMediator(main_tab_layout, main_view_pager_container) { tab, position ->
            when(position){
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
                     tab.text =" "
                }

            }
            main_view_pager_container.setCurrentItem(tab.position, true)
        }.attach()
        main_tab_layout.getTabAt(4)?.view?.isClickable = false
    }


    internal class ViewPager2Adapter(activity:AppCompatActivity) : FragmentStateAdapter(activity){
        private val fragments = ArrayList<Fragment>()
        init {
            fragments.add(MainHomeFragment())
            fragments.add(MainPostsFragment())
            fragments.add(MainDraftsFragment())
            fragments.add(MainProfileFragment())
            fragments.add(MainProfileFragment())
        }
        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }
}