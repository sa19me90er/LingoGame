package hu.backend.lingo.application;

import hu.backend.lingo.LingoGameApplication;

import org.json.simple.JSONObject;
//import org.junit.Test;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= LingoGameApplication.class)
public class GameServiceTest {

    private static List<String> guessedWordsLevel5 = Arrays.asList("loard", "waard", "samen", "KAMER", "woord");

    @Autowired GameService gameService;

    private static Stream<Arguments> provideWordsAndResultLevel5() {
        return Stream.of(
                Arguments.of(1, guessedWordsLevel5.get(0), Arrays.asList(TurnResult.invalid, TurnResult.invalid,TurnResult.invalid,TurnResult.invalid,TurnResult.invalid), EndResult.win),
                Arguments.of(2, guessedWordsLevel5.get(1), Arrays.asList(TurnResult.correct, TurnResult.absent,TurnResult.absent,TurnResult.correct,TurnResult.correct), EndResult.win),
                Arguments.of(3, guessedWordsLevel5.get(2), Arrays.asList(TurnResult.absent, TurnResult.absent,TurnResult.absent,TurnResult.absent,TurnResult.absent), EndResult.win),
                Arguments.of(4, guessedWordsLevel5.get(3), Arrays.asList(TurnResult.absent, TurnResult.absent,TurnResult.absent,TurnResult.absent,TurnResult.present), EndResult.win),
                Arguments.of(5, guessedWordsLevel5.get(4), Arrays.asList(TurnResult.correct, TurnResult.correct,TurnResult.correct,TurnResult.correct,TurnResult.correct), EndResult.win)
        );
    }


    @ParameterizedTest
    @MethodSource("provideWordsAndResultLevel5")
    @DisplayName("Compare to 'Woord' word")
    public void validGuessWordTestLevel5(int turnNumber, String wordTry, List<TurnResult> exceptedTurnResults){
        JSONObject result = this.gameService.validGuessWord(5, "woord", guessedWordsLevel5);
        assertEquals(result.get(String.valueOf(turnNumber)), exceptedTurnResults);
    }

    @Test
    @DisplayName("Compare to 'Woord' word")
    public void endResultTestLevel5(){
        JSONObject result = this.gameService.validGuessWord(5, "woord", guessedWordsLevel5);
        assertEquals(result.get("Result"), EndResult.win);
    }

    private static List<String> guessedWordsLevel6 = Arrays.asList("kijken", "dsadadsas", "", "roeren", "rijken");

    private static Stream<Arguments> provideWordsAndResultLevel6() {
        return Stream.of(
                Arguments.of(1, guessedWordsLevel6.get(0), Arrays.asList(TurnResult.absent, TurnResult.correct,TurnResult.correct,TurnResult.absent,TurnResult.correct, TurnResult.correct)),
                Arguments.of(2, guessedWordsLevel6.get(1), Arrays.asList(TurnResult.invalid, TurnResult.invalid,TurnResult.invalid,TurnResult.invalid,TurnResult.invalid, TurnResult.invalid)),
                Arguments.of(3, guessedWordsLevel6.get(2), Arrays.asList(TurnResult.invalid, TurnResult.invalid,TurnResult.invalid,TurnResult.invalid,TurnResult.invalid, TurnResult.invalid)),
                Arguments.of(4, guessedWordsLevel6.get(3), Arrays.asList(TurnResult.correct, TurnResult.absent,TurnResult.present,TurnResult.absent,TurnResult.absent, TurnResult.correct)),
                Arguments.of(5, guessedWordsLevel6.get(4), Arrays.asList(TurnResult.correct, TurnResult.correct,TurnResult.correct,TurnResult.absent,TurnResult.correct, TurnResult.correct))
        );
    }

    @ParameterizedTest
    @MethodSource("provideWordsAndResultLevel6")
    @DisplayName("Compare to 'rijden' word")
    public void validGuessWordTestLevel6(int turnNumber, String wordTry, List<TurnResult> exceptedTurnResults){
        JSONObject result = this.gameService.validGuessWord(6, "rijden", guessedWordsLevel6);
        assertEquals(result.get(String.valueOf(turnNumber)), exceptedTurnResults);
    }

    @Test
    @DisplayName("Compare to 'rijden' word 5 tries")
    public void endResultTestLevel6(){
        JSONObject result = this.gameService.validGuessWord(5, "woord", guessedWordsLevel6);
        assertEquals(result.get("Result"), EndResult.tryAgain);
    }

}

