package ift3913.tp3.currencyConverter;

import currencyConverter.Currency;
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
public class CurrencyTest {
    
    public CurrencyTest() {
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
     * Test of convert method, of class Currency.
     */
    @Test
    public void testConvert() {
        System.out.println("convert");
        Double amount = null;
        Double exchangeValue = null;
        Double expResult = null;
        Double result = Currency.convert(amount, exchangeValue);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
