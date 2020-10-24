package com.cwality;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creates random matches for the kris kindle participants. Doesn't support partners or exclusions.
 * Creates a Hamiltonian path in a graph of all participants.
 * @author cwalsh
 *
 */
public class HamiltonianJumbler {
    
    static < T > List<Match<T>> jumble(List<T> participants){
        if(participants==null || participants.size()<=1) {
            throw new IllegalArgumentException("We need at least 2 participants to run a kris kindle");
        }
        List<Match<T>> matches = new ArrayList<>();
        Collections.shuffle(participants);
        for(int i = 0; i<participants.size(); i++ ) {
            if(i==participants.size()-1) {
                matches.add(new Match<T>(participants.get(i), participants.get(0)));
            }
            else {
                matches.add(new Match<T>(participants.get(i), participants.get(i+1)));               
            }
        }
        return matches;
    }
}
