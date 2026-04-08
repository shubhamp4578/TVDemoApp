package com.example.smartbuddyai.feature.quiz

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.smartbuddyai.feature.quiz.components.QuizOption
@Composable
fun QuizScreen(
    videoId: String,
    viewModel: QuizViewModel,
    onQuizFinished: () -> Unit
) {
    val quiz = viewModel.quiz

    LaunchedEffect(Unit) {
        viewModel.loadQuiz(videoId)
    }
    if (quiz == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading...", color = Color.White)
        }
        return
    }

    val question = quiz.questions[viewModel.currentQuestionIndex]

    val optionFocus = remember(question) {
        List(question.options.size) { FocusRequester() }
    }

    val nextFocus = remember { FocusRequester() }

    LaunchedEffect(viewModel.currentQuestionIndex) {
        optionFocus.firstOrNull()?.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = question.question,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            question.options.forEachIndexed { index, option ->

                var isFocused by remember(index) { mutableStateOf(false) }

                QuizOption(
                    text = option,
                    isFocused = isFocused,
                    isSelected = viewModel.selectedIndex == index,
                    isCorrect = question.correctIndex == index,
                    showResult = viewModel.selectedIndex != null,
                    onClick = {
                        viewModel.selectAnswer(index)
                    },
                    modifier = Modifier
                        .focusRequester(optionFocus[index])
                        .onFocusChanged { isFocused = it.isFocused }
                        .onKeyEvent {
                            if (it.type == KeyEventType.KeyDown) {
                                when (it.key) {
                                    Key.DirectionDown -> {
                                        if (index == optionFocus.lastIndex) {
                                            nextFocus.requestFocus()
                                        } else {
                                            optionFocus.getOrNull(index + 1)?.requestFocus()
                                        }
                                        true
                                    }

                                    Key.DirectionUp -> {
                                        optionFocus.getOrNull(index - 1)?.requestFocus()
                                        true
                                    }

                                    else -> false
                                }
                            } else false
                        }
                )
            }
        }

        if (viewModel.selectedIndex != null) {

            Spacer(modifier = Modifier.height(30.dp))

            var isFocused by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .focusRequester(nextFocus)
                    .onFocusChanged { isFocused = it.isFocused }
                    .background(
                        if (isFocused) Color(0xFFEEC200) else Color.DarkGray,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 32.dp, vertical = 14.dp)
                    .focusable()
                    .onKeyEvent {
                        if (it.type == KeyEventType.KeyDown &&
                            (it.key == Key.Enter || it.key == Key.DirectionCenter)
                        ) {
                            if (viewModel.isLastQuestion) {
                                onQuizFinished()
                            } else {
                                viewModel.nextQuestion()
                            }
                            true
                        } else false
                    }
            ) {
                Text(
                    text = if (viewModel.isLastQuestion) "Finish ->" else "Next ->",
                    color = if (isFocused) Color.Black else Color.White
                )
            }

            LaunchedEffect(viewModel.selectedIndex) {
                nextFocus.requestFocus()
            }
        }
    }
}