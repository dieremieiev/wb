package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.*;
import net.hrobotics.wb.model.Dictionary;

import java.util.Iterator;

import static net.hrobotics.wb.dao.DictionaryDAO.DICTIONARY_KIND;

public class DAOUtils {
    public static final long NEVER = Long.MAX_VALUE;
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    static void removeChildren(Dictionary dictionary, String wordKind) {
        datastoreService.delete(
                toKeys(datastoreService.prepare(
                        new Query(wordKind)
                                .setKeysOnly()
                                .setAncestor(key(DICTIONARY_KIND, dictionary.getId())))
                        .asIterable()));
    }

    static Key key(String kind, String id) {
        return new KeyFactory.Builder(kind, id).getKey();
    }

    static int getIntProperty(Entity entity, String propertyName) {
        return entity.getProperty(propertyName) != null ? ((Long) entity.getProperty(propertyName)).intValue() : 0;
    }

    private static Iterable<Key> toKeys(final Iterable<Entity> entities) {
        return new Iterable<Key>() {
            @Override
            public Iterator<Key> iterator() {
                final Iterator<Entity> iterator = entities.iterator();
                return new Iterator<Key>() {
                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public Key next() {
                        return iterator.next().getKey();
                    }

                    @Override
                    public void remove() {
                        iterator.remove();
                    }
                };
            }
        };
    }
}
