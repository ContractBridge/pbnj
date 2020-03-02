package nl.tistis.pbn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("PbnVerifier is the main method for the application")
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
    @Test
    void verifyPbn10ErrorsReportedInLogFile() throws IOException {
        String logFilePath = testFileRootPath + "test.log";
        String inputFilePath = Paths.get(testFileRootPath + "errors_10.pbn").toAbsolutePath().toString();
        String[] args = new String[]{"-O" + testFileRootPath + "test.log", inputFilePath};

        new PbnVerifier(args, true);
        List<String> lines = Files.readAllLines(Paths.get(logFilePath), StandardCharsets.ISO_8859_1);

        getPbn10ErrorMessagesAndCounts().forEach((errorMessage, expectedCount) -> {
            Long numberOfErrors = lines.stream()
                    .filter(line -> line.equals(errorMessage))
                    .count();
            assertEquals(expectedCount, numberOfErrors);
        });
    }

    private static Map<String, Long> getPbn10ErrorMessagesAndCounts() {
        return new HashMap<String, Long>() {{
            put("I: No error", 0L);
            put("E: General error", 0L);
            put("S: Bad call", 3L);
            put("S: Bad card", 3L);
            put("E: Bad character", 8L);
            put("S: Bad comment", 0L);
            put("S: Bad contract", 0L);
            put("S: Bad deal", 5L);
            put("S: Bad declarer", 4L);
            put("S: Bad illegal", 2L);
            put("S: Bad inheritance", 7L);
            put("S: Bad lead", 1L);
            put("E: Bad nag", 6L);
            put("E: Bad note", 6L);
            put("E: Bad number", 13L);
            put("S: Bad rank", 5L);
            put("E: Bad result", 5L);
            put("S: Bad revoke", 4L);
            put("S: Bad risk", 2L);
            put("E: Bad score", 0L);
            put("E: Bad section", 1L);
            put("S: Bad side", 3L);
            put("E: Bad suffix", 0L);
            put("S: Bad suit", 1L);
            put("E: Bad tag value", 19L);
            put("S: Bad trump", 6L);
            put("S: Double illegal", 2L);
            put("W: Double nag", 5L);
            put("E: Double note", 2L);
            put("E: Double suffix", 4L);
            put("F: File error", 0L);
            put("S: Line too long", 3L);
            put("W: New tag name", 1L);
            put("R: New tag value", 4L);
            put("S: No contract", 1L);
            put("S: No deal", 1L);
            put("S: No dealer", 1L);
            put("S: No declarer", 3L);
            put("E: No note for =%d=", 0L);
            put("I: No tag", 0L);
            put("E: No tag value", 1L);
            put("F: No resources", 0L);
            put("S: Revoke", 1L);
            put("S: Wrong character", 3L);
            put("S: Wrong tag value", 3L);
            put("S: Sufficient", 1L);
            put("W: Tag exists already", 1L);
            put("W: Unknown lead", 0L);
            put("W: Unused note [%d]", 0L);
            put("E: Bad last call", 0L);
            put("E: Bad last card", 0L);
            put("E: Bad table", 0L);
            put("E: Bad table order", 0L);
            put("E: Bad vulnerable", 0L);
            put("W: Bad play", 0L);
            put("W: No new line", 6L);
        }};
    }

    @DisplayName("A pbn 2.0 file containing errors reports those errors to the log file")
    @ParameterizedTest
    @MethodSource("getPbn20ErrorMessagesAndCounts")
    void verifyPbn20ErrorsReportedInLogFile(String errorMessage, long expectedErrorCount) throws IOException {
        String logFilePath = testFileRootPath + "test.log";
        String inputFilePath = Paths.get(testFileRootPath + "errors_20.pbn").toAbsolutePath().toString();
        String[] args = new String[]{"-O" + testFileRootPath + "test.log", inputFilePath};

        new PbnVerifier(args, true);
        List<String> lines = Files.readAllLines(Paths.get(logFilePath), StandardCharsets.ISO_8859_1);

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
        List<String> lines = Files.readAllLines(Paths.get(logFilePath), StandardCharsets.ISO_8859_1);

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
