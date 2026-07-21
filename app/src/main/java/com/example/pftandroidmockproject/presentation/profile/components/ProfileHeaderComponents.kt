package com.example.pftandroidmockproject.presentation.profile.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.presentation.theme.HealthAccent

@Composable
fun HealthTrackerHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = HealthAccent,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            LeafIcon(
                modifier = Modifier.size(23.dp)
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = HealthAccent
        )
    }
}

@Composable
private fun LeafIcon(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
    ) {
        val stroke = Stroke(
            width = 2.dp.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

        val stemStart = Offset(
            x = size.width * 0.5f,
            y = size.height * 0.84f
        )

        val stemEnd = Offset(
            x = size.width * 0.5f,
            y = size.height * 0.26f
        )

        drawLine(
            color = Color.White,
            start = stemStart,
            end = stemEnd,
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )

        val leftLeaf = Path().apply {
            moveTo(
                size.width * 0.5f,
                size.height * 0.58f
            )

            cubicTo(
                size.width * 0.20f,
                size.height * 0.57f,
                size.width * 0.18f,
                size.height * 0.29f,
                size.width * 0.25f,
                size.height * 0.20f
            )

            cubicTo(
                size.width * 0.48f,
                size.height * 0.22f,
                size.width * 0.59f,
                size.height * 0.43f,
                size.width * 0.5f,
                size.height * 0.58f
            )

            close()
        }

        val rightLeaf = Path().apply {
            moveTo(
                size.width * 0.5f,
                size.height * 0.68f
            )

            cubicTo(
                size.width * 0.80f,
                size.height * 0.65f,
                size.width * 0.85f,
                size.height * 0.38f,
                size.width * 0.77f,
                size.height * 0.28f
            )

            cubicTo(
                size.width * 0.55f,
                size.height * 0.31f,
                size.width * 0.43f,
                size.height * 0.52f,
                size.width * 0.5f,
                size.height * 0.68f
            )

            close()
        }

        drawPath(
            path = leftLeaf,
            color = Color.White,
            style = stroke
        )

        drawPath(
            path = rightLeaf,
            color = Color.White,
            style = stroke
        )
    }
}
