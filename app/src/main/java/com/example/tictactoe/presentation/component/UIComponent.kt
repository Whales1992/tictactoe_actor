package com.example.tictactoe.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class UIComponent {

    @Composable
    fun ButtonUI(){
        val selected = remember { mutableStateOf(false) }

        return Button(
            colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected.value) Color.Blue else Color.Gray,
            contentColor = MaterialTheme.colors.primary),
            onClick = {selected.value = !selected.value },
            modifier = Modifier
                .padding(7.dp)
                .width(30.dp)
                .height(30.dp),
            enabled = true,
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            )
        ) {
        }
    }

}