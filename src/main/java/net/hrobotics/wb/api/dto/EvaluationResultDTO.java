package net.hrobotics.wb.api.dto;

public class EvaluationResultDTO {
    private CurrentDictionaryDTO dictionary;
    private EvaluationDTO evaluation;

    public EvaluationResultDTO(CurrentDictionaryDTO dictionary, EvaluationDTO evaluation) {
        this.dictionary = dictionary;
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
}
