package gof_patterns.composite;

public class File implements FileSystemComponent {
    private final String name;
    private final long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "File: " + name + " (" + size + " bytes)");
    }

    @Override
    public long getSize() {
        return size;
    }
}