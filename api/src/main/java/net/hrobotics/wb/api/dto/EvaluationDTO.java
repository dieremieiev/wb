package net.hrobotics.wb.api.dto;

@SuppressWarnings("WeakerAccess")
public class EvaluationDTO {
    private int result;
    private String spelling;

    public EvaluationDTO(int result, String spelling) {
        this.result = result;
        this.spelling = spelling;
    }

    public EvaluationDTO() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }
}
