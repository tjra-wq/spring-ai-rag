package com.tjr.springairag.controllers;

import com.tjr.springairag.model.Answer;
import com.tjr.springairag.model.Question;
import com.tjr.springairag.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("question")
public class QuestionController {

    private final OpenAIService openAIService;

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

    @PostMapping("/askAboutAppropriateTruck")
    public Answer askAboutAppropriateTruck(@RequestBody Question question) {
        return openAIService.getTruckAnswer(question);
    }
}
