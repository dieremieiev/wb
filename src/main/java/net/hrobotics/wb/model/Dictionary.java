package net.hrobotics.wb.model;

/**
 * ## Dictionary
 * 1. id: string
 * 2. name: string
 * 3. caption: string
 * 4. from: string
 * 5. to: string
 * 6. version: string
 * 7. number: int
 * id => {}
 */
public class Dictionary {
    private String id;
    private String name;
    private String caption;
    private String from;
    private String to;
    private String version;
    private Integer number;
    private Integer lastLevel;

    public Dictionary(String id,
                      String name,
                      String caption,
                      String from,
                      String to,
                      String version,
                      Integer number,
                      Integer lastLevel) {
        this.id = id;
        this.name = name;
        this.caption = caption;
        this.from = from;
        this.to = to;
        this.version = version;
        this.number = number;
        this.lastLevel = lastLevel;
    }

    public Dictionary() {
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

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(Integer lastLevel) {
        this.lastLevel = lastLevel;
    }
}
