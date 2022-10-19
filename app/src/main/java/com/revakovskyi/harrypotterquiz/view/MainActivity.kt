package com.revakovskyi.harrypotterquiz.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.revakovskyi.harrypotterquiz.R
import com.revakovskyi.harrypotterquiz.databinding.ActivityMainBinding
import com.revakovskyi.harrypotterquiz.makeToast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.mainNameText.text
        binding.mainButtonStart.setOnClickListener {
            if (savedInstanceState == null) {

                if (name.isNullOrBlank()) makeToast(R.string.enter_your_name)

                else showFragment(QuestionsFragment(), false)
            }
        }
    }

    fun showFragment(fragment: Fragment, doReplaceFragment: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        val container = R.id.fragment_main_container

        if (doReplaceFragment) {
            transaction
                .replace(container, fragment)
        } else {
            transaction
                .add(container, fragment)
                .addToBackStack(fragment.javaClass.simpleName)
        }
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}