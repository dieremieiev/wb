package net.hrobotics.wb.api.dto;

public class ResponseDTO {
    private int result;
    private Object data;

    public ResponseDTO(int result) {
        this.result = result;
    }

    public ResponseDTO(int result, Object data) {
        this.result = result;
        this.data = data;
    }

    public ResponseDTO() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
