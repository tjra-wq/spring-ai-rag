package com.tjr.springairag.services;

import com.tjr.springairag.model.Answer;
import com.tjr.springairag.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAIServiceImpl implements OpenAIService{

    private final ChatModel chatModel;
    private final SimpleVectorStore simpleVectorStore;

    @Value("classpath:templates/rag-prompt-template-metadata.st")
    private Resource ragPromptTemplate;

    @Override
    public Answer getAnswer(Question question) {
        List<Document> documentList = simpleVectorStore.similaritySearch(SearchRequest.builder()
                        .query(question.question())
                        .topK(4)        //change this to lower number to save on tokens
                        .build());
        assert documentList != null;
        List<String> contentList = documentList.stream().map(Document::getText).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("input", question.question(),
                "documentList",String.join("\n", contentList)));
        contentList.forEach(System.out::println);

        ChatResponse chatResponse = chatModel.call(prompt);
        return new Answer(chatResponse.getResult().getOutput().getContent());
    }
}
