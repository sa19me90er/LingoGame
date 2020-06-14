package hu.backend.lingo.repository;

import hu.backend.lingo.LingoGameApplication;
import hu.backend.lingo.domain.Word;
import hu.backend.lingo.infrastructure.persistence.WordRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.util.Optional;

import static org.junit.Assert.fail;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=LingoGameApplication.class)
public class RepositoryIngrationTest {


    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private LingoGameApplication lingoGameApplication;


    @Test
    @DisplayName("FindByID function")
    public void findByIDTest() throws NullPointerException{
        Optional<Word> result = this.wordRepository.findById("woord");
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("getting random word to guess function test")
    public void pickRandomWordTest() {
        Word resultWord = this.wordRepository.electWord(7);
        assertTrue(resultWord.getLength() == 7);
    }




}
