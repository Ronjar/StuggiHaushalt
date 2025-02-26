package com.robingebert.stuggihaushalt.feature_swiping.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.ThumbDown
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.robingebert.stuggihaushalt.feature_swiping.ui.composables.ProposalCard
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun SwipingScreen(viewModel: SwipingViewModel = koinViewModel()) {
    val proposal by viewModel.currentProposal.collectAsState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 30.dp)) {
        proposal?.let { currentProposal ->
            key(currentProposal) {
                val state = rememberSwipeableCardState()
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .weight(1f)
                        .swipableCard(
                            state,
                            onSwiped = { direction -> viewModel.rateProposal(direction) },
                            onSwipeCancel = { }
                        )
                ) {
                    ProposalCard(proposal = currentProposal)
                }
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CircleButton(Icons.Rounded.ThumbDown) {
                        scope.launch {
                            viewModel.rateProposal(Direction.Left)
                            state.swipe(Direction.Left)
                        }
                    }
                    CircleButton(Icons.Rounded.Clear) {
                        scope.launch {
                            viewModel.rateProposal(Direction.Up)
                            state.swipe(Direction.Up)
                        }
                    }
                    CircleButton(Icons.Rounded.ThumbUp) {
                        scope.launch {
                            viewModel.rateProposal(Direction.Right)
                            state.swipe(Direction.Right)
                        }
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Rounded.Timelapse, contentDescription = "Keine Vorschläge")
                Text("Vorschläge werden geladen")
            }
        }
    }
}

@Composable
fun CircleButton(icon: ImageVector, onClick: () -> Unit) {
    FilledTonalIconButton(modifier = Modifier.size(60.dp), onClick = onClick) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = icon,
            contentDescription = "Weniger gut"
        )
    }
}
