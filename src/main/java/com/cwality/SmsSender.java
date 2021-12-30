package com.cwality;

import java.util.List;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class SmsSender {

    private static String BODY_TEMPLATE = "Hi %s, you're %s's secret santa."
            + " Get them something nice! The limit is €%s.";

    private static final Logger logger = LoggerFactory.getLogger(SmsSender.class);

    protected List<Match<Person>> sendMessages(List<Person> participants, Integer limit) {
        List<Match<Person>> matches = GraphJumbler.jumble(participants);
        GraphJumbler.validateKindleMatches(matches);
        for (Match<Person> match : matches) {

            String body = String.format(BODY_TEMPLATE, match.giver.name, match.taker.name, limit);
            logger.info("dry run for gifter {} to giftee {}", match.giver.name, match.taker.name);
            logger.info("to: {}", match.giver.number);
            if (match.giver.number.startsWith("+1")) {
                logger.info("god damn canadians");
            }
            logger.info("body: {}", body);
        }
        return matches;
    }
}