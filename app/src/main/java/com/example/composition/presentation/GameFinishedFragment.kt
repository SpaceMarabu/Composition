package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

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
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}

private fun retryGame() {
    requireActivity().supportFragmentManager.popBackStack(
        GameFragment.NAME,
        FragmentManager.POP_BACK_STACK_INCLUSIVE
    )
}

private fun parseArguments() {
    requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
        gameResult = it
    }
}


companion object {

    private const val KEY_GAME_RESULT = "game_result"

    fun newInstance(gameResult: GameResult): GameFinishedFragment {
        return GameFinishedFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_GAME_RESULT, gameResult)
            }
        }
    }
}
}