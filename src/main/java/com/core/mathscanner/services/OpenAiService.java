package com.core.mathscanner.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import io.github.cdimascio.dotenv.Dotenv;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    private final WebClient webClient;
    Dotenv dotenv = Dotenv.load();

    public OpenAiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }

    public Mono<String> getCompletion(String prompt) {
        String apiKey = dotenv.get("OPENAI_API_KEY");

        return webClient.post()
                .uri("/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(buildRequestBody(prompt))
                .retrieve()
                .bodyToMono(String.class);
    }

    private Map<String, Object> buildRequestBody(String prompt) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));
        requestBody.put("temperature", 0.7);
        return requestBody;
    }
}

