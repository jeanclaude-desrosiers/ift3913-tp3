package ift3913.tp3.currencyConverter;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CurrencyTest {

    public static Stream<Arguments> blackbox_arguments() {
        Object[][] exchangeValues = {
            {1.0, true}, // any -> any

            {1.25, true}, // USD -> CAD
            {123.54, false}, // USD -> JPY
            {20.18, false}, // USD -> MXN

            {0.8, true}, // CAD -> USD
            {98.81, false}, // CAD -> JPY
            {16.09, false}, // CAD -> MXN

            {0.0081, false}, // JPY -> USD
            {0.010, false}, // JPY -> CAD
            {0.16, false}, // JPY -> MXN

            {0.050, false}, // MXN -> USD
            {0.062, false}, // MXN -> CAD
            {6.14, false}, // MXN -> JPY
        };

        Object[][] amounts = {
            {-42.0, false},
            {42.0, true},
            {42000.0, false},
            {-0.01, false},
            {0.0, true},
            {10000.0, true},
            {10000.01, false},};

        ArrayList<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i < exchangeValues.length; i++) {
            Double exchangeValue = (Double) exchangeValues[i][0];
            Boolean exchangeValue_valid = (Boolean) exchangeValues[i][1];

            for (int j = 0; j < amounts.length; j++) {
                Double amount = (Double) amounts[j][0];
                Boolean amount_valid = (Boolean) amounts[j][1];

                Object expectedResult = null;
                if (exchangeValue_valid && amount_valid) {
                    expectedResult = amount * exchangeValue;
                }

                arguments.add(Arguments.of(
                        amount,
                        exchangeValue,
                        expectedResult
                ));
            }
        }

        return arguments.stream();
    }

//    @ParameterizedTest
//    @MethodSource("blackbox_arguments")
//    public void test_convert_blackbox(Double amount, Double exchangeValue, Double expectedResult) {
//        String expectedMessage = "\nTesting amount=" + amount + " with exchangeValue=" + exchangeValue + "\n"
//                + "Should output=" + (expectedResult == null ? "exception" : expectedResult) + "\n";
//        
//        if (expectedResult == null) {
//            assertThrows(Exception.class, () -> Currency.convert(amount, exchangeValue), expectedMessage);
//        } else {
//            assertEquals(expectedResult, Currency.convert(amount, exchangeValue), 0.001, expectedMessage);
//        }
//    }
}
