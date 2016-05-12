package net.hrobotics.wb.api.dto;

import net.hrobotics.wb.model.Dictionary;
import net.hrobotics.wb.model.Level;
import net.hrobotics.wb.model.Word;

import java.util.List;

public class DictionaryDocumentDTO {
    private Dictionary dictionary;
    private List<Word> words;
    private List<Level> levels;

    public DictionaryDocumentDTO(Dictionary dictionary, List<Word> words, List<Level> levels) {
        this.dictionary = dictionary;
        this.words = words;
        this.levels = levels;
    }

    public DictionaryDocumentDTO() {
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

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }
}
