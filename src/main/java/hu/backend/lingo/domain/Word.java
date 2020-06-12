package hu.backend.lingo.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
// Table for Dutch words
@Table(name = "nl_words")
public class Word {

    @Id
    private String word;
    //word length for game level
    private int length;

    public Word(){};

    public Word(String word, int length) {
        this.word = word;
        this.length = length;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
