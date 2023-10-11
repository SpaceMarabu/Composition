package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    //    переопределяю поведение при нажатии кнопки назад при открытом фрагменте
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        устарело с появлением jetpack navigation
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                retryGame()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        binding.emojiResult.setImageResource(getSmileResId())
        binding.tvRequiredAnswers.text = String.format(
            getString(R.string.required_score),
            gameResult.gameSettings.minCountOfRightAnswers.toString()
        )
        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.required_percentage),
            gameResult.gameSettings.minPercentOfRightAnswers.toString()
        )
        binding.tvScoreAnswers.text = String.format(
            getString(R.string.score_answers),
            gameResult.countOfRightAnswers.toString()
        )
        binding.tvScorePercentage.text = String.format(
            getString(R.string.score_percentage),
            gameResult.gamePercentage.toString()
        )
    }

    private fun getSmileResId(): Int {
        return if (gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    private fun parseArguments() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }


    companion object {

        const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}