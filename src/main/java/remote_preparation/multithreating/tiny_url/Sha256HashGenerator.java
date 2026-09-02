package remote_preparation.multithreating.tiny_url;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Sha256HashGenerator implements HashGenerator {

    private static final String ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final int HASH_LENGTH = 7;

    @Override
    public String generate(String value) {
        byte[] hash = sha256(value);

        return toBase62(hash);
    }

    private byte[] sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(value.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    private String toBase62(byte[] bytes) {
        BigInteger number = new BigInteger(1, bytes);

        StringBuilder result = new StringBuilder();

        while (number.signum() > 0 && result.length() < HASH_LENGTH) {
            BigInteger[] division = number.divideAndRemainder(
                    BigInteger.valueOf(62)
            );

            result.append(ALPHABET.charAt(division[1].intValue()));
            number = division[0];
        }

        while (result.length() < HASH_LENGTH) {
            result.append('0');
        }

        return result.reverse().toString();
    }
}
