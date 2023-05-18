package ru.tech.imageresizershrinker.main_screen.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiSymbols
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tech.imageresizershrinker.R
import ru.tech.imageresizershrinker.theme.EmojiItem
import ru.tech.imageresizershrinker.theme.outlineVariant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EmojiSheet(
    selectedEmojiIndex: Int,
    emojis: List<ImageVector>,
    onEmojiPicked: (Int) -> Unit,
    visible: MutableState<Boolean>
) {
    var showSheet by visible

    val sheetContent: @Composable ColumnScope.() -> Unit = {
        Column(
            Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            Divider()
            FlowRow(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(
                    4.dp,
                    Alignment.CenterVertically
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    4.dp,
                    Alignment.CenterHorizontally
                )
            ) {
                val selected = selectedEmojiIndex == -1
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .background(
                            MaterialTheme
                                .colorScheme
                                .surfaceVariant
                                .copy(alpha = animateFloatAsState(if (selected) 1f else 0.5f).value),
                            MaterialTheme.shapes.medium
                        )
                        .border(
                            animateDpAsState(
                                if (!selected) {
                                    LocalBorderWidth.current
                                } else {
                                    LocalBorderWidth.current.coerceAtLeast(1.dp) * 2
                                }
                            ).value,
                            MaterialTheme.colorScheme.outlineVariant(
                                animateFloatAsState(if (selected) 0.5f else 0.2f).value
                            ),
                            MaterialTheme.shapes.medium
                        )
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            onEmojiPicked(-1)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    EmojiItem(
                        emoji = null,
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        onNoEmoji = { size ->
                            Icon(
                                imageVector = Icons.Rounded.Block,
                                contentDescription = null,
                                modifier = Modifier.size(size)
                            )
                        }
                    )
                }
                emojis.forEachIndexed { index, emoji ->
                    val emojiSelected = index == selectedEmojiIndex
                    Box(
                        modifier = Modifier
                            .size(58.dp)
                            .background(
                                MaterialTheme
                                    .colorScheme
                                    .surfaceVariant
                                    .copy(alpha = animateFloatAsState(if (emojiSelected) 1f else 0.5f).value),
                                MaterialTheme.shapes.medium
                            )
                            .border(
                                animateDpAsState(
                                    if (!emojiSelected) {
                                        LocalBorderWidth.current
                                    } else {
                                        LocalBorderWidth.current.coerceAtLeast(1.dp) * 2
                                    }
                                ).value,
                                MaterialTheme.colorScheme.outlineVariant(
                                    animateFloatAsState(if (emojiSelected) 0.5f else 0.2f).value
                                ),
                                MaterialTheme.shapes.medium
                            )
                            .clip(MaterialTheme.shapes.medium)
                            .clickable {
                                onEmojiPicked(index)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        EmojiItem(
                            emoji = emoji,
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize
                        )
                    }
                }
            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(end = 16.dp),
            ) {
                TitleItem(text = stringResource(R.string.emoji), icon = Icons.Outlined.EmojiSymbols)
                Spacer(Modifier.weight(1f))
                OutlinedButton(
                    onClick = { showSheet = false },
                    colors = ButtonDefaults.filledTonalButtonColors(),
                    border = BorderStroke(
                        LocalBorderWidth.current, MaterialTheme.colorScheme.outlineVariant()
                    )
                ) {
                    Text(stringResource(R.string.close))
                }
            }
        }
    }

    SimpleSheet(
        nestedScrollEnabled = false,
        sheetContent = sheetContent,
        visible = visible
    )
}