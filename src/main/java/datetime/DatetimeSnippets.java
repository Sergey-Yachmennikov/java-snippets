package datetime;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatetimeSnippets {

    public static void run() {
        LocalDate days1 = LocalDate.now().minusDays(10);
        LocalDate days2 = LocalDate.now().minusDays(10);
        LocalDateTime now = LocalDateTime.now();
        long day = 86_400_000L;
        long milliSeconds = Timestamp.valueOf(LocalDateTime.now()).getTime();
        long miiliesBefore = milliSeconds - (day * 10);
        System.out.println(milliSeconds);
        System.out.println(miiliesBefore);
        System.out.println(milliSeconds - miiliesBefore);
        System.out.println((milliSeconds - miiliesBefore) / day);
    }
}
