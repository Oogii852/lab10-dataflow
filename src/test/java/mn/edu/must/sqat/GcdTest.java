package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Part A: all-DU-pairs coverage for gcd(x, y).
 * Line numbers refer to DataFlowTargets.java:
 * 13 def x,y | 15 p-use y | 16 c-use x,y; def tmp | 17 c-use y; def x | 18 c-use tmp; def y | 20 c-use x
 * Pair notation: var(def -> use); p-use edges written as (15,16)=True, (15,20)=False.
 */
class GcdTest {

    @Test
    @DisplayName("G1 gcd(7,0)=7: x(13->20), y(13->(15,20)) - zero iterations")
    void g1_zeroIterations_coversX13to20_Y13toFalse() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("G2 gcd(0,5)=5: x(13->16), x(17->20), y(13->(15,16)), y(13->16), y(13->17), y(18->(15,20)), tmp(16->18) - one iteration")
    void g2_oneIteration_coversFirstPassPairs() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }

    @Test
    @DisplayName("G3 gcd(12,8)=4: x(17->16), y(18->(15,16)), y(18->16), y(18->17) - two iterations (loop-carried pairs)")
    void g3_twoIterations_coversLoopCarriedPairs() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    @Test
    @DisplayName("G4 gcd(8,12)=4: x<y case (first iteration swaps), three iterations - extra check of loop-carried pairs")
    void g4_threeIterations_swapCase() {
        assertEquals(4, DataFlowTargets.gcd(8, 12));
    }
}
