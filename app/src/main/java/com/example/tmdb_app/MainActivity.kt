package com.example.tmdb_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tmdb_app.Fragment.PopularFragment
import com.example.tmdb_app.Fragment.RatedFragment
import com.example.tmdb_app.Fragment.UpcomingFragment
import com.example.tmdb_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val popularFragment = PopularFragment()
        val ratedFragment = RatedFragment()
        val upcomingFragment = UpcomingFragment()

        setCurrentFragment(popularFragment)

        binding.btnNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.miPopular -> setCurrentFragment(popularFragment)
                R.id.miTopRated -> setCurrentFragment(ratedFragment)
                R.id.miUpComing -> setCurrentFragment(upcomingFragment)
            }
            //因lambda預期會返回boolean值
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fgPopular, fragment)
            commit()
        }
}