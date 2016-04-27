package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.*;
import net.hrobotics.wb.model.Dictionary;
import net.hrobotics.wb.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WordDAO {
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    private static final String WORD_KIND = "word";

    public static List<Word> createWords(Dictionary dictionary, List<Word> words) {
        List<Entity> wordsEntities = new ArrayList<>();
        for (Word word : words) {
            String id = UUID.randomUUID().toString();
            word.setId(id);
            word.setDictionaryId(dictionary.getId());
            Key parentKey = new KeyFactory.Builder(DictionaryDAO.DICTIONARY_KIND,
                    dictionary.getId()).getKey();
            Entity entity = new Entity(WORD_KIND, id, parentKey);
            entity.setProperty("spelling", word.getSpelling());
            entity.setProperty("id", word.getId());
            entity.setProperty("dictionaryId", dictionary.getId());
            entity.setProperty("tip", word.getTip());
            entity.setProperty("translation", word.getTranslation());
            wordsEntities.add(entity);
        }
        datastoreService.put(wordsEntities);
        return words;
    }
}