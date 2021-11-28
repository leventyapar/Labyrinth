package com.leventyapar.labyrinth.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.leventyapar.labyrinth.R
import com.leventyapar.labyrinth.databinding.FragmentLevelBinding


class LevelFragment : Fragment() {

    private lateinit var binding : FragmentLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLevelBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            level1Button.setOnClickListener {
                val bundle = bundleOf("level" to 1, "length" to 15 , "square" to 10 )
                findNavController().navigate(R.id.action_levelFragment_to_gameFragment,bundle)
            }
            level2Button.setOnClickListener {
                val bundle = bundleOf("level" to 2, "length" to 15, "square" to 15  )
                findNavController().navigate(R.id.action_levelFragment_to_gameFragment,bundle)
            }
            level3Button.setOnClickListener {
                val bundle = bundleOf("level" to 3, "length" to 15,"square" to 20 )
                findNavController().navigate(R.id.action_levelFragment_to_gameFragment,bundle)
            }

        }

    }

}