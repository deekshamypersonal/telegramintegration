# Sync Reminder

This project resolves the issue of forgotten reminders by automatically generating events from messages in chat applications such as WhatsApp and Telegram. By using natural language processing (NLP) techniques, the app interprets messages to determine dates, times, and contexts and directly sets reminders in google calender. 

It uses OAuth to connect to Google Calendar The application is deployed using a serverless architecture, leveraging Lambda functions triggered by API Gateway. Lambda connects to AWS S3 to retrieve token details to connect to google API.

# High Level Flow

- Telegram (any messagner bot) hits API Gateway endpoint (HTTPS webhook)
- Lambda parses the RequestMessage
- MessageInterpreter extracts EventDetails (text + time)
- S3BucketReader fetches an OAuth refresh token
- SetGoogleEvent inserts an event into the user’s primary calendar

<img width="485" alt="image" src="https://github.com/deekshamypersonal/telegramintegration/assets/150110347/9b00e676-1fdc-4874-9539-ee1303728412">

# Limitation

Bot requires webhook API endpoint to be publicly accessible over HTTPS. To test locally, we’ll need a tunneling tool as the webhook can’t reach local machine.

