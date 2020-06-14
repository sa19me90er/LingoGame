package hu.backend.lingo.application;

import com.google.common.base.CharMatcher;
import hu.backend.lingo.domain.Word;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Enumerated(EnumType.STRING)
    private TurnResult turnResult;

    @Enumerated(EnumType.STRING)
    private EndResult endResult;

    @Autowired
    private WordService wordService;


    public List<TurnResult> compareLetters(String theRightWord, String guessedWord) {
        guessedWord = guessedWord.toLowerCase();
        List<TurnResult> roundTurnResultList = new ArrayList<>();
        List temporaryList = new ArrayList();

        for (int letterIndex = 0; letterIndex < theRightWord.length(); letterIndex++) {
            char rightLetter = theRightWord.charAt(letterIndex);
            char guessedLetter = guessedWord.charAt(letterIndex);

            int countLetterInRightWord = CharMatcher.is(guessedLetter).countIn(theRightWord);
            int countLetterInGuessedWord = CharMatcher.is(guessedLetter).countIn(guessedWord);

            // Check if letter in word, otherwise absent
                if (theRightWord.indexOf(guessedLetter) != -1) {

                    if (lettersAreEqual(rightLetter, guessedLetter) ) {
                    roundTurnResultList.add(this.turnResult = TurnResult.correct);
                     } else {
                    roundTurnResultList.add(this.turnResult = TurnResult.present);
                }
                    // Check if the letter has duplicate in the word then change the value to absent
                    if (temporaryList.contains(guessedLetter) && countLetterInRightWord<countLetterInGuessedWord){
                        roundTurnResultList.remove( roundTurnResultList.size() - 1);
                        roundTurnResultList.add(this.turnResult = TurnResult.absent);
                    }
                    temporaryList.add(guessedLetter);
            } else {
                roundTurnResultList.add(this.turnResult = TurnResult.absent);
            }
        }
        return roundTurnResultList;
    }

    private boolean lettersAreEqual(char rightLetter, char guessedLetter) {
        return rightLetter == guessedLetter;
    }


    public JSONObject validGuessWord(int gameRounds, String theRightWord, List<String> guessedWords) {
        JSONObject resultJsonObject = new JSONObject();
        int roundNumber = 0;

        for (String guessedWord : guessedWords) {

            // If rounds are equal or more than the allowed game rounds stop the game
            if (roundNumber > gameRounds){
                this.endResult=EndResult.lose;
                break;
            }
            guessedWord = guessedWord.toLowerCase();
            this.endResult=EndResult.tryAgain;
            List<TurnResult> roundTurnResultList = new ArrayList<>();
            roundNumber++;

            // check first if the word in the dictionary, otherwise invalid turn.
            Optional<Word> word = this.wordService.findWordById(guessedWord);
            if (word.isPresent()) {
                // check if the word equal to the to guess word --> All correct --> win
                if (theRightWord.equals(guessedWord)){
                    this.endResult=EndResult.win;
                    for (int letterIndex = 0; letterIndex < guessedWord.length(); letterIndex++) {
                        roundTurnResultList.add(this.turnResult = TurnResult.correct);
                    }
                    resultJsonObject.put(String.valueOf(roundNumber), roundTurnResultList);
                    break;
                } else {
                    // Compare the letters using the method compareLetters
                    roundTurnResultList.addAll(compareLetters(theRightWord, guessedWord));
                    resultJsonObject.put(String.valueOf(roundNumber), roundTurnResultList);
                }

            } else{
                for (int letterIndex = 0; letterIndex < theRightWord.length(); letterIndex++) {
                    roundTurnResultList.add(this.turnResult = TurnResult.invalid);
                }
                resultJsonObject.put(String.valueOf(roundNumber), roundTurnResultList);
            }
        }
        resultJsonObject.put("Result", endResult);
        return resultJsonObject;
    }
}
