package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.*;
import net.hrobotics.wb.model.Dictionary;
import net.hrobotics.wb.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.hrobotics.wb.dao.DAOUtils.key;
import static net.hrobotics.wb.dao.DAOUtils.removeChildren;
import static net.hrobotics.wb.dao.DictionaryDAO.DICTIONARY_KIND;

public class WordDAO {
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    private static final String WORD_KIND = "word";

    public static List<Word> putWords(Dictionary dictionary, List<Word> words) {
        List<Entity> wordsEntities = new ArrayList<>();
        for (Word word : words) {
            String id = word.getId();
            if (id == null) {
                id = UUID.randomUUID().toString();
                word.setId(id);
            }
            wordsEntities.add(toEntity(dictionary, word));
        }
        datastoreService.put(wordsEntities);
        return words;
    }

    public static void removeWords(Dictionary dictionary) {
        removeChildren(dictionary, WORD_KIND);
    }

    public static Word getWord(String dictionaryId, String id) {
        Entity entity;
        try {
            entity = datastoreService.get(new KeyFactory.Builder(
                    DICTIONARY_KIND, dictionaryId)
                    .addChild(WORD_KIND, id)
                    .getKey());
        } catch (EntityNotFoundException e) {
            return null;
        }
        return toWord(entity);
    }

    public static List<Word> getWords(String dictionaryId, int limit, int offset) {
        return toWords(datastoreService.prepare(
                new Query(WORD_KIND).setAncestor(key(DICTIONARY_KIND, dictionaryId)))
                .asIterable(FetchOptions.Builder.withDefaults()
                        .limit(limit).offset(offset)));
    }

    private static List<Word> toWords(Iterable<Entity> entities) {
        List<Word> result = new ArrayList<>();
        for (Entity entity : entities) {
            result.add(toWord(entity));
        }
        return result;
    }

    private static Word toWord(Entity entity) {
        Word word = new Word();
        word.setId((String) entity.getProperty("id"));
        word.setDictionaryId((String) entity.getProperty("dictionaryid"));
        word.setSpelling((String) entity.getProperty("spelling"));
        word.setTranslation((String) entity.getProperty("translation"));
        word.setTip((String) entity.getProperty("tip"));
        return word;
    }

    private static Entity toEntity(Dictionary dictionary, Word word) {
        String dictionaryId = dictionary.getId();
        Entity entity = new Entity(WORD_KIND, word.getId(),
                key(DICTIONARY_KIND, dictionaryId));
        entity.setProperty("spelling", word.getSpelling());
        entity.setProperty("id", word.getId());
        entity.setProperty("dictionaryId", dictionaryId);
        entity.setProperty("tip", word.getTip());
        entity.setProperty("translation", word.getTranslation());
        return entity;
    }
}