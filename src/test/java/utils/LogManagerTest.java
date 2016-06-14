package utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the LogManager utility class
 *
 * @author Jean-Paul Labadie
 */
public class LogManagerTest {
    private static LogManager lm;

    @Before
    public void setUp() throws Exception {
        lm = LogManager.getInstance();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsEnabled() throws Exception {

    }

    @Test
    public void testLog() throws Exception {

    }

    @Test
    public void testInfo() throws Exception {
        lm.info(null, null, "Testing Info");
    }

    @Test
    public void testWarn() throws Exception {

    }

    @Test
    public void testError() throws Exception {

    }

}