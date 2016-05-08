package net.hrobotics.wb.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import net.hrobotics.wb.api.dto.*;
import net.hrobotics.wb.dao.*;
import net.hrobotics.wb.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static net.hrobotics.wb.dao.DAOUtils.NEVER;
import static net.hrobotics.wb.dao.DictionaryDAO.getDictionary;
import static net.hrobotics.wb.dao.UserDictionaryDAO.getUserDictionary;
import static net.hrobotics.wb.dao.UserDictionaryDAO.putUserDictionary;
import static net.hrobotics.wb.dao.UserStateDAO.putUserState;
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
    public static final int WITHOUT_LIMIT = -1;
    public static final int WITHOUT_OFFSET = -1;

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
        UserState userState = UserStateDAO.getUserState(userId);
        if (userState == null) {
            userState = new UserState(userId);
        }
        userState.setCurrentDictionary(dictionaryId);
        putUserState(userState);
        UserDictionary userDictionary = getUserDictionary(userId, dictionaryId);
        if (userDictionary == null) {
            userDictionary = new UserDictionary(userId, dictionaryId);
            List<Word> words = WordDAO.getWords(dictionaryId, WITHOUT_LIMIT, WITHOUT_OFFSET);
            if(words.size() == 0) {
                throw new RuntimeException("no words in dictionary: " + dictionaryId);
            }
            userDictionary.setNextWordId(words.get(0).getId());
            putUserDictionary(userDictionary);
            for (Word word : words) {
                UserWordDAO.put(new UserWord(userId, dictionaryId, word.getId(), null, NEVER));
            }
        }
        return new ResponseDTO(0, toDTO(getDictionary(dictionaryId), userDictionary));
    }

    @ApiMethod(
            name = "checkWord",
            path = "check",
            httpMethod = "POST")
    public ResponseDTO checkWord(WordDTO word,
                                 User user) throws OAuthRequestException {
        // check result
        String userId = user.getUserId();
        UserState userState = UserStateDAO.getUserState(userId);
        if (userState == null) {
            throw new IllegalArgumentException("wrong user: " + userId + " - not present in database");
        }
        String dictionaryId = userState.getCurrentDictionary();
        Dictionary dictionary = DictionaryDAO.getDictionary(dictionaryId);
        if(dictionary == null) {
            throw new RuntimeException("cannot find dictionary for id: " + dictionaryId);
        }
        Word savedWord = WordDAO.getWord(dictionaryId, word.getId());
        if (savedWord == null) {
            throw new IllegalArgumentException("wrong word: " + word.getId() + " - not present in database");
        }
        String validSpelling = savedWord.getSpelling();
        int result = validSpelling.equals(word.getSpelling()) ? 0 : 1;

        // log
        long timestamp = System.currentTimeMillis();
        CheckResultDAO.put(new CheckLog(timestamp, userId, dictionaryId, word.getId(), result));

        // save word level
        UserWord userWord = UserWordDAO.get(userId, dictionaryId, word.getId());
        if(userWord == null) {
            userWord = new UserWord(userId, dictionaryId, word.getId(), 1, timestamp);
        }
        int level = 1;
        if(result == 0) {
            level = userWord.getLevel() + 1;
            userWord.setLevel(level);
        } else {
            userWord.setLevel(level);
        }
        Level levelDetails = LevelDAO.getLevel(dictionaryId, level);
        Integer delay = levelDetails.getDelay();
        userWord.setCheckDate(timestamp + fromDaysToMillisecs(delay));
        UserWordDAO.put(userWord);

        // recalculate next word
        UserWord nextUserWord = UserWordDAO.pickWordBeforeCheckDate(userId, dictionaryId, timestamp);
        if(nextUserWord == null) {
            nextUserWord = UserWordDAO.pickWordWithoutCheckDate(userId, dictionaryId);
        }
        if(nextUserWord == null) {
            throw new RuntimeException("cannot get next word for user: " + userId +
                    " in dictionary: " + dictionaryId);
        }
        String nextWordId = nextUserWord.getWordId();
        UserDictionary userDictionary = getUserDictionary(userId, dictionaryId);
        if(userDictionary == null) {
            throw new RuntimeException(
                    "user dictionary is not found userId: " + userId +
                            ", dictionaryId: " + dictionaryId);
        }
        userDictionary.setNextWordId(nextWordId);
        userDictionary.setLearned(UserWordDAO.
                numWordsWithLevel(userId, dictionaryId, dictionary.getLastLevel()));
        userDictionary.setActive(UserWordDAO.
                numWordsWithCheckDate(userId, dictionaryId));
        putUserDictionary(userDictionary);

        return new ResponseDTO(0, new EvaluationResultDTO(
                toDTO(dictionary, userDictionary),
                new EvaluationDTO(result, validSpelling)));
    }

    private long fromDaysToMillisecs(Integer delay) {
        return delay * 3600 * 24 * 1000;
    }

    private UserStateDTO currentState(User user) {
        return new UserStateDTO(currentDictionary(user), dictionariesList(), user.getEmail());
    }

    private List<DictionaryDTO> dictionariesList() {
        return toDTO(DictionaryDAO.list());
    }

    private CurrentDictionaryDTO currentDictionary(User user) {
        String userId = user.getUserId();
        UserState userState = UserStateDAO.getUserState(userId);
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
