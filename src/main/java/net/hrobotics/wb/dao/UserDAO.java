package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.*;
import net.hrobotics.wb.model.UserDictionary;
import net.hrobotics.wb.model.UserState;

import static net.hrobotics.wb.dao.DAOUtils.key;

@SuppressWarnings("WeakerAccess")
public class UserDAO {
    public static final String USER_STATE_KIND = "userState";
    public static final String USER_DICTIONARY_KIND = "userDictionary";
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    public static UserDictionary getUserDictionary(String userId, String dictionaryId) {
        try {
            return toUserDictionary(datastoreService.get(
                    new KeyFactory.Builder(USER_STATE_KIND, userId)
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

    public static UserState getUserState(String userId) {
        try {
            return toUserState(datastoreService.get(key(USER_STATE_KIND, userId)));
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public static UserState putUserState(UserState userState) {
        Key key = datastoreService.put(toEntity(userState));
        userState.setId(key.getName());
        return userState;
    }

    private static Entity toEntity(UserDictionary userDictionary) {
        Entity entity = new Entity(USER_DICTIONARY_KIND,
                userDictionary.getDictionaryId(),
                key(USER_STATE_KIND, userDictionary.getUserId()));
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

    private static int getIntProperty(Entity entity, String propertyName) {
        return entity.getProperty(propertyName) != null ? ((Long) entity.getProperty(propertyName)).intValue() : 0;
    }

    private static Entity toEntity(UserState userState) {
        Entity entity = new Entity(USER_STATE_KIND, userState.getId());
        entity.setProperty("id", userState.getId());
        entity.setProperty("dictionaryId", userState.getCurrentDictionary());
        return entity;
    }

    private static UserState toUserState(Entity entity) {
        return new UserState((String) entity.getProperty("id"),
                (String) entity.getProperty("dictionaryId"));
    }
}
