package com.example.expenseobserver.core.data

import com.example.expenseobserver.R

enum class Currency(val code: Int, val imageResId: Int, val nameResId: Int) {
    BYN(933, R.drawable.ic_currency_byn, R.string.currency_byn),
    AZN(944, R.drawable.ic_currency_azn, R.string.currency_azn),
    AUD(36, R.drawable.ic_currency_aud, R.string.currency_aud),
    AMD(51, R.drawable.ic_currency_amd, R.string.currency_amd),

    USD(840, R.drawable.ic_currency_usd, R.string.currency_usd),
    CAD(124, R.drawable.ic_currency_cad, R.string.currency_cad),
    CNY(156, R.drawable.ic_currency_cny, R.string.currency_cny),
    KZT(398, R.drawable.ic_currency_kzt, R.string.currency_kzt),

    EUR(978, R.drawable.ic_currency_eur, R.string.currency_eur),
    MDL(498, R.drawable.ic_currency_mdl, R.string.currency_mdl),
    GBP(826, R.drawable.ic_currency_gbp, R.string.currency_gbp),
    UAH(980, R.drawable.ic_currency_uah, R.string.currency_uah),

    RUB(643, R.drawable.ic_currency_rub, R.string.currency_rur),
    GEL(981, R.drawable.ic_currency_gel, R.string.currency_gel),
    PLN(985, R.drawable.ic_currency_pln, R.string.currency_pln),
    TRY(949, R.drawable.ic_currency_trl, R.string.currency_try),
    UNKNOWN(-1, -1, -1);

    companion object {
        fun getCurrency(name: String?) = values().firstOrNull { it.name == name } ?: UNKNOWN
    }
}