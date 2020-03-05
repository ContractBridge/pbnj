package nl.tistis.pbn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("PbnVerifier")
class PbnVerifierTest {
    private static final String testFileRootPath = "src/test/resources/";

    @DisplayName("When I instantiate the class it should return an object")
    @Test
    void main() {
        PbnVerifier sut = new PbnVerifier(null, true);
        assertNotNull(sut, "PbnVerifier.main()");
    }

    @DisplayName("A pbn 1.0 file containing one game is processed against 1.0 standards with no errors")
    @Test
    void verifySingle10FileWithOneGameAgainst10Standards() {
        String expected = "Total number of games read : 1\n\nImport file is OK";
        PrintStream outMock = mock(PrintStream.class);
        System.setOut(outMock);
        String inputFilePath = Paths.get(testFileRootPath + "basic_10.pbn").toAbsolutePath().toString();
        String[] args = new String[]{inputFilePath};

        new PbnVerifier(args, true);

        verify(outMock).println(expected);
    }

    @DisplayName("A pbn 1.0 file containing one game is processed against 2.0 standards with no errors")
    @Test
    void verifySingle10FileWithOneGameAgainst20Standards() {
        String expected = "Total number of games read : 1\n\nImport file is OK";
        PrintStream outMock = mock(PrintStream.class);
        System.setOut(outMock);
        String inputFilePath = Paths.get(testFileRootPath + "basic_10.pbn").toAbsolutePath().toString();
        String[] args = new String[]{"-20", inputFilePath};

        new PbnVerifier(args, true);

        verify(outMock).println(expected);
    }

    @DisplayName("A pbn 2.0 file containing one game is processed against 2.0 standards with no errors")
    @Test
    void verifySingle20FileWithOneGameAgainst20Standards() {
        String expected = "Total number of games read : 1\n\nImport file is OK";
        PrintStream outMock = mock(PrintStream.class);
        System.setOut(outMock);
        String inputFilePath = Paths.get(testFileRootPath + "basic_20.pbn").toAbsolutePath().toString();
        String[] args = new String[]{"-20", inputFilePath};

        new PbnVerifier(args, true);

        verify(outMock).println(expected);
    }

    @DisplayName("A pbn 2.0 file containing one game is processed against 1.0 standards and has errors")
    @Test
    void verifySingle20FileWithOneGameAgainst10Standards() {
        String expected = "Total number of games read : 1\nGames with PBN error : 1";
        PrintStream outMock = mock(PrintStream.class);
        System.setOut(outMock);
        String inputFilePath = Paths.get(testFileRootPath + "basic_20.pbn").toAbsolutePath().toString();
        String[] args = new String[]{"-10", inputFilePath};

        new PbnVerifier(args, true);

        verify(outMock).println(expected);
    }

    @DisplayName("A pbn 1.0 file containing numerous errors displays error summary")
    @Test
    void verifyPbn10FileWithNumerousErrors() {
        String expected = "Total number of games read : 74\n" +
                "Games with severe PBN error : 49\n" +
                "Games with PBN error : 22\n" +
                "Games with PBN warning : 1\n" +
                "Games with PBN remark : 1";
        PrintStream outMock = mock(PrintStream.class);
        System.setOut(outMock);
        String inputFilePath = Paths.get(testFileRootPath + "errors_10.pbn").toAbsolutePath().toString();
        String[] args = new String[]{inputFilePath};

        new PbnVerifier(args, true);

        verify(outMock).println(expected);
    }


    @DisplayName("A pbn 1.0 file containing errors reports those errors to the log file")
    @ParameterizedTest
    @MethodSource("getPbn10ErrorMessagesAndCounts")
    void verifyPbn10ErrorsReportedInLogFile(String errorMessage, long expectedErrorCount) throws IOException {
        String logFilePath = testFileRootPath + "test.log";
        String inputFilePath = Paths.get(testFileRootPath + "errors_10.pbn").toAbsolutePath().toString();
        String[] args = new String[]{"-O" + testFileRootPath + "test.log", inputFilePath};

        new PbnVerifier(args, true);
        List<String> lines = Files.readAllLines(Paths.get(logFilePath), StandardCharsets.ISO_8859_1);

        long numberOfErrors = lines.stream()
                .filter(line -> line.equals(errorMessage))
                .count();
        assertEquals(expectedErrorCount, numberOfErrors);
    }

    private static Stream<Arguments> getPbn10ErrorMessagesAndCounts() {
        return Stream.of(
                Arguments.of("I: No error", 0),
                Arguments.of("E: General error", 0),
                Arguments.of("S: Bad call", 3),
                Arguments.of("S: Bad card", 3),
                Arguments.of("E: Bad character", 8),
                Arguments.of("S: Bad comment", 0),
                Arguments.of("S: Bad contract", 0),
                Arguments.of("S: Bad deal", 5),
                Arguments.of("S: Bad declarer", 4),
                Arguments.of("S: Bad illegal", 2),
                Arguments.of("S: Bad inheritance", 7),
                Arguments.of("S: Bad lead", 1),
                Arguments.of("E: Bad nag", 6),
                Arguments.of("E: Bad note", 6),
                Arguments.of("E: Bad number", 13),
                Arguments.of("S: Bad rank", 5),
                Arguments.of("E: Bad result", 5),
                Arguments.of("S: Bad revoke", 4),
                Arguments.of("S: Bad risk", 2),
                Arguments.of("E: Bad score", 0),
                Arguments.of("E: Bad section", 1),
                Arguments.of("S: Bad side", 3),
                Arguments.of("E: Bad suffix", 0),
                Arguments.of("S: Bad suit", 1),
                Arguments.of("E: Bad tag value", 19),
                Arguments.of("S: Bad trump", 6),
                Arguments.of("S: Double illegal", 2),
                Arguments.of("W: Double nag", 5),
                Arguments.of("E: Double note", 2),
                Arguments.of("E: Double suffix", 4),
                Arguments.of("F: File error", 0),
                Arguments.of("S: Line too long", 3),
                Arguments.of("W: New tag name", 1),
                Arguments.of("R: New tag value", 4),
                Arguments.of("S: No contract", 1),
                Arguments.of("S: No deal", 1),
                Arguments.of("S: No dealer", 1),
                Arguments.of("S: No declarer", 3),
                Arguments.of("E: No note for =%d=", 0),
                Arguments.of("I: No tag", 0),
                Arguments.of("E: No tag value", 1),
                Arguments.of("F: No resources", 0),
                Arguments.of("S: Revoke", 1),
                Arguments.of("S: Wrong character", 3),
                Arguments.of("S: Wrong tag value", 3),
                Arguments.of("S: Sufficient", 1),
                Arguments.of("W: Tag exists already", 1),
                Arguments.of("W: Unknown lead", 0),
                Arguments.of("W: Unused note [%d]", 0),
                Arguments.of("E: Bad last call", 0),
                Arguments.of("E: Bad last card", 0),
                Arguments.of("E: Bad table", 0),
                Arguments.of("E: Bad table order", 0),
                Arguments.of("E: Bad vulnerable", 0),
                Arguments.of("W: Bad play", 0),
                Arguments.of("W: No new line", 6)
        );
    }

    @DisplayName("A pbn 2.0 file containing errors reports those errors to the log file")
    @ParameterizedTest
    @MethodSource("getPbn20ErrorMessagesAndCounts")
    void verifyPbn20ErrorsReportedInLogFile(String errorMessage, long expectedErrorCount) throws IOException {
        String logFilePath = testFileRootPath + "test.log";
        String inputFilePath = Paths.get(testFileRootPath + "errors_20.pbn").toAbsolutePath().toString();
        String[] args = new String[]{"-O" + testFileRootPath + "test.log", inputFilePath};

        new PbnVerifier(args, true);
        List lines = Files.readAllLines(Paths.get(logFilePath), StandardCharsets.ISO_8859_1);

        long numberOfErrors = lines.stream()
                .filter(line -> line.equals(errorMessage))
                .count();
        assertEquals(expectedErrorCount, numberOfErrors);
    }

    private static Stream<Arguments> getPbn20ErrorMessagesAndCounts() {
        return Stream.of(
                Arguments.of("I: No error", 0),
                Arguments.of("E: General error", 0),
                Arguments.of("S: Bad call", 0),
                Arguments.of("S: Bad card", 0),
                Arguments.of("E: Bad character", 33),
                Arguments.of("S: Bad comment", 0),
                Arguments.of("S: Bad contract", 0),
                Arguments.of("S: Bad deal", 0),
                Arguments.of("S: Bad declarer", 1),
                Arguments.of("S: Bad illegal", 0),
                Arguments.of("S: Bad inheritance", 0),
                Arguments.of("S: Bad lead", 0),
                Arguments.of("E: Bad nag", 0),
                Arguments.of("E: Bad note", 0),
                Arguments.of("E: Bad number", 0),
                Arguments.of("S: Bad rank", 0),
                Arguments.of("E: Bad result", 0),
                Arguments.of("S: Bad revoke", 0),
                Arguments.of("S: Bad risk", 0),
                Arguments.of("E: Bad score", 0),
                Arguments.of("E: Bad section", 0),
                Arguments.of("S: Bad side", 0),
                Arguments.of("E: Bad suffix", 0),
                Arguments.of("S: Bad suit", 0),
                Arguments.of("E: Bad tag value", 0),
                Arguments.of("S: Bad trump", 0),
                Arguments.of("S: Double illegal", 0),
                Arguments.of("W: Double nag", 0),
                Arguments.of("E: Double note", 0),
                Arguments.of("E: Double suffix", 0),
                Arguments.of("F: File error", 0),
                Arguments.of("S: Line too long", 0),
                Arguments.of("W: New tag name", 17),
                Arguments.of("R: New tag value", 2),
                Arguments.of("S: No contract", 0),
                Arguments.of("S: No deal", 0),
                Arguments.of("S: No dealer", 0),
                Arguments.of("S: No declarer", 0),
                Arguments.of("E: No note for =%d=", 0),
                Arguments.of("I: No tag", 0),
                Arguments.of("E: No tag value", 0),
                Arguments.of("F: No resources", 0),
                Arguments.of("S: Revoke", 0),
                Arguments.of("S: Wrong character", 0),
                Arguments.of("S: Wrong tag value", 0),
                Arguments.of("S: Sufficient", 0),
                Arguments.of("W: Tag exists already", 0),
                Arguments.of("W: Unknown lead", 0),
                Arguments.of("W: Unused note [%d]", 0),
                Arguments.of("E: Bad last call", 0),
                Arguments.of("E: Bad last card", 0),
                Arguments.of("E: Bad table", 0),
                Arguments.of("E: Bad table order", 0),
                Arguments.of("E: Bad vulnerable", 0),
                Arguments.of("W: Bad play", 0),
                Arguments.of("W: No new line", 0)
        );
    }

    @DisplayName("A pbn 2.1 file containing errors reports those errors to the log file")
    @ParameterizedTest
    @MethodSource("getPbn21ErrorMessagesAndCounts")
    void verifyPbn21ErrorsReportedInLogFile(String errorMessage, long expectedErrorCount) throws IOException {
        String logFilePath = testFileRootPath + "test.log";
        String inputFilePath = Paths.get(testFileRootPath + "errors_21.pbn").toAbsolutePath().toString();
        String[] args = new String[]{"-O" + testFileRootPath + "test.log", inputFilePath};

        new PbnVerifier(args, true);
        List lines = Files.readAllLines(Paths.get(logFilePath), StandardCharsets.ISO_8859_1);

        long numberOfErrors = lines.stream()
                .filter(line -> line.equals(errorMessage))
                .count();
        assertEquals(expectedErrorCount, numberOfErrors);
    }

    private static Stream<Arguments> getPbn21ErrorMessagesAndCounts() {
        return Stream.of(
                Arguments.of("I: No error", 0),
                Arguments.of("E: General error", 0),
                Arguments.of("S: Bad call", 0),
                Arguments.of("S: Bad card", 0),
                Arguments.of("E: Bad character", 0),
                Arguments.of("S: Bad comment", 0),
                Arguments.of("S: Bad contract", 0),
                Arguments.of("S: Bad deal", 0),
                Arguments.of("S: Bad declarer", 0),
                Arguments.of("S: Bad illegal", 0),
                Arguments.of("S: Bad inheritance", 0),
                Arguments.of("S: Bad lead", 0),
                Arguments.of("E: Bad nag", 0),
                Arguments.of("E: Bad note", 0),
                Arguments.of("E: Bad number", 0),
                Arguments.of("S: Bad rank", 0),
                Arguments.of("E: Bad result", 0),
                Arguments.of("S: Bad revoke", 0),
                Arguments.of("S: Bad risk", 0),
                Arguments.of("E: Bad score", 0),
                Arguments.of("E: Bad section", 0),
                Arguments.of("S: Bad side", 0),
                Arguments.of("E: Bad suffix", 0),
                Arguments.of("S: Bad suit", 0),
                Arguments.of("E: Bad tag value", 0),
                Arguments.of("S: Bad trump", 0),
                Arguments.of("S: Double illegal", 0),
                Arguments.of("W: Double nag", 0),
                Arguments.of("E: Double note", 0),
                Arguments.of("E: Double suffix", 0),
                Arguments.of("F: File error", 0),
                Arguments.of("S: Line too long", 0),
                Arguments.of("W: New tag name", 2),
                Arguments.of("R: New tag value", 0),
                Arguments.of("S: No contract", 0),
                Arguments.of("S: No deal", 0),
                Arguments.of("S: No dealer", 0),
                Arguments.of("S: No declarer", 0),
                Arguments.of("E: No note for =%d=", 0),
                Arguments.of("I: No tag", 0),
                Arguments.of("E: No tag value", 0),
                Arguments.of("F: No resources", 0),
                Arguments.of("S: Revoke", 0),
                Arguments.of("S: Wrong character", 0),
                Arguments.of("S: Wrong tag value", 0),
                Arguments.of("S: Sufficient", 0),
                Arguments.of("W: Tag exists already", 0),
                Arguments.of("W: Unknown lead", 0),
                Arguments.of("W: Unused note [%d]", 0),
                Arguments.of("E: Bad last call", 0),
                Arguments.of("E: Bad last card", 0),
                Arguments.of("E: Bad table", 0),
                Arguments.of("E: Bad table order", 0),
                Arguments.of("E: Bad vulnerable", 0),
                Arguments.of("W: Bad play", 0),
                Arguments.of("W: No new line", 0)
        );
    }
}
