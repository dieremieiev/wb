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
    private Integer level;
    private String dictionaryId;
    private Integer delay;

    public Level(String dictionaryId, Integer level, Integer delay) {
        this.dictionaryId = dictionaryId;
        this.level = level;
        this.delay = delay;
    }

    public Level() {
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
