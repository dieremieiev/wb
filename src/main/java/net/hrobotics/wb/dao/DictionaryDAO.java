package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import net.hrobotics.wb.model.Dictionary;

import java.util.UUID;

public class DictionaryDAO {
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    public static final String DICTIONARY_KIND = "dictionary";

    public static Dictionary createDictionary(Dictionary dictionary) {
        String id = UUID.randomUUID().toString();
        dictionary.setId(id);
        Entity entity = new Entity(DICTIONARY_KIND, id);
        entity.setProperty("id", dictionary.getId());
        entity.setProperty("name", dictionary.getName());
        entity.setProperty("caption", dictionary.getCaption());
        entity.setProperty("from", dictionary.getFrom());
        entity.setProperty("to", dictionary.getTo());
        entity.setProperty("version", dictionary.getVersion());
        datastoreService.put(entity);
        return dictionary;
    }
}
