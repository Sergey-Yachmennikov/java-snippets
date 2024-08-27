package patterns.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void PostBuilderTest() {
        Post post = new Post.Builder()
                .title("Java Builder Pattern")
                .text("Explaining how to implement the Builder Pattern in Java")
                .category("Programming")
                .build();

        assertEquals("Java Builder Pattern", post.getTitle());
        assertEquals("Explaining how to implement the Builder Pattern in Java", post.getText());
        assertEquals("Programming", post.getCategory());
    }
}