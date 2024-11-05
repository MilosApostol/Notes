package com.notes.custom

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.notes.R
import com.notes.utils.toFormattedDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CNoteField(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    date: Long,
    onClick: (Int) -> Unit,
    id: Int = -1
) {
    var isLarge by remember { mutableStateOf(false) }

    val descriptionMaxLines = if (isLarge) Int.MAX_VALUE else 2
    val backgroundColor = remember(id) {
        Color(
            red = (id * 37 % 256) / 255f,
            green = (id * 57 % 256) / 255f,
            blue = (id * 77 % 256) / 255f
        )
    }

    val luminance = (0.299 * backgroundColor.red + 0.587 * backgroundColor.green + 0.114 * backgroundColor.blue)
    val textColor = if (luminance > 0.5) Color.Black else Color.White

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .combinedClickable(onClick = { onClick(id) })
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    fontSize = 16.sp,
                )
                Text(
                    text = date.toFormattedDate(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
                fontSize = 16.sp,
                maxLines = descriptionMaxLines,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isLarge) stringResource(R.string.show_less) else stringResource(R.string.read_more),
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { isLarge = !isLarge }
            )
        }
    }
}

@Composable
fun SwipeToDismissNoteField(
    title: String,
    description: String,
    date: Long,
    onClick: (Int) -> Unit,
    onRemove: () -> Unit,
    id: Int = -1,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val swipeToDismissState = rememberSwipeToDismissBoxState(confirmValueChange = { direction ->
        if (direction == SwipeToDismissBoxValue.EndToStart) {
            scope.launch {
                delay(100)
                onRemove()
            }
            true
        } else false
    })

    SwipeToDismissBox(state = swipeToDismissState, backgroundContent = {
        val backgroundColor by animateColorAsState(
            targetValue = when (swipeToDismissState.currentValue) {
                SwipeToDismissBoxValue.EndToStart -> Color.Transparent
                else -> Color.Transparent
            }, animationSpec = tween(100)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(end = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete),
                tint = Color.Black
            )
        }
    }) {
        CNoteField(
            modifier = modifier,
            title = title,
            description = description,
            date = date,
            onClick = onClick,
            id = id
        )
    }
}