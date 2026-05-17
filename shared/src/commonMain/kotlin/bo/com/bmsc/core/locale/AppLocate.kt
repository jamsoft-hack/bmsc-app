package bo.com.bmsc.core.locale

data class AppLocate(
    val spanish: String,
    val basque: String,
    val english: String,
) {
    fun getLabel(lang: AppLang): String = when (lang) {
        AppLang.Spanish -> spanish
        AppLang.Euskera -> basque
        AppLang.English -> english
    }

    companion object {
        fun empty() = AppLocate("", "", "")
    }
}
