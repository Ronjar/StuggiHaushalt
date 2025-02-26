package com.robingebert.stuggihaushalt.feature_swiping.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.robingebert.stuggihaushalt.feature_swiping.repository.Proposal
import com.robingebert.stuggihaushalt.feature_swiping.ui.composables.ProposalCard
import com.spartapps.swipeablecards.state.rememberSwipeableCardsState
import com.spartapps.swipeablecards.ui.SwipeableCardDirection
import com.spartapps.swipeablecards.ui.lazy.LazySwipeableCards
import com.spartapps.swipeablecards.ui.lazy.items
import org.koin.androidx.compose.koinViewModel

@Composable
fun SwipingScreen(viewModel: SwipingViewModel = koinViewModel()) {
    val proposals by viewModel.proposals.collectAsState()

    val state = rememberSwipeableCardsState(
        initialCardIndex = 0,
        itemCount = { 10 }
    )

    LazySwipeableCards<Proposal>(
        modifier = Modifier.padding(10.dp),
        state = state,
        onSwipe = { proposal, direction ->
            when (direction) {
                SwipeableCardDirection.Right -> {
                    viewModel.acceptProposal(proposal)
                }
                SwipeableCardDirection.Left -> {
                    viewModel.rejectProposal(proposal)
                }
            }
        }
    ) {
        items(proposals) { profile, index, offset ->
            ProposalCard(
                proposal = profile
            )
        }
    }
}