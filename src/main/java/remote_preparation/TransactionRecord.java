package remote_preparation;

record TransactionRecord(
        String id,
        double amount,
        String type
) {

    TransactionRecord {
        if (amount < 1000.00) throw new IllegalArgumentException("Too little amount");
    }
}
