package nl.tistis.pbn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PbnCharTest {

    private static final int UNICODE_0_NULL_CHARACTER = 0;
    private static final int UNICODE_9_HORIZONTAL_TAB = 9;
    private static final int UNICODE_32_SPACE = 32;
    private static final int UNICODE_33_EXCLAMATION_MARK = 33;
    private static final int UNICODE_34_DOUBLE_QUOTE = 34;
    private static final int UNICODE_37_PERCENT_SIGN = 37;
    private static final int UNICODE_45_HYPHEN = 45;
    private static final int UNICODE_48_DIGIT_0 = 48;
    private static final int UNICODE_57_DIGIT_9 = 57;
    private static final int UNICODE_59_SEMICOLON = 59;
    private static final int UNICODE_63_QUESTION_MARK = 63;
    private static final int UNICODE_65_CAPITAL_A = 65;
    private static final int UNICODE_90_CAPITAL_Z = 90;
    private static final int UNICODE_91_OPENING_BRACKET = 91;
    private static final int UNICODE_93_CLOSING_BRACKET = 93;
    private static final int UNICODE_95_UNDERSCORE = 95;
    private static final int UNICODE_97_SMALL_A = 97;
    private static final int UNICODE_122_SMALL_Z = 122;
    private static final int UNICODE_123_OPENING_BRACE = 123;
    private static final int UNICODE_125_CLOSING_BRACE = 125;
    private static final int UNICODE_126_TILDE = 126;
    private static final int UNICODE_127_DELETE = 127;
    private static final int UNICODE_160_NON_BREAKING_SPACE = 160;
    private static final int UNICODE_255_SMALL_Y_WITH_DIAERESIS = 255;


    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_0_NULL_CHARACTER,
            UNICODE_126_TILDE,
            UNICODE_160_NON_BREAKING_SPACE,
            UNICODE_255_SMALL_Y_WITH_DIAERESIS})
    void isPbnChar(int i) {
        assertTrue(PbnChar.IsPbnChar((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -1, 127, 130, 159, 256, 1000})
    void isPbnChar_invalidPbnChar_false(int i) {
        assertFalse(PbnChar.IsPbnChar((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {UNICODE_48_DIGIT_0, UNICODE_57_DIGIT_9})
    void isDigit(int i) {
        assertTrue(PbnChar.IsDigit((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_0_NULL_CHARACTER,
            UNICODE_65_CAPITAL_A,
            UNICODE_160_NON_BREAKING_SPACE})
    void isDigit_notADigit_false(int i) {
        assertFalse(PbnChar.IsDigit((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_48_DIGIT_0,
            UNICODE_57_DIGIT_9,
            UNICODE_63_QUESTION_MARK})
    void isDateChar(int i) {
        assertTrue(PbnChar.IsDateChar((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_0_NULL_CHARACTER,
            UNICODE_65_CAPITAL_A,
            UNICODE_33_EXCLAMATION_MARK})
    void isDateChar_notADateChar_false(int i) {
        assertFalse(PbnChar.IsDateChar((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_65_CAPITAL_A,
            UNICODE_90_CAPITAL_Z,
            UNICODE_97_SMALL_A,
            UNICODE_122_SMALL_Z})
    void isLetter(int i) {
        assertTrue(PbnChar.IsLetter((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_33_EXCLAMATION_MARK,
            UNICODE_63_QUESTION_MARK,
            UNICODE_126_TILDE})
    void isLetter_notALetter_false(int i) {
        assertFalse(PbnChar.IsLetter((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_0_NULL_CHARACTER,
            UNICODE_9_HORIZONTAL_TAB,
            UNICODE_32_SPACE})
    void isSpace(int i) {
        assertTrue(PbnChar.IsSpace((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_33_EXCLAMATION_MARK,
            UNICODE_65_CAPITAL_A,
            UNICODE_160_NON_BREAKING_SPACE})
    void isSpace_noASpaceCharacter_false(int i) {
        assertFalse(PbnChar.IsSpace((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_45_HYPHEN,
            UNICODE_48_DIGIT_0,
            UNICODE_57_DIGIT_9,
            UNICODE_65_CAPITAL_A,
            UNICODE_90_CAPITAL_Z,
            UNICODE_97_SMALL_A,
            UNICODE_122_SMALL_Z})
    void inMove(int i) {
        assertTrue(PbnChar.InMove((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_0_NULL_CHARACTER,
            UNICODE_32_SPACE,
            UNICODE_63_QUESTION_MARK,
            UNICODE_126_TILDE})
    void inMove_notALetterDigitOrHyphen_false(int i) {
        assertFalse(PbnChar.InMove((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_33_EXCLAMATION_MARK,
            UNICODE_126_TILDE})
    void inSection(int i) {
        assertTrue(PbnChar.InSection((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_0_NULL_CHARACTER,
            UNICODE_32_SPACE,
            UNICODE_34_DOUBLE_QUOTE,
            UNICODE_37_PERCENT_SIGN,
            UNICODE_59_SEMICOLON,
            UNICODE_91_OPENING_BRACKET,
            UNICODE_93_CLOSING_BRACKET,
            UNICODE_123_OPENING_BRACE,
            UNICODE_125_CLOSING_BRACE,
            UNICODE_127_DELETE,
            UNICODE_255_SMALL_Y_WITH_DIAERESIS
    })
    void inSection_outsideRangeOrInvalidChar_false(int i) {
        assertFalse(PbnChar.InSection((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            UNICODE_48_DIGIT_0,
            UNICODE_57_DIGIT_9,
            UNICODE_65_CAPITAL_A,
            UNICODE_90_CAPITAL_Z,
            UNICODE_95_UNDERSCORE,
            UNICODE_97_SMALL_A,
            UNICODE_122_SMALL_Z,
    })
    void inTagName(int i) {
        assertTrue(PbnChar.InTagName((char) i));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            'ƒ', '@', '#' ,'$', 'Ỳ', 'ö', 'ü'
    })
    void inTagName_notADigitLetterOrUnderscore_false(int i) {
        assertFalse(PbnChar.InTagName((char) i));
    }

    @Test
    void skipSpaceRemovesLeadingSpaces() {
        assertEquals("a b c ", PbnChar.SkipSpace("a b c "));
        assertEquals("a b c ", PbnChar.SkipSpace("      a b c "));
        assertEquals("", PbnChar.SkipSpace("     "));
    }

    @Test
    void filterBackslash_givenNull_returnsNull() {
        assertNull(PbnChar.FilterBackslash(null));
    }

    @Test
    void filterBackslash_replacesDoubleBackslash_withSingleBackslash() {
        assertEquals("\\", PbnChar.FilterBackslash("\\\\"));
    }

    @Test
    void filterBackslash_replacesBackslashBeforeDoublequote_withDoublequote() {
        assertEquals("\"", PbnChar.FilterBackslash("\\\""));
    }
}
