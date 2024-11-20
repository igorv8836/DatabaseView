package org.example.databaseview.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class ContractClientModel(
    val contract: Contract,
    val client: Client
)