package nl.tistis.pbn;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PbnVerifierTest {
    @Test void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
