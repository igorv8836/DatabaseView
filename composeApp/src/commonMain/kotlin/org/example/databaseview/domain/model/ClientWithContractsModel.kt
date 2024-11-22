package org.example.databaseview.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class ClientWithContractsModel(
    val client: Client,
    val contracts: List<Contract>
)