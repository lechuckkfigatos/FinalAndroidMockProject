package com.example.pftandroidmockproject.presentation.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

//chỉ có 2 child : dynamic / static
sealed interface UiText {

    // dành cho user input hay api
    data class DynamicString(
        val value: String
    ) : UiText

    //dành các chuỗi static trong string.xml
    data class StringResource(
        @StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText
}
@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.DynamicString -> value
        //"args.toTypedArray()" biến list thành array
        // "*" ném phần tử bên trong thành biến đơn lẻ
        is UiText.StringResource -> stringResource(resId, *args.toTypedArray())
    }
}