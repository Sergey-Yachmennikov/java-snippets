package remote_preparation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeUtilTest {

    @Test
    void reverseToConsole() {
        NodeV2<Integer> list = new NodeV2<>(1, new NodeV2<>(2, new NodeV2<>(3, new NodeV2<>(4, null))));
        NodeUtil.reverse(list);
    }
}