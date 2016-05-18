package net.hrobotics.wb.api.dto;

public class SelectDictionaryResultDTO {
    private CurrentDictionaryDTO dictionary;
    private WordDTO word;

    public SelectDictionaryResultDTO(CurrentDictionaryDTO dictionary, WordDTO word) {
        this.dictionary = dictionary;
        this.word = word;
    }

    public SelectDictionaryResultDTO() {
    }

    public CurrentDictionaryDTO getDictionary() {
        return dictionary;
    }

    public void setDictionary(CurrentDictionaryDTO dictionary) {
        this.dictionary = dictionary;
    }

    public WordDTO getWord() {
        return word;
    }

    public void setWord(WordDTO word) {
        this.word = word;
    }
}
