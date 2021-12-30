package com.cwality;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HamiltonianJumblerTest {
    private static final Logger logger = LoggerFactory.getLogger(HamiltonianJumblerTest.class);
    
    @Test
    public void testJumbling() {
        assertThrows(IllegalArgumentException.class, () -> {
            HamiltonianJumbler.jumble(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            HamiltonianJumbler.jumble(Arrays.asList("santa"));
        });
        List<Match<String>> twoKindle = HamiltonianJumbler.jumble(Arrays.asList("santa", "rudolf"));
        logger.debug(twoKindle.toString());
        validateKindleMatches(twoKindle);
        List<Match<String>> threeKindle = HamiltonianJumbler.jumble(Arrays.asList("santa", "rudolf", "jimmy", "jack"));
        logger.debug(threeKindle.toString());
        validateKindleMatches(threeKindle);
        List<Match<String>> fourKindle = HamiltonianJumbler.jumble(Arrays.asList("santa", "rudolf", "jesus", "snowman", "elf"));
        logger.debug(fourKindle.toString());
        validateKindleMatches(fourKindle);
    }
    
    private void validateKindleMatches(List<Match<String>> fourKindle) {
        Set<String> givers = new HashSet<>();
        Set<String> takers = new HashSet<>();
        for(Match<String> match: fourKindle) {
            if(match.giver==match.taker) {
                throw new AssertionError("shouldn't give a present to yourself, "+ match);
            }
            if(givers.contains(match.giver)) {
                throw new AssertionError(match.giver+" is giving a present to someone else\n"+ fourKindle);                
            }
            if(takers.contains(match.taker)) {
                throw new AssertionError(match.taker+" is giving a present to someone else\n"+ fourKindle);                
            }
            else {
                givers.add(match.giver);
                takers.add(match.taker);
            }
        }
    }

}
