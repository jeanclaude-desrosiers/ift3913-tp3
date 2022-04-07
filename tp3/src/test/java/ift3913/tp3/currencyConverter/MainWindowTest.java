package ift3913.tp3.currencyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author jclaude
 */
public class MainWindowTest {

    private static final Object[][] exchangeValues = {
        {"USD", "USD", 1.0}, // USD -> USD
        {"USD", "CAD", 1.25}, // USD -> CAD
        {"USD", "JPY", 123.54}, // USD -> JPY
        {"USD", "MXN", 20.18}, // USD -> MXN

        {"CAD", "CAD", 1.0}, // CAD -> CAD
        {"CAD", "USD", 0.8}, // CAD -> USD
        {"CAD", "JPY", 98.81}, // CAD -> JPY
        {"CAD", "MXN", 16.09}, // CAD -> MXN

        {"JPY", "JPY", 1.0}, // JPY -> JPY
        {"JPY", "USD", 0.0081}, // JPY -> USD
        {"JPY", "CAD", 0.010}, // JPY -> CAD
        {"JPY", "MXN", 0.16}, // JPY -> MXN

        {"MXN", "MXN", 1.0}, // MXN -> MXN
        {"MXN", "USD", 0.050}, // MXN -> USD
        {"MXN", "CAD", 0.062}, // MXN -> CAD
        {"MXN", "JPY", 6.14}, // MXN -> JPY
    };

    private static Double get_exchangeValues(Currency from, Currency to) {
        String currency1_short_str = from.getShortName();
        String currency2_short_str = to.getShortName();

        for (Object[] exchangeValue_arr : exchangeValues) {
            if (exchangeValue_arr[0].equals(currency1_short_str)
                    && exchangeValue_arr[1].equals(currency2_short_str)) {
                return (Double) exchangeValue_arr[2];
            }
        }

        return null;
    }

    private static final Object[][] currencies = {
        {new Currency("US Dollar", "USD"), true},
        {new Currency("Canadian Dollar", "CAD"), true},
        {new Currency("Japanese Yen", "JPY"), false},
        {new Currency("Mexican Peso", "MXN"), false},
        {new Currency("Alpha Beta Currency", "ABC"), false},
        {new Currency("", "   "), false}
    };

    private static final Object[][] amounts = {
        {-42.0, false},
        {42.0, true},
        {42000.0, false},
        {-0.01, false},
        {0.0, true},
        {10000.0, true},
        {10000.01, false}
    };

    private static final Object[][] currencies_arraylists;

    static {
        List<Currency> correct_currencies = Arrays.asList(
                new Currency("US Dollar", "USD"),
                new Currency("Canadian Dollar", "CAD"),
                new Currency("Pound sterling", "GBP"),
                new Currency("Euro", "EUR"),
                new Currency("Swiss Franc", "CHF"),
                new Currency("Indian Rupee", "INR"),
                new Currency("Australian Dollar", "AUD")
        );
        for(Currency c : correct_currencies) {
            c.defaultValues();
        }

        List<Currency> correct_currencies_miss_usd = Arrays.asList(
                new Currency("Canadian Dollar", "CAD"),
                new Currency("Pound sterling", "GBP"),
                new Currency("Euro", "EUR"),
                new Currency("Swiss Franc", "CHF"),
                new Currency("Indian Rupee", "INR"),
                new Currency("Australian Dollar", "AUD")
        );
        for(Currency c : correct_currencies_miss_usd) {
            c.defaultValues();
        }

        currencies_arraylists = new Object[][]{
            {new ArrayList<>(correct_currencies), true},
            {new ArrayList<>(correct_currencies_miss_usd), false},
            {new ArrayList<Currency>(), false}
        };
    }

    public static Stream<Arguments> blackbox_arguments() {
        ArrayList<Arguments> arguments = new ArrayList<>();

        for (Object[] currency1_arr : currencies) {
            Currency currency1 = (Currency) currency1_arr[0];
            Boolean currency1_valid = (Boolean) currency1_arr[1];

            for (Object[] currency2_arr : currencies) {
                Currency currency2 = (Currency) currency2_arr[0];
                Boolean currency2_valid = (Boolean) currency2_arr[1];

                for (Object[] currencies_arraylists_arr : currencies_arraylists) {
                    ArrayList<Currency> currency_arraylist
                            = (ArrayList<Currency>) currencies_arraylists_arr[0];
                    Boolean currency_arraylist_valid
                            = (Boolean) currencies_arraylists_arr[1];

                    for (Object[] amount_arr : amounts) {
                        Double amount = (Double) amount_arr[0];
                        Boolean amount_valid = (Boolean) amount_arr[1];

                        Object expectedResult = null;
                        if (currency1_valid && currency2_valid && amount_valid && currency_arraylist_valid) {
                            expectedResult = amount * get_exchangeValues(currency1, currency2);
                        }

                        arguments.add(Arguments.of(
                                currency1.getName(),
                                currency2.getName(),
                                amount,
                                currency_arraylist,
                                expectedResult
                        ));
                    }
                }
            }
        }

        return arguments.stream();
    }

    @ParameterizedTest
    @MethodSource("blackbox_arguments")
    public void test_convert_blackbox(String currency1, String currency2, Double amount, ArrayList<Currency> currency_arraylist, Double expectedResult) {
        String expectedMessage = "\nTesting amount=" + amount + " from currency1=" + currency1 + " to currency2=" + currency2 + "\n"
                + "Should output=" + (expectedResult == null ? "exception" : expectedResult) + "\n";
        System.out.println(expectedMessage);

        if (expectedResult == null) {
            assertThrows(Exception.class, () -> MainWindow.convert(currency1, currency2, currency_arraylist, amount), expectedMessage);
        } else {
            assertEquals(expectedResult, MainWindow.convert(currency1, currency2, currency_arraylist, amount), 0.001, expectedMessage);
        }
    }

    /**
     * Tests boîte blanche *
     */
    @Test
    public void test_convert_lorsqueCurrency1EtCurrency2Valides_retourneMontantConvertie() {
        Double montantConvertie = MainWindow.convert(
                "US Dollar", "Euro", Currency.init(), 600d);

        Double valeurEchange = trouverValeurDechange("US Dollar", "EUR");

        assertEquals(montantConvertie, 600d * valeurEchange, 0.001);
    }

    @Test
    public void test_convert_lorsqueListeCurrenciesVideEtShortNameCurrency2Null_retourneException() {
        assertThrows(NoSuchElementException.class, () -> MainWindow.convert(
                "US Dollar", "Euro", new ArrayList<>(), 600d));
    }

    @Test
    public void test_convert_lorsqueCurrency1ValideEtCurrency2Invalide_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "US Dollar", "CanadianDollar", Currency.init(), 600d));
    }

    @Test
    public void test_convert_lorsqueCurrency1EtCurrency2Valides_etShortNameCurrency2Invalide_retourneException() {
        ArrayList<Currency> currenciesModifie = Currency.init();

        for (Currency currency : currenciesModifie) {
            if (Objects.equals(currency.getName(), "CanadianDollar")) {
                currency.setShortName(null);
            }
        }
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "US Dollar", "CanadianDollar", currenciesModifie, 600d));
    }

    @Test
    public void test_convert_lorsqueCurrency2ValideEtCurrency1Invalide_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "CanadianDollar", "US Dollar", Currency.init(), 600d));
    }

    private Double trouverValeurDechange(String currency1, String shortNameCurrency2) {
        Double valeurEchange = 0d;
        for (Currency currency : Currency.init()) {
            if (Objects.equals(currency.getName(), currency1)) {
                valeurEchange = currency.getExchangeValues().get(shortNameCurrency2);
                break;
            }
        }
        return valeurEchange;
    }

}
