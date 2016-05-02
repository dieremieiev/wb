package net.hrobotics.wb.model;

/**
 * ## Level
 * 1. id: string
 * 2. dictionaryId: string
 * 3. number: int
 * 4. delay: int (days)
 * dictionary.id/level.id => {}
 */
public class Level {
    private String id;
    private String dictionaryId;
    private Integer number;
    private Integer delay;

    public Level(String id, String dictionaryId, Integer number, Integer delay) {
        this.id = id;
        this.dictionaryId = dictionaryId;
        this.number = number;
        this.delay = delay;
    }

    public Level() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
