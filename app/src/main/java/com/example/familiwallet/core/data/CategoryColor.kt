package com.example.familiwallet.core.data

import androidx.compose.ui.graphics.Color
import com.example.familiwallet.ui.theme.mainColor
import com.example.familiwallet.ui.theme.openColor0
import com.example.familiwallet.ui.theme.openColor1
import com.example.familiwallet.ui.theme.openColor10
import com.example.familiwallet.ui.theme.openColor11
import com.example.familiwallet.ui.theme.openColor12
import com.example.familiwallet.ui.theme.openColor13
import com.example.familiwallet.ui.theme.openColor2
import com.example.familiwallet.ui.theme.openColor3
import com.example.familiwallet.ui.theme.openColor4
import com.example.familiwallet.ui.theme.openColor5
import com.example.familiwallet.ui.theme.openColor6
import com.example.familiwallet.ui.theme.openColor7
import com.example.familiwallet.ui.theme.openColor8
import com.example.familiwallet.ui.theme.openColor9

enum class CategoryColor(val color: Color) {
    COLOR0(openColor0),
    COLOR1(openColor1),
    COLOR2(openColor2),
    COLOR3(openColor3),
    COLOR4(openColor4),
    COLOR5(openColor5),
    COLOR6(openColor6),
    COLOR7(openColor7),
    COLOR8(openColor8),
    COLOR9(openColor9),
    COLOR10(openColor10),
    COLOR11(openColor11),
    COLOR12(openColor12),
    COLOR13(openColor13),
    UNKNOWN(mainColor);

    companion object {
        fun getColor(colorName: String) = values().firstOrNull { it.name == colorName } ?: UNKNOWN
    }
}