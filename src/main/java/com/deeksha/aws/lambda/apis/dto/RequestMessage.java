package com.deeksha.aws.lambda.apis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestMessage {
    @JsonProperty("update_id")
    private long updateId;

    private Message message;

    // Getter and Setter for updateId
    public long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(long updateId) {
        this.updateId = updateId;
    }

    // Getter and Setter for message
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {

        private String text;

        // Getter and Setter for text
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
