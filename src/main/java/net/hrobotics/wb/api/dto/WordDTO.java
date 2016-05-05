package net.hrobotics.wb.api.dto;

public class WordDTO {
    private String id;
    private String tip;
    private String translation;
    private String spelling;

    public WordDTO(String id, String tip, String translation, String spelling) {
        this.id = id;
        this.tip = tip;
        this.translation = translation;
        this.spelling = spelling;
    }

    public WordDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }
}
