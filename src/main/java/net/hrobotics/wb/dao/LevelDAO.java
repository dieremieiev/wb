package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.*;
import net.hrobotics.wb.model.Dictionary;
import net.hrobotics.wb.model.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.hrobotics.wb.dao.DAOUtils.removeChildren;

public class LevelDAO {
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    private static final String LEVEL_KIND = "level";

    public static void removeLevels(Dictionary dictionary) {
        removeChildren(dictionary, LEVEL_KIND);
    }

    public static List<Level> putLevels(Dictionary dictionary, List<Level> levels) {
        List<Entity> wordsEntities = new ArrayList<>();
        for (Level level : levels) {
            String id = level.getId();
            if (id == null) {
                id = UUID.randomUUID().toString();
                level.setId(id);
            }
            level.setDictionaryId(dictionary.getId());
            Key parentKey = new KeyFactory.Builder(DictionaryDAO.DICTIONARY_KIND,
                    dictionary.getId()).getKey();
            Entity entity = new Entity(LEVEL_KIND, id, parentKey);
            entity.setProperty("id", level.getId());
            entity.setProperty("dictionaryId", dictionary.getId());
            entity.setProperty("number", level.getNumber());
            entity.setProperty("delay", level.getDelay());
            wordsEntities.add(entity);
        }
        datastoreService.put(wordsEntities);
        return levels;
    }
}
