package com.revakovskyi.harrypotterquiz.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.revakovskyi.harrypotterquiz.R
import com.revakovskyi.harrypotterquiz.databinding.FragmentResultBinding
import com.revakovskyi.harrypotterquiz.utils.makeToast

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    private lateinit var userName: TextView
    private lateinit var score: TextView
    private lateinit var buttonFinish: Button

    private val totalQuestion: String = "7"
    private val totalCorrectAnswers: String = MainActivity.totalCorrectAnswers

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.makeToast(R.string.success)

        initViews()
        userName.text = MainActivity.userName.toString()
        score.text = "Your Score is $totalCorrectAnswers out of $totalQuestion"

        buttonFinish.setOnClickListener {
            activity?.let {
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
                requireActivity().finishAffinity()
            }
        }
    }

    private fun initViews() {
        userName = binding.userName
        score = binding.score
        buttonFinish = binding.buttonFinish
    }

}