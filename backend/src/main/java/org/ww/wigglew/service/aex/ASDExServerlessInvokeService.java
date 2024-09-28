package org.ww.wigglew.service.aex;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class ASDExServerlessInvokeService {

    public String invokeServerless(String urlString, Map<String, String> queryParams)  {
        try{
            String queryString = queryParams.entrySet().stream()
                    .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
                            + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));

            URI uri = new URI(urlString + "?" + queryString);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            CompletableFuture<Void> _future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .orTimeout(3, TimeUnit.SECONDS) // Time out after 2 seconds
                    .thenAcceptAsync(response -> {
                        System.out.println("Response received asynchronously: " + response.body());
                    }).exceptionally(ex -> {
                        System.out.println("Request failed or timed out: " + ex.getMessage());
                        return null;
                    });

            return null;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
}



