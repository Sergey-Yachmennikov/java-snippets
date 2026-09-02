package remote_preparation.multithreating.tiny_url;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryUrlRepository implements UrlRepository {

    private final Map<String, String> hashToUrl = new HashMap<>();
    private final Map<String, String> urlToHash = new HashMap<>();

    @Override
    public String findUrlByHash(String hash) {
        return hashToUrl.get(hash);
    }

    @Override
    public String findHashByUrl(String url) {
        return urlToHash.get(url);
    }

    @Override
    public void save(String hash, String url) {
        hashToUrl.put(hash, url);
        urlToHash.put(url, hash);
    }
}
