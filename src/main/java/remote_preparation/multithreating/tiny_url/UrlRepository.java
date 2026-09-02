package remote_preparation.multithreating.tiny_url;

public interface UrlRepository {

    String findUrlByHash(String hash);

    String findHashByUrl(String url);

    void save(String hash, String url);
}
