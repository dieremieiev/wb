package net.hrobotics.wb.api.dto;

public class DictionaryDTO {
    private String id;
    private String name;

    public DictionaryDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public DictionaryDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
