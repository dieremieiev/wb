package net.hrobotics.wb.api;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import net.hrobotics.wb.api.dto.*;
import net.hrobotics.wb.model.Word;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WordbookAPITest extends WordBookTestSupport {
//    private final LocalServiceTestHelper helper =
//            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
//    private DatastoreService datastoreService;
//    private WordbookAPI wordbookAPI;
//    private User user;
//    private DictionaryDocumentDTO dictionaryDocumentDTO;
//    private WordbookAdminAPI adminAPI;
//
//    @After
//    public void tearDown() throws Exception {
//        helper.tearDown();
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        helper.setUp();
//        datastoreService = DatastoreServiceFactory.getDatastoreService();
//        wordbookAPI = new WordbookAPI();
//        user = user();
//        adminAPI = new WordbookAdminAPI();
//        dictionaryDocumentDTO = adminAPI.doPutDictionary(dictionaryDocument(), user);
//    }
//
//    @Test
//    public void getUserState() throws Exception {
//        //When
//        ResponseDTO userState = wordbookAPI.getUserState(user);
//
//        //then
//        assertNotNull(userState);
//        assertEquals(0, userState.getResult());
//        assertNotNull(userState.getBody());
//        assertTrue(userState.getBody() instanceof UserStateDTO);
//        UserStateDTO userStateDTO = (UserStateDTO) userState.getBody();
//        List<DictionaryDTO> dictionaries = userStateDTO.getDictionaries();
//        assertNotNull(dictionaries);
//        assertEquals(1, dictionaries.size());
//        DictionaryDTO dictionaryDTO = dictionaries.get(0);
//        assertNotNull(dictionaryDTO);
//        assertEquals(dictionaryName, dictionaryDTO.getName());
//        CurrentDictionaryDTO dictionary = userStateDTO.getDictionary();
//        assertNull(dictionary);
//        assertEquals(email, userStateDTO.getEmail());
//    }
//
//    @Test
//    public void selectDictionary() throws Exception {
//        //Given
//        Entity dictionary = datastoreService.prepare(new Query("dictionary")).asSingleEntity();
//        String dictionaryId = (String) dictionary.getProperty("id");
//
//        //When
//        ResponseDTO responseDTO = wordbookAPI.selectDictionary(dictionaryId, user);
//        assertNotNull(responseDTO);
//        assertEquals(0, responseDTO.getResult());
//        assertNotNull(responseDTO.getBody());
//        assertTrue(responseDTO.getBody() instanceof CurrentDictionaryDTO);
//        CurrentDictionaryDTO currentDictionaryDTO = (CurrentDictionaryDTO) responseDTO.getBody();
//        assertEquals(0, currentDictionaryDTO.getActive());
//        assertEquals(0, currentDictionaryDTO.getLearned());
//        assertEquals(1, currentDictionaryDTO.getTotal());
//        assertEquals(dictionaryId, currentDictionaryDTO.getId());
//        assertNotNull(currentDictionaryDTO.getWord());
//        WordDTO word = currentDictionaryDTO.getWord();
//        assertEquals(wordSpelling, word.getSpelling());
//        assertEquals(wordTip, word.getTip());
//        assertEquals(wordTranslation, word.getTranslation());
//        assertNotNull(word.getId());
//    }
//
//    @Test
//    public void checkWord_positive() throws Exception {
//        //Given
//        Entity dictionary = datastoreService.prepare(new Query("dictionary")).asSingleEntity();
//        Entity word = datastoreService.prepare(new Query("word")).asSingleEntity();
//        String dictionaryId = (String) dictionary.getProperty("id");
//        wordbookAPI.selectDictionary(dictionaryId, user);
//
//        //When
//        WordDTO wordDTO = new WordDTO();
//        Object spelling = word.getProperty("spelling");
//        wordDTO.setSpelling((String) spelling);
//        wordDTO.setId((String) word.getProperty("id"));
//        ResponseDTO responseDTO = wordbookAPI.checkWord(wordDTO, user);
//        assertNotNull(responseDTO);
//        assertEquals(0, responseDTO.getResult());
//        assertNotNull(responseDTO.getBody());
//        assertTrue(responseDTO.getBody() instanceof EvaluationResultDTO);
//        EvaluationResultDTO evaluationResultDTO = (EvaluationResultDTO) responseDTO.getBody();
//        EvaluationDTO evaluation = evaluationResultDTO.getEvaluation();
//        assertNotNull(evaluation);
//        assertEquals(0, evaluation.getResult());
//        assertEquals(spelling, evaluation.getSpelling());
//        CurrentDictionaryDTO currentDictionaryDTO = evaluationResultDTO.getDictionary();
//        assertEquals(0, currentDictionaryDTO.getActive());
//        assertEquals(1, currentDictionaryDTO.getLearned());
//        assertEquals(1, currentDictionaryDTO.getTotal());
//        assertEquals(dictionaryId, currentDictionaryDTO.getId());
//        assertNull(currentDictionaryDTO.getWord());
//    }
//
//    @Test
//    public void checkWord_negative() throws Exception {
//        //Given
//        Entity dictionary = datastoreService.prepare(new Query("dictionary")).asSingleEntity();
//        Entity word = datastoreService.prepare(new Query("word")).asSingleEntity();
//        String dictionaryId = (String) dictionary.getProperty("id");
//        wordbookAPI.selectDictionary(dictionaryId, user);
//
//        //When
//        WordDTO wordDTO = new WordDTO();
//        wordDTO.setSpelling("wrong spelling");
//        wordDTO.setId((String) word.getProperty("id"));
//        ResponseDTO responseDTO = wordbookAPI.checkWord(wordDTO, user);
//        assertNotNull(responseDTO);
//        assertEquals(0, responseDTO.getResult());
//        assertNotNull(responseDTO.getBody());
//        assertTrue(responseDTO.getBody() instanceof EvaluationResultDTO);
//        EvaluationResultDTO evaluationResultDTO = (EvaluationResultDTO) responseDTO.getBody();
//        EvaluationDTO evaluation = evaluationResultDTO.getEvaluation();
//        assertNotNull(evaluation);
//        assertEquals(1, evaluation.getResult());
//        assertEquals(word.getProperty("spelling"), evaluation.getSpelling());
//        CurrentDictionaryDTO currentDictionaryDTO = evaluationResultDTO.getDictionary();
//        assertEquals(0, currentDictionaryDTO.getActive());
//        assertEquals(1, currentDictionaryDTO.getLearned());
//        assertEquals(1, currentDictionaryDTO.getTotal());
//        assertEquals(dictionaryId, currentDictionaryDTO.getId());
//        assertNull(currentDictionaryDTO.getWord());
//    }
//
//    @Test
//    public void checkWord_Choose() throws Exception {
//        //Given
//        String dictionaryId = dictionaryDocumentDTO.getDictionary().getId();
//        String additionaSpelling = "additionaSpelling";
//        String additionalTranslation = "additionalTranslation";
//        String additionalTip = "additionalTip";
//        List<Word> wordList = new ArrayList<>(dictionaryDocumentDTO.getWords());
//        wordList.add(new Word(null,
//                dictionaryId, additionaSpelling, additionalTranslation, additionalTip));
//        dictionaryDocumentDTO.setWords(wordList);
//        adminAPI.doPutDictionary(dictionaryDocumentDTO, user);
//        List<Entity> words = datastoreService.prepare(new Query("word")).asList(FetchOptions.Builder.withDefaults());
//        wordbookAPI.selectDictionary(dictionaryId, user);
//        Entity wordToCheck;
//        Entity nextWord;
//        if(wordSpelling.equals(words.get(0).getProperty("spelling"))) {
//            wordToCheck = words.get(0);
//            nextWord = words.get(1);
//        } else {
//            wordToCheck = words.get(1);
//            nextWord = words.get(0);
//        }
//        WordDTO wordDTO = new WordDTO();
//        Object spelling = wordToCheck.getProperty("spelling");
//        wordDTO.setSpelling((String) spelling);
//        wordDTO.setId((String) wordToCheck.getProperty("id"));
//        ResponseDTO responseDTO = wordbookAPI.checkWord(wordDTO, user);
//        assertNotNull(responseDTO);
//        assertEquals(0, responseDTO.getResult());
//        assertNotNull(responseDTO.getBody());
//        assertTrue(responseDTO.getBody() instanceof EvaluationResultDTO);
//        EvaluationResultDTO evaluationResultDTO = (EvaluationResultDTO) responseDTO.getBody();
//        EvaluationDTO evaluation = evaluationResultDTO.getEvaluation();
//        assertNotNull(evaluation);
//        assertEquals(0, evaluation.getResult());
//        assertEquals(spelling, evaluation.getSpelling());
//        CurrentDictionaryDTO currentDictionaryDTO = evaluationResultDTO.getDictionary();
//        assertEquals(0, currentDictionaryDTO.getActive());
//        assertEquals(1, currentDictionaryDTO.getLearned());
//        assertEquals(1, currentDictionaryDTO.getTotal());
//        assertEquals(dictionaryId, currentDictionaryDTO.getId());
//        WordDTO word = currentDictionaryDTO.getWord();
//        assertNotNull(word);
//        assertEquals(nextWord.getProperty("spelling"), additionaSpelling);
//        assertEquals(nextWord.getProperty("translation"), additionalTranslation);
//        assertEquals(nextWord.getProperty("tip"), additionalTip);
//    }
}