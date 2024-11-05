package com.notes.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notes.R


@Composable
fun AddEditNoteContent(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    currentTime: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        CTextField(
            value = title,
            onValueChange = onTitleChange,
            placeholder = stringResource(R.string.title),
            maxLength = 15,
            textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 20.sp),
            placeholderColor = Color.LightGray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${title.length}/15",
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp)
        )

        Text(
            text = stringResource(
                R.string.characters, currentTime, title.length + description.length
            ),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp, start = 16.dp)
        )

        CTextField(
            value = description,
            onValueChange = onDescriptionChange,
            placeholder = stringResource(R.string.start_typing),
            textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 18.sp),
            placeholderColor = Color.LightGray
        )
    }
}
