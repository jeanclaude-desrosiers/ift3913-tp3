package ift3913.tp3.currencyConverter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    /**
     * On pose l'hypothèse qu'une valeur extérieure aux frontières (min ou max) devrait lancer une exception.
     *
     * Pas de sens de faire des tests à boîte blanche, car la fonction est relativement courte et
     * ne suit qu'un seul chemin.
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

    /** Test hors frontière min -> Devrait retourné une erreur car le montant doit être entre 0 et 1000 **/
    @Test
    void convert_montantAConvertirPlusPetitQueZero_retourneErreur() {
        assertThrows(IllegalArgumentException.class, () -> Currency.convert(-1d, 0.93));
    }

    /** Test hors frontière max ->Devrait retourné une erreur car le montant doit être entre 0 et 10000 **/
    @Test
    void convert_montantAConvertirPlusGrandQueMille_retourneErreur() {
        assertThrows(IllegalArgumentException.class, () -> Currency.convert(10001d, 0.93));
    }

}