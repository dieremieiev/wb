package net.hrobotics.wb.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import net.hrobotics.wb.api.dto.*;
import net.hrobotics.wb.dao.DictionaryDAO;
import net.hrobotics.wb.dao.UserDAO;
import net.hrobotics.wb.dao.WordDAO;
import net.hrobotics.wb.model.Dictionary;
import net.hrobotics.wb.model.UserDictionary;
import net.hrobotics.wb.model.UserState;
import net.hrobotics.wb.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static net.hrobotics.wb.dao.DictionaryDAO.getDictionary;
import static net.hrobotics.wb.dao.UserDAO.getUserDictionary;
import static net.hrobotics.wb.dao.UserDAO.putUserState;
import static net.hrobotics.wb.dao.WordDAO.getWord;

@Api(name = "wordbook",
        version = "v1",
        clientIds = {"271144171558-qduruebh6ok3oqtk9irgdkkavt3tsqcu.apps.googleusercontent.com",
                com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {"271144171558-qduruebh6ok3oqtk9irgdkkavt3tsqcu.apps.googleusercontent.com"},
        namespace = @ApiNamespace(ownerDomain = "wb.hrobotics.net",
                ownerName = "wb.hrobotics.net"))
public class WordbookAPI {
    private static final Logger LOG = Logger.getLogger(WordbookAPI.class.getName());

    @ApiMethod(
            name = "getUserState",
            httpMethod = "GET")
    public ResponseDTO getUserState(User user) {
        try {
            if (user == null) {
                throw new OAuthRequestException("not authorized");
            }
            return new ResponseDTO(0, currentState(user));
        } catch (Exception e) {
            e.printStackTrace();
            LOG.severe(e.getMessage());
            return new ResponseDTO(1, e.getMessage());
        }
    }

    @ApiMethod(
            name = "selectDictionary",
            httpMethod = "POST"
    )
    public ResponseDTO selectDictionary(@Named("dictionaryId") String dictionaryId, User user)
            throws OAuthRequestException {
        String userId = user.getUserId();
        UserState userState = UserDAO.getUserState(userId);
        if (userState == null) {
            userState = new UserState(userId);
        }
        userState.setCurrentDictionary(dictionaryId);
        putUserState(userState);
        UserDictionary userDictionary = getUserDictionary(userId, dictionaryId);
        if (userDictionary == null) {
            userDictionary = new UserDictionary(userId, dictionaryId);
            List<Word> words = WordDAO.getWords(dictionaryId, 1, 0);
            userDictionary.setNextWordId(
                    words.size() == 0 ? null :
                            words.get(0).getId());
            UserDAO.putUserDictionary(userDictionary);
        }
        return new ResponseDTO(0, toDTO(getDictionary(dictionaryId)));
    }

    @ApiMethod(
            name = "checkWord",
            path = "check",
            httpMethod = "POST")
    public ResponseDTO checkWord(WordDTO word, User user) throws OAuthRequestException {
        return new ResponseDTO(0, new EvaluationResultDTO());//todo: implement
    }

    private UserStateDTO currentState(User user) {
        return new UserStateDTO(currentDictionary(user), dictionariesList());
    }

    private List<DictionaryDTO> dictionariesList() {
        return toDTO(DictionaryDAO.list());
    }

    private CurrentDictionaryDTO currentDictionary(User user) {
        String userId = user.getUserId();
        UserState userState = UserDAO.getUserState(userId);
        if (userState == null) {
            return null;
        }
        UserDictionary currentDictionary = getUserDictionary(userId,
                userState.getCurrentDictionary());
        if (currentDictionary == null) {
            return null;
        }
        return toDTO(getDictionary(currentDictionary.getDictionaryId()),
                currentDictionary);
    }

    private CurrentDictionaryDTO toDTO(Dictionary dictionary, UserDictionary userDictionary) {
        CurrentDictionaryDTO result = new CurrentDictionaryDTO();
        result.setId(dictionary.getId());
        result.setActive(userDictionary.getActive() == null ? 0 : userDictionary.getActive());
        result.setLearned(userDictionary.getLearned() == null ? 0 : userDictionary.getLearned());
        result.setTotal(dictionary.getNumber() == null ? 0 : dictionary.getNumber());
        String nextWordId = userDictionary.getNextWordId();
        result.setWord(nextWordId == null ? null :
                toDTO(getWord(userDictionary.getDictionaryId(), nextWordId)));
        return result;
    }

    private WordDTO toDTO(Word word) {
        WordDTO wordDTO = new WordDTO();
        wordDTO.setId(word.getId());
        wordDTO.setSpelling(word.getSpelling());
        wordDTO.setTranslation(word.getTranslation());
        wordDTO.setTip(word.getTip());
        return wordDTO;
    }

    private List<DictionaryDTO> toDTO(List<Dictionary> dictionaries) {
        List<DictionaryDTO> result = new ArrayList<>();
        for (Dictionary dictionary : dictionaries) {
            result.add(toDTO(dictionary));
        }
        return result;
    }

    private DictionaryDTO toDTO(Dictionary dictionary) {
        return new DictionaryDTO(dictionary.getId(),
                dictionary.getName());
    }
}
