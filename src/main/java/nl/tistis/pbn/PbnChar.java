package nl.tistis.pbn;

/*
 * File   :     PbnChar.java
 * Author :     Tis Veugen
 * Date   :     1999-10-10
 * PBN    :     2.0
 *
 * History
 * -------
 * 1999-06-06 Added InSection()
 * 1999-10-10 Added FilterBackslash()
 */

public class PbnChar {
  public static final char HORTAB = '\u0009';
  public static final char LINEFEED = '\n';
  public static final char EOL = '\n';
  public static final char VERTAB = '\u000B';
  public static final char RETURN = '\r'; // '\r'
  public static final char SPACE = '\u0020';
  public static final char ESCAPE = '\u0025'; // '%'
  public static final String StringEol = "\r\n";

  public static boolean IsPbnChar(char c) {
    return c <= 126 || 160 <= c && c <= 255;
  }

  public static boolean IsDigit(char c) {
    return (('0' <= c) && (c <= '9'));
  }

  public static boolean IsDateChar(char c) {
    return ((c == '?') || IsDigit(c));
  }

  public static boolean IsLetter(char c) {
    return ((('a' <= c) && (c <= 'z')) || (('A' <= c) && (c <= 'Z')));
  }

  public static boolean IsSpace(char c) {
    return (c <= ' ');
  }

  public static boolean InMove(char c) {
    return (IsLetter(c) || IsDigit(c) || (c == '-'));
  }

  public static boolean InSection(char c) {
    char[] prohibitedChars = {'[', ']', '{', '}', ';', '%', '"'};

    return (33 <= c) && (c <= 126) &&
      !(new String(prohibitedChars).contains(Character.toString(c)));
  }

  public static boolean InTagName(char c) {

    return (IsLetter(c) || IsDigit(c) || (c == '_'));
  }

  public static String SkipSpace(String s) {

    return s.replaceAll("^\\s+", "");
  }

  public static String FilterBackslash(String s) {

    return s == null ?
            null :
            s.replace("\\\\", "\\").replace("\\\"", "\"");
  }
}
