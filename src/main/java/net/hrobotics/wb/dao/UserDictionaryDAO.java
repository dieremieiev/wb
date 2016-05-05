package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.*;
import net.hrobotics.wb.model.UserDictionary;

import static net.hrobotics.wb.dao.DAOUtils.getIntProperty;
import static net.hrobotics.wb.dao.DAOUtils.key;

public class UserDictionaryDAO {
    public static final String USER_DICTIONARY_KIND = "userDictionary";
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();


    public static UserDictionary getUserDictionary(String userId, String dictionaryId) {
        try {
            return toUserDictionary(datastoreService.get(
                    new KeyFactory.Builder(UserStateDAO.USER_KIND, userId)
                            .addChild(USER_DICTIONARY_KIND, dictionaryId)
                            .getKey()));
        } catch (EntityNotFoundException e) {
            return null;
        }
    }


    public static UserDictionary putUserDictionary(UserDictionary userDictionary) {
        Key key = datastoreService.put(toEntity(userDictionary));
        userDictionary.setDictionaryId(key.getName());
        return userDictionary;
    }

    private static Entity toEntity(UserDictionary userDictionary) {
        Entity entity = new Entity(USER_DICTIONARY_KIND,
                userDictionary.getDictionaryId(),
                key(UserStateDAO.USER_KIND, userDictionary.getUserId()));
        entity.setProperty("dictionaryId", userDictionary.getDictionaryId());
        entity.setProperty("active", userDictionary.getActive());
        entity.setProperty("learned", userDictionary.getLearned());
        entity.setProperty("nextWordId", userDictionary.getNextWordId());
        entity.setProperty("userId", userDictionary.getUserId());
        return entity;
    }


    private static UserDictionary toUserDictionary(Entity entity) {
        UserDictionary userDictionary = new UserDictionary();
        userDictionary.setActive(getIntProperty(entity, "active"));
        userDictionary.setDictionaryId((String) entity.getProperty("dictionaryId"));
        userDictionary.setLearned(getIntProperty(entity, "learned"));
        userDictionary.setNextWordId((String) entity.getProperty("nextWordId"));
        userDictionary.setUserId((String) entity.getProperty("userId"));
        return userDictionary;
    }

}
