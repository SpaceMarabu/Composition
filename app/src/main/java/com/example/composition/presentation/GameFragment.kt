package com.example.composition.presentation

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val viewModelFactory: GameViewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }

//    private val tvOptions by lazy {
//        mutableListOf<TextView>().apply {
//            add(binding.tvOption1)
//            add(binding.tvOption2)
//            add(binding.tvOption3)
//            add(binding.tvOption4)
//            add(binding.tvOption5)
//            add(binding.tvOption6)
//        }
//    }

    private lateinit var question: Question

    private var percentOfRightAnswers: Int = 0
    private var answer: Int = -1

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeVars()
//        setOnNumberClickListeners()
        viewModel.getGameSettings()
    }

//    private fun setOnNumberClickListeners() {
//        for (i in 0 until tvOptions.size) {
//            tvOptions[i].setOnClickListener {
//                answer = tvOptions[i].text.toString().toInt()
//                viewModel.choseAnswer(answer)
//            }
//        }
//    }


    private fun observeVars() {
//        viewModel.question.observe(viewLifecycleOwner) {
//            question = it
//            initViewsQuestion()
//        }
//        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
//            percentOfRightAnswers = it
//            binding.progressBar.setProgress(it, true)
//        }
//        viewModel.progressAnswers.observe(viewLifecycleOwner) {
//            binding.tvAnswersProgress.text = it.toString()
//        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchFragmentGameFinished(it)
        }
//        viewModel.minPercent.observe(viewLifecycleOwner) {
//            binding.progressBar.secondaryProgress = it
//        }
//        viewModel.enoughCount.observe(viewLifecycleOwner) {
//            val color = getColorByState(it)
//            binding.tvAnswersProgress.setTextColor(color)
//        }
//        viewModel.enoughPercent.observe(viewLifecycleOwner) {
//            val color = getColorByState(it)
//            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
//        }
    }

//    private fun getColorByState(state: Boolean): Int {
//        val colorResId = if (state) {
//            android.R.color.holo_green_light
//        } else {
//            android.R.color.holo_red_light
//        }
//        return ContextCompat.getColor(requireContext(), colorResId)
//    }

//    private fun initViewsQuestion() {
//        binding.tvSum.text = question.sum.toString()
//        binding.tvLeftNumber.text = question.visibleNumber.toString()
//        for (i in 0 until tvOptions.size) {
//            tvOptions[i].text = question.options[i].toString()
//        }
//    }


    private fun launchFragmentGameFinished(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}