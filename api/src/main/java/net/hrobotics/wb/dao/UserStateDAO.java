package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.*;
import net.hrobotics.wb.model.UserState;

import static net.hrobotics.wb.dao.DAOUtils.key;

@SuppressWarnings("WeakerAccess")
public class UserStateDAO {
    public static final String USER_KIND = "user";
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();


    public static UserState getUserState(String userId) {
        try {
            return toUserState(datastoreService.get(key(USER_KIND, userId)));
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public static UserState putUserState(UserState userState) {
        Key key = datastoreService.put(toEntity(userState));
        userState.setId(key.getName());
        return userState;
    }

    private static Entity toEntity(UserState userState) {
        Entity entity = new Entity(USER_KIND, userState.getId());
        entity.setProperty("id", userState.getId());
        entity.setProperty("dictionaryId", userState.getCurrentDictionary());
        return entity;
    }

    private static UserState toUserState(Entity entity) {
        return new UserState((String) entity.getProperty("id"),
                (String) entity.getProperty("dictionaryId"));
    }
}
