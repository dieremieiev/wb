package net.hrobotics.wb.bot.telegram.model;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({
        "update_id",
//        "message",
        "edited_message",
        "uselessValue",
        "chosen_inline_result",
        "callback_query"})
public class Update {
    private Message message;

    public Update() {
    }

    public Update(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
