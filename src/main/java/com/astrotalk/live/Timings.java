package com.astrotalk.live;

import org.apache.logging.log4j.LogManager;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Timings {

    private static final org.apache.logging.log4j.Logger logPapertrail = LogManager.getLogger("papertrail-log");

    public final static Long millisInADay = 24 * 60 * 60 * 1000l;
    public final static Long TEN_MIN = 10 * 60 * 1000l;
    public final static Long TWO_MIN = 2 * 60 * 1000l;
    public final static Long ONE_MIN = 1 * 60 * 1000l;
    public final static Long THREE_MIN = 3 * 60 * 1000l;
    public final static Long FIVE_MIN = 5 * 60 * 1000l;
    public final static Long FOURTY_FIVE_MIN = 45 * 60 * 1000l;
    public final static Long TWELVE_HOUR = 12 * 60 * 60 * 1000l;
    public final static Long FOURTYEIGHT_HOUR = 48 * 60 * 60 * 1000l;
    public final static Long millisIn3Days = 3 * 24 * 60 * 60 * 1000l;
    public final static Long millisInAMonth = 30 * 24 * 60 * 60 * 1000l;
    public final static Long millisIn2Month = 2 * 30 * 24 * 60 * 60 * 1000l;

    public final static Long millisIn3Month = 90 * 24 * 60 * 60 * 1000l;


    public final static Long millisInHour = 60 * 60 * 1000l;
    public final static Long millisInTwoHour = 2 * 60 * 60 * 1000l;
    // public final static String TIME_zONE = 20 * 60 * 1000l;

    private static final org.apache.logging.log4j.Logger logMain = LogManager.getLogger("main-log");

    public enum Day {
        Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
    }

    public class Timezone {
        public static final long IST = 11 * 30 * 60 * 1000;
    }

    @SuppressWarnings("static-access")
    public static int getDay() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(cal.getTimeInMillis() + 11 * 30 * 60 * 1000);
        int i = cal.get(cal.DAY_OF_WEEK);
        return i;
    }

    // here currentTime received as param is obtained from different time_zone
    @SuppressWarnings("static-access")
    public static int getDay(long currentTime) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(currentTime);
        int i = cal.get(cal.DAY_OF_WEEK);
        // logMain.info("currentTime:- "+currentTime+", DAY_OF_WEEK:-
        // "+i);
        return i;
    }

    public static Day getDayasName() {
        int i = getDay();

        switch (i) {
            case 1: {
                return Day.Sunday;
            }
            case 2: {
                return Day.Monday;
            }
            case 3: {
                return Day.Tuesday;
            }
            case 4: {
                return Day.Wednesday;
            }
            case 5: {
                return Day.Thursday;
            }

            case 6: {
                return Day.Friday;
            }
            case 7: {
                return Day.Saturday;
            }
            default: {
                return null;
            }
        }

    }

    public static long getOffsetTime(long time) {
        long offset = time % (24 * 60 * 60 * 1000);
        return offset;
    }

    public static long getAbsoluteTime(long offset) {
        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();// + 11 * 30 * 60 * 1000;
        long q = offset / (24 * 60 * 60 * 1000);
        long r = offset % (24 * 60 * 60 * 1000);
        long absoluteTime = (currentTime - getOffsetTime(currentTime)) + q * (24 * 60 * 60 * 1000) + r;
        return absoluteTime;
    }

    public static long getAbsoluteTime(long offset, Long epochtime) {
        long currentTime = epochtime;
        long q = offset / (24 * 60 * 60 * 1000);
        long r = offset % (24 * 60 * 60 * 1000);
        long absoluteTime = (currentTime - getOffsetTime(currentTime)) + q * (24 * 60 * 60 * 1000) + r;
        return absoluteTime;
    }

    public static Long getTimeAfterDays(long absoluteStartTimeOfDay, Integer days) {
        return absoluteStartTimeOfDay + millisInADay * (days - 1);

    }

    public static long getTimeZoneOffsetTime(String time_zone) {
        LocalDateTime dt = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of(time_zone);
        ZonedDateTime zdt = dt.atZone(zoneId);
        ZoneOffset offset = zdt.getOffset();
        long offsetTimeInMillSec = offset.getTotalSeconds() * 1000;
        return offsetTimeInMillSec;
    }

    public static long currentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        long time = cal.getTimeInMillis();
        return time;
    }

    public static long currentTime(long offset) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        long time = cal.getTimeInMillis() + offset;
        return time;
    }

    public static String currentDate(String timezone_id) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone_id));
        return sdf.format(new Date(currentTime()));
    }

    public static boolean withinDay(long target_day, long reference_day, long timezoneoffset) {
        target_day = target_day - timezoneoffset;
        target_day = getAbsoluteTime(0, target_day);
        target_day = target_day + timezoneoffset;
        reference_day = getAbsoluteTime(0, currentTime());
        reference_day = reference_day + timezoneoffset;
        long end_of_day = reference_day + millisInADay;
        return (target_day >= reference_day && target_day <= end_of_day);
    }

    public static long startofday(long timestamp, String timezone_id) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone(timezone_id));
            return sdf.parse((sdf.format(new Date(timestamp)))).getTime();
        } catch (ParseException e) {
            logMain.error("EXCEPTION", e);
            return 0;
        }
    }

    public static String getDate(long timestamp, String timezone_id) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone_id));
        return sdf.format(new Date(timestamp));

    }

    public static String getHumanReadableDateTime(long timestamp, String timezone_id) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone_id));
        return sdf.format(new Date(timestamp));
    }

    public static String getHour(long timestamp, String timezone_id) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone_id));
        return sdf.format(new Date(timestamp));
    }

    public static int daysBetween(Long time1, Long time2) {
        return (int) Math.abs((time1 - time2) / millisInADay) + 1;

    }

    @SuppressWarnings("static-access")
    public static int daysBetweenExcludingSunday(Long time1, Long time2) {
        Integer days_between = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        Long start_time = startofday(time1, "Asia/Kolkata");
        logMain.info(start_time);
        Long end_time = startofday(time2, "Asia/Kolkata");
        Long time = start_time;

        while (time <= end_time) {
            calendar.setTimeInMillis(time);
            int i = calendar.get(calendar.DAY_OF_WEEK);
            // logMain.info(i);
            if (i != 1)
                days_between++;
            time += millisInADay;
        }
        return days_between;

    }

    public static int daysBetween(Date start_date, Date end_date) {
        return daysBetween(start_date.getTime(), end_date.getTime());

    }

    // get current time according to time zone
    public static Long getTimeByTimeZone(String timezone) {

        Calendar c = Calendar.getInstance();

        c.setTimeZone(TimeZone.getTimeZone(timezone));

        return c.getTimeInMillis();

    }

    public static Long getLongTimeOfHours(String time) throws ParseException {

        // String time_zone = "Asia/Kolkata";
        // LocalDateTime dt = LocalDateTime.now();
        // ZoneId zoneId = ZoneId.of("Australia/Melbourne");
        // ZoneId zoneId = ZoneId.of(time_zone);
        // ZonedDateTime zdt = dt.atZone(zoneId);
        // ZoneOffset offset = zdt.getOffset();
        // long offsetTimeInMillSec = offset.getTotalSeconds() * 1000;
        // logMain.info("offset time =" + offsetTimeInMillSec);
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        Date date = sdf.parse(time);

        logMain.info("time = " + date.getTime());
        // logMain.info("Time: " + (date.getTime() +
        // offsetTimeInMillSec));
        // return ((date.getTime()) + (offsetTimeInMillSec));
        return date.getTime();

    }

    public static String convertTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        logMain.info("data == > " + format.format(date));
        return format.format(date);
    }

    public static String getUTCTimeSting(String time, String timezone) {
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        if (time == null)
            return null;

        Date date = null;
        try {
            date = sdf.parse(time);
            long tt = Timings.getTimeZoneOffsetTime(timezone);
            long final_ = date.getTime() - tt;
            return sdf.format(new Date(final_));
        } catch (ParseException e) {
            logMain.error("EXCEPTION", e);
            return null;
        }
    }

    public static Long getCurrentTimeByTimeZone(String timeZone) {
        DateTime now = new DateTime(System.currentTimeMillis(), DateTimeZone.forID(timeZone));
        logMain.info("Current time by time zone is: =======>>>>>>>>>" + now.getMillis());
        return now.getMillis();

    }

    public static String dateInUtcFormate(long currentTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm a");

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String timeOfUtc = sdf.format(new Date(currentTime));

        return timeOfUtc;

    }

    public static String currentDateTime(long currentTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");

        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        String timeOfUtc = sdf.format(new Date(currentTime));

        return timeOfUtc;

    }

    public static String convertTimeIntoString(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
        // logMain.info("data == > " + format.format(date));
        return format.format(date);
    }

//	public static String getDayByTimeZone(String timezone) {
//		Long time = currentTime();
//
//		DateFormat df = new SimpleDateFormat("EEEE");
//
//		// df.setTimeZone(TimeZone.getTimeZone("America/Atka"));
//
//		df.setTimeZone(TimeZone.getTimeZone(timezone));
//
//		String day = df.format(new Date(time));
//
//		logMain.info("day  = " + day);
//		int i = Offer.getDayasName(day);
//
//		time = time + millisInADay * i;
//
//		DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
//
//		df1.setTimeZone(TimeZone.getTimeZone(timezone));
//
//		return df1.format(new Date(time));
//
//	}

    public static Long getStartTimeOfDay(String timezone) {

        DateTimeZone timeZone = DateTimeZone.forID(timezone);

        DateTime now = DateTime.now(timeZone);
        logMain.info("days of weeak = > " + now.getDayOfWeek());
        DateTime todayStart = now.withTimeAtStartOfDay();
        DateTime tomorrowStart = now.plusDays(1).withTimeAtStartOfDay();
        Interval today = new Interval(todayStart, tomorrowStart);
        logMain.info("time of start of day= >" + today.getStartMillis());
        return today.getStartMillis();
    }

    public static Long getLongByStringTime(String time) throws ParseException {

        DateFormat sdf = new SimpleDateFormat("hh:mm:ss a");

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = sdf.parse(time);

        return date.getTime();

    }

    public static Long getLongByStringTime(String time, String format) throws ParseException {

        DateFormat sdf = new SimpleDateFormat(format);

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = sdf.parse(time);

        return date.getTime();

    }

    public static Long getTimeInMillis(String time, String timezone) throws ParseException {

        DateFormat sdf = new SimpleDateFormat("hh:mm:ss a");

        sdf.setTimeZone(TimeZone.getTimeZone(timezone));

        Date date = sdf.parse(time);

        return date.getTime();

    }

    @SuppressWarnings("deprecation")
    public static long getAge(Date date) {
        try {
            logMain.info("my dob " + (date.getYear() + 1900) + " " + date.getMonth() + " " + date.getDate());
            LocalDate start = LocalDate.of(date.getYear() + 1900, date.getMonth(), date.getDate());
            LocalDate end = LocalDate.now(ZoneId.of("Asia/Kolkata"));// use for
            // age-calculation:
            // LocalDate.now()
            long years = ChronoUnit.YEARS.between(start, end);
            logMain.info("years" + years);
            return years;
        } catch (Exception e) {
            return 0l;

        }
    }

    public static long getLastDaysTime(int day, String timezone) {
        long time = startofday(currentTime(), timezone) - 19800000l;

        return (time - (millisInADay * day));

    }

    public static String getTimeZoneShortName(String timezone) {

        TimeZone timeZone = TimeZone.getTimeZone(timezone);

        String timeZoneId = timeZone.getDisplayName();

        logMain.info("timeZoneId =  > " + timeZoneId);

        String[] stz = timeZoneId.split(" ");

        StringBuilder sName = new StringBuilder();

        for (int i = 0; i < stz.length; i++) {
            sName.append(stz[i].charAt(0));
        }
        return sName.toString().replace("(", "");
    }

    public static Long getTodaysTimeAbsolute(Long date) {

        long q = date / (24 * 60 * 60 * 1000L);
        // long r = offset % (24 * 60 * 60 * 1000);
        long absoluteTime = q * (24 * 60 * 60 * 1000L);
        return absoluteTime;
    }

    public static int convertMonthToInt(String month) throws ParseException {
        Date date = new SimpleDateFormat("MMMM").parse(month);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static long getStartTimeOfDay(long time, String timeZone) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        cal.setTime(new Date(time)); // compute start of the day for the timestamp
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static String getAppointmentTime(long time) {

        DateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy");

        sdf1.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
        String date = sdf1.format(time);
        DateFormat sdf = new SimpleDateFormat("hh:mm a");

        sdf.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));

        String start_time = sdf.format(time);

        String end_time = sdf.format(time + (30 * 60 * 1000));

        return date + " (" + start_time + " - " + end_time + " IST)";

    }

    public static Long NextMinute(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

//		logMain.info("Day " + day + " Hour " + hours + " Minute " + minute + " Seconds " + second);

        long timeInMin = 0;
        if (hours > 0)
            timeInMin = hours * 60 + minute;
        else
            timeInMin = minute;
        // if (second > 0)
        // timeInMin += 1;

        if (second > 1)
            timeInMin += 1;

        // logMain.info("timeInMin " + timeInMin);

        return timeInMin;
    }

    public static Long NextMinuteChat(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

//		logMain.info("Day " + day + " Hour " + hours + " Minute " + minute + " Seconds " + second);

        long timeInMin = 0;
        if (hours > 0)
            timeInMin = hours * 60 + minute;
        else
            timeInMin = minute;
        // if (second > 0)
        // timeInMin += 1;

        if (second > 3)
            timeInMin += 1;

        // logMain.info("timeInMin " + timeInMin);

        return timeInMin;
    }

    public static long minusMonthStart(int month) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.minusMonths(month).withDayOfMonth(1).withTimeAtStartOfDay().getMillis();
    }

    public static long minusMonth(int month) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.minusMonths(month).withTimeAtStartOfDay().getMillis();
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis()-14515200000l);
    }
    public static long plusMonth(int month) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.plusMonths(month).withTimeAtStartOfDay().getMillis();
    }

    public static long minusWeek(int week) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.minusWeeks(week).withTimeAtStartOfDay().getMillis();
    }

    public static long minusMinute(int minute) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.minusMinutes(minute).getMillis();
    }

    public static long plusMinute(int minute) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.plusMinutes(minute).getMillis();
    }

    public static long minusHourIndianTimezone(int hour) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.minusHours(hour).getMillis();
    }

    public static long minusHour(int hour) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.minusHours(hour).getMillis();
    }

    public static long plusHour(int hour) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.plusHours(hour).getMillis();
    }

//	public static void main(String[] args) {
//		System.out.println(Timings.getYear(System.currentTimeMillis()));
//	}

    public static String getDate(Long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        sdfDate.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return sdfDate.format(date);
    }

    public static String getYear(Long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy");
        sdfDate.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return sdfDate.format(date);
    }

    public static String getTime(Long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        sdfTime.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return sdfTime.format(date);
    }

    public static String getMonth(Long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat sdfTime = new SimpleDateFormat("MMMM yyyy");
        sdfTime.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return sdfTime.format(date);
    }

    public static String getToday() {
        Date date = new Date();
        SimpleDateFormat sdfTime = new SimpleDateFormat("EEEE");
        sdfTime.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return sdfTime.format(date);
    }

    public static String getTimeForFreeCalling(Long startTime, Long endTime) {
        Date date = new Date(startTime);
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh");
        sdfTime.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        String time = sdfTime.format(date) + ":00";

        date = new Date(endTime);
        sdfTime = new SimpleDateFormat("hh");
        sdfTime.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        time = time + " -" + sdfTime.format(date) + ":00";

        date = new Date(endTime);
        sdfTime = new SimpleDateFormat("a");
        sdfTime.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        time = time + " " + sdfTime.format(date);

        return time;
    }

    public static long plusWeek(Long time, int week) {
        DateTime now = new DateTime(time);
        return now.plusWeeks(week).getMillis();
    }

    public static long getPreviousStartTime() {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        DateTime todayStart = now.minusDays(1).withTimeAtStartOfDay();
        return todayStart.getMillis();
    }

    public static long getPreviousEndTime() {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.withTimeAtStartOfDay().getMillis();
    }

    public static long getPreviousStartTimeUtc() {
        DateTime now = DateTime.now();
        DateTime todayStart = now.minusDays(1).withTimeAtStartOfDay();
        return todayStart.getMillis();
    }

    public static long getPreviousEndTimeUtc() {
        DateTime now = DateTime.now();
        return now.withTimeAtStartOfDay().getMillis();
    }

    public static long getTodayStartTime() {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        DateTime todayStart = now.withTimeAtStartOfDay();
        return todayStart.getMillis();
    }

    public static long getPreviousStartTime(int minusDays) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        DateTime todayStart = now.minusDays(minusDays).withTimeAtStartOfDay();
        return todayStart.getMillis();
    }

    public static long getTimeNowIndia() {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.getMillis();
    }

    public static long getTodayEndTime() {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.plusDays(1).withTimeAtStartOfDay().getMillis();
    }

    public static Date getTimeStamp(long time) {
        Timestamp t = new Timestamp(time);
        return t;
    }

    public static boolean isDateMatched(long time1, long time2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date1 = sdf.format(new Date(time2));
            String date2 = sdf.format(new Date(time1));

            if (date1.equals(date2))
                return true;

        } catch (Exception e) {
            logPapertrail.error("EXCEPTION", e);
        }
        return false;
    }

    public static void main2sd(String[] args) {
        logMain.info(getOrdersByTimeForConsultant());

//		logMain.info(getMonth(1543602600000l));
//		getPreviousMonthsYYYY(1);
    }

//	public static String getPreviousMonths(int monthsToSubtract) {
//		SimpleDateFormat formatter = new SimpleDateFormat("MMM yy");
//		formatter.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
//		String s = formatter.format(new Date(Timings.minusMonth(monthsToSubtract)));
//		logMain.info(s);
//		return s;
//	}

    public static String getDate_MMDDYYYY(Long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
        sdfDate.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return sdfDate.format(date);
    }

    public static String getDate_DDMMYYYY(Long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        sdfDate.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return sdfDate.format(date);
    }


//	public static String getPreviousMonthsYYYY(int monthsToSubtract) {
//		SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
//		formatter.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
//		String s = formatter.format(new Date(Timings.minusMonth(monthsToSubtract)));
//		logMain.info(s);
//		return s;
//	}

    public static String getMonth_MMYYYY(Long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat sdfDate = new SimpleDateFormat("MMMM-yyyy");
        sdfDate.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return sdfDate.format(date);
    }

    public static long getTimeOfDate(String strDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
        Date date = formatter.parse(strDate);
        return date.getTime();
    }

    public static String getMonth_MMMYY(Long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat formatter = new SimpleDateFormat("MMM yy");
        formatter.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return formatter.format(date);
    }

//	public static long getPreviousMonthStartTime(int minusMonth) {
//		DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
//		DateTime now = DateTime.now(timeZone);
//		now = now.minusDays(1);
//		DateTime todayStart = now.minusMonths(1).withTimeAtStartOfDay();
//		return todayStart.getMillis();
//	}

    public static long getThisMonthStartTime() {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.withDayOfMonth(1).withTimeAtStartOfDay().getMillis();
    }

    public static long getPreviousMonthStartTime(int minusMonth) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        now = now.minusMonths(minusMonth);
        return now.withDayOfMonth(1).withTimeAtStartOfDay().getMillis();
    }

    public static long getPreviousMonthEndTime(int minusMonth) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        now = now.minusMonths(minusMonth);
        int totalDaysInMonth = now.dayOfMonth().getMaximumValue();
        return now.withDayOfMonth(1).withTimeAtStartOfDay().plusDays(totalDaysInMonth).getMillis();
    }

    public static long getPreviousMonthDays(int minusMonth) {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        now = now.minusMonths(minusMonth);
        return now.dayOfMonth().getMaximumValue();
    }

    public static long currentTimeIndia() {
        DateTimeZone timeZone = DateTimeZone.forID(Constants.AsiaKolkata);
        DateTime now = DateTime.now(timeZone);
        return now.getMillis();
    }

    public static DateTime currentTimeByTimeZone(String tz) {
        DateTimeZone timeZone = DateTimeZone.forID(tz);
        DateTime now = DateTime.now(timeZone);
        return now;
    }

    public static String readableDateTimeForLogs(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return sdf.format(new Date(timeInMillis));
    }

    public static String readableDateTimeForLog_ddMMyyyy_hhmm(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        return sdf.format(new Date(timeInMillis));
    }

    public static Long getDateInLong(String date) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone(Constants.AsiaCalcutta));
        Date date_ = sdf.parse(date);
        return date_.getTime();
    }

    public static Long getDateInMillis(String yyyyMMdd) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone(Constants.AsiaCalcutta));
        Date date_ = sdf.parse(yyyyMMdd);
        return date_.getTime();
    }

    public static Timestamp getDateInTimestamp(String date) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date_ = sdf.parse(date);
        Timestamp timeStampDate = new Timestamp(date_.getTime());
        return timeStampDate;
    }

    public static long minusDays(int days) {
        return Timings.getTodayStartTime() - (days * 24 * 60 * 60 * 1000);
    }

    public static long plusDays(int days) {
        return System.currentTimeMillis() + (days * 24 * 60 * 60 * 1000);
    }

    public static long daysInMillis(int days) {
        return (days * 24 * 60 * 60 * 1000);
    }

    public static void main23(String[] args) {
        System.err.println(System.currentTimeMillis() + daysInMillis(1));
    }

    public static long convert_yyyy_MM_dd_HH_mm_ss_intoMillis(String dateInString) throws ParseException {
        String dateTimeFormatPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormatPattern);
        // formatter.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
        formatter.setTimeZone(TimeZone.getTimeZone(Constants.AsiaKolkata));
        Date date = formatter.parse(dateInString);
        return date.getTime();
    }

    public static long getOrdersByTimeForConsultant() {
        ZoneId zoneId = ZoneId.of(Constants.AsiaKolkata);
//		int day = LocalDateTime.now(zoneId).getDayOfMonth();
//		if (day <= 7)
//			return Timings.getPreviousMonthStartTime(1);
//		else
//			return Timings.getThisMonthStartTime();
        return minusDays(7);
    }

    public static void timeTaken(long startTime, String status, String type) {
        long endTime = System.currentTimeMillis();
        logPapertrail.info(type + " Time Taken: " + (System.currentTimeMillis() - startTime) / 1000
                + " second. status: " + status + " startTime: " + startTime + " endTime: " + endTime);

    }

    public static long plusMonthInUTC(int plusMonth) {
        return DateTime.now().plusMonths(plusMonth).getMillis();
    }

    public static String dateTimeFormatter(String startTimeString, String endTimeString, Long startTimeLong,
                                           Long endTimeLong, String timezone) throws ParseException {

        if (startTimeString != null) {
            String dateTimeFormatPattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormatPattern);
            formatter.setTimeZone(TimeZone.getTimeZone(timezone));
            Date StartDate_ = formatter.parse(startTimeString);
            Date endDate_ = formatter.parse(endTimeString);

            formatter = new SimpleDateFormat("dd MMM yyyy");
            formatter.setTimeZone(TimeZone.getTimeZone(timezone));
            String datestr = formatter.format(StartDate_);

            formatter = new SimpleDateFormat("hh:mm");
            formatter.setTimeZone(TimeZone.getTimeZone(timezone));
            startTimeString = formatter.format(StartDate_);

            formatter = new SimpleDateFormat("hh:mm a");
            formatter.setTimeZone(TimeZone.getTimeZone(timezone));
            endTimeString = formatter.format(endDate_);
            formatter.setTimeZone(TimeZone.getTimeZone(timezone));

            return datestr + " (" + startTimeString + "-" + endTimeString + ")";
        } else {

            Date StartDate_ = new Date(startTimeLong);
            Date endDate_ = new Date(endTimeLong);
            DateFormat monthFormat = new SimpleDateFormat("dd MMM yyyy");
            monthFormat.setTimeZone(TimeZone.getTimeZone(timezone));
            String datestr = monthFormat.format(StartDate_);
//		 System.out.println(datestr);

            monthFormat = new SimpleDateFormat("hh:mm");
            monthFormat.setTimeZone(TimeZone.getTimeZone(timezone));
            String startTime = monthFormat.format(StartDate_);
//		 System.out.println(startTime);

            monthFormat = new SimpleDateFormat("hh:mm a");
            monthFormat.setTimeZone(TimeZone.getTimeZone(timezone));
            String endTime = monthFormat.format(endDate_);
//		 System.out.println(endTime);

            return datestr + " (" + startTime + "-" + endTime + ")";
        }
    }

    public static String getHumanReadableDateTimeByZoneId(long timestamp, String timezone_id) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone_id));
        return sdf.format(new Date(timestamp));
    }

    public static Long getDayStartTimeInMillis() {
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        // date.add(Calendar.DAY_OF_MONTH, 1);

        return date.getTimeInMillis();

    }

    public static String getDateOneDayAfter(String date) throws ParseException {
        Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(new Date(date1.getTime() + Constants.oneDayInMillis));
    }
}


