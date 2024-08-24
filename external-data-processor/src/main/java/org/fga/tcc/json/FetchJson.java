package org.fga.tcc.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fga.tcc.entities.OpenDataBaseResponse;

import java.io.File;
import java.io.IOException;
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

    public OpenDataBaseResponse<T> get(String url, TypeReference<OpenDataBaseResponse<T>> typeReference) {
        System.out.println("Sending GET request: " + url);
        try {
            String uri = extractUri(url).uri;
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
                    return mapper.readValue(response.body(), typeReference);
                } else {
                    System.out.println("Error: " + response.statusCode());
                }
            } else {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(new File(filePath), typeReference);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    private void saveResponseInLocalCache(String url, String jsonResponseBody) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            UriParams uriParams = extractUri(url);
            File file = getFile(uriParams);

            // Save in cache only if response has some data
            if (!jsonResponseBody.contains("\"dados\":[]")) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, mapper.readValue(jsonResponseBody, OpenDataBaseResponse.class));
                System.out.println("JSON saved in: " + file.getAbsolutePath());
            } else {
                System.out.println("No data to save in cache.");
            }
        } catch (IOException e) {
            System.out.println("Error saving response in local cache");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error saving response in local cache: Index out bounds exception in splitUri");
        }
    }

    private File getFile(UriParams uriParams) {
        String filePath;

        if (uriParams.splitUri.length == 1) {
            String fileName = uriParams.splitUri[0];
            filePath = RESOURCES_PATH + "/" + uriParams.splitUri[0] + "/" + fileName + ".json";
        } else {
            String fileName = uriParams.splitUri[1];
            filePath = RESOURCES_PATH + "/" + uriParams.splitUri[0] + "/" + uriParams.splitUri[2] + "/" + fileName + ".json";
        }

        File file = new File(filePath);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    private boolean isFileAlreadyCreated(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    private UriParams extractUri(String url) {
        UriParams uriParams = new UriParams();

        int initialIndex = 42;
        int finalIndex = url.length();

        if (url.contains("?")) {
            finalIndex = url.indexOf('?');
        }

        String resourcePath = url.substring(initialIndex, finalIndex);
        String[] params = resourcePath.split("/");

        uriParams.setSplitUri(params);
        uriParams.setUri(resourcePath);

        return uriParams;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class UriParams {
        private String uri;
        private String[] splitUri;
    }
}
