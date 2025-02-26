package com.robingebert.stuggihaushalt.feature_swiping.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robingebert.stuggihaushalt.feature_swiping.repository.Proposal
import com.robingebert.stuggihaushalt.feature_swiping.repository.ProposalAction
import com.robingebert.stuggihaushalt.feature_swiping.repository.SwipingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SwipingViewModel(private val repository: SwipingRepository) : ViewModel() {

    private val _proposals = MutableStateFlow<List<Proposal>>(emptyList())
    val proposals: StateFlow<List<Proposal>> = _proposals

    init {
        getNewProposals()
    }

    private fun getNewProposals() {
        if (_proposals.value.size > 3) return
        viewModelScope.launch {
            repository.getNewViews().onSuccess {
                _proposals.emit(it)
            }.onFailure {

            }
        }
    }

    fun acceptProposal(proposal: Proposal) {
        _proposals.value = _proposals.value.toMutableList().apply { remove(proposal) }
        viewModelScope.launch {
            repository.rateProposal(proposal, ProposalAction.GOOD)
                .onSuccess {
                    getNewProposals()
                }.onFailure {

                }
        }
    }

    fun rejectProposal(proposal: Proposal) {
        viewModelScope.launch {
            repository.rateProposal(proposal, ProposalAction.BAD)
                .onSuccess {
                    getNewProposals()
                }.onFailure {

                }
        }
    }

    fun whateverProposal(proposal: Proposal) {
        viewModelScope.launch {
            repository.rateProposal(proposal, ProposalAction.WHATEVER)
                .onSuccess {
                    getNewProposals()
                }.onFailure {

                }
        }
    }
}