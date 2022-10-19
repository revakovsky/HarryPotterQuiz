package com.revakovskyi.harrypotterquiz.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.revakovskyi.harrypotterquiz.R
import com.revakovskyi.harrypotterquiz.databinding.FragmentQuastionsBinding
import com.revakovskyi.harrypotterquiz.questions.QuestionsList
import kotlin.properties.Delegates

class QuestionsFragment : Fragment() {

    private lateinit var binding: FragmentQuastionsBinding

    private val questionId = 1

    private lateinit var questionTitle: TextView
    private lateinit var questionBanner: ImageView
    private lateinit var optionOne: TextView
    private lateinit var optionTwo: TextView
    private lateinit var optionThree: TextView
    private lateinit var optionFour: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private var correctAnswer by Delegates.notNull<Int>()

    // todo context
    private val questionsList = QuestionsList(requireActivity()).getQuestions()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuastionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        val currentQuestion = questionsList[questionId - 1]

        progressBar.progress = questionId
        progressText.text = "$questionId/${progressBar.max}"

        questionTitle.text = currentQuestion.question.toString()
        questionBanner.setImageResource(currentQuestion.image)
        optionOne.text = currentQuestion.optionOne.toString()
        optionTwo.text = currentQuestion.optionTwo.toString()
        optionThree.text = currentQuestion.optionThree.toString()
        optionFour.text = currentQuestion.optionFour.toString()
    }

    private fun initViews() {
        questionTitle = binding.questionTitle
        questionBanner = binding.questionBanner
        optionOne = binding.optionOne
        optionTwo = binding.optionTwo
        optionThree = binding.optionThree
        optionFour = binding.optionFour
        progressBar = binding.progressBar
        progressText = binding.progressText
    }

}