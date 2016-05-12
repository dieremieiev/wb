package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.*;
import net.hrobotics.wb.model.Dictionary;
import net.hrobotics.wb.model.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.hrobotics.wb.dao.DAOUtils.getIntProperty;
import static net.hrobotics.wb.dao.DAOUtils.removeChildren;
import static net.hrobotics.wb.dao.DictionaryDAO.DICTIONARY_KIND;

public class LevelDAO {
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    private static final String LEVEL_KIND = "level";

    public static void removeLevels(Dictionary dictionary) {
        removeChildren(dictionary, LEVEL_KIND);
    }

    public static List<Level> putLevels(Dictionary dictionary, List<Level> levels) {
        List<Entity> entities = new ArrayList<>();
        for (Level level : levels) {
            String dictionaryId = dictionary.getId();
            level.setDictionaryId(dictionaryId);
            Entity entity = toEntity(level);
            entities.add(entity);
        }
        datastoreService.put(entities);
        return levels;
    }

    private static Entity toEntity(Level level) {
        String dictionaryId = level.getDictionaryId();
        Key parentKey = new KeyFactory.Builder(DICTIONARY_KIND,
                dictionaryId).getKey();
        Entity entity = new Entity(LEVEL_KIND, level.getLevel(), parentKey);
        entity.setProperty("dictionaryId", dictionaryId);
        entity.setProperty("level", level.getLevel());
        entity.setProperty("delay", level.getDelay());
        return entity;
    }

    private static Level toLevel(Entity entity) {
        Level level = new Level();
        level.setDictionaryId((String) entity.getProperty("dictionaryId"));
        level.setLevel(getIntProperty(entity, "level"));
        level.setDelay(getIntProperty(entity, "delay"));
        return level;
    }

    public static Level getLevel(String dictionaryId, int level) {
        try {
            return toLevel(datastoreService.get(new KeyFactory.Builder(DICTIONARY_KIND, dictionaryId).addChild(LEVEL_KIND, level).getKey()));
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException(
                    "level: " + level + " for dictionary id: " + dictionaryId + " is not found");
        }
    }
}
