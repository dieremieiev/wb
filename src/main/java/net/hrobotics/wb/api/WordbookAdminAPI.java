package net.hrobotics.wb.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import net.hrobotics.wb.api.dto.DictionaryDocumentDTO;
import net.hrobotics.wb.dao.LevelDAO;
import net.hrobotics.wb.model.Dictionary;
import net.hrobotics.wb.model.Level;
import net.hrobotics.wb.model.Word;

import java.util.List;
import java.util.logging.Logger;

import static net.hrobotics.wb.dao.DictionaryDAO.putDictionary;
import static net.hrobotics.wb.dao.LevelDAO.removeLevels;
import static net.hrobotics.wb.dao.WordDAO.putWords;
import static net.hrobotics.wb.dao.WordDAO.removeWords;

@Api(name = "admin",
        version = "v1",
        clientIds = {"115238264042860888797",
                com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {"115238264042860888797"},
        namespace = @ApiNamespace(ownerDomain = "wb.hrobotics.net",
                ownerName = "wb.hrobotics.net"))
public class WordbookAdminAPI {
    private static final Logger LOG = Logger.getLogger(WordbookAPI.class.getName());

    @ApiMethod(
            name = "dictionary.put",
            path = "dictionary",
            httpMethod = "POST")
    public DictionaryDocumentDTO doPutDictionary(DictionaryDocumentDTO dictionaryDocument, User user)
            throws OAuthRequestException
    {
        if(user == null) {
            throw new OAuthRequestException("not authorized");
        }
        Dictionary dictionary = dictionaryDocument.getDictionary();
        if(dictionary.getId() != null) {
            removeWords(dictionary);
            removeLevels(dictionary);
        }
        putDictionary(dictionary);
        List<Word> words = dictionaryDocument.getWords();
        dictionary.setNumber(words.size());
        putWords(dictionary, words);
        List<Level> levels = dictionaryDocument.getLevels();
        LevelDAO.putLevels(dictionary, levels);
        return dictionaryDocument;
    }
}
