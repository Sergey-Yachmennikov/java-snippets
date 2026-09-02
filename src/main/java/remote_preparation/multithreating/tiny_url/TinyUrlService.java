package remote_preparation.multithreating.tiny_url;

import java.net.URI;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class TinyUrlService {

    private static final int HASH_LENGTH = 7;

    private final HashGenerator hashGenerator;
    private final UrlValidator urlValidator;
    private final UrlRepository repository;
    private final String baseUrl;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public TinyUrlService(
            HashGenerator hashGenerator,
            UrlValidator urlValidator,
            UrlRepository repository,
            String baseUrl
    ) {
        this.hashGenerator = hashGenerator;
        this.urlValidator = urlValidator;
        this.repository = repository;
        this.baseUrl = normalizeBaseUrl(baseUrl);
    }

    public String encode(String longUrl) {
        urlValidator.validateLongUrl(longUrl);

        lock.writeLock().lock();

        try {
            String existingHash = repository.findHashByUrl(longUrl);

            if (existingHash != null) {
                return buildTinyUrl(existingHash);
            }

            String hash = findFreeHash(longUrl);

            repository.save(hash, longUrl);

            return buildTinyUrl(hash);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String decode(String tinyUrl) {
        String hash = extractHash(tinyUrl);

        lock.readLock().lock();

        try {
            String longUrl = repository.findUrlByHash(hash);

            if (longUrl == null) {
                throw new NotFoundException(
                        "Tiny URL does not exist: " + tinyUrl
                );
            }

            return longUrl;
        } finally {
            lock.readLock().unlock();
        }
    }

    private String findFreeHash(String longUrl) {
        String initialHash = hashGenerator.generate(longUrl);

        validateHash(initialHash);

        String hash = initialHash;

        while (true) {
            String existingUrl = repository.findUrlByHash(hash);

            if (existingUrl == null) {
                return hash;
            }

            if (existingUrl.equals(longUrl)) {
                return hash;
            }

            hash = nextHash(hash);

            if (hash.equals(initialHash)) {
                throw new IllegalStateException("Hash space is exhausted");
            }
        }
    }

    private String nextHash(String hash) {
        char[] chars = hash.toCharArray();

        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] != 'z') {
                chars[i]++;
                return new String(chars);
            }

            chars[i] = '0';
        }

        return new String(chars);
    }

    private String buildTinyUrl(String hash) {
        return baseUrl + hash;
    }

    private String extractHash(String tinyUrl) {
        URI uri;

        try {
            uri = URI.create(tinyUrl);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid tiny URL",
                    e
            );
        }

        String expectedBase = URI.create(baseUrl).toString();

        String actualBase = uri.getScheme()
                + "://"
                + uri.getAuthority()
                + "/";

        if (!expectedBase.equals(actualBase)) {
            throw new IllegalArgumentException(
                    "Tiny URL belongs to another domain"
            );
        }

        String path = uri.getPath();

        if (path == null || path.length() != HASH_LENGTH + 1) {
            throw new IllegalArgumentException("Invalid hash");
        }

        String hash = path.substring(1);

        validateHash(hash);

        return hash;
    }

    private void validateHash(String hash) {
        if (hash.length() != HASH_LENGTH) {
            throw new IllegalArgumentException(
                    "Hash must contain exactly 7 characters"
            );
        }

        for (char c : hash.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                throw new IllegalArgumentException(
                        "Hash contains invalid characters"
                );
            }
        }
    }

    private String normalizeBaseUrl(String value) {
        return value.endsWith("/") ? value : value + "/";
    }
}
