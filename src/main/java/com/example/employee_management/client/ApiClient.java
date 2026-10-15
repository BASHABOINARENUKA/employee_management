package com.example.employee_management.client;
import tools.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import java.io.IOException;
import tools.jackson.databind.JsonNode;
@Component
public class ApiClient {

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ApiClient(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public <T> T get(String url, Class<T> responseType,String token) throws IOException {
        Request request = new Request.Builder().url(url).header("Authorization", "Bearer " + token).get().build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API request failed with status: " + response.code());
            }
            if (response.body() == null) {
                throw new IOException("API returned an empty response");
            }
            String json = response.body().string();
            return objectMapper.readValue(json, responseType);
        }
    }
        public <T> T post(String url,Object requestData,Class<T> responseType ,String token)  throws IOException{
            String json;
            if (requestData instanceof JsonNode jsonNode) {
                json = jsonNode.toString();
            } else {
                json = objectMapper.writeValueAsString(requestData);
            }
           RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder().url(url).header("Authorization","Bearer "+token).post(body).build();
            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Downstream status: " + response.code() );
                }
                if (response.body()==null) {
                    throw new IOException("API returned an empty response");
                }
                return objectMapper.readValue(json, responseType);
            }

        }

    public <T> T put(String url, Object requestData, Class<T> responseType, String token) throws IOException {
        String json = objectMapper.writeValueAsString(requestData);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).header("Authorization", "Bearer " + token).put(body).build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API request failed with status: " + response.code());
            }
            if (response.body() == null) {
                throw new IOException("API returned an empty response");
            }
            return objectMapper.readValue(response.body().string(), responseType);
        }
    }


    public void delete(String url, String token) throws IOException {
        Request request = new Request.Builder().url(url).header("Authorization", "Bearer " + token).delete().build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API request failed with status: " + response.code());
            }
        }
    }
}




