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

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public WordService() { }

    public List<Word> getAllWords()
    {
        List<Word> userRecords = new ArrayList<>();
        wordRepository.findAll().forEach(userRecords::add);
        return userRecords;
    }

    public Optional<Word> findWordById(String word) {
        return this.wordRepository.findById(word);
    }

    public void writeWord(String word, int length) {
        this.wordRepository.save(
                new Word(word, length)
        );
    }


    public Word electWord(int wordLength){

        return wordRepository.electWord(wordLength);
    }

}
