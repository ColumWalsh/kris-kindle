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

    @Value("${twilio.us.number}")
    private String twilioUsNumber;

    private static String BODY_TEMPLATE = "Hi %s, you're %s's secret santa."
            + " Get them something nice! The limit is €%s.";
            
    private static final Logger logger = LoggerFactory.getLogger(SmsSender.class);

    protected List<Match<Person>> sendMessages(List<Person> participants, boolean dryRun, Integer limit) {
        Twilio.init(twilioSid, authToken);
        List<Match<Person>> matches = GraphJumbler.jumble(participants);
        GraphJumbler.validateKindleMatches(matches);
        for (Match<Person> match : matches) {
            
            String body = String.format(BODY_TEMPLATE, match.giver.name, match.taker.name, limit);
            String from = twilioNumber;
            if(match.giver.number.startsWith("+1")) {
                logger.info("getting us number");
                from = twilioUsNumber;
            }
            if (dryRun == true) {
                logger.info("dry run for gifter {} to giftee {}", match.giver.name, match.taker.name);
                logger.info("from: {}", from);
                logger.info("to: {}", match.giver.number);
                if(match.giver.number.startsWith("+1")) {
                    logger.info("god damn canadians");
                }
                logger.info("body: {}", body);
            } else {
                try {
                    Message message = Message.creator(new PhoneNumber(match.giver.number), // to
                            new PhoneNumber(from), // from
                            body).create();
                    logger.info("message sid is {}", message.getSid()); 
                } catch (Exception e) {
                    logger.error("failed sending message to number: {} failed. message: {}.", match.giver.number, body, e);
                }
            }
        }
        return matches;
    }
}