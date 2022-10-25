package com.revakovskyi.harrypotterquiz.view

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.revakovskyi.harrypotterquiz.utils.hideSoftInput
import com.revakovskyi.harrypotterquiz.utils.makeToast
import com.revakovskyi.harrypotterquiz.view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var name: AppCompatEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = binding.mainNameText
        userName = name.text

        binding.mainButtonStart.setOnClickListener {
            if (savedInstanceState == null) {

                if (name.text.isNullOrEmpty()) makeToast(R.string.enter_your_name)
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
        const val MAX_AMOUNT_OF_QUESTIONS = 7
        var userName: Editable? = null
        lateinit var totalCorrectAnswers: String
    }
}