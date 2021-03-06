package net.hrobotics.wb.api.dto;

public class EvaluationResultDTO {
    private CurrentDictionaryDTO dictionary;
    private WordDTO word;
    private EvaluationDTO evaluation;

    public EvaluationResultDTO(CurrentDictionaryDTO dictionary, WordDTO word, EvaluationDTO evaluation) {
        this.dictionary = dictionary;
        this.word = word;
        this.evaluation = evaluation;
    }

    public EvaluationResultDTO() {
    }

    public CurrentDictionaryDTO getDictionary() {
        return dictionary;
    }

    public void setDictionary(CurrentDictionaryDTO dictionary) {
        this.dictionary = dictionary;
    }

    public EvaluationDTO getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationDTO evaluation) {
        this.evaluation = evaluation;
    }

    public WordDTO getWord() {
        return word;
    }

    public void setWord(WordDTO word) {
        this.word = word;
    }
}
