package hu.backend.lingo.infrastructure.web.controller;

import hu.backend.lingo.application.GameService;
import hu.backend.lingo.application.WordService;
import hu.backend.lingo.domain.Word;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class WordController {

    @Autowired
    private WordService wordService;

    @Autowired
    private GameService gameService;

    @GetMapping(value = "/random/{wordLength}", produces = "application/json")
    public Word electWord(@PathVariable int wordLength) {
        return this.wordService.electWord(wordLength);
    }


    @GetMapping("/addWord")
    public JSONObject addWord(@RequestParam("word") String word, HttpServletRequest request) {
        //get the notes from request session
        List<String> words = (List<String>) request.getSession().getAttribute("WORDS_SESSION");
        //check if notes is present in session or not

        if (words == null) {
            String randomWord = this.wordService.electWord(5).getWord();


            words = new ArrayList<>();
            // if notes object is not present in session, set notes in the request session
            request.getSession().setAttribute("WORDS_SESSION", words);
            request.getSession().setAttribute("RANDOM_WORD_SESSION",randomWord);

        }
        words.add(word);
        request.getSession().setAttribute("WORDS_SESSION", words);
        String rWord = (String) request.getSession().getAttribute("RANDOM_WORD_SESSION");
//        return this.gameService.validGuessWord(rWord, words);
        return this.gameService.validGuessWord(5, rWord, words);
    }

    @GetMapping("theWord")
    public String theWord(HttpSession session){
        String rWord = (String) session.getAttribute("RANDOM_WORD_SESSION");
        return rWord;
    }


    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        List<String> notes = (List<String>) session.getAttribute("NOTES_SESSION");
        model.addAttribute("WORDS_Session", notes!=null? notes:new ArrayList<>());
        return "home";
    }

    @GetMapping("/newGame")
    public String destroySession(@RequestParam("level") int level, HttpServletRequest request) {

        //invalidate the session , this will clear the session data from configured database (Mysql/redis/hazelcast)
        request.getSession().invalidate();

        // begin new game and set level
        if (level < 5 || level > 7) {return "Level not allowed";}
        String randomWord = this.wordService.electWord(level).getWord();
        request.getSession().setAttribute("RANDOM_WORD_SESSION",randomWord);
        return "redirect:/home";
    }

    @GetMapping("/test")
    public Optional<Word> findWordById(@RequestParam("word") String word) {
        return this.wordService.findWordById(word);
    }

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World";
    }

}


