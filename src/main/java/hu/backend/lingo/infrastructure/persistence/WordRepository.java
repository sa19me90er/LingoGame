package hu.backend.lingo.infrastructure.persistence;

import hu.backend.lingo.domain.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<Word, String> {

    @Query(value = "select * from nl_words where length=?1 order by random() limit 1", nativeQuery = true)
    Word electWord(int wordLength);
}
