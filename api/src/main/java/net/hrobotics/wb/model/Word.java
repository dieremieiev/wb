package net.hrobotics.wb.model;

/**
 * ## Word
 * 1. id: string
 * 2. dictionaryId: string
 * 3. spelling: string
 * 4. translation: string
 * 5. tip: string
 * dictionary.id/word.id => spelling, translation, tip
 */
public class Word {
    private String id;
    private String dictionaryId;
    private String spelling;
    private String translation;
    private String tip;

    public Word(String id, String dictionaryId, String spelling, String translation, String tip) {
        this.id = id;
        this.dictionaryId = dictionaryId;
        this.spelling = spelling;
        this.translation = translation;
        this.tip = tip;
    }

    public Word() {
    }

    public Word(String spelling, String translation) {
        this.spelling = spelling;
        this.translation = translation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }
}
