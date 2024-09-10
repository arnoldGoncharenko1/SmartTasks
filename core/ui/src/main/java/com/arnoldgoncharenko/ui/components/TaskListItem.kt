package com.arnoldgoncharenko.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.arnoldgoncharenko.ui.theme.SmartTasksTheme
import com.houseravenstudios.ui.R

@Composable
fun TaskListItem(
    taskId: String,
    taskTitle: String,
    taskDueDate: String,
    taskDaysLeft: Int?,
    onTaskClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.task_list_corner_radius)),
        color = colorResource(id = R.color.white),
        modifier = modifier.clickable { onTaskClicked(taskId) }
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.margin_16))
                .fillMaxWidth()
        ) {
            Text(
                text = taskTitle,
                style = MaterialTheme.typography.displayMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary,
                thickness = dimensionResource(id = R.dimen.divider_thickness),
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin_8))
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.due_date),
                        style = MaterialTheme.typography.labelSmall.copy(color = colorResource(id = R.color.grey_yellow)),
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.margin_4))
                    )
                    Text(
                        text = taskDueDate,
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = stringResource(id = R.string.days_left),
                        style = MaterialTheme.typography.labelSmall.copy(color = colorResource(id = R.color.grey_yellow)),
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.margin_4))
                    )
                    Text(
                        text = taskDaysLeft.toString(),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskListItemPreview() {
    SmartTasksTheme {
        TaskListItem(
            taskId = "",
            taskTitle = "Task Title",
            taskDueDate = "Apr 23 2016",
            taskDaysLeft = 12,
            onTaskClicked = {}
        )
    }
}