package objects;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;


class ObjectsSnippetsTest {


    @Spy
    ObjectsSnippets objectsSnippets = new ObjectsSnippets();

    @Test
    @Disabled
    void getNumbersTest() {
        Mockito.verify(objectsSnippets).getNumbers();
        Mockito.verify(objectsSnippets, Mockito.times(1)).getNumbers();
    }
}