/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

//     TODO (01) Move over the word, score and wordList variables to the GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = GameFragmentBinding.inflate(inflater, container, false)

        // Get the viewModel
        Log.i("GameFragment", "Called ViewModelProvider")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

//         TODO (04) Update these onClickListeners to refer to call methods in the ViewModel then
//         update the UI
        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
            updateWordText()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
            updateWordText()
        }
        viewModel.score.observe(viewLifecycleOwner, Observer {newScore ->
            binding.scoreText.text = newScore.toString()
        })
        viewModel.eventGameFinish.observe(viewLifecycleOwner
            , Observer { hasFinish ->
            if(hasFinish){
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        })
        viewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = newTime.toString()
        })
        return binding.root
    }
    // TODO (02) Move over methods resetList, nextWord, onSkip and onCorrect to the GameViewModel
    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?:0)
        findNavController(this).navigate(action)
        Toast.makeText(this.context,"Game Finished", Toast.LENGTH_SHORT).show()
    }

    /** Methods for updating the UI **/

//    TODO (05) Update these methods to get word and score from the viewModel
    private fun updateWordText() {
        binding.wordText.text = viewModel.word
    }

}
