package hu.backend.lingo.application;

import hu.backend.lingo.LingoGameApplication;
import hu.backend.lingo.domain.Word;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= LingoGameApplication.class)
public class WordServiceIntegrationTest {

    @Autowired
    private WordService wordService;

    @Autowired
    private LingoGameApplication lingoGameApplication;

    @Test
    @DisplayName("FindByID function")
    public void findByIDTest() throws NullPointerException{
        //Test the toLowercase by writing capital letter
        Optional<Word> result = this.wordService.findWordById("Hallo");
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("getting random word to guess function test")
    public void pickRandomWordTest() {
        Word resultWord = this.wordService.electWord(7);
        assertTrue(resultWord.getLength() == 7);
    }

}
