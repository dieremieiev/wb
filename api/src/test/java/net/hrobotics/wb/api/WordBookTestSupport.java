package net.hrobotics.wb.api;

import com.google.appengine.api.users.User;
import net.hrobotics.wb.api.dto.DictionaryDocumentDTO;
import net.hrobotics.wb.model.Dictionary;
import net.hrobotics.wb.model.Level;
import net.hrobotics.wb.model.Word;

import java.util.Collections;

public class WordBookTestSupport {
    protected final String email = "email@email";
    protected final String authDomain = "authDomain";
    protected final String userId = "userId";
    protected final String dictionaryId = null;
    protected final String dictionaryName = "dictionaryName";
    protected final String dictionaryCaption = "dictionaryCaption";
    protected final String dictionaryFrom = "dictionaryFrom";
    protected final String dictionaryTo = "dictionaryTo";
    protected final String dictionaryVerision = "dictionaryVerision";
    protected final String wordId = null;
    protected final String wordSpelling = "wordSpelling";
    protected final String wordTranslation = "wordTranslation";
    protected final String wordTip = "wordTip";


    protected DictionaryDocumentDTO dictionaryDocument() {
        DictionaryDocumentDTO dictionaryDocument = new DictionaryDocumentDTO();
        Integer number = 1;
        int lastLevel = 2;
        dictionaryDocument.setDictionary(new Dictionary(
                dictionaryId, dictionaryName, dictionaryCaption, dictionaryFrom,
                dictionaryTo, dictionaryVerision, number, lastLevel));
        dictionaryDocument.setLevels(Collections.singletonList(new Level(dictionaryId, 1, 1)));
        dictionaryDocument.setWords(Collections.singletonList(new Word(
                wordId, dictionaryId, wordSpelling, wordTranslation, wordTip)));
        return dictionaryDocument;
    }

    protected User user() {
        return new User(email, authDomain, userId);
    }
}
