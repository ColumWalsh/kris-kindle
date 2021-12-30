package com.cwality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates random matches for the Kris Kindle participants.
 * Uses a graph to find matches, inspired by https://www.tjmahr.com/secret-santa-graph-traversal/
 * Supports excluding partners, sometimes fails to find matches for everyone
 * @author cwalsh
 *
 */
public class GraphJumbler {
    
    private static Logger logger = LoggerFactory.getLogger(GraphJumbler.class);

    public static  List<Match<Person>> jumble(List<Person> participants){
        if(participants==null || participants.size()<=1) {
            throw new IllegalArgumentException("We need at least 2 participants to run a kris kindle");
        }
        try {
            return oneShotJumbler(participants);            
        }
        catch (IllegalStateException e) {
            //one more try
            return oneShotJumbler(participants);
        }
    }

    private static List<Match<Person>> oneShotJumbler(List<Person> participants) {
        Random rand = new Random();
        List<Match<Person>> matches = new ArrayList<>();
        Map<Person, List<Person>> graph = new HashMap<>();
        for (Person person : participants) {
            graph.put(person, new ArrayList<>(participants));
        }
        
        for(Person person : participants) {
            List<Person> edges = graph.get(person);
            edges.remove(person); //can't gift yourself
            if(person.partner != null) {
                edges.remove(person.partner); //can't gift your partner                
            }
            if(edges.size()==0) {
                logger.error("no matches left for {}", person);
                logger.error(matches.toString());
                throw new IllegalStateException("no matches left for "+ person);
            }
            int recipientIndex=rand.nextInt(edges.size());
            Person recipient = edges.get(recipientIndex);
            edges.remove(recipientIndex);
            graph = removeRecipient(graph, recipient);
            matches.add(new Match<Person>(person, recipient));
        }
        return matches;
    }

    private static Map<Person, List<Person>> removeRecipient(Map<Person, List<Person>> graph, Person recipient) {
        for(List<Person> person: graph.values()) {
            person.remove(recipient);
        }
        return graph;
    }
    
    static void validateKindleMatches(List<Match<Person>> matches) {
        Set<Person> givers = new HashSet<>();
        Set<Person> takers = new HashSet<>();
        for(Match<Person> match: matches) {
            if(match.giver.equals(match.taker)) {
                throw new AssertionError("shouldn't give a present to yourself, "+ match);
            }
            if(match.taker.equals(match.giver.partner)) {
                throw new AssertionError("shouldn't give a present to your partner, "+ match );
            }
            if(givers.contains(match.giver)) {
                throw new AssertionError(match.giver+" is giving a present to someone else\n"+ matches);                
            }
            if(takers.contains(match.taker)) {
                throw new AssertionError(match.taker+" is recieving a present from someone else\n"+ matches);                
            }
            else {
                givers.add(match.giver);
                takers.add(match.taker);
            }
        }
    }
}