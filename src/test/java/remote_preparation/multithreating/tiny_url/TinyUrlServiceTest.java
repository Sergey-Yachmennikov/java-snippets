package remote_preparation.multithreating.tiny_url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TinyUrlServiceTest {

    private TinyUrlService service;

    @BeforeEach
    void setUp() {
        service = new TinyUrlService(
                new Sha256HashGenerator(),
                new UrlValidator(),
                new InMemoryUrlRepository(),
                "https://tiny.local/"
        );
    }

    @Test
    void shouldEncodeUrl() {
        String longUrl = "https://example.com/some/very/long/path";

        String tinyUrl = service.encode(longUrl);

        assertTrue(tinyUrl.startsWith("https://tiny.local/"));

        String hash = tinyUrl.substring("https://tiny.local/".length());

        assertEquals(7, hash.length());
    }

    @Test
    void shouldDecodeTinyUrl() {
        String longUrl = "https://example.com/some/very/long/path";

        String tinyUrl = service.encode(longUrl);

        String decoded = service.decode(tinyUrl);

        assertEquals(longUrl, decoded);
    }

    @Test
    void sameUrlShouldAlwaysReturnSameTinyUrl() {
        String longUrl = "https://example.com/test";

        String first = service.encode(longUrl);
        String second = service.encode(longUrl);

        assertEquals(first, second);
    }

    @Test
    void differentUrlsShouldHaveDifferentHashes() {
        String firstUrl = "https://example.com/first";
        String secondUrl = "https://example.com/second";

        String first = service.encode(firstUrl);
        String second = service.encode(secondUrl);

        assertNotEquals(first, second);
    }

    @Test
    void shouldRejectInvalidLongUrl() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.encode("not a url")
        );
    }

    @Test
    void shouldRejectUnknownTinyUrl() {
        assertThrows(
                NotFoundException.class,
                () -> service.decode("https://tiny.local/abcdefg")
        );
    }

    @Test
    void shouldRejectInvalidTinyUrl() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.decode("https://another.local/abcdefg")
        );
    }
}
