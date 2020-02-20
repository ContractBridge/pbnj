package nl.tistis.pbn;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PbnVerifierTest {
    @Test void appHasAGreeting() {
        // App classUnderTest = new App();
        App classUnderTest = null;
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }

    @Test void main() {
        PbnVerifier sut = new PbnVerifier(null, true);
        assertNotNull(sut, "PbnVerifier.main()");
    }
}
