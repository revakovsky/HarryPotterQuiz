package com.revakovskyi.harrypotterquiz.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.revakovskyi.harrypotterquiz.R
import com.revakovskyi.harrypotterquiz.databinding.ActivityMainBinding
import com.revakovskyi.harrypotterquiz.utils.hideSoftInput
import com.revakovskyi.harrypotterquiz.utils.makeToast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var name: AppCompatEditText
    private lateinit var name2: String

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
        var userName: Editable? = null
        lateinit var totalCorrectAnswers: String
    }
}