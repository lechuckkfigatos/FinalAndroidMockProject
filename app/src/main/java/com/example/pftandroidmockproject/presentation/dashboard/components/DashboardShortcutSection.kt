package com.example.pftandroidmockproject.presentation.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.presentation.theme.HealthAccent

@Composable
fun DashboardShortcutSection(
    onAddMealClick: () -> Unit,
    onAddActivityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onAddMealClick,
            modifier = Modifier
                .weight(1f)
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = HealthAccent,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.add_meal),
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedButton(
            onClick = onAddActivityClick,
            modifier = Modifier
                .weight(1f)
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(
                width = 1.dp,
                color = HealthAccent
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = HealthAccent
            )
        ) {
            Text(
                text = stringResource(R.string.add_activity),
                fontWeight = FontWeight.Bold
            )
        }
    }
}