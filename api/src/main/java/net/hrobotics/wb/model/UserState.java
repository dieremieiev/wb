package net.hrobotics.wb.model;


/**
 * ## UserState
 * 1. id: string
 * 2. email: string
 * 3. dictionary: string
 * id => {}
 */
public class UserState {
    private String id;
    private String currentDictionary;

    public UserState(String id, String currentDictionary) {
        this.id = id;
        this.currentDictionary = currentDictionary;
    }

    public UserState(String id) {
        this.id = id;
    }

    public UserState() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentDictionary() {
        return currentDictionary;
    }

    public void setCurrentDictionary(String currentDictionary) {
        this.currentDictionary = currentDictionary;
    }
}
