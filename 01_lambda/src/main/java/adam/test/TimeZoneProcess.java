package adam.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneProcess {
    public static void main(String[] args) throws ParseException {
        TimeZone timeZone = TimeZone.getTimeZone("Europe/London");
        boolean b = timeZone.inDaylightTime(new SimpleDateFormat("yyyyMMdd").parse("20241027"));
        System.out.println("当前系统时间是否是夏令时:"+b);
    }
}
