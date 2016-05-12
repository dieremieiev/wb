package net.hrobotics.wb.dao;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import net.hrobotics.wb.model.CheckLog;

public class CheckResultDAO {
    private static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    public static final String CHECK_LOG_KIND = "CheckLog";

    public static void put(CheckLog checkLog) {
        datastoreService.put(toEntity(checkLog));
    }

    private static Entity toEntity(CheckLog checkLog) {
        Entity entity = new Entity(CHECK_LOG_KIND);
        entity.setProperty("userId", checkLog.getUserId());
        entity.setProperty("dictionaryId", checkLog.getDictionaryId());
        entity.setProperty("checkResult", checkLog.getCheckResult());
        entity.setProperty("timestamp", checkLog.getTimestamp());
        entity.setProperty("wordId", checkLog.getWordId());
        return entity;
    }
}
