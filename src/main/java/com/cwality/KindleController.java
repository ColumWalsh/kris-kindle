package com.cwality;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;

@Controller("/KrisKindle")
public class KindleController {

    Logger logger = LoggerFactory.getLogger(KindleController.class);

    @Inject
    SmsSender smsSender;

    @Post("/")
    public List<Match<Person>> runKrisKindle(@Body List<Person> participants, @QueryValue Integer limit) {
        logger.info("creating kris kindle for group {}. the limit is {}", participants, limit);
        return smsSender.sendMessages(participants, limit);
    }
}