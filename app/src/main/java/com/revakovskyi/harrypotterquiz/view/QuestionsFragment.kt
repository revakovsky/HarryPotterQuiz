package com.revakovskyi.harrypotterquiz.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.revakovskyi.harrypotterquiz.R
import com.revakovskyi.harrypotterquiz.databinding.FragmentQuastionsBinding
import com.revakovskyi.harrypotterquiz.questions.QuestionsList
import com.revakovskyi.harrypotterquiz.questions.model.QuestionModel
import com.revakovskyi.harrypotterquiz.utils.AppearanceUtils
import com.revakovskyi.harrypotterquiz.utils.makeToast

class QuestionsFragment : Fragment() {

    private lateinit var binding: FragmentQuastionsBinding
    private var appearanceUtils: AppearanceUtils? = null
    private var questionsList: ArrayList<QuestionModel>? = null

    private var questionId: Int = 1
    private lateinit var questionTitle: TextView
    private lateinit var questionBanner: ImageView
    private lateinit var fieldOption1: TextView
    private lateinit var fieldOption2: TextView
    private lateinit var fieldOption3: TextView
    private lateinit var fieldOption4: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private var correctAnswer: Int = 0

    private var currentQuestionNumber: Int = 1
    private var selectedOptionNumber: Int = 0

    private var optionsList = ArrayList<TextView>()

    private val choosingOption1 = 1
    private val choosingOption2 = 2
    private val choosingOption3 = 3
    private val choosingOption4 = 4
    private val maxAmountOfQuestions = 7

    private var counter = 1
    private var rightChoice = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuastionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appearanceUtils = AppearanceUtils(requireActivity())
        initViews()
        setQuestion()
        optionsList = getOptionsList(fieldOption1, fieldOption2, fieldOption3, fieldOption4)

        fieldOption1.setOnClickListener { selectedOptionFieldLook(fieldOption1, choosingOption1) }
        fieldOption2.setOnClickListener { selectedOptionFieldLook(fieldOption2, choosingOption2) }
        fieldOption3.setOnClickListener { selectedOptionFieldLook(fieldOption3, choosingOption3) }
        fieldOption4.setOnClickListener { selectedOptionFieldLook(fieldOption4, choosingOption4) }

        binding.submitButton.setOnClickListener { setUpAction() }
    }

    private fun setUpAction() {
        if (questionId != currentQuestionNumber) selectedOptionNumber = 0

        if (selectedOptionNumber == 0) createNewQuestions()

         else checkAnswer()
    }

    private fun createNewQuestions() {
        setQuestion()
        appearanceUtils?.setDefaultOptionLook(optionsList)
        chooseButtonText()

        if (appearanceUtils?.isOptionActivated(optionsList) == true) {
            currentQuestionNumber++
            selectedOptionNumber = 0
            counter++
            chooseButtonText()

        } else askToChooseSomething()
    }

    private fun checkAnswer() {
        val question = questionsList?.get(currentQuestionNumber - 1)

        question?.let { checkAnswerWithSelected(it.correctAnswer) }
        chooseButtonText()

        currentQuestionNumber++
        selectedOptionNumber = 0
    }

    private fun setQuestion() {
        if (currentQuestionNumber == maxAmountOfQuestions + 1) showNextScreen()

        questionsList = QuestionsList(requireContext()).getQuestions()
        val question = questionsList!![currentQuestionNumber - 1]

        progressBar.progress = currentQuestionNumber
        progressText.text = "$currentQuestionNumber/${progressBar.max}"

        questionId = question.questionId
        questionTitle.text = question.questionTitle
        questionBanner.setImageResource(question.banner)
        fieldOption1.text = question.optionOne
        fieldOption2.text = question.optionTwo
        fieldOption3.text = question.optionThree
        fieldOption4.text = question.optionFour
        correctAnswer = question.correctAnswer
        binding.questionContainer.background = appearanceUtils?.getQuestionBackground(questionId)
    }

    private fun askToChooseSomething() {
        if (counter == currentQuestionNumber) this.makeToast(R.string.make_choice)
        counter = questionId
    }

    private fun chooseButtonText() {
        if (selectedOptionNumber == 0) {
            binding.submitButton.text = getString(R.string.submit)
        } else if (currentQuestionNumber >= (questionsList?.size ?: questionId)) {
            binding.submitButton.text = getString(R.string.finish)
            MainActivity.totalCorrectAnswers = rightChoice.toString()
        } else {
            binding.submitButton.text = getString(R.string.next_question)
        }
    }

    private fun checkAnswerWithSelected(correctAnswer: Int) {
        if (correctAnswer != selectedOptionNumber) {
            appearanceUtils?.setAnsweredOptionLook(
                selectedOptionNumber,
                R.drawable.incorrect_option_bg,
                optionsList
            )
        } else {
            rightChoice++
        }
        appearanceUtils?.setAnsweredOptionLook(
            correctAnswer,
            R.drawable.correct_option_bg,
            optionsList
        )
    }

    private fun initViews() {
        questionTitle = binding.questionTitle
        questionBanner = binding.questionBanner
        fieldOption1 = binding.optionOne
        fieldOption2 = binding.optionTwo
        fieldOption3 = binding.optionThree
        fieldOption4 = binding.optionFour
        progressBar = binding.progressBar
        progressText = binding.progressText
    }

    private fun selectedOptionFieldLook(fieldOption: TextView, optionNumber: Int) {
        appearanceUtils?.setDefaultOptionLook(optionsList)
        selectedOptionNumber = optionNumber
        appearanceUtils?.setStyle(fieldOption, R.drawable.selected_option_bg)
    }

    private fun getOptionsList(
        fieldOption1: TextView,
        fieldOption2: TextView,
        fieldOption3: TextView,
        fieldOption4: TextView
    ): ArrayList<TextView> {

        val options = ArrayList<TextView>()
        options.add(0, fieldOption1)
        options.add(1, fieldOption2)
        options.add(2, fieldOption3)
        options.add(3, fieldOption4)
        return options
    }

    private fun showNextScreen() {
        currentQuestionNumber = 1
        questionId = 1

        val resultFragment = ResultFragment()
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_main_container, resultFragment)
            .addToBackStack(resultFragment.javaClass.simpleName)
            .commit()
    }

    override fun onDetach() {
        super.onDetach()
        questionsList = null
        appearanceUtils = null
    }

}