package com.revakovskyi.harrypotterquiz.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.revakovskyi.harrypotterquiz.R
import com.revakovskyi.harrypotterquiz.databinding.FragmentQuastionsBinding
import com.revakovskyi.harrypotterquiz.models.QuestionModel
import com.revakovskyi.harrypotterquiz.questions.QuestionsList

class QuestionsFragment : Fragment() {

    private lateinit var binding: FragmentQuastionsBinding

    private var currentQuestionNumber: Int = 1
    private var selectedOptionNumber: Int = 0

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

    private lateinit var questionsList: ArrayList<QuestionModel>

    private val choosingOption1 = 1
    private val choosingOption2 = 2
    private val choosingOption3 = 3
    private val choosingOption4 = 4

    private var counter = 1


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
        setQuestion()

        fieldOption1.setOnClickListener { selectedOptionFieldLook(fieldOption1, choosingOption1) }
        fieldOption2.setOnClickListener { selectedOptionFieldLook(fieldOption2, choosingOption2) }
        fieldOption3.setOnClickListener { selectedOptionFieldLook(fieldOption3, choosingOption3) }
        fieldOption4.setOnClickListener { selectedOptionFieldLook(fieldOption4, choosingOption4) }

        binding.submitButton.setOnClickListener { setUpAction() }
    }

    private fun setUpAction() {
        //todo delete
        showLogs()

        if (questionId != currentQuestionNumber) selectedOptionNumber = 0
        if (selectedOptionNumber == 0) {
            makeActionForOtherQuestions()
        } else {
            makeActionFirstQuestion()
        }
    }

    private fun makeActionForOtherQuestions() {
        setOptionFieldsDefaultLook()
        setQuestion()
        chooseNextButtonLook()

        if (isOptionActivated()) {
            currentQuestionNumber++
            selectedOptionNumber = 0
            counter++
            chooseNextButtonLook()

        } else askToChooseSomething()
    }

    private fun makeActionFirstQuestion() {
        val question = questionsList[currentQuestionNumber - 1]

        checkAnswerWithSelected(question.correctAnswer)
        chooseNextButtonLook()

        currentQuestionNumber++
        selectedOptionNumber = 0
    }

    private fun askToChooseSomething() {
        if (counter == currentQuestionNumber) makeToast(R.string.make_choice)
        counter = questionId
    }

    private fun showLogs() {
        Log.d("TAG", "questionId: $questionId")
        Log.d("TAG", "currentQuestionNumber: $currentQuestionNumber")
        Log.d("TAG", "selectedOptionNumber: $selectedOptionNumber")
        Log.d("TAG", "correctAnswer: $correctAnswer")
        Log.d("TAG", "counter: $counter")
        Log.d("TAG", "")
    }

    private fun chooseNextButtonLook() {
        if (selectedOptionNumber == 0) {
            binding.submitButton.text = getString(R.string.submit)
        } else if (currentQuestionNumber >= questionsList.size) {
            binding.submitButton.text = getString(R.string.finish)
            makeToast(R.string.success)
        } else {
            binding.submitButton.text = getString(R.string.next_question)
        }
    }

    private fun checkAnswerWithSelected(correctAnswer: Int) {
        if (correctAnswer != selectedOptionNumber) {
            setAnsweredOptionFieldsLook(selectedOptionNumber, R.drawable.incorrect_option_bg)
        }
        setAnsweredOptionFieldsLook(correctAnswer, R.drawable.correct_option_bg)
    }

    private fun isOptionActivated(): Boolean {
        return fieldOption1.isActivated ||
                fieldOption2.isActivated ||
                fieldOption3.isActivated ||
                fieldOption4.isActivated
    }

    private fun makeToast(message: Int) {
        Toast.makeText(
            requireContext(),
            resources.getString(message),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setQuestion() {
        questionsList = QuestionsList(requireContext()).getQuestions()
        val question = questionsList[currentQuestionNumber - 1]

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

    private fun setOptionFieldsDefaultLook() {
        val options = ArrayList<TextView>()
        options.add(0, fieldOption1)
        options.add(1, fieldOption2)
        options.add(2, fieldOption3)
        options.add(3, fieldOption4)

        for (option in options) {
            option.setTextColor(getColor(R.color.gray))
            option.background = getBackground(R.drawable.option_bg)
        }
    }

    private fun selectedOptionFieldLook(fieldOption: TextView, optionNumber: Int) {
        setOptionFieldsDefaultLook()
        selectedOptionNumber = optionNumber
        setStyle(fieldOption, R.drawable.selected_option_bg)
    }

    private fun setAnsweredOptionFieldsLook(answer: Int, drawableResource: Int) {
        when (answer) {
            choosingOption1 -> setStyle(fieldOption1, drawableResource)
            choosingOption2 -> setStyle(fieldOption2, drawableResource)
            choosingOption3 -> setStyle(fieldOption3, drawableResource)
            choosingOption4 -> setStyle(fieldOption4, drawableResource)
        }
    }

    private fun setStyle(fieldOption: TextView, drawableResource: Int) {
        fieldOption.setTextColor(getColor(R.color.base))
        fieldOption.background = getBackground(drawableResource)
    }

    private fun getBackground(drawableResource: Int): Drawable? {
        return ResourcesCompat.getDrawable(
            requireActivity().resources, drawableResource, null
        )
    }

    private fun getColor(colorResource: Int): Int {
        return ResourcesCompat.getColor(requireActivity().resources, colorResource, null)
    }
}