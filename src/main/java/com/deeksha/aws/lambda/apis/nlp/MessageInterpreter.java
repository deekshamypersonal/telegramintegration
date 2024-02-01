package com.deeksha.aws.lambda.apis.nlp;

import com.deeksha.aws.lambda.apis.dto.EventDetails;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MessageInterpreter {


    public static EventDetails interpretInput(String message) {

        String text = message.replaceFirst("^(set reminder to|set reminder for)\\s+", "").trim();
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        // these will hold your extracted information
        String reminderDescription = "";
        String reminderTime = "";

        // Iterate over all sentences in the document
        for (CoreSentence sentence : document.sentences()) {
            // Iterate over all tokens (words) in the sentence
            for (CoreLabel token : sentence.tokens()) {
                // Check the NER label of each token
                String ner = token.ner();
                if (ner.equals("TIME") || ner.equals("DATE")) {
                    reminderTime += token.word() + " ";
                } else {
                    reminderDescription += token.word() + " ";
                }
            }
        }

        // Use Natty to parse the time expression
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(reminderTime);
        Date reminderDateTime = groups.stream().findFirst().get().getDates().stream().findFirst().get();

        return new EventDetails(reminderDescription.trim(), reminderDateTime);

    }

}






