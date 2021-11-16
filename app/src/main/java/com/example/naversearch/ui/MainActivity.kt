package com.example.naversearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.naversearch.R
import com.example.naversearch.databinding.ActivityMainBinding
import com.example.naversearch.fragment.BlogFragment
import com.example.naversearch.fragment.CafeFragment
import com.example.naversearch.fragment.ImageFragment
import com.example.naversearch.fragment.NewsFragment


private const val TAG_BLOG = "blog_fragment"
private const val TAG_NEWS = "news_fragment"
private const val TAG_CAFE = "cafe_fragment"
private const val TAG_IMAGE = "image_fragment"


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var btnListener: BtnListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment(TAG_BLOG, BlogFragment())


        with(binding) {
            btnBlogMenu.setOnClickListener {
                setFragment(TAG_BLOG, BlogFragment())
            }

            btnNewsMenu.setOnClickListener {
                setFragment(TAG_NEWS, NewsFragment())

            }
            btnCafeMenu.setOnClickListener {
                setFragment(
                    TAG_CAFE,
                    CafeFragment()
                )
            }
            btnImageMenu.setOnClickListener {
                setFragment(TAG_IMAGE, ImageFragment())
            }
            btnSearch.setOnClickListener { btnListener?.onClickSearch(etInput.text.toString()) }
            btnLookUp.setOnClickListener { btnListener?.onClickLookUp() }
        }


    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager

        // 프래그먼트 트랜잭션 시작
        val ft: FragmentTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            ft.add(R.id.main_frame, fragment, tag)
        }

        val blog = manager.findFragmentByTag(TAG_BLOG)
        val news = manager.findFragmentByTag(TAG_NEWS)
        val cafe = manager.findFragmentByTag(TAG_CAFE)
        val image = manager.findFragmentByTag(TAG_IMAGE)

        // Hide all Fragment
        if (blog != null) {
            ft.hide(blog)
        }
        if (news != null) {
            ft.hide(news)
        }
        if (cafe != null) {
            ft.hide(cafe)
        }
        if (image != null) {
            ft.hide(image)
        }

        // Show  current Fragment
        if (tag == TAG_BLOG) {
            if (blog != null) {
                ft.show(blog)
            }
        }
        if (tag == TAG_NEWS) {
            if (news != null) {
                ft.show(news)
            }
        }
        if (tag == TAG_CAFE) {
            if (cafe != null) {
                ft.show(cafe)
            }
        }
        if (tag == TAG_IMAGE) {
            if (image != null) {
                ft.show(image)
            }
        }

        ft.commitAllowingStateLoss()
    }

    interface BtnListener {
        fun onClickSearch(keword: String)
        fun onClickLookUp()
    }
}