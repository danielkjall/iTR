package com.intiro.itr.util;

import com.intiro.itr.util.log.IntiroLog;
import java.text.*;
import java.util.*;

public class NumberFormatter {

  static private final double M = 1000000;
  static private final double k = 1000;

  //~ Methods ..........................................................................................................
  /**
   * Formats a number string to the format '###,##0.#' Returns 'N/A' if something went wrong. Ex. 12456.78 -> 12 456 Ex. 123456789 -> 123
   * 456 789
   * <P>
   * @param dblVal
   * @param noOfDecimals The Number of decimals to be used.
   * @param delimiter Shall thousands delimter be used.
   * @return A String on the format '###,##0.'
   */
  public static String format(double dblVal, int noOfDecimals, boolean delimiter) {
    return format(String.valueOf(dblVal), "null", noOfDecimals, delimiter);
  }

  /**
   * Formats a number string to the format '###,##0.#' Returns 'N/A' if something went wrong. Ex. 12456.78 -> 12 456 Ex. 123456789 -> 123
   * 456 789
   * <P>
   * @param strVal The number string on format (########) Ex. 1233456, 2345.45
   * @param noOfDecimals The Number of decimals to be used.
   * @param delimiter Shall thousands delimter be used.
   * @return A String on the format '###,##0.'
   */
  public static String format(String strVal, int noOfDecimals, boolean delimiter) {
    return format(strVal, "null", noOfDecimals, delimiter);
  }

  /**
   * Formats a number string to the format '###,##0. <suffix>' Suffix is the trailing letters after the number. ( k, M ) The return value
   * are divided with 1 000 respectively 1 000 000. Returns 'N/A' if something went wrong. Ex. 12 456 k Ex. 123 456 789 M
   * <P>
   * @param strVal The number string on format (########) Ex. 1233456, 2345.45
   * @param suffix The suffix, should be either k or M.
   * @param noOfDecimals The Number of decimals to be used, in the range 0-3.
   * @param delimiter Shall thousands delimter be used.
   * @return A String on the format '###,##0. <suffix>'
   */
  public static String format(String strVal, String suffix, int noOfDecimals, boolean delimiter) {
    double factor = 1;
    String retval;
    String decimalsPattern = ".";
    String pattern;

    try {
      NumberFormat formNum = NumberFormat.getInstance(new Locale("SV", "SE", "Traditional_WIN"));
      DecimalFormat formDec = (DecimalFormat) formNum;

      if (delimiter) {
        pattern = "###,##0";
      } else {
        pattern = "0";
      }
      //                pattern = "#";
      if (noOfDecimals != 0) {
        for (int i = 0; i < noOfDecimals; i++) {
          decimalsPattern += "0";
        }
      } else {
        decimalsPattern = "";
      }
      if (suffix.equals("null")) {
        formDec.applyPattern(pattern + decimalsPattern);
      } else {
        formDec.applyPattern(pattern + decimalsPattern + " " + suffix);
      }

      double val = Double.parseDouble(strVal);

      if (suffix.equals("k")) {
        factor = k;
      }
      if (suffix.equals("M")) {
        factor = M;
      }

      val = val / factor;
      retval = formDec.format(val);
    } catch (NumberFormatException e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(NumberFormatter.class,
                ".format(String strVal, String suffix, int noOfDecimals, boolean delimiter): Error=" + e
                + " strVal=" + strVal + " suffix=" + suffix + " noOfDecimals=" + noOfDecimals + " delimiter="
                + delimiter);
      }

      retval = "N/A";
    } catch (Exception e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(NumberFormatter.class,
                ".format(String strVal, String suffix, int noOfDecimals, boolean delimiter): Error=" + e
                + " strVal=" + strVal + " suffix=" + suffix + " noOfDecimals=" + noOfDecimals + " delimiter="
                + delimiter);
      }

      retval = "N/A";
    }

    return retval;
  }

  /**
   * Formats a number Strings decimals if it ends with 0' Ex. 123.300 -> 123.3
   * <P>
   * @param strVal The number string on format (###.###...) Ex. 123.300
   * @return A String on the format '###,##0.'
   */
  public static String formatDecimals(String strVal) {
    String retval;

    try {
      NumberFormat formNum = NumberFormat.getInstance(new Locale("SV", "SE", "Traditional_WIN"));
      DecimalFormat formDec = (DecimalFormat) formNum;
      formDec.applyPattern("#.#");

      double val = Double.parseDouble(strVal);
      retval = formDec.format(val);
    } catch (NumberFormatException e) {
      //            if(IntiroLog.d()) IntiroLog.detail(getClass(), getClass().getName() + "toFormattedNumber(String strVal, String suffix): Error=" + e + " strVal=" + strVal + " factor=" + factor);
      retval = "N/A";
    } catch (Exception e) {
      //            if(IntiroLog.d()) IntiroLog.detail(getClass(), getClass().getName() + "toFormattedNumber(String strVal, String suffix): Error=" + e + " strVal=" + strVal + " factor=" + factor);
      retval = "N/A";
    }

    return retval;
  }

  public static void main(String[] args) {
    System.out.println("Started");
    System.out.println("Decimal test med delimiter and decimals");
    System.out.println("123456.789 -> " + format(123456.789, 0, true));
    System.out.println("123456.789 -> " + format(123456.789, 1, true));
    System.out.println("123456.789 -> " + format(123456.789, 2, true));
    System.out.println("123456.789 -> " + format(123456.789, 3, true));
    System.out.println("123456.000 -> " + format(123456.7899123342, 6, true));
    System.out.println("123456.000 -> " + format(123456.7, 3, true));
    System.out.println("sdflkjsdlkfjsdlkj");
    System.out.println("0000.7 -> " + format("0000.7", 3, true));
    System.out.println(".7324523 -> " + format(".7324523", 3, true));
    System.out.println("Decimal test utan delimiter and decimals");
    System.out.println("123456.789 -> " + format("123456.789", 0, false));
    System.out.println("123456.789 -> " + format("123456.789", 1, false));
    System.out.println("123456.789 -> " + format("123456.789", 2, false));
    System.out.println("123456.789 -> " + format("123456.789", 3, false));
    System.out.println("123456.000 -> " + format("123456.000", 4, false));
    System.out.println("\ntest med delimter and suffix");
    System.out.println("123456.789 -> " + format("123456.789", "k", 0, true));
    System.out.println("123456.789 -> " + format("123456.789", "k", 1, false));
    System.out.println("123456.000 -> " + format("123456.000", "k", 2, true));
    System.out.println("\ntest utan delimiter");
    System.out.println("123456.789 -> " + formatDecimals("123456.789"));
    System.out.println("123456.00 -> " + formatDecimals("123456.00"));
    System.out.println("Ended");
  }
}
