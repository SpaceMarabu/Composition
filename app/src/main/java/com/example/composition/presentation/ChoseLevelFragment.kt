package com.example.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentChoseLevelBinding
import com.example.composition.databinding.FragmentWelcomeBinding
import com.example.composition.domain.entity.Level
import java.lang.RuntimeException


class ChoseLevelFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    private var _binding: FragmentChoseLevelBinding? = null
    private val binding: FragmentChoseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChoseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }



    private fun launchGameByLevel(level: Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnButtonClickListeners()
    }

    private fun setOnButtonClickListeners() {
        with(binding) {
            buttonLevelEasy.setOnClickListener {
                launchGameByLevel(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                launchGameByLevel(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                launchGameByLevel(Level.HARD)
            }
            buttonLevelTest.setOnClickListener {
                launchGameByLevel(Level.TEST)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val NAME = "ChoseLevelFragment"
        fun newInstance(): ChoseLevelFragment {
            return ChoseLevelFragment()
        }
    }
}