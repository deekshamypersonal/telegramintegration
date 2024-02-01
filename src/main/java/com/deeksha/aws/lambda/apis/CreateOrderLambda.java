package com.deeksha.aws.lambda.apis;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.deeksha.aws.lambda.apis.dto.EventDetails;
import com.deeksha.aws.lambda.apis.dto.RequestMessage;
import com.deeksha.aws.lambda.apis.googleintegration.SetGoogleEvent;
import com.deeksha.aws.lambda.apis.nlp.MessageInterpreter;
import com.deeksha.aws.lambda.apis.s3.S3BucketReader;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

public class CreateOrderLambda {

    private static final Logger logger = Logger.getLogger(CreateOrderLambda.class.getName());

    public APIGatewayProxyResponseEvent createOrder(final APIGatewayProxyRequestEvent request) throws IOException, GeneralSecurityException {
        try {
            //extract message request from input
            String requestMessage = request.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            RequestMessage update = objectMapper.readValue(requestMessage, RequestMessage.class);
            String message = update.getMessage().getText();

            //Pass to NLP model to extract description and time
            EventDetails model = MessageInterpreter.interpretInput(message);

            //Retrieve google credential token from AWS s3
            String token = S3BucketReader.getToken();

            InputStream credentialsStream = new ByteArrayInputStream(token.getBytes(StandardCharsets.UTF_8));

            //Create google event
            SetGoogleEvent.setEvent(credentialsStream, model);

        } catch (Exception e) {
            logger.info("This will be logged in CloudWatch" + e.getMessage());
        }

        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Event Created");

    }
}
