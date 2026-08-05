package remote_preparation;

public class Transaction {
    private String type;
    private double amount;
    private String currency;

    public Transaction(String type, double amount, String currency) {
        this.type = type;
        this.amount = amount;
        this.currency = currency;
    }

    public String getCurrency() { return currency; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return String.format("Transaction{type='%s', amount=%.2f, currency='%s'}", type, amount, currency);
    }
}
