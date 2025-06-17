package com.deeksha.aws.lambda.apis.googleintegration;

import com.deeksha.aws.lambda.apis.dto.EventDetails;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

public class SetGoogleEvent {

    private static final Logger logger = Logger.getLogger(SetGoogleEvent.class.getName());

    public static void setEvent(InputStream credentialsStream, EventDetails model) throws IOException, GeneralSecurityException {

        GoogleCredential credential = GoogleCredential.fromStream(credentialsStream)
                .createScoped(Arrays.asList(CalendarScopes.CALENDAR, "https://www.googleapis.com/auth/calendar.events"));
        logger.info("Google credential created" + credential.getAccessToken());
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service =
                new Calendar.Builder(HTTP_TRANSPORT, GsonFactory.getDefaultInstance(), credential)
                        .setApplicationName("Test Email 2")
                        .build();
        logger.info("Service Object Created");


        Date startDateTime = model.getDateTime();

        // Convert Date to Calendar
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(startDateTime);
        calendar.add(java.util.Calendar.MINUTE, 60);
        DateTime endDateTime = new DateTime(calendar.getTime());

        // Create the event object
        Event event = new Event()
                .setSummary("New Event")
                .setDescription(model.getDescription());

        // Set the start and end times for the event
        EventDateTime start = new EventDateTime()
                .setDateTime(new DateTime(model.getDateTime()))
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles"); 
        event.setEnd(end);

        String calendarId = "primary";

        event = service.events().insert(calendarId, event).execute();
        logger.info("Event Created "+event.getId());


    }
}
