package net.hrobotics.wb.bot.telegram.model;

public class SendMessage {
    private Long chat_id;
    private String text;

    public SendMessage() {
    }

    public SendMessage(Long chat_id, String text) {
        this.chat_id = chat_id;
        this.text = text;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat_id(Long chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
