package guru;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    // @Trasactional(noRollbackFor = Exception.class)
    public void processTransaction(Data data) {
        if (data.isValid()) {
            // logic
        } else {
            throw new RuntimeException();
        }
    }

    public static abstract class Data {
        abstract boolean isValid();
    }
}
