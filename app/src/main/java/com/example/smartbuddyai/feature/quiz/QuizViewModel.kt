package com.example.smartbuddyai.feature.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbuddyai.core.data.repository.FirebaseQuizRepository
import com.example.smartbuddyai.data.model.Quiz
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val repository = FirebaseQuizRepository()

    var quiz by mutableStateOf<Quiz?>(null)
        private set

    var currentQuestionIndex by mutableIntStateOf(0)
        private set

    var selectedIndex by mutableStateOf<Int?>(null)
        private set

    var score by mutableIntStateOf(0)
        private set

    val totalQuestions: Int
        get() = quiz?.questions?.size ?: 0

    val isLastQuestion: Boolean
        get() = currentQuestionIndex == totalQuestions - 1

    fun loadQuiz(videoId: String) {
        viewModelScope.launch {
            quiz = repository.getQuiz(videoId)
            resetQuiz()
        }
    }

    fun nextQuestion() {
        if (!isLastQuestion) {
            currentQuestionIndex++
            selectedIndex = null
        }
    }

    fun selectAnswer(index: Int) {
        if (selectedIndex != null) {
            return
        }
        selectedIndex = index
        val correct = quiz?.questions?.get(currentQuestionIndex)?.correctIndex

        if (index == correct) {
            score++
        }
    }

    fun resetQuiz() {
        currentQuestionIndex = 0
        selectedIndex = null
        score = 0
    }
}