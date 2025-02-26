package com.robingebert.stuggihaushalt.feature_swiping.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexstyl.swipeablecard.Direction
import com.robingebert.stuggihaushalt.feature_swiping.repository.Proposal
import com.robingebert.stuggihaushalt.feature_swiping.repository.ProposalAction
import com.robingebert.stuggihaushalt.feature_swiping.repository.SwipingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SwipingViewModel(private val repository: SwipingRepository) : ViewModel() {

    private val _proposals = MutableStateFlow<List<Proposal>>(emptyList())
    val proposals: StateFlow<List<Proposal>> = _proposals

    private val _currentProposal = MutableStateFlow<Proposal?>(null)
    val currentProposal: StateFlow<Proposal?> = _currentProposal

    init {
        loadProposals()
    }

    private fun loadProposals() {
        viewModelScope.launch {
            repository.getNewViews().onSuccess { proposals ->
                _proposals.value = proposals
                updateCurrentProposal()
            }
        }
    }

    private fun updateCurrentProposal() {
        _currentProposal.value = _proposals.value.firstOrNull()
    }

    private fun removeCurrentProposal() {
        _proposals.update { it.drop(1) }
        updateCurrentProposal()
        if (_proposals.value.size < 3) loadProposals()
    }

    fun rateProposal(direction: Direction) {
        val proposal = _currentProposal.value ?: return
        val action = when (direction) {
            Direction.Right -> ProposalAction.GOOD
            Direction.Left -> ProposalAction.BAD
            else -> ProposalAction.WHATEVER
        }
        viewModelScope.launch {
            repository.rateProposal(proposal, action).onSuccess {
                removeCurrentProposal()
            }
        }
    }
}