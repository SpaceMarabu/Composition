package com.example.composition.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentChoseLevelBinding
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question

class GameFragment : Fragment() {

    private lateinit var level: Level
    private lateinit var gameSettings: GameSettings
    private lateinit var question: Question
    private lateinit var viewModel: GameViewModel
    private var textProgress = ""
    private var textProgressMask = ""
    private var minCountOfRightAnswers: Int = -1
    private var countOfRightAnswers: Int = 0

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        observeGameSettings()
//        binding.tvLeftNumber.setOnClickListener {
//            launchFragmentGameFinished(GameResult(
//                true,
//                0,
//                0,
//                GameSettings(
//                    0,
//                    0,
//                    0,
//                    0
//                )
//            ))
//        }
    }

    private fun setOnClickListeners() {
        binding.tvOption1.setOnClickListener {

            if (checkAnswer(binding.tvOption1.text.toString())) {
                countOfRightAnswers = countOfRightAnswers + 1

            }
        }
    }

    private fun checkAnswer(userAnswer: String): Boolean {
        return viewModel.checkAnswer(
            userAnswer,
            binding.tvLeftNumber.text.toString(),
            question.sum.toString()
        )
    }

    private fun observeGameSettings() {
        viewModel.getGameSettings(level)
        viewModel.gameSettings.observe(viewLifecycleOwner) {
            gameSettings = it
            viewModel.getQuestion(it.maxSumValue)
        }
        viewModel.question.observe(viewLifecycleOwner) {
            question = it
            initViews()
        }
    }

    private fun initViews() {
        binding.tvSum.text = question.sum.toString()
        binding.tvLeftNumber.text = question.visibleNumber.toString()
        binding.tvOption1.text = question.options[0].toString()
        binding.tvOption2.text = question.options[1].toString()
        binding.tvOption3.text = question.options[2].toString()
        binding.tvOption4.text = question.options[3].toString()
        binding.tvOption5.text = question.options[4].toString()
        binding.tvOption6.text = question.options[5].toString()
        textProgressMask = resources.getString(R.string.progress_answers)
        textProgress = String.format(
            textProgressMask,
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        binding.tvAnswersProgress.text = textProgress
    }

    private fun launchFragmentGameFinished(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object {

        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): Fragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}