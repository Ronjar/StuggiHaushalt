package com.robingebert.stuggihaushalt.feature_swiping.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.robingebert.stuggihaushalt.feature_swiping.repository.Proposal

@Composable
fun ProposalCard(
    modifier: Modifier = Modifier,
    proposal: Proposal,
) {
    Card(modifier = modifier.then(Modifier.height(500.dp))) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = proposal.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(

                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BadgeWithIcon(Icons.Rounded.LocationOn, proposal.county)
                Spacer(modifier = Modifier.width(8.dp))
                BadgeWithIcon(Icons.Rounded.Bookmark, proposal.category)
                Spacer(modifier = Modifier.width(8.dp))
                BadgeWithIcon(Icons.Rounded.AttachMoney, proposal.effect)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = proposal.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun BadgeWithIcon(icon: ImageVector, text: String){
    Badge(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor =  MaterialTheme.colorScheme.onTertiaryContainer
    ) {
        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = icon,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = text, style = MaterialTheme.typography.labelSmall)
        }
    }
}