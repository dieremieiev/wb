package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.*;
import net.hrobotics.wb.model.Dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.hrobotics.wb.dao.DAOUtils.key;

public class DictionaryDAO {
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    static final String DICTIONARY_KIND = "dictionary";

    public static Dictionary putDictionary(Dictionary dictionary) {
        String id = dictionary.getId();
        if (id == null) {
            id = UUID.randomUUID().toString();
            dictionary.setId(id);
        }
        datastoreService.put(toEntity(dictionary));
        return dictionary;
    }

    public static List<Dictionary> list() {
        List<Dictionary> result = new ArrayList<>();
        Iterable<Entity> entities = datastoreService.prepare(
                new Query(DICTIONARY_KIND)).asIterable();
        for (Entity entity : entities) {
            result.add(toDictionary(entity));
        }
        return result;
    }

    public static Dictionary getDictionary(String id) {
        try {
            return toDictionary(
                    datastoreService.get(
                            key(DICTIONARY_KIND, id)));
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    private static Dictionary toDictionary(Entity entity) {
        Dictionary dictionary = new Dictionary();
        dictionary.setId((String) entity.getProperty("id"));
        dictionary.setVersion((String) entity.getProperty("version"));
        dictionary.setNumber((Integer) entity.getProperty("number"));
        dictionary.setTo((String) entity.getProperty("to"));
        dictionary.setFrom((String) entity.getProperty("from"));
        dictionary.setCaption((String) entity.getProperty("caption"));
        dictionary.setName((String) entity.getProperty("name"));
        return dictionary;
    }

    private static Entity toEntity(Dictionary dictionary) {
        Entity entity = new Entity(DICTIONARY_KIND, dictionary.getId());
        entity.setProperty("id", dictionary.getId());
        entity.setProperty("name", dictionary.getName());
        entity.setProperty("caption", dictionary.getCaption());
        entity.setProperty("from", dictionary.getFrom());
        entity.setProperty("to", dictionary.getTo());
        entity.setProperty("version", dictionary.getVersion());
        return entity;
    }
}
