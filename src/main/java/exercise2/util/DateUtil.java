package exercise2.util;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {

    public static Date addDays(final Date date, int days) {
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("CET"));
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return new Date(calendar.getTime().getTime());
    }
}
