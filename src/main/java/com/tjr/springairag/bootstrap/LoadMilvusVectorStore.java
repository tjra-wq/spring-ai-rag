package com.tjr.springairag.bootstrap;

import com.tjr.springairag.config.SimpleVectorStoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class LoadMilvusVectorStore implements CommandLineRunner {

    @Autowired
    VectorStore vectorStore;

    @Autowired
    SimpleVectorStoreProperties vectorStoreProperties;

    @Override
    public void run(String... args) throws Exception {
        if(vectorStore.similaritySearch("Sportsman").isEmpty()) {
            log.debug("Loading documents into vector store");

            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.debug("Loading document: " + document.getFilename());

                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documentList = documentReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocumentsList = textSplitter.apply(documentList);
                vectorStore.add(splitDocumentsList);
            });
        }
        log.debug("Vector Store Loaded");
    }
}
