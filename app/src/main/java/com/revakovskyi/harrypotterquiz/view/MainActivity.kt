package com.revakovskyi.harrypotterquiz.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.revakovskyi.harrypotterquiz.R
import com.revakovskyi.harrypotterquiz.databinding.ActivityMainBinding
import com.revakovskyi.harrypotterquiz.utils.hideSoftInput
import com.revakovskyi.harrypotterquiz.utils.makeToast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.mainNameText.text
        userName = name.toString()

        binding.mainButtonStart.setOnClickListener {
            if (savedInstanceState == null) {

                if (name.isNullOrBlank()) makeToast(R.string.enter_your_name)
                else {
                    val questionsFragment = QuestionsFragment()

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_main_container, questionsFragment)
                        .add(R.id.fragment_main_container, QuestionsFragment())
                        .addToBackStack(questionsFragment.javaClass.simpleName)
                        .commit()

                    binding.mainButtonStart.hideSoftInput()
                }
            }
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) super.onBackPressed()
        else supportFragmentManager.popBackStack()

    }

    companion object {
        lateinit var userName: String
        const val TOTAL_QUESTIONS: String = "7"
        lateinit var totalCorrectAnswers: String
    }
}