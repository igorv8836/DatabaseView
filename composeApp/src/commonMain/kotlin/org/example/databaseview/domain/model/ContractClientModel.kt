package org.example.databaseview.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class ContractClientModel(
    val contract: Contract,
    val client: Client
) {
    fun getDescription(): String {
        if (contract.id == -1) return ""
        return "ID: ${contract.id} - " +
                "${client.fullName} - " +
                "Срок: ${contract.deadline}"
    }
}