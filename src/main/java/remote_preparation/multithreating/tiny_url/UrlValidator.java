package remote_preparation.multithreating.tiny_url;

import java.net.URI;

public final class UrlValidator {

    public void validateLongUrl(String value) {
        URI uri;

        try {
            uri = URI.create(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid URL", e);
        }

        if (!uri.isAbsolute()) {
            throw new IllegalArgumentException("URL must be absolute");
        }

        if (!"http".equalsIgnoreCase(uri.getScheme())
                && !"https".equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException(
                    "Only HTTP and HTTPS URLs are supported"
            );
        }

        if (uri.getHost() == null) {
            throw new IllegalArgumentException("URL must contain a host");
        }
    }
}