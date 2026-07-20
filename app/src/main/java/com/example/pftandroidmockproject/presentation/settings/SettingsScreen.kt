package com.example.pftandroidmockproject.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pftandroidmockproject.R
import com.example.pftandroidmockproject.domain.model.setting.AppLanguage
import com.example.pftandroidmockproject.domain.model.setting.AppThemeMode
import com.example.pftandroidmockproject.presentation.profile.components.HealthTrackerHeader
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundBottom
import com.example.pftandroidmockproject.presentation.theme.HealthBackgroundTop
import com.example.pftandroidmockproject.presentation.theme.HealthGreen

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsContent(
        uiState = uiState,
        onLanguageSelected = viewModel::onLanguageSelected,
        onThemeModeSelected = viewModel::onThemeModeSelected
    )
}

@Composable
private fun SettingsContent(
    uiState: SettingsUiState,
    onLanguageSelected: (AppLanguage) -> Unit,
    onThemeModeSelected: (AppThemeMode) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        HealthBackgroundTop,
                        HealthBackgroundBottom
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StaticSettingsHeader()

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 14.dp,
                    end = 16.dp,
                    bottom = 28.dp
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (uiState.isLoading) {
                    item {
                        SettingsLoadingCard()
                    }
                } else {
                    item {
                        SettingsSectionTitle(
                            title = stringResource(R.string.language)
                        )
                    }

                    item {
                        SettingsOptionCard {
                            SettingsRadioRow(
                                title = stringResource(R.string.vietnamese),
                                selected = uiState.selectedLanguage == AppLanguage.VI,
                                onClick = {
                                    onLanguageSelected(AppLanguage.VI)
                                }
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.english),
                                selected = uiState.selectedLanguage == AppLanguage.EN,
                                onClick = {
                                    onLanguageSelected(AppLanguage.EN)
                                }
                            )
                        }
                    }

                    item {
                        SettingsSectionTitle(
                            title = stringResource(R.string.theme)
                        )
                    }

                    item {
                        SettingsOptionCard {
                            SettingsRadioRow(
                                title = stringResource(R.string.theme_system),
                                selected = uiState.selectedThemeMode == AppThemeMode.SYSTEM,
                                onClick = {
                                    onThemeModeSelected(AppThemeMode.SYSTEM)
                                }
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.theme_light),
                                selected = uiState.selectedThemeMode == AppThemeMode.LIGHT,
                                onClick = {
                                    onThemeModeSelected(AppThemeMode.LIGHT)
                                }
                            )

                            SettingsRadioRow(
                                title = stringResource(R.string.theme_dark),
                                selected = uiState.selectedThemeMode == AppThemeMode.DARK,
                                onClick = {
                                    onThemeModeSelected(AppThemeMode.DARK)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StaticSettingsHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = HealthBackgroundTop.copy(alpha = 0.96f)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HealthTrackerHeader(
            title = stringResource(R.string.health_tracker)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(R.string.settings_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SettingsOptionCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun SettingsSectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(21.dp)
                .background(
                    color = HealthGreen,
                    shape = RoundedCornerShape(50)
                )
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun SettingsRadioRow(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                color = if (selected) {
                    HealthGreen.copy(alpha = 0.08f)
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(14.dp)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 9.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = HealthGreen,
                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = if (selected) {
                HealthGreen
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

@Composable
private fun SettingsLoadingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = HealthGreen
            )
        }
    }
}
