package com.core.mathscanner.controllers;

import com.core.mathscanner.services.OpenAiService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class OpenAiController {

    private final OpenAiService openAiService;

    public OpenAiController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping("/process-text")
    public Mono<String> processText(@RequestBody String text) {
        return openAiService.getCompletion(text);
    }
}
