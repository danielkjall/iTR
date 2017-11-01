package com.intiro.itr.util;

import com.intiro.itr.util.log.IntiroLog;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example util-class.
 */
public class ItrUtil {

  private static final Logger LOGGER = Logger.getLogger(ItrUtil.class.getName());
  public static int BEFORE = -1;
  public static int BETWEEN = 0;
  public static int AFTER = 1;

  /**
   * Kontrollerar om en sträng är null eller tom sträng dvs har längd lika med 0.
   *
   * @param in
   * @return
   */
  public static boolean isStrNullOrEmpty(final String in) {
    return in == null || in.isEmpty();
  }

  public static boolean isStrContainingValue(final String in) {
    return !ItrUtil.isStrNullOrEmpty(in);
  }

  public static boolean isObjNull(final Object in) {
    return in == null;
  }

  public static boolean isObjNotNull(final Object in) {
    return !ItrUtil.isObjNull(in);
  }

  private static int countChar(final char c, final String str) {
    int cnt = 0;

    final char[] charArray = str.toCharArray();

    for (final char d : charArray) {
      if (d == c) {
        cnt++;
      }
    }

    return cnt;

  }

  private static int getDay(final String persOrgNr) {
    final String d = persOrgNr.substring(6, 8);
    return Integer.parseInt(d);
  }

  private static int getMonth(final String persOrgNr) {
    final String m = persOrgNr.substring(4, 6);
    return Integer.parseInt(m) - 1;
  }

  private static int getYear(final String persOrgNr) {
    final String y = persOrgNr.substring(0, 4);
    return Integer.parseInt(y);
  }

  public static String getPersOrgNrForView(final String persOrgNr) {
    String persOrgNrRet = persOrgNr;

    if (persOrgNrRet != null && !(countChar('-', persOrgNrRet) > 1 || countChar('+', persOrgNrRet) > 1)) {
      persOrgNrRet = persOrgNrRet.trim();
      if (persOrgNrRet.length() == 12 || persOrgNrRet.length() == 13) {

        if (!persOrgNrRet.startsWith("16")) {
          final Calendar cal = Calendar.getInstance();
          cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 100);

          // 100 years ago
          final Date date100YearsAgo = cal.getTime();

          // Date datePersOrgNr = formater.parse(persOrgNrRet.substring(0, 8));
          final GregorianCalendar gCal = new GregorianCalendar(getYear(persOrgNr), getMonth(persOrgNr),
                  getDay(persOrgNr));
          final Date datePersOrgNr = gCal.getTime();

          if (datePersOrgNr.compareTo(date100YearsAgo) <= 0) {
            persOrgNrRet = persOrgNrRet.substring(2);
            if (!persOrgNrRet.contains("-") && !persOrgNrRet.contains("+")) {
              persOrgNrRet = persOrgNrRet.substring(0, 6) + "+" + persOrgNrRet.substring(6);
            } else {
              persOrgNrRet = persOrgNrRet.replace('-', '+');
            }
            return persOrgNrRet;
          }
        }

        persOrgNrRet = persOrgNrRet.substring(2);
        persOrgNrRet = persOrgNrRet.replace('+', '-');

        if (!persOrgNrRet.contains("-")) {
          persOrgNrRet = persOrgNrRet.substring(0, 6) + "-" + persOrgNrRet.substring(6);
        }

      } else if (persOrgNrRet.length() == 10) {
        persOrgNrRet = persOrgNrRet.substring(0, 6) + "-" + persOrgNrRet.substring(6);
      }
    }
    return persOrgNrRet;
  }

  public static boolean matchRegExp(final String input, final String regexp) {
    final Pattern pL = Pattern.compile(regexp);
    final Matcher mL = pL.matcher(input);
    return mL.matches();
  }

  /**
   * Converts short representation of a month to a string value. The input value must be within 1-12. Return is Jan, Feb, Mar, Apr, Maj,
   * Jun, Jul, Aug, Sep, Nov, Dec
   *
   * @param monthIn
   * @return a String representation of the incoming short.
   * @throws IllegalArgumentException if argument monthIn is: null, less then 1 or greater then 12.
   */
  public static String getMonthFromShort(final Short monthIn) {
    if (monthIn == null) {
      throw new IllegalArgumentException(String.format("monthIn [%s]",
              monthIn == null ? "null" : monthIn.toString()));
    }
    switch (monthIn) {
      case 1:
        return "jan";
      case 2:
        return "feb";
      case 3:
        return "mar";
      case 4:
        return "apr";
      case 5:
        return "maj";
      case 6:
        return "jun";
      case 7:
        return "jul";
      case 8:
        return "aug";
      case 9:
        return "sep";
      case 10:
        return "okt";
      case 11:
        return "nov";
      case 12:
        return "dec";
      default:
        throw new IllegalArgumentException(String.format("monthIn [%s]",
                monthIn == null ? "null" : monthIn.toString()));
    }
  }

  /**
   * Converts short representation of a year to a string value. The input value must be within 1000-9999.
   *
   * @param ar
   * @return
   */
  public static String getArFromShort(final Short ar) {
    if (ar == null || ar < 1000 || ar > 9999) {
      throw new IllegalArgumentException(String.format("ar [%s]", ar == null ? "null" : ar.toString()));
    }
    return ar.toString();
  }

  /**
   * Converts long/Long value to Date. Input value must be within 10000101 - 99991231.
   *
   * @param datLong
   * @return a date representing the incoming long.
   * @throws IllegalArgumentException if argument datLong is: null, less then 10000101 or greater then 99991231.
   */
  public static Date getDateFromLong(final Long datLong) {
    if (datLong == null) {
      throw new IllegalArgumentException(String.format("datLong [%s]",
              datLong == null ? "null" : datLong.toString()));
    }
    return ItrUtil.getDateFromString(datLong.toString());
  }

  /**
   * Converts int/Integer value to Date. Input value must be within 10000101 - 99991231.
   *
   * @param datInt
   * @param logOnError
   * @return a date representing the incoming int.
   * @throws IllegalArgumentException if argument datInt is: null, less then 10000101 or greater then 99991231.
   */
  public static Date getDateFromInt(final Integer datInt, final boolean logOnError) {
    if (datInt == null) {
      throw new IllegalArgumentException(String.format("datInt [%s]",
              datInt == null ? "null" : datInt.toString()));
    }
    return ItrUtil.getDateFromString(datInt.toString(), logOnError);
  }

  /**
   * {@link MsUtil.getDateFromInt(Integer, boolean)}
   *
   * @param datInt
   * @return
   */
  public static Date getDateFromInt(final Integer datInt) {
    return ItrUtil.getDateFromInt(datInt, true);
  }

  public static Date getDateTimeFromString(final String dateTimeStr) {
    if (dateTimeStr == null || dateTimeStr.length() < 14) {
      throw new IllegalArgumentException(String.format("dateTimeStr [%s]", dateTimeStr));
    }

    Date date = null;
    String dateTimeStrTmp = dateTimeStr;
    if (dateTimeStrTmp.length() > 14) {
      dateTimeStrTmp = dateTimeStrTmp.substring(0, 14);
    }

    final SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");

    try {
      formater.setLenient(false);
      date = formater.parse(dateTimeStrTmp);
    } catch (final ParseException e) {
      throw new IllegalArgumentException(String.format("dateTimeStr [%s]", dateTimeStr), e);
    }

    return date;
  }

  /**
   * Converts string value to a Date. The first 8 chars in the input value must be yyyyMMdd.
   *
   * @param datStr
   * @param logOnError
   * @return a date representing the incoming str.
   * @throws IllegalArgumentException if argument datStr is: null, datStr is less then 8 chars long or if {@link SimpleDateFormat} throws
   * {@link ParseException}
   */
  public static Date getDateFromString(final String datStr, final boolean logOnError) {
    if (datStr == null || datStr.length() < 8) {
      throw new IllegalArgumentException(String.format("datStr [%s]", datStr));
    }
    Date date = null;
    String datStrTmp = datStr;
    if (datStrTmp.length() > 8) {
      datStrTmp = datStrTmp.substring(0, 8);
    }

    final SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
    try {
      formater.setLenient(false);
      date = formater.parse(datStrTmp);
    } catch (final ParseException e) {
      if (logOnError) {
        IntiroLog.warning(ItrUtil.class, "Unable to parse the string, dateStr: " + datStr);
      }
      throw new IllegalArgumentException(String.format("datStr [%s]", datStr), e);
    }

    return date;
  }

  /**
   * Converts string value to a Date. The first 8 chars in the input value must be yyyyMMdd.
   *
   * @param datStr
   * @return a date representing the incoming str.
   * @throws IllegalArgumentException if argument datStr is: null, datStr is less then 8 chars long or if {@link SimpleDateFormat} throws
   * {@link ParseException}
   */
  public static Date getDateFromString(final String datStr) {
    return ItrUtil.getDateFromString(datStr, true);
  }

  /**
   * Converts double/Double value to a long. It uses {@link Math#round(double)}
   *
   * @param doubleIn
   * @return a long.
   */
  public static Long getLongFromDouble(final Double doubleIn) {
    Long ret = null;
    if (doubleIn != null) {
      ret = Math.round(doubleIn);
    }
    return ret;
  }

  /**
   * @param date
   * @return date in number format
   * @throws IllegalArgumentException if argument date is null
   */
  public static Integer getIntegerFromDate(final Date date) {
    if (date == null) {
      throw new IllegalArgumentException(String.format("date is [%s]", "null"));
    }
    Integer ret = null;
    final Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int tmp = cal.get(Calendar.YEAR) * 10000;
    tmp = tmp + ((cal.get(Calendar.MONTH) + 1) * 100);
    tmp = tmp + cal.get(Calendar.DAY_OF_MONTH);
    ret = tmp;

    return ret;
  }

  /**
   * Get the number of members declared in a VO. Note that members inherited by extended classes will not count.
   *
   * @param vo The VO to get the number of members for
   * @return The number of members
   */
  public static int getNumberOfMembers(final Object vo) {
    return vo.getClass().getDeclaredFields().length;
  }

  /**
   * Get the number of members declared in a Class. Note that members inherited by extended classes will not count.
   *
   * @param c The Class to get the number of members for
   * @return The number of members
   */
  public static int getNumberOfMembers(final Class<?> c) {
    return c.getDeclaredFields().length;
  }

  /**
   * Converts string value of 'J'/'N' to boolean value
   *
   * @param value Expected to be 'J'/'N'
   * @return Boolean.TRUE if 'J', Boolean.FALSE if 'N'
   * @throws MsException if value other than 'J'/'N'
   */
  public static Boolean getBooleanFromJN(final String value) {
    Boolean ret = null;
    if (value != null) {
      if ("J".equals(value)) {
        ret = Boolean.TRUE;
      } else if ("N".equals(value)) {
        ret = Boolean.FALSE;
      } else {
        throw new IllegalArgumentException("Ogiltig indata. Ska vara 'J' eller 'N' men var [" + value + "]");
      }
    }

    if (ret == null) {
      throw new IllegalArgumentException("Ogiltig indata. Ska vara 'J' eller 'N' men var [" + value + "]");
    }

    return ret;
  }

  public static Boolean getBooleanFromJaNej(final String value) {
    Boolean ret = false;
    if ("ja".equalsIgnoreCase(value)) {
      ret = true;
    } else if ("nej".equalsIgnoreCase(value)) {
      ret = false;
    } else {
      throw new IllegalArgumentException("Ogiltig indata. Ska vara 'JA/ja/Ja/jA' eller 'NEJ/nej/...' men var ["
              + value + "]");
    }

    return ret;
  }

  public static Boolean getBooleanFromDBValue(final String value) {
    Boolean ret = false;
    if (value == null) {
      return null;
    }

    if ("ja".equalsIgnoreCase(value) || "j".equalsIgnoreCase(value) || "y".equalsIgnoreCase(value)
            || "yes".equalsIgnoreCase(value)) {
      ret = true;
    } else if ("nej".equalsIgnoreCase(value) || "n".equalsIgnoreCase(value)) {
      ret = false;
    } else {
      throw new IllegalArgumentException("Ogiltig indata. Ska vara 'JA/ja/Ja/jA/J/j/Yes/YEs/YES/yES/yeS/y/Y' eller 'NEJ/nej/n/N/' men var ["
              + value + "]");
    }

    return ret;
  }

  public static boolean isClosedBasedOnDate(Calendar now, Date closedUntil) {
    boolean isClosedBasedOnDate = (closedUntil != null && now.getTime().getTime() < closedUntil.getTime());
    return isClosedBasedOnDate;
  }

  public static boolean isClosedBasedOnTimeOfDay(Calendar now, Integer opened, Integer closed) {
    Calendar openedCal = GregorianCalendar.getInstance();
    Calendar closedCal = (Calendar) openedCal.clone();

    openedCal.set(Calendar.HOUR_OF_DAY, extractHour(opened));
    openedCal.set(Calendar.MINUTE, extractMinute(opened));

    closedCal.set(Calendar.HOUR_OF_DAY, extractHour(closed));
    closedCal.set(Calendar.MINUTE, extractMinute(closed));

    // same open and close time --> return false.
    if (openedCal.equals(closedCal)) {
      return false;
    }
    if (opened.intValue() == 0 && (closed.intValue() == 24 || closed.intValue() == 2400)) {
      return false;
    }

    if (now.before(closedCal) && closedCal.before(openedCal)) {
      return false;
    }

    if (openedCal.before(closedCal) || now.before(closedCal)) {
      boolean nowAfterOpening = now.after(openedCal);
      boolean nowBeforeClose = now.before(closedCal);
      boolean openingTime = nowAfterOpening && nowBeforeClose;
      return !openingTime;
    }

    // If open time is after close time it means we have to move date for close to next day
    closedCal.add(Calendar.DAY_OF_YEAR, 1);
    boolean nowAfterOpening = now.after(openedCal);
    boolean nowBeforeClose = now.before(closedCal);
    boolean openingTime = nowAfterOpening && nowBeforeClose;
    return !openingTime;

  }

  public static int extractHour(Integer hourAndMinutesAsInteger) {
    int hour = 24;
    String str = String.valueOf(hourAndMinutesAsInteger);
    if (isStrContainingValue(str)) {
      if (str.trim().length() == 4) {
        hour = Integer.parseInt(str.trim().substring(0, 2));
      }
      if (str.trim().length() == 3) {
        hour = Integer.parseInt(str.trim().substring(0, 1));
      }
      if (str.trim().length() == 2 || str.trim().length() == 1) {
        hour = Integer.parseInt(str);
      }
    }
    return hour;
  }

  private static int extractMinute(Integer hourAndMinutesAsInteger) {
    int minute = 0;
    String str = String.valueOf(hourAndMinutesAsInteger);
    if (isStrContainingValue(str)) {
      if (str.trim().length() == 4) {
        minute = Integer.parseInt(str.trim().substring(2));
      }
      if (str.trim().length() == 3) {
        minute = Integer.parseInt(str.trim().substring(1));
      }
    }
    return minute;
  }

  /**
   * Takes two dates and returns a String representation of the period. The result will be on format "mmm - mmm yyyy".
   *
   * @param periodFROM
   * @param periodTOM
   * @return
   */
  public static String getPeriodFromTwoDates(final Date periodFROM, final Date periodTOM) {
    String result = "";

    final SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM", new Locale("sv", "SE"));
    final SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy", new Locale("sv", "SE"));

    if (periodFROM != null) {
      result += monthFormatter.format(periodFROM);
    }
    result += " - ";
    if (periodTOM != null) {
      result += monthFormatter.format(periodTOM);
      result += " " + yearFormatter.format(periodTOM);
    }

    return result;
  }

  /**
   * Takes two dates and returns a String representation of the period. The result will be on format "mmm yyyy - mmm yyyy".
   *
   * @param periodFROM
   * @param periodTOM
   * @return
   */
  public static String getYearAndMonthPeriodFromTwoDates(final Date periodFROM, final Date periodTOM) {
    final StringBuilder result = new StringBuilder();

    final SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM", new Locale("sv", "SE"));
    final SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy", new Locale("sv", "SE"));

    if (periodFROM != null) {
      result.append(monthFormatter.format(periodFROM));
      result.append(" ");
      result.append(yearFormatter.format(periodFROM));
    }

    result.append(" - ");

    if (periodTOM != null) {
      result.append(monthFormatter.format(periodTOM));
      result.append(" ");
      result.append(yearFormatter.format(periodTOM));
    }

    return result.toString();
  }

  /**
   * Takes one date and returns a String representation the quater period for that date The result will be on format "mmm - mmm yyyy".
   *
   * @param date
   * @return "mmm - mmm yyyy" if the date has a value, otherwise "" (empty string).
   */
  public static String getQuaterPeriodFromOneDate(final Date date) {
    String result = "";

    if (date != null) {
      final SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy", new Locale("sv", "SE"));

      final GregorianCalendar tmpCal = new GregorianCalendar();
      tmpCal.setTime(date);

      final int month = tmpCal.get(Calendar.MONTH);

      if (month < Calendar.APRIL) {
        // 1st quater
        result = "jan - mar ";
      } else if (month < Calendar.JULY) {
        // 2nd quater
        result = "apr - jun ";
      } else if (month < Calendar.OCTOBER) {
        // 3rd quater
        result = "jul - sep ";
      } else {
        // 4th quater
        result = "okt - dec ";
      }

      result += yearFormatter.format(date);

    }

    return result;
  }

  /**
   * Takes one date and returns a String representation of the period. The result will be on format "mmm yyyy".
   *
   * @param periodFROM
   * @param periodTOM
   * @return
   */
  public static String getPeriodFromOneDate(final Date date) {
    String result = "";
    final SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy", new Locale("sv", "SE"));
    if (date != null) {
      result = formatter.format(date);
    }
    return result;
  }

  // YYYY-MM-DD HH24:MI:SS
  /**
   * Allowed formats:<br>
   * 2015-10-23 <br>
   * 2015-10-23 16:30:26<br>
   * 20151023 <br>
   * 20151023 16:30:26 <br>
   *
   * @param dateStr
   * @return
   */
  public static Date getDateTime(final String dateStr) {
    Date date = null;

    try {
      String formatString = null;
      if (dateStr.length() == 8) {
        formatString = "yyyyMMdd";
      } else if (dateStr.length() == 10) {
        formatString = "yyyy-MM-dd";
      } else if (dateStr.length() == 17) {
        formatString = "yyyyMMdd HH:mm:ss";
      } else if (dateStr.length() == 19) {
        formatString = "yyyy-MM-dd HH:mm:ss";
      }
      if (formatString == null) {
        throw new Exception("Could not find a formatstring for dateStr: " + dateStr);
      }
      final SimpleDateFormat formater = new SimpleDateFormat(formatString);
      formater.setLenient(false);
      date = formater.parse(dateStr);
    } catch (final Exception e) {
      IntiroLog.warning(ItrUtil.class, "Unable to parse date, e: " + e.getMessage());
    }

    return date;
  }

  public static String getAuditDateTime() {
    final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    Date date = new Date();
    return formater.format(date);
  }

  /**
   * Skapar ett datum ({@link Date}) från tre integers som representerar år, måndad och dag.
   *
   * @param ar året för datumet
   * @param manad månaden för datumet
   * @param dag dagen för datumet
   * @return Ett datum
   */
  public static Date getDate(final int ar, final int manad, final int dag) {
    return new GregorianCalendar(ar, manad - 1, dag).getTime();
  }

  /**
   * Skapa ett datum ({@link Date}) utifrån en sträng som är enligt formatet "yyyy-MM-dd".
   *
   * @param dateStr datrumsträng
   * @return Ett datum
   * @throws ParseException
   */
  public static Date getDate(final String dateStr) throws ParseException {
    final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    return formater.parse(dateStr);
  }

  /**
   * Skapa ett datum ({@link Date}) utifrån en sträng. DateFormat (exempelvis yyyy-MM-dd eller yyyyMMdd) skickas in.
   *
   * @param dateStr datrumsträng
   * @return Ett datum
   * @throws ParseException
   */
  public static Date getDate(final String dateStr, String dateFormat) throws ParseException {
    final SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
    formater.setLenient(false);
    return formater.parse(dateStr);
  }

  /**
   * Skapar ett {@link Date} objekt utifrån parametern {@code date} och modifierar det objektet med hjälp av parametrarna
   * {@code calendarFieldConstant} och {@code addValue}.
   *
   * @param date
   * @param calendarFieldConstant
   * @param addValue
   * @return
   */
  public static Date getDateMinus(final Date date, final int calendarFieldConstant, final int addValue) {
    if (date == null) {
      throw new IllegalArgumentException(String.format("date is [%s]", "null"));
    }
    final Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(calendarFieldConstant, addValue);
    return cal.getTime();
  }

  /**
   * Skapar datum som har inträffat innevarande år + kvå kalenderår tillbaka. Exempelvis om dagensdatum är 20130215 så returneras datum
   * 20110101. Parametern {@code datumNu} bör alltid vara lika med {@code new Date()} och är med främst för enhetstesternas skull.
   *
   * @param datumNu
   * @return
   */
  public static Date getDateCurrentYearMinusTwoCalendarYears(final Date datumNu) {
    if (datumNu == null) {
      throw new IllegalArgumentException(String.format("datumNu is [%s]", "null"));
    }
    final Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTime(datumNu);
    cal.add(Calendar.YEAR, -2);
    cal.set(Calendar.MONTH, Calendar.JANUARY);
    cal.set(Calendar.DAY_OF_MONTH, 1);

    return cal.getTime();
  }

  /**
   * Returnerar en sträng med ett datum formaterat på formatet ÅÅÅÅ-MM-DD
   *
   * @param datumet att formatera
   * @return en sträng med ett formaterat datum.
   */
  public static String formatDate(final Date date) {
    if (date == null) {
      return "";
    }
    final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    return formater.format(date);
  }

  /**
   * Returnerar ett {@link Date}-objekt utifrån en sträng. Formatet på strängen måste vara ÅÅÅÅ-MM-DD.
   *
   * @param datumsträng
   * @return ett datum, eller null om strängen inte går att tolka.
   */
  public static Date getDateFromFormatedString(final String dateStr) {
    Date date = null;

    try {
      if (dateStr.length() == 10) {
        final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        formater.setLenient(false);
        date = formater.parse(dateStr);
      }
    } catch (final Exception e) {
      IntiroLog.warning(ItrUtil.class, "Unable to parse date, e: " + e.getMessage());
    }

    return date;
  }

  // Kollar om datum är valid
  public static boolean isTuxedoDateValid(final int tuxedoDate) {
    try {
      final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
      df.setLenient(false);
      df.parse(String.valueOf(tuxedoDate));
      return true;
    } catch (final ParseException e) {
      return false;
    }
  }

  // Kollar om datum är valid
  public static boolean isTuxedoDateValid(final String tuxedoDate) {
    try {
      final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
      df.setLenient(false);
      df.parse(tuxedoDate);
      return true;
    } catch (final ParseException e) {
      return false;
    }
  }

  /**
   * Returnerar ett {@link Date}-objekt utifrån en sträng. Formatet på strängen kan vara:<br/>
   * 1) <strong>yyyy-mm-dd</strong><br/>
   * 2) <strong>yyyy-mm-ddThh:mm:ss</strong><br/>
   * 3) <strong>yyyymmdd</strong><br/>
   * <br/>
   * <strong>OBS!</strong> Datumsträng <strong>yyyy-mm-dd hh:mm:ss</strong> blir en {@link Date}-objekt utan tidsstämpel!<br/>
   * <br/>
   * <strong>OBS!</strong> Datumsträng med ogiltiga värden för år, månad och dag kastar {@link IllegalArgumentException}.
   *
   * @param datumString
   * @return ett datum, eller null om strängen inte går att tolka.
   */
  public static Date getDateFromMultipleFormatString(final String datumString) {
    if (datumString == null) {
      throw new IllegalArgumentException("Ogiltig indata: datumString får inte vara null");
    } else if (datumString.contains("-")) {
      if (datumString.contains("T")) {
        return ItrUtil.getDateFromTimestampString(datumString);
      } else {
        try {
          return ItrUtil.getDate(datumString);
        } catch (final ParseException e) {
          throw new IllegalArgumentException(String.format("datumString [%s]", datumString), e);
        }
      }
    } else {
      return ItrUtil.getDateFromString(datumString);
    }
  }

  /**
   * Returnerar ett {@link Date}-objekt utifrån en sträng. Formatet på strängen kan vara:<br/>
   * 1) <strong>yyyy-mm-dd</strong><br/>
   * 2) <strong>yyyy-mm-ddThh:mm:ss</strong><br/>
   * <br/>
   * <strong>OBS!</strong> Datumsträng <strong>yyyy-mm-dd hh:mm:ss</strong> blir en {@link Date}-objekt utan tidsstämpel!<br/>
   * <br/>
   * <strong>OBS!</strong> Datumsträng med ogiltiga värden för år, månad och dag kastar {@link IllegalArgumentException}.
   *
   * @param datumString
   * @return
   */
  public static Date getDateFromTimestampString(final String datumString) {
    Date date = null;

    SimpleDateFormat formater = null;

    if (datumString != null && datumString.contains("T")) {
      formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    } else {
      formater = new SimpleDateFormat("yyyy-MM-dd");
    }

    try {
      formater.setLenient(false);
      date = formater.parse(datumString);
    } catch (final ParseException e) {
      IntiroLog.warning(ItrUtil.class, "Unable to parse the string, " + datumString);
      throw new IllegalArgumentException(String.format("Unable to parse the string [%s]", datumString), e);
    }

    return date;
  }

  /**
   * Checks if notNullableObj is null and if so throws {@link IllegalArgumentException} using formatStr and args.
   *
   * @param notNullableObj
   * @param formatStr
   * @param args
   */
  public static void require(final Object notNullableObj, final String formatStr, final Object... args) {
    if (notNullableObj == null) {
      throw new IllegalArgumentException(String.format(formatStr, args));
    }
  }

  /**
   * Skapar en int-representation (yyyyMMdd) från ett date-objekt
   *
   * @param date
   * @return
   * @throws ParseException
   */
  public static int getDateAsInt(Date date) throws NumberFormatException {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    String dateAsString = df.format(date);

    return Integer.parseInt(dateAsString);
  }

  public static String formatAmount(Long amount) {
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    formatter.setMaximumFractionDigits(0);

    String moneyString = formatter.format(amount).replace(' ', '\u00A0');
    return moneyString;
  }

  public static String formatDateStr(String dateStr) {
    if (dateStr.length() != 8) {
      return dateStr;
    }
    dateStr = dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8);
    return dateStr;
  }

  public static String ensureNoLineBreaks(String text) {
    if (isStrNullOrEmpty(text)) {
      return text;
    }
    return text.replace("\r\n", "").replace("\n", "");
  }

  public static String switchHtmlLineBreaks(String text) {
    if (isStrNullOrEmpty(text)) {
      return text;
    }
    return text.replace("<br />", "\r\n").replace("<br/>", "\r\n");
  }

  private static boolean isBeforeOrEquals(Date a, Date b) {
    return a.equals(b) || a.before(b);
  }

  private static boolean isAfterOrEquals(Date a, Date b) {
    return a.equals(b) || a.after(b);
  }

  /**
   * Hjälpmetod för att kontrollera om idag är mellan ett visst datumintervall, dvs om (datumFrom är <= idag) och (idag är <= datumTom).
   * Datumen som skickas in skall vara formaterade enligt det dateFormat som skickas in (yyyy-MM-dd eller yyyyMMdd exempelvis) Är det fel
   * format på något av datumen så returneras false.
   *
   * @param datumFrom
   * @param datumTom
   * @return -1 if today is before, 0 today is between dates, and 1 if today is after
   * @throws MsException
   */
  public static int todayIsBetweenDates(Date datumFrom, Date datumTom) throws Exception {

    Date now = new Date();

    if (datumFrom == null && datumTom == null) {
      return BETWEEN;
    }
    if (datumFrom != null && isAfterOrEquals(now, datumFrom) && datumTom == null) {
      return BETWEEN;
    }
    if (datumTom != null && datumFrom == null && isBeforeOrEquals(now, datumTom)) {
      return BETWEEN;
    }
    if (datumFrom != null && datumTom != null && isAfterOrEquals(now, datumFrom)
            && isBeforeOrEquals(now, datumTom)) {
      return BETWEEN;
    }

    if (datumFrom != null && isBeforeOrEquals(now, datumFrom)) {
      return BEFORE;
    }
    if (datumTom != null && isAfterOrEquals(now, datumTom)) {
      return AFTER;
    }

    throw new Exception("Missed state, should never reach this exception. Add code to handle state");
  }

  public static List<String> splitStringToChunks(String content, int chunkSize) {
    int arrayLength = (int) Math.ceil(((content.length() / (double) chunkSize)));
    String[] result = new String[arrayLength];

    int j = 0;
    int lastIndex = result.length - 1;
    for (int i = 0; i < lastIndex; i++) {
      result[i] = content.substring(j, j + chunkSize);
      j += chunkSize;
    } // Add the last bit
    result[lastIndex] = content.substring(j);

    return Arrays.asList(result);
  }

  public static int getAge(String pnr, Date nowDate) {
    Date birthDate = getDateFromString(pnr);

    Calendar birth = Calendar.getInstance();
    birth.setTime(birthDate);

    Calendar now = Calendar.getInstance();
    now.setTime(nowDate);

    int years = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

    int nowMonth = now.get(Calendar.MONTH);
    int birthMonth = birth.get(Calendar.MONTH);

    if (nowMonth < birthMonth) {
      // birthday is in a month ahead of current month
      years--;
    } else if (nowMonth == birthMonth && now.get(Calendar.DATE) < birth.get(Calendar.DATE)) {
      years--;
    }

    return years;

  }

  public static int getAge(String pnr) {
    return getAge(pnr, new Date());
  }

  public static String getOCRfromPersOrgNr(String persOrgnr) {
    String pnr = persOrgnr;

    int sum = 0;
    int multiplier = 1;

    for (int i = 0; i < pnr.length(); i++) {
      char c = pnr.charAt(i);
      int num = Character.getNumericValue(c) * multiplier;
      sum += num > 9 ? (1 + num - 10) : num;
      multiplier = multiplier == 2 ? 1 : 2;

    }

    int digit = 10 - sum % 10;

    if (digit == 10) {
      digit = 0;
    }

    return pnr + digit;
  }
}
