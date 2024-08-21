package data_structures.monad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonadTest {

    @Test
    void monadTest() {
        Client client = new Client("Harry Carter", 15, true);
        String result = Monad.from(client)
                .map(Client::getName)
                .map(s -> s.split(" "))
                .map(c -> c[0])
                .unwrapValue();

        assertEquals("Harry", result);
    }

    @Test
    void maybeMonadTest() {
        Client client = new Client("Harry Carter", 15, true);
        String result = MaybeMonad.from(client)
                .map(Client::getName)
                .map(s -> s.split(" "))
                .map(c -> c[0])
                .unwrapValue();

        assertEquals("Harry", result);
    }

    @Test
    void lazyMonad() {
        Client client = new Client("Harry Carter", 15, true);
        LazyMonad<String> composed = LazyMonad.from(client)
                .map(Client::getName)
                .map(s -> s.split(" "))
                .map(c -> c[0]);

        String result = composed.get();
        assertEquals("Harry", result);
    }

}