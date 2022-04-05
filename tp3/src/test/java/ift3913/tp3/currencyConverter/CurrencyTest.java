package ift3913.tp3.currencyConverter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    /** Tests boîte noire **/

    /**
     * Ne fait pas de sens de tester les frontières de la valeur d'échange car ce n'est pas dans les spécifications.
     * Le client pourrait vouloir que des valeurs d'échange soit plus petit que 0.
     */

    /** Test entre frontières **/
    @Test
    void convert_montantAConvertirEntreFrontiere_retourneMontantConvertie() {
        double montantConvertie = Currency.convert(423d, 0.93);
        assertEquals(montantConvertie, (423d * 0.93), 0.001);
    }

    /** Test frontière min **/
    @Test
    void convert_montantAConvertirFrontiereMin_retourneMontantConvertie() {
        double montantConvertie = Currency.convert(0d, 0.93);
        assertEquals(montantConvertie, 0);
    }

    /** Test frontière max **/
    @Test
    void convert_montantAConvertirFrontiereMax_retourneMontantConvertie() {
        double montantConvertie = Currency.convert(10000d, 0.93);
        assertEquals(montantConvertie, 10000d * 0.93, 0.001);
    }

    /** Devrait retourné une erreur car le montant doit être entre 0 et 1000 **/
    @Test
    void convert_montantAConvertirPlusPetitQueZero_retourneErreur() {
        assertThrows(IllegalArgumentException.class, () -> Currency.convert(-1d, 0.93));
    }

    /** Devrait retourné une erreur car le montant doit être entre 0 et 10000 **/
    @Test
    void convert_montantAConvertirPlusGrandQueMille_retourneErreur() {
        assertThrows(IllegalArgumentException.class, () -> Currency.convert(10001d, 0.93));
    }

    /** Tests boîte blanche **/

}