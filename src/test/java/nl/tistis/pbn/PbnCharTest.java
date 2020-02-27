package nl.tistis.pbn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PbnCharTest {

    private static final int UNICODE_0_NULL_CHARACTER = 0;
    private static final int UNICODE_32_SPACE = 32;
    private static final int UNICODE_33_EXCLAMATION_MARK = 33;
    private static final int UNICODE_45_HYPHEN = 45;
    private static final int UNICODE_48_DIGIT_0 = 48;
    private static final int UNICODE_57_DIGIT_9 = 57;
    private static final int UNICODE_63_QUESTION_MARK = 63;
    private static final int UNICODE_65_CAPITAL_A = 65;
    private static final int UNICODE_90_CAPITAL_Z = 90;
    private static final int UNICODE_95_UNDERSCORE = 95;
    private static final int UNICODE_97_SMALL_A = 97;
    private static final int UNICODE_122_SMALL_Z = 122;
    private static final int UNICODE_126_TILDE = 126;
    private static final int UNICODE_160_NON_BREAKING_SPACE = 160;
    private static final int UNICODE_255_SMALL_Y_WITH_DIAERESIS = 255;

    @Test
    void isPbnCharReturnsBooleanCheckingIfCharacterIsAValidPortableBridgeNotationStandardCharacter() {

        for (int i = UNICODE_0_NULL_CHARACTER; i <= UNICODE_255_SMALL_Y_WITH_DIAERESIS; i++) {

            if (i <= UNICODE_126_TILDE || i >= UNICODE_160_NON_BREAKING_SPACE) {
                assertTrue(PbnChar.IsPbnChar((char) i));
            } else {
                assertFalse(PbnChar.IsPbnChar((char) i));
            }
        }
    }

    @Test
    void isDigitReturnsBooleanCheckingIfCharacterIsADigit() {

        for (int i = UNICODE_0_NULL_CHARACTER; i <= UNICODE_255_SMALL_Y_WITH_DIAERESIS; i++) {

            if (isADigit(i)) {
                assertTrue(PbnChar.IsDigit((char) i));
            } else {
                assertFalse(PbnChar.IsDigit((char) i));
            }
        }
    }

    @Test
    void isDateCharReturnsBooleanCheckingIfCharacterIsADigitOrQuestionMark() {

        for (int i = UNICODE_0_NULL_CHARACTER; i <= UNICODE_255_SMALL_Y_WITH_DIAERESIS; i++) {

            if (isADigit(i) || i == UNICODE_63_QUESTION_MARK) {
                assertTrue(PbnChar.IsDateChar((char) i));
            } else {
                assertFalse(PbnChar.IsDateChar((char) i));
            }
        }
    }

    @Test
    void isLetterReturnsBooleanCheckingIfCharacterIsALetter() {

        for (int i = UNICODE_0_NULL_CHARACTER; i <= UNICODE_255_SMALL_Y_WITH_DIAERESIS; i++) {

            if (isACapitalLetter(i) || isALowercaseLetter(i)) {
                assertTrue(PbnChar.IsLetter((char) i));
            } else {
                assertFalse(PbnChar.IsLetter((char) i));
            }
        }
    }

    @Test
    void isSpaceReturnsBooleanCheckingIfCharacterLessThanOrEqualToUnicode32Space() {

        for (int i = UNICODE_0_NULL_CHARACTER; i <= UNICODE_255_SMALL_Y_WITH_DIAERESIS; i++) {

            if (i <= UNICODE_32_SPACE) {
                assertTrue(PbnChar.IsSpace((char) i));
            } else {
                assertFalse(PbnChar.IsSpace((char) i));
            }
        }
    }

    @Test
    void inMoveReturnsABooleanCheckingIfCharacterIsEitherLetterDigitOrHyphen() {

        for (int i = UNICODE_0_NULL_CHARACTER; i <= UNICODE_255_SMALL_Y_WITH_DIAERESIS; i++) {

            if (isACapitalLetter(i) || isALowercaseLetter(i) || isADigit(i) || isAHyphen(i)) {
                assertTrue(PbnChar.InMove((char) i));
            } else {
                assertFalse(PbnChar.InMove((char) i));
            }
        }
    }

    @Test
    void inSectionReturnsABooleanCheckingSomeThingsThatAreNotClearFromTheMethodName() {

        for (int i = UNICODE_0_NULL_CHARACTER; i <= UNICODE_255_SMALL_Y_WITH_DIAERESIS; i++) {

            if ((i >= UNICODE_33_EXCLAMATION_MARK && i <= UNICODE_126_TILDE) && !isOneOfTheseChars(i)) {
                assertTrue(PbnChar.InSection((char) i));
            } else {
                assertFalse(PbnChar.InSection((char) i));
            }
        }
    }

    @Test
    void inTagNameReturnsABooleanCheckingIfCharacterIsEitherLetterDigitOrUnderscore() {

        for (int i = UNICODE_0_NULL_CHARACTER; i <= UNICODE_255_SMALL_Y_WITH_DIAERESIS; i++) {

            if (isACapitalLetter(i) || isALowercaseLetter(i) || isADigit(i) || isAnUnderscore(i)) {
                assertTrue(PbnChar.InTagName((char) i));
            } else {
                assertFalse(PbnChar.InTagName((char) i));
            }
        }
    }

    @Test
    void skipSpaceGivenAStringWithNoLeadingSpacesReturnsTheSameString() {
        assertEquals("abc", PbnChar.SkipSpace("abc"));
        assertEquals("a b c   ", PbnChar.SkipSpace("a b c   "));
    }

    @Test
    void skipSpaceRemovesLeadingSpaces() {
        assertEquals("a b c ", PbnChar.SkipSpace(" a b c "));
        assertEquals("a b c ", PbnChar.SkipSpace("      a b c "));
    }

    @Test
    void skipSpaceGivenAStringOfOnlySpacesReturnsAnEmptyString() {
        assertEquals("", PbnChar.SkipSpace("     "));
    }

    @Test
    void filterBackslashGivenANullStringReturnsNull() {
        assertNull(PbnChar.FilterBackslash(null));
    }

    @Test
    void filterBackslashGivenAStringWithNoBackslashesReturnsSameString() {
        assertEquals("abc123\"[]!@#$%^&*()", PbnChar.FilterBackslash("abc123\"[]!@#$%^&*()"));
    }

    @Test
    void filterBackslashGivenAStringWithNonConsecutiveBackslashesReturnsTheSameString() {
        assertEquals("\\a\\b\\c\\", PbnChar.FilterBackslash("\\a\\b\\c\\"));
    }

    @Test
    void filterBackslashRemovesAnyBackslashThatIsImmediatelyBeforeAnotherBackslashOrADoubleQuote() {
        assertEquals("\"_\\_\"_\\", PbnChar.FilterBackslash("\\\"_\\\\_\"_\\"));
    }


    private boolean isAnUnderscore(int i) {
        return i == UNICODE_95_UNDERSCORE;
    }

    private boolean isADigit(int i) {
        return i >= UNICODE_48_DIGIT_0 && i <= UNICODE_57_DIGIT_9;
    }

    private boolean isALowercaseLetter(int i) {
        return i >= UNICODE_97_SMALL_A && i <= UNICODE_122_SMALL_Z;
    }

    private boolean isACapitalLetter(int i) {
        return i >= UNICODE_65_CAPITAL_A && i <= UNICODE_90_CAPITAL_Z;
    }

    private boolean isAHyphen(int i) {
        return i == UNICODE_45_HYPHEN;
    }

    private boolean isOneOfTheseChars(int i) {
        char[] myChars = {'[', ']', '{', '}', ';', '%', '"'};
        for (char character : myChars) {
            if (character == (char) i) {
                return true;
            }
        }
        return false;
    }
}
