import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun sendGETrequest(webadress: String): String {
    val client = HttpClient.newBuilder().build();
    val request = HttpRequest.newBuilder().uri(URI.create(webadress)).build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body()
}
