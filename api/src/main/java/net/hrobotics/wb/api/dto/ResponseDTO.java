package net.hrobotics.wb.api.dto;

public class ResponseDTO {
    private int result;
    private Object body;

    public ResponseDTO(int result) {
        this.result = result;
    }

    public ResponseDTO(int result, Object body) {
        this.result = result;
        this.body = body;
    }

    public ResponseDTO() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
