package com.deeksha.aws.lambda.apis.dto;

import java.util.Date;

public class EventDetails {

    String description;
    Date dateTime;

    public EventDetails() {
    }

    public EventDetails(String msg, Date dateTime) {
        this.description = msg;
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
