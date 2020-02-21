package nl.tistis.pbn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PbnVerifier is the main method for the application")
public class PbnVerifierTest {
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
}
