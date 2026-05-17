package bo.com.bmsc.core.extension

import bo.com.bmsc.core.locale.AppLang
import bo.com.bmsc.core.locale.currentLanguage
import bo.com.bmsc.core.locale.localeFormat

fun Float.toPrintableAmount(decimals: Int = 2): String {
    val language = currentLanguage()

    return "${this.localeFormat(language, decimals)}€"
}

fun Float.toPrintableAmount(decimals: Int = 2, lang: AppLang): String {
    return "${this.localeFormat(lang, decimals)}€"
}
