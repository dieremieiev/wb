package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import net.hrobotics.wb.model.UserWord;

import java.util.List;

import static com.google.appengine.api.datastore.Query.FilterOperator.*;
import static net.hrobotics.wb.dao.DAOUtils.NEVER;
import static net.hrobotics.wb.dao.DAOUtils.getIntProperty;
import static net.hrobotics.wb.dao.DictionaryDAO.DICTIONARY_KIND;
import static net.hrobotics.wb.dao.UserStateDAO.USER_KIND;

public class UserWordDAO {
    private static final String USER_WORD_KIND = "userWord";
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    public static UserWord get(String userId, String dictionaryId, String id) {
        Entity entity;
        try {
            entity = datastoreService.get(new KeyFactory.Builder(
                    USER_KIND, userId)
                    .addChild(DICTIONARY_KIND, dictionaryId)
                    .addChild(USER_WORD_KIND, id)
                    .getKey());
        } catch (EntityNotFoundException e) {
            return null;
        }
        return toUserWord(entity);
    }

    private static UserWord toUserWord(Entity entity) {
        UserWord userWord = new UserWord();
        userWord.setDictionaryId((String) entity.getProperty("dictionaryId"));
        userWord.setCheckDate((Long) entity.getProperty("checkDate"));
        userWord.setLevel(getIntProperty(entity, "level"));
        userWord.setUserId((String) entity.getProperty("userId"));
        userWord.setWordId((String) entity.getProperty("wordId"));
        return userWord;
    }

    public static void put(UserWord userWord) {
        datastoreService.put(toEntity(userWord));
    }

    private static Entity toEntity(UserWord userWord) {
        Entity entity = new Entity(USER_WORD_KIND,
                userWord.getWordId(),
                new KeyFactory.Builder(USER_KIND, userWord.getUserId())
                        .addChild(DICTIONARY_KIND, userWord.getDictionaryId())
                        .getKey());
        entity.setProperty("dictionaryId", userWord.getDictionaryId());
        entity.setProperty("checkDate", userWord.getCheckDate());
        entity.setProperty("level", userWord.getLevel());
        entity.setProperty("userId", userWord.getUserId());
        entity.setProperty("wordId", userWord.getWordId());
        return entity;
    }

    public static UserWord pickWordBeforeCheckDate(String userId,
                                                   String dictionaryId,
                                                   long timestamp) {
        List<Entity> entities = datastoreService.prepare(
                new Query(USER_WORD_KIND)
                        .setAncestor(new KeyFactory.Builder(USER_KIND, userId)
                                .addChild(DICTIONARY_KIND, dictionaryId).getKey())
                        .setFilter(new FilterPredicate(
                                "checkDate", LESS_THAN_OR_EQUAL, timestamp))
                        .addSort("checkDate"))
                .asList(FetchOptions.Builder.withDefaults().limit(1).offset(0));
        return entities.size() == 0 ? null : toUserWord(entities.get(0));
    }

    public static Integer numWordsWithLevel(String userId,
                                            String dictionaryId,
                                            Integer lastLevel) {
        return datastoreService.prepare(
                new Query(USER_WORD_KIND)
                        .setAncestor(new KeyFactory.Builder(USER_KIND, userId)
                                .addChild(DICTIONARY_KIND, dictionaryId).getKey())
                        .setFilter(new FilterPredicate(
                                "level", EQUAL, lastLevel)))
                .countEntities(FetchOptions.Builder.withDefaults());
    }

    public static Integer numWordsWithCheckDate(String userId, String dictionaryId) {
        return datastoreService.prepare(
                new Query(USER_WORD_KIND)
                        .setAncestor(new KeyFactory.Builder(USER_KIND, userId)
                                .addChild(DICTIONARY_KIND, dictionaryId).getKey())
                        .setFilter(new FilterPredicate(
                                "checkDate", LESS_THAN, NEVER)))
                .countEntities(FetchOptions.Builder.withDefaults());
    }

    public static UserWord pickWordWithoutCheckDate(String userId, String dictionaryId) {
        List<Entity> entities = datastoreService.prepare(
                new Query(USER_WORD_KIND)
                        .setAncestor(new KeyFactory.Builder(USER_KIND, userId)
                                .addChild(DICTIONARY_KIND, dictionaryId).getKey())
                        .setFilter(new FilterPredicate(
                                "checkDate", EQUAL, NEVER)))
                .asList(FetchOptions.Builder.withDefaults().limit(1).offset(0));
        return entities.size() == 0 ? null : toUserWord(entities.get(0));    }
}