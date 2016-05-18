package net.hrobotics.wb.api.dto;

public class CurrentDictionaryDTO {
    private String id;
    private int active;
    private int learned;
    private int total;
    private String from;
    private String to;

    public CurrentDictionaryDTO(String id,
                                int active,
                                int learned,
                                int total,
                                String from,
                                String to) {
        this.id = id;
        this.active = active;
        this.learned = learned;
        this.total = total;
        this.from = from;
        this.to = to;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
