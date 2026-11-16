package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Part B4: data-flow tests for bonus(attendance, threshold).
 * Line numbers refer to DataFlowTargets.java (31-56).
 *
 * Stage 1 - ALL-DEFS (minimum 2 tests):
 * score def 49 can reach a use only when line 51 is skipped (present < threshold),
 * score def 51 exists only when present >= threshold -> mutually exclusive in one run.
 */
class BonusTest {

    // ---------- Stage 1: all-defs ----------

    @Test
    @DisplayName("AD1 [1,1,1,1,1,0,1] thr=6 -> 8: present(35,40) streak(36,41,46) best(37,43) a(38) score(51->54, 54->56)")
    void ad1_streakOfFiveThenBreak_exactThreshold() {
        assertEquals(8, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1, 0, 1}, 6));
    }

    @Test
    @DisplayName("AD2 [0] thr=1 -> 0: score(49->56), present(35->(50,53)), best(37->(53,56))")
    void ad2_allAbsent_belowThreshold() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {0}, 1));
    }
}
