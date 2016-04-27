package net.hrobotics.wb.api;

import net.hrobotics.wb.model.Dictionary;
import net.hrobotics.wb.model.Word;

import java.util.List;

public class DictionaryDocument {
    private Dictionary dictionary;
    private List<Word> words;

    public DictionaryDocument(Dictionary dictionary, List<Word> words) {
        this.dictionary = dictionary;
        this.words = words;
    }

    public DictionaryDocument() {
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
