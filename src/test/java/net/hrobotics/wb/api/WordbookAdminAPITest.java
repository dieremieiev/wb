package net.hrobotics.wb.api;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import net.hrobotics.wb.api.dto.DictionaryDocumentDTO;
import net.hrobotics.wb.model.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WordbookAdminAPITest extends WordBookTestSupport {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private WordbookAdminAPI adminAPI;
    private DatastoreService datastoreService;

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        adminAPI = new WordbookAdminAPI();
        datastoreService =  DatastoreServiceFactory.getDatastoreService();
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void doPutDictionary() throws Exception {
        //Given
        User user = user();
        DictionaryDocumentDTO dictionaryDocument = dictionaryDocument();

        //When
        adminAPI.doPutDictionary(dictionaryDocument, user);

        //Then

        //dictionary
        List<Entity> dictionariesList = datastoreService.prepare(new Query("dictionary")).asList(FetchOptions.Builder.withDefaults());
        assertEquals(1, dictionariesList.size());
        Entity dictionary = dictionariesList.get(0);
        assertNotNull(dictionary);
        assertNotNull(dictionary.getProperty("id"));
        assertEquals(dictionaryName, dictionary.getProperty("name"));
        assertEquals(dictionaryCaption, dictionary.getProperty("caption"));
        assertEquals(dictionaryFrom, dictionary.getProperty("from"));
        assertEquals(dictionaryTo, dictionary.getProperty("to"));
        assertEquals(dictionaryVerision, dictionary.getProperty("version"));
        assertEquals(1L, dictionary.getProperty("number"));
        assertEquals(1L, dictionary.getProperty("lastLevel"));

        //level
        List<Entity> levelslist = datastoreService.prepare(new Query("level")).asList(FetchOptions.Builder.withDefaults());
        assertEquals(1, levelslist.size());
        Entity level = levelslist.get(0);
        assertNotNull(level);
        assertEquals(dictionary.getProperty("id"), level.getProperty("dictionaryId"));
        assertEquals(1L, level.getProperty("level"));
        assertEquals(1L, level.getProperty("delay"));

        //level
        List<Entity> wordsList = datastoreService.prepare(new Query("word")).asList(FetchOptions.Builder.withDefaults());
        assertEquals(1, wordsList.size());
        Entity word = wordsList.get(0);
        assertNotNull(word);

        assertNotNull(word.getProperty("id"));
        assertEquals(dictionary.getProperty("id"), word.getProperty("dictionaryId"));
        assertEquals(wordSpelling, word.getProperty("spelling"));
        assertEquals(wordTranslation, word.getProperty("translation"));
        assertEquals(wordTip, word.getProperty("tip"));
    }

    @Test
    public void doPutDictionary_update() throws Exception {
        //Given
        User user = user();
        DictionaryDocumentDTO dictionaryDocument = dictionaryDocument();

        //When
        DictionaryDocumentDTO newDictionaryDocument = adminAPI.doPutDictionary(dictionaryDocument, user);
        //update
        newDictionaryDocument.setLevels(Collections.singletonList(new Level(dictionaryId, 2, 3)));
        String correctedSpelling = "correctedSpelling";
        newDictionaryDocument.getWords().get(0).setSpelling(correctedSpelling);
        String correctedName = "correctedName";
        newDictionaryDocument.getDictionary().setName(correctedName);
        adminAPI.doPutDictionary(newDictionaryDocument, user);

        //Then

        //dictionary
        List<Entity> dictionariesList = datastoreService.prepare(new Query("dictionary")).asList(FetchOptions.Builder.withDefaults());
        assertEquals(1, dictionariesList.size());
        Entity dictionary = dictionariesList.get(0);
        assertNotNull(dictionary);
        assertNotNull(dictionary.getProperty("id"));
        assertEquals(correctedName, dictionary.getProperty("name"));
        assertEquals(dictionaryCaption, dictionary.getProperty("caption"));
        assertEquals(dictionaryFrom, dictionary.getProperty("from"));
        assertEquals(dictionaryTo, dictionary.getProperty("to"));
        assertEquals(dictionaryVerision, dictionary.getProperty("version"));
        assertEquals(1L, dictionary.getProperty("number"));
        assertEquals(1L, dictionary.getProperty("lastLevel"));

        //level
        List<Entity> levelslist = datastoreService.prepare(new Query("level")).asList(FetchOptions.Builder.withDefaults());
        assertEquals(1, levelslist.size());
        Entity level = levelslist.get(0);
        assertNotNull(level);
        assertEquals(dictionary.getProperty("id"), level.getProperty("dictionaryId"));
        assertEquals(2L, level.getProperty("level"));
        assertEquals(3L, level.getProperty("delay"));

        //level
        List<Entity> wordsList = datastoreService.prepare(new Query("word")).asList(FetchOptions.Builder.withDefaults());
        assertEquals(1, wordsList.size());
        Entity word = wordsList.get(0);
        assertNotNull(word);

        assertNotNull(word.getProperty("id"));
        assertEquals(dictionary.getProperty("id"), word.getProperty("dictionaryId"));
        assertEquals(correctedSpelling, word.getProperty("spelling"));
        assertEquals(wordTranslation, word.getProperty("translation"));
        assertEquals(wordTip, word.getProperty("tip"));
    }
}