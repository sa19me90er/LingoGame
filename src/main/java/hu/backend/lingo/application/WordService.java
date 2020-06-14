package hu.backend.lingo.application;

import hu.backend.lingo.domain.Word;
import hu.backend.lingo.infrastructure.persistence.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

//    public WordService(WordRepository wordRepository) {
//        this.wordRepository = wordRepository;
//    }
//
//    public WordService() { }

    public Optional<Word> findWordById(String word) {
        word = word.toLowerCase();
        return this.wordRepository.findById(word);
    }


    public Word electWord(int wordLength){

        return wordRepository.electWord(wordLength);
    }

}
