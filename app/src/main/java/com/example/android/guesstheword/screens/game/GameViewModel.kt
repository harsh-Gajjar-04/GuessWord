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

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * ViewModel containing all the logic needed to run the game
 */
class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    private val timer : CountDownTimer
    private val _currentTime : MutableLiveData<Long> = MutableLiveData()
    val currentTime : LiveData<Long> get() = _currentTime

    // TODO (06) Once you've copied over the variables and methods, remove any code referring back
    // to the GameFragment. You can also clean up the log statements from the last step.

    // The current word
    var word = ""
    // The current score
    private val _score : MutableLiveData<Int>  = MutableLiveData()
    val score : LiveData<Int> get() = _score

    private val _eventGameFinish :MutableLiveData<Boolean> = MutableLiveData()
    val eventGameFinish : LiveData<Boolean> get() =  _eventGameFinish

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        _eventGameFinish.value = false
        resetList()
        nextWord()
        _score.value = 0

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                // TODO implement what should happen each tick of the timer
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }
            override fun onFinish() {
                // TODO implement what should happen when the timer finishes
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        word = wordList.removeAt(0)
    }
    /** Methods for buttons presses **/
     fun onSkip() {
        _score.value = _score.value?.minus(1)
        nextWord()
    }
     fun onCorrect() {
        _score.value = _score.value?.plus(1)
        nextWord()
    }

     fun onGameFinishComplete(){
         _eventGameFinish.value = false
     }

}