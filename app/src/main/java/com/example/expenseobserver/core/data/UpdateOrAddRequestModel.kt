package com.example.expenseobserver.core.data

data class UpdateOrAddRequestModel(
    var collectionPath: String = "",
    var data: Map<String, Any?> = mapOf(),
    var itemId: String = ""
)
