package ift3913.tp3.currencyConverter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MainWindowTest {

    static MainWindow mainWindow;
    static ArrayList<Currency> currencies;

    @BeforeAll
    static void setUp() {
        mainWindow = new MainWindow();
        init();
    }

    /** Tests boîte noire **/

    @Test
    void convert() {
//        mainWindow.convert();
    }

    static void init() {
        currencies = new ArrayList<Currency>();
        currencies.add( new Currency("US Dollar", "USD") );
        currencies.add( new Currency("Euro", "EUR") );
        currencies.add( new Currency("British Pound", "GBP") );
        currencies.add( new Currency("Swiss Franc", "CHF") );

        for (Integer i =0; i < currencies.size(); i++) {
            Currency currencyExistante = currencies.get(i);
            currencyExistante.defaultValues();
            currencyExistante.getExchangeValues().remove("CNY");
            currencyExistante.getExchangeValues().remove("JPY");

            if (currencyExistante.getShortName().equals("USD")) {
                currencyExistante.setExchangeValues("INR", 76.00);
                currencyExistante.setExchangeValues("AUD", 1.33);
                currencyExistante.setExchangeValues("CAD", 1.25);
            }
            if (currencyExistante.getShortName().equals("EUR")) {
                currencyExistante.setExchangeValues("INR", 83.94);
                currencyExistante.setExchangeValues("AUD", 1.47);
                currencyExistante.setExchangeValues("CAD", 1.38);
            }
            if (currencyExistante.getShortName().equals("GBP")) {
                currencyExistante.setExchangeValues("INR", 99.66);
                currencyExistante.setExchangeValues("AUD", 1.75);
                currencyExistante.setExchangeValues("CAD", 1.64);
            }
            if (currencyExistante.getShortName().equals("CHF")) {
                currencyExistante.setExchangeValues("INR", 82.09);
                currencyExistante.setExchangeValues("AUD", 1.44);
                currencyExistante.setExchangeValues("CAD", 1.35);
            }
        }

        Currency currency = new Currency("Canadian Dollar", "CAD");
        currency.setExchangeValues("USD", 0.80);
        currency.setExchangeValues("EUR", 0.72);
        currency.setExchangeValues("GBP", 0.61);
        currency.setExchangeValues("CHF", 0.74);
        currency.setExchangeValues("INR", 60.69);
        currency.setExchangeValues("AUD", 1.06);
        currency = new Currency("Indian Rupee", "INR");
        currency.setExchangeValues("USD", 0.013);
        currency.setExchangeValues("EUR", 0.012);
        currency.setExchangeValues("GBP", 0.010);
        currency.setExchangeValues("CHF", 0.012);
        currency.setExchangeValues("CAD", 0.016);
        currency.setExchangeValues("AUD", 0.018);
        currency = new Currency("Australian Dollar", "AUD");
        currency.setExchangeValues("USD", 0.75);
        currency.setExchangeValues("EUR", 0.68);
        currency.setExchangeValues("GBP", 0.57);
        currency.setExchangeValues("CHF", 0.69);
        currency.setExchangeValues("INR", 56.92);
        currency.setExchangeValues("CAD", 0.94);
    }
}