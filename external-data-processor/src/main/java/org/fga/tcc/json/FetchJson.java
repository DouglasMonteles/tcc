package org.fga.tcc.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fga.tcc.entities.OpenDataBaseResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FetchJson<T> {

    private final String RESOURCES_PATH = "external-data-processor/src/main/resources";

//    public static void main(String[] args) {
//        String url = "https://dadosabertos.camara.leg.br/api/v2/deputados?pagina=1&ordem=ASC&ordenarPor=nome";
//        FetchJson fetchJson = new FetchJson();
//
//        System.out.println(fetchJson.get(url).getData().getFirst());
//    }

    public OpenDataBaseResponse<T> get(String url) {
        try {
            String uri = extractUri(url);
            String filePath = RESOURCES_PATH + "/" + uri + "/" + uri + ".json";

            if (!isFileAlreadyCreated(filePath)) {
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    saveResponseInLocalCache(url, response.body());

                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(response.body(), OpenDataBaseResponse.class);
                } else {
                    System.out.println("Error: " + response.statusCode());
                }
            } else {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(new File(filePath), OpenDataBaseResponse.class);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    private void saveResponseInLocalCache(String url, String jsonResponseBody) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String uri = extractUri(url);
            String filePath = RESOURCES_PATH + "/" + uri + "/" + uri + ".json";
            File file = new File(filePath);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, mapper.readValue(jsonResponseBody, OpenDataBaseResponse.class));
            System.out.println("JSON saved in: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving response in local cache");
        }
    }

    private boolean isFileAlreadyCreated(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    private String extractUri(String url) {
        int initialIndex = 42;
        int finalIndex = url.length();

        if (url.contains("?")) {
            finalIndex = url.indexOf('?');
        }

        return url.substring(initialIndex, finalIndex);
    }
}
