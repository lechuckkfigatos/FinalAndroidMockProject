package com.example.pftandroidmockproject.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.presentation.theme.HealthSecondaryText

@Composable
fun <T> ProfileDropdownField(
    label: String,
    selectedValue: T?,
    options: List<T>,
    optionText: @Composable (T) -> String,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.select_option)
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var menuWidthPx by remember {
        mutableIntStateOf(0)
    }

    val density = LocalDensity.current

    val selectedText = selectedValue?.let { value ->
        optionText(value)
    }.orEmpty()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ProfileFieldLabel(
            text = label
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    menuWidthPx = coordinates.size.width
                }
        ) {
            ProfileReadOnlyField(
                value = selectedText,
                placeholder = placeholder,
                trailingContent = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = HealthSecondaryText
                    )
                },
                onClick = {
                    isExpanded = true
                }
            )

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                },
                modifier = Modifier.width(
                    with(density) {
                        menuWidthPx.toDp()
                    }
                )
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = optionText(option)
                            )
                        },
                        onClick = {
                            onSelect(option)
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}