package stream;


import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Messages {
    private StringBuffer sb = new StringBuffer();

    public void addMessageRecord(MessageRecord record) {
        sb.append(record);
    }

    public Stream<MessageRecord> stream() {
        return StreamSupport.stream(getSpliterator(), false);
    }

    public Spliterator<MessageRecord> getSpliterator() {
        class InnerSpliterator implements Spliterator<MessageRecord> {
            private int start;
            private int end;

            public InnerSpliterator(int start, int end) {
                this.start = start;
                this.end = end;
            }

            @Override
            public boolean tryAdvance(Consumer<? super MessageRecord> action) {
                if (start >= end) return false;
                int s = MessageRecord.SIZE;
                MessageRecord messageRecord = MessageRecord.fromString(sb.substring(start * s, (start + 1) * s));
                action.accept(messageRecord);
                sb.replace(start * s, (start + 1) * s, messageRecord.toString());
                start += 1;
                return true;
            }

            @Override
            public Spliterator<MessageRecord> trySplit() {
                if (end - start < 2) return null;
                int middle = (end + start) / 2;
                Spliterator<MessageRecord> newSplit = new InnerSpliterator(start, middle);
                start = middle;
                return newSplit;
            }

            @Override
            public long estimateSize() {
                return end - start;
            }

            @Override
            public int characteristics() {
                return Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.NONNULL;
            }
        }
        return new InnerSpliterator(0, sb.length() / MessageRecord.SIZE);
    }
}
