package ru.sccraft.rlu_ru_api

class URLshortner {
}

fun short_url(url: String, preview: Boolean = false, del_time: Int = 0) {
    val HTTPS_ЗАПРОС = "https://rlu.ru/index.sema?a=api&del=${del_time}&preview=${if (preview) "1" else "0"}&link=$url"
}
