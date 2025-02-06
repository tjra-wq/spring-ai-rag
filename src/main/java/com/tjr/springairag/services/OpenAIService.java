package com.tjr.springairag.services;

import com.tjr.springairag.model.Answer;
import com.tjr.springairag.model.Question;

public interface OpenAIService {
    Answer getAnswer(Question question);

    Answer getTruckAnswer(Question question);
}
