package com.notes.custom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun CTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    maxLength: Int = Int.MAX_VALUE,
    textStyle: TextStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 18.sp),
    placeholderColor: Color = Color.Gray,
    backgroundColor: Color = Color.White,
    focusedIndicatorColor: Color = Color.Transparent,
    unfocusedIndicatorColor: Color = Color.Transparent
) {
    TextField(
        value = value,
        onValueChange = { newText ->
            if (newText.length <= maxLength) {
                onValueChange(newText)
            }
        },
        placeholder = {
            Text(
                text = placeholder,
                color = placeholderColor,
                fontSize = textStyle.fontSize
            )
        },
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            focusedIndicatorColor = focusedIndicatorColor,
            unfocusedIndicatorColor = unfocusedIndicatorColor
        ),
        textStyle = textStyle
    )
}