package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    // ---------- Stage 2: all-DU-pairs (added on top of all-defs) ----------

    @Test
    @DisplayName("DU1 null -> IAE: attendance(31->(32,33))")
    void du1_nullAttendance_throws() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 3));
    }

    @Test
    @DisplayName("DU2 [1] thr=-1 -> IAE: threshold(31->(32,33))")
    void du2_negativeThreshold_throws() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[] {1}, -1));
    }

    @Test
    @DisplayName("DU3 [] thr=0 -> 5: present(35->(50,51)), score(51->56), attendance(31->(38,49)) with zero iterations")
    void du3_emptyArray_thresholdZero() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {}, 0));
    }

    @Test
    @DisplayName("DU4 [1,0,1] thr=3 -> 0: streak(41->(42,38)), best(43->(42,38)), best(43->(53,56)), present(40->(50,53))")
    void du4_presentAbsentPresent_belowThreshold() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {1, 0, 1}, 3));
    }

    @Test
    @DisplayName("DU5 [1,1,1,1,1] thr=6 -> 3: score(49->54)")
    void du5_streakOfFive_belowThreshold() {
        assertEquals(3, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1}, 6));
    }

    @Test
    @DisplayName("DU6 [1,1,1,1,1,1] thr=0 -> 8: streak longer than 5, threshold 0")
    void du6_streakLongerThanFive_thresholdZero() {
        assertEquals(8, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1, 1}, 0));
    }

    @Test
    @DisplayName("DU7 [1,1,1,1,0,1,1,1,1] thr=8 -> 5: long run broken in the middle, best stays 4")
    void du7_longRunBrokenInMiddle_noStreakBonus() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 0, 1, 1, 1, 1}, 8));
    }

    // ---------- Part C: test added to kill mutant M2 (best = present) ----------

    @Test
    @DisplayName("K1 [1,1,1,0,1,1,1,1] thr=10 -> 0: best updated at line 43 while present(7) != streak(4)")
    void k1_bestUpdatedAfterBreak_presentDiffersFromStreak() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {1, 1, 1, 0, 1, 1, 1, 1}, 10));
    }
}
