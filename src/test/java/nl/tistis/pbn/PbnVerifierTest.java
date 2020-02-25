package nl.tistis.pbn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName("PbnVerifier is the main method for the application")
class PbnVerifierTest {
    @DisplayName("It has a test that needs to be deleted \uD83D\uDE31")
    @Test void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }

    @DisplayName("When I instantiate the class it should return an object")
    @Test void main() {
        PbnVerifier sut = new PbnVerifier(null, true);
        assertNotNull(sut, "PbnVerifier.main()");
    }

    @DisplayName("A pbn 1.0 file containing one game is processed against 1.0 standards with no errors")
    @Test void verifySingle10FileWithOneGameAgainst10Standards() {
        String expected = "Total number of games read : 1\n\nImport file is OK";
        PrintStream outMock = mock(PrintStream.class);
        System.setOut(outMock);
        String path = Paths.get("src/test/resources/basic_10.pbn").toAbsolutePath().toString();
        String[] args = new String[] { path };

        new PbnVerifier(args, true);

        verify(outMock).println(expected);
    }

    @DisplayName("A pbn 1.0 file containing one game is processed against 2.0 standards with no errors")
    @Test void verifySingle10FileWithOneGameAgainst20Standards() {
        String expected = "Total number of games read : 1\n\nImport file is OK";
        PrintStream outMock = mock(PrintStream.class);
        System.setOut(outMock);
        String path = Paths.get("src/test/resources/basic_10.pbn").toAbsolutePath().toString();
        String[] args = new String[] { "-20", path };

        new PbnVerifier(args, true);

        verify(outMock).println(expected);
    }

    @DisplayName("A pbn 2.0 file containing one game is processed against 2.0 standards with no errors")
    @Test void verifySingle20FileWithOneGameAgainst20Standards() {
        String expected = "Total number of games read : 1\n\nImport file is OK";
        PrintStream outMock = mock(PrintStream.class);
        System.setOut(outMock);
        String path = Paths.get("src/test/resources/basic_20.pbn").toAbsolutePath().toString();
        String[] args = new String[] { "-20", path };

        new PbnVerifier(args, true);

        verify(outMock).println(expected);
    }

    @DisplayName("A pbn 2.0 file containing one game is processed against 1.0 standards and has errors")
    @Test void verifySingle20FileWithOneGameAgainst10Standards() {
        String expected = "Total number of games read : 1\nGames with PBN error : 1";
        PrintStream outMock = mock(PrintStream.class);
        System.setOut(outMock);
        String path = Paths.get("src/test/resources/basic_20.pbn").toAbsolutePath().toString();
        String[] args = new String[] { "-10", path };

        new PbnVerifier(args, true);

        verify(outMock).println(expected);
    }

    @DisplayName("drive 10")
    @Test void  drive10() {

    }
}
