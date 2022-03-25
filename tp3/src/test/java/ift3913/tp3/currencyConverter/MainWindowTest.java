package ift3913.tp3.currencyConverter;

import currencyConverter.Currency;
import currencyConverter.MainWindow;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jclaude
 */
public class MainWindowTest {
    
    public MainWindowTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of convert method, of class MainWindow.
     */
    @Test
    public void testConvert() {
        System.out.println("convert");
        String currency1 = "";
        String currency2 = "";
        ArrayList<Currency> currencies = null;
        Double amount = null;
        Double expResult = null;
        Double result = MainWindow.convert(currency1, currency2, currencies, amount);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
