package patterns.builder;

import gof_patterns.builder.GenericBuilder;
import gof_patterns.builder.GenericPost;
import gof_patterns.builder.Post;
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

    @Test
    void genericBuilderTest() {
        GenericPost post = GenericBuilder.of(GenericPost::new)
                .with(GenericPost::setTitle, "Java Builder Pattern")
                .with(GenericPost::setText, "Explaining how to implement the Builder Pattern in Java")
                .with(GenericPost::setCategory, "Programming")
                .build();

        assertEquals("Java Builder Pattern", post.getTitle());
        assertEquals("Explaining how to implement the Builder Pattern in Java", post.getText());
        assertEquals("Programming", post.getCategory());
    }
}