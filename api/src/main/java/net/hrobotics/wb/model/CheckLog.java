package net.hrobotics.wb.model;

/**
 * ## Checks log
 * 1. timestamp: date
 * 2. user: string
 * 3. dictionary: string
 * 4. word: string
 * 5. check_result: string
 * timestamp/user.id/dictionary.id/word.id => check_result
 */
public class CheckLog {
    private long timestamp;
    private String userId;
    private String dictionaryId;
    private String wordId;
    private int checkResult;

    public CheckLog(long timestamp,
                    String userId,
                    String dictionaryId,
                    String wordId,
                    int checkResult) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.dictionaryId = dictionaryId;
        this.wordId = wordId;
        this.checkResult = checkResult;
    }

    public CheckLog() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public int getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }
}
