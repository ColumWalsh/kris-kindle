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
    public List<Match<Person>> runKrisKindle(@Body List<Person> participants, @QueryValue Integer limit,
            @Nullable @QueryValue(defaultValue = "false") Boolean dryRun) {
        logger.info("creating kris kindle for group {}. the limit is {} and dry run flag is {} ", participants, limit,
                dryRun);
        return smsSender.sendMessages(participants, dryRun, limit);
    }
}
