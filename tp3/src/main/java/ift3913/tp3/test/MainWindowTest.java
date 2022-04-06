package test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

class MainWindowTest {

    static ArrayList<Currency> currencies;

    @BeforeAll
    static void setUp() {
        currencies = Currency.init();
    }

    /** Tests boîte noire **/

    @Test
    void convert_lorsqueCurrency1EtCurrency2Valides_etMontantEntre0et10000_retourneMontantConvertie() {
        Double montantConvertie = MainWindow.convert(
                "US Dollar", "Euro", currencies, 600d);

        Double valeurEchange = trouverValeurDechange("US Dollar", "EUR");

        assertEquals(montantConvertie, 600d * valeurEchange, 0.001);
    }

    @Test
    void convert_lorsqueCurrency1EtCurrency2Valides_etMontantFrontiere0_retourneMontantConvertie() {
        Double montantConvertie = MainWindow.convert(
                "US Dollar", "Euro", currencies, 0d);

        Double valeurEchange = trouverValeurDechange("US Dollar", "EUR");

        assertEquals(montantConvertie, 600d * valeurEchange, 0.001);
    }

    @Test
    void convert_lorsqueCurrency1EtCurrency2Valides_etMontantFrontiere10000_retourneMontantConvertie() {
        Double montantConvertie = MainWindow.convert(
                "US Dollar", "Euro", currencies, 10000d);

        Double valeurEchange = trouverValeurDechange("US Dollar", "EUR");

        assertEquals(montantConvertie, 600d * valeurEchange, 0.001);
    }

    @Test
    void convert_lorsqueCurrency1EtCurrency2Valides_etMontantInvalideFrontiereMin_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "US Dollar", "Euro", currencies, -1d));
    }

    @Test
    void convert_lorsqueCurrency1EtCurrency2Valides_etMontantInvalideFrontiereMax_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "US Dollar", "Euro", currencies, 10000d));
    }

    @Test
    void convert_lorsqueCurrency1EtCurrency2Valides_etMontantInvalidePlusPetit_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "US Dollar", "Euro", currencies, -110d));
    }

    @Test
    void convert_lorsqueCurrency1EtCurrency2SontDansLaListe_etMontantInvalidePlusGrand_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "US Dollar", "Euro", currencies, 68000d));
    }

    @Test
    void convert_lorsqueCurrency1ValideEtCurrency2Invalide_etMontantValide_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "US Dollar", "Canadian Dollar", currencies, 600d));
    }

    @Test
    void convert_lorsqueCurrency2ValideEtCurrency1Invalide_etMontantValide_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "Canadian Dollar", "Euro", currencies, 600d));
    }

    @Test
    void convert_lorsqueCurrency1EtCurrency2Invalides_etMontantValide_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "Canadian Dollar", "Indian Rupee", currencies, 600d));
    }

    @Test
    void convert_lorsqueCurrency1EtCurrency2Null_etMontantValide_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                null, null, currencies, 600d));
    }

    @Test
    void convert_lorsqueCurrency1EtCurrency2Empty_etMontantValide_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "", "", currencies, 600d));
    }

    @Test
    void convert_lorsqueListeCurrenciesVide_etMontantValide_retourneException() {
        assertThrows(NoSuchElementException.class, () -> MainWindow.convert(
                "US Dollar", "Euro", new ArrayList<>(), 600d));
    }

    /** Tests boîte blanche **/

    @Test
    void convert_lorsqueCurrency1EtCurrency2Valides_retourneMontantConvertie() {
        Double montantConvertie = MainWindow.convert(
                "US Dollar", "Euro", currencies, 600d);

        Double valeurEchange = trouverValeurDechange("US Dollar", "EUR");

        assertEquals(montantConvertie, 600d * valeurEchange, 0.001);
    }

    @Test
    void convert_lorsqueListeCurrenciesVideEtShortNameCurrency2Null_retourneException() {
        assertThrows(NoSuchElementException.class, () -> MainWindow.convert(
                "US Dollar", "Euro", new ArrayList<>(), 600d));
    }

    @Test
    void convert_lorsqueCurrency1ValideEtCurrency2Invalide_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "US Dollar", "CanadianDollar", currencies, 600d));
    }

    @Test
    void convert_lorsqueCurrency1EtCurrency2Valides_etShortNameCurrency2Invalide_retourneException() {
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
    void convert_lorsqueCurrency2ValideEtCurrency1Invalide_retourneException() {
        assertThrows(IllegalArgumentException.class, () -> MainWindow.convert(
                "CanadianDollar", "US Dollar", currencies, 600d));
    }


    private Double trouverValeurDechange(String currency1, String shortNameCurrency2) {
        Double valeurEchange = 0d;
        for (Currency currency : currencies) {
            if (Objects.equals(currency.getName(), currency1)) {
                valeurEchange = currency.getExchangeValues().get(shortNameCurrency2);
                break;
            }
        }
        return valeurEchange;
    }
}