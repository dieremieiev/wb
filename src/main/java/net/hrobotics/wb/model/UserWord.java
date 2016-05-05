package net.hrobotics.wb.model;

import java.util.Date;

/**
 * Created by di on 01/05/16.## User Word
 * 1. userId: string
 * 2. dictionaryId: string
 * 3. wordId: string
 * 2. level: int
 * 3. checkDate: date
 * user.id/dictionary.id/word.id => {}
 */
public class UserWord {
    private String userId;
    private String dictionaryId;
    private String wordId;
    private Integer level;
    private Date checkDate;

    public UserWord(String userId,
                    String dictionaryId,
                    String wordId,
                    Integer level,
                    Date checkDate) {
        this.userId = userId;
        this.dictionaryId = dictionaryId;
        this.wordId = wordId;
        this.level = level;
        this.checkDate = checkDate;
    }

    public UserWord() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
}
