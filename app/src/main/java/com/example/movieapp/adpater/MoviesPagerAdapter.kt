package com.example.movieapp.adpater

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapp.fragment.PopularFragment
import com.example.movieapp.fragment.TopRatedFragment
import com.example.movieapp.models.ResultPopular
import com.example.movieapp.models.ResultTop

class MoviesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        var popularList: ArrayList<ResultPopular>? = null
        var topList: ArrayList<ResultTop>? = null
    }

    override fun getItemCount(): Int = 2 // عدد الفئات

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularFragment() // الفئة الأولى
            1 -> TopRatedFragment() // الفئة الثانية
            else -> PopularFragment()
        }
    }
}
