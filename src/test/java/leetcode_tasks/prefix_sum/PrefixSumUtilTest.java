package leetcode_tasks.prefix_sum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrefixSumUtilTest {

    @Test
    void sumRange() {
        PrefixSumUtil.NumArray prefSummer = new PrefixSumUtil.NumArray(new int[]{1,2,3,4,5,6});
        assertEquals(20, prefSummer.sumRange(1, 5));
    }

    @Test
    void findMaxLength() {
        assertEquals(2, PrefixSumUtil.findMaxLength(new int[]{0,1}));
        assertEquals(2, PrefixSumUtil.findMaxLength(new int[]{0,1,0}));
    }

}