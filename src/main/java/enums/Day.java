package enums;

public enum Day {
    MONDAY("dick", 1),
    TUESDAY("pick", 2);

    final String type;
    final int index;

    Day(String type, int index) {
        this.type = type;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return type;
    }
}