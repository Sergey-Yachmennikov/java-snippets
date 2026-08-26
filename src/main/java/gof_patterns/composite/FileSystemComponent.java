package gof_patterns.composite;

public interface FileSystemComponent {
    void print(String indent);
    long getSize(); // размер в байтах
}
