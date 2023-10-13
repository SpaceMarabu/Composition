package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.databinding.FragmentChoseLevelBinding
import com.example.composition.domain.entity.Level


class ChoseLevelFragment : Fragment() {

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

        findNavController().navigate(
            ChoseLevelFragmentDirections.actionChoseLevelFragmentToGameFragment(level)
        )
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

}