package net.hrobotics.wb.api.dto;

public class CurrentDictionaryDTO {
    private String id;
    private int active;
    private int learned;
    private int total;
    private WordDTO word;

    public CurrentDictionaryDTO(String id,
                                int active,
                                int learned,
                                int total,
                                WordDTO word) {
        this.id = id;
        this.active = active;
        this.learned = learned;
        this.total = total;
        this.word = word;
    }

    public CurrentDictionaryDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getLearned() {
        return learned;
    }

    public void setLearned(int learned) {
        this.learned = learned;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public WordDTO getWord() {
        return word;
    }

    public void setWord(WordDTO word) {
        this.word = word;
    }
}
