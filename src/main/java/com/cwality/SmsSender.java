package com.cwality;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import io.micronaut.context.annotation.Value;

public class SmsSender {

    // Find your Account Sid and Auth Token at twilio.com/console
    @Value("${twilio.account.sid}")
    private String twilioSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.number}")
    private String twilioNumber;

    private String BODY_TEMPLATE = "Hi %s, you're in a Kris Kindle."
            + " There's a €%s price limit and you're getting a present for %s.";

    private static final Logger logger = LoggerFactory.getLogger(SmsSender.class);

    protected List<Match<Person>> sendMessages(List<Person> participants, boolean dryRun, Integer limit) {
        Twilio.init(twilioSid, authToken);
        List<Match<Person>> matches = GraphJumbler.jumble(participants);
        GraphJumbler.validateKindleMatches(matches);
        for (Match<Person> match : matches) {
            
            String body = String.format(BODY_TEMPLATE, match.giver.name, limit, match.taker.name);
            String from = twilioNumber;
            if (dryRun == true) {
                logger.info("dry run for gifter {} to giftee {}", match.giver.name, match.taker.name);
                logger.info("from: {}", from);
                logger.info("to: {}", match.giver.number);
                logger.info("body: {}", body);
            } else {
                Message message = Message.creator(new PhoneNumber(match.giver.number), // to
                        new PhoneNumber(from), // from
                        body).create();
                logger.info("message sid is {}", message.getSid());
            }
        }
        return matches;
    }
}