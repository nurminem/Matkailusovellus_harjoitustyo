package com.example.matkailusovellus_harjoitustyo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/** Luokka toteuttaa Matkailusovelluksen, jolla on
 * käyttäjä, yksittäinen matkamuisto, päivämäärä, matkamuistot ja tulevat matkat
 * @author Minna Nurminem
 * @version 1.0
 * 7.4.2024
 */
public class Matkailusovellus implements Serializable {

    /**
     * Käyttäjä merkkijonona
     */
    private String kayttaja;

    /**
     * Päivämäärä jolloin matkalle lähdetään
     */
    private LocalDate matkanPaivamaara;

    /**
     * Matkamuistot listana
     */
    private ArrayList<String> matkamuistot = new ArrayList<>();

    /**
     * Tulevat matkat listana
     */
    private List<String> tulevatMatkat = new ArrayList<>();

    /**
     * Alustaja, joka luo matkailusovelluksen käyttäjällä
     * @param kayttaja String kayttaja
     */
    public Matkailusovellus(String kayttaja){
        this.kayttaja = kayttaja;
    }

    /**
     * Palauttaa käyttäjän
     * @return String kayttaja
     */
    public String getKayttaja() {
        return kayttaja;
    }

    /**
     * Palauttaa päivien määrän kirjautumisen
     * ja matkalle lähdön päivämäärien välillä
     * @param matkanPaivamaara LocalDate matkan päivämäärä
     * @param kirjautumisenPaiva LocalDate kirjautumisen päivä
     * @return int paivienMaara
     */
    public static int palautaPaivienMaara(LocalDate matkanPaivamaara, LocalDate kirjautumisenPaiva){
        int paivienMaara = Math.toIntExact(ChronoUnit.DAYS.between(kirjautumisenPaiva, matkanPaivamaara));
        return paivienMaara;
    }

    /**
     * Palauttaa tulevatMatkat listan
     * @return List tulevatMatkat
     */
    public List<String> getTulevatMatkat() {
        return tulevatMatkat;
    }

    /**
     * Palauttaa matkamuistot listan
     * @return Arraylist matkamuistot
     */
    public ArrayList<String> getMatkamuistot() {
        return matkamuistot;
    }

    /**
     * Asettaa matkamuiston matkamuistot listalle
     * @param matkamuistot String matkamuistot
     */
    public void setMatkamuistot(String matkamuistot) {
        this.matkamuistot.add(matkamuistot);
    }

    /**
     * Tallentaa tulevat matkat listalle
     * @param matkaTiedot String matkatiedot
     */
    public void tallennaTulevatMatkat(String matkaTiedot) {
        tulevatMatkat.add(matkaTiedot);
    }

}
