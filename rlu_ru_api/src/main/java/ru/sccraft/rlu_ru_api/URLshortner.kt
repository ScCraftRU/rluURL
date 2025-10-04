package ru.sccraft.rlu_ru_api

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class URLshortner {
}

fun short_url(url: String, preview: Boolean = false, del_time: Int = 0): String? {
    val HTTPS_ЗАПРОС = "https://rlu.ru/index.sema?a=api&del=${del_time}&preview=${if (preview) "1" else "0"}&link=$url"
    val client = HttpClient.newBuilder().build();
    val request = HttpRequest.newBuilder().uri(URI.create(HTTPS_ЗАПРОС)).build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString());
    val ответ = response.body()
    return ответ
}

fun main() {
    val url = short_url("https://ya.ru/")
    print(url)
}