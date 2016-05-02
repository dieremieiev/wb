package net.hrobotics.wb.model;

/**
 * ## User dictionary
 * 1. userId: string
 * 2. dictionaryId: string
 * 3. words: int
 * 4. active: int
 * 5. learned: int
 * 5. nextWord: string
 * user.id/dictionary.id => {}
 */
public class UserDictionary {
    private String userId;
    private String dictionaryId;
    private Integer active;
    private Integer learned;
    private String nextWordId;

    public UserDictionary(String userId,
                          String dictionaryId,
                          Integer active,
                          Integer learned,
                          String nextWordId) {
        this.userId = userId;
        this.dictionaryId = dictionaryId;
        this.active = active;
        this.learned = learned;
        this.nextWordId = nextWordId;
    }

    public UserDictionary(String userId, String dictionaryId) {
        this.userId = userId;
        this.dictionaryId = dictionaryId;
        active = 0;
        learned = 0;
        nextWordId = null;
    }

    public UserDictionary() {
        active = 0;
        learned = 0;
        nextWordId = null;
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

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getLearned() {
        return learned;
    }

    public void setLearned(Integer learned) {
        this.learned = learned;
    }

    public String getNextWordId() {
        return nextWordId;
    }

    public void setNextWordId(String nextWordId) {
        this.nextWordId = nextWordId;
    }
}
