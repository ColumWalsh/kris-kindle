package com.cwality;

import static com.cwality.GraphJumbler.validateKindleMatches;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GraphJumblerTest {
    
    private static final Person JESUS = new Person("jesus", "0");
    private static final Person ELF = new Person("elf", "0");
    private static final Person MRS_SANTA = new Person("mrs santa", "0", new Person("santa", "0"));
    private static final Person RUDOLF = new Person("rudolf", "0");
    private static final Person SANTA = new Person("santa", "0", new Person("mrs santa", "0"));

    @Test
    public void testJumbling() {
        assertThrows(IllegalArgumentException.class, () -> {
            GraphJumbler.jumble(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            HamiltonianJumbler.jumble(Arrays.asList("santa"));
        });
        List<Match<Person>> twoKindle = GraphJumbler.jumble(Arrays.asList(SANTA, ELF));
        System.out.println(twoKindle);
        validateKindleMatches(twoKindle);
        List<Match<Person>> fourKindle = GraphJumbler.jumble(Arrays.asList(SANTA, RUDOLF, JESUS, MRS_SANTA));
        System.out.println(fourKindle);
        validateKindleMatches(fourKindle);
        List<Match<Person>> fiveKindle = GraphJumbler.jumble(Arrays.asList(SANTA, RUDOLF, JESUS, MRS_SANTA, ELF));
        System.out.println(fiveKindle);
        validateKindleMatches(fiveKindle);
    }
    
    @Test
    public void testUnmatchableGroup() {
        assertThrows(IllegalStateException.class, () -> {
            //Impossible to find a match for this group as only one person can gift to Rudolf, and the santas can't gift to each other
            GraphJumbler.jumble(Arrays.asList(MRS_SANTA, SANTA, RUDOLF));
        });
    }
}
