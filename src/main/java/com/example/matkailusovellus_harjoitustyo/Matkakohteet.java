package com.example.matkailusovellus_harjoitustyo;

/**
 * Luokka toteuttaa Matkakohteet,
 * jolla on nimi, sijainti, kuvaus ja nähtävyydet
 * @author Minna Nurminem
 * @version 1.0
 * 7.4.2024
 */
public class Matkakohteet {

    /**
     * Matkakohteen nimi merkkijonona
     */
    private String nimi;
    /**
     * Sijainti merkkijonona
     */
    private String sijainti;
    /**
     * Matkakohteen kuvaus merkkijonona
     */
    private String kuvaus;
    /**
     * Nähtävyydet merkkijonona
     */
    private String nahtavyydet;

    /**
     * Alustaja, joka luo uuden matkakohteen
     * @param nimi String matkakohteen nimi
     * @param sijainti String matkakohteen sijainti
     * @param kuvaus String matkakohteen kuvaus
     * @param nahtavyydet String nähtävyydet
     */
    public Matkakohteet(String nimi, String sijainti, String kuvaus, String nahtavyydet) {
        this.nimi = nimi;
        this.sijainti = sijainti;
        this.kuvaus = kuvaus;
        this.nahtavyydet = nahtavyydet;
    }

    /**
     * Palauttaa matkakohteen nimen
     * @return String nimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Palauttaa sijainnin
     * @return String sijainti
     */
    public String getSijainti() {
        return sijainti;
    }

    /**
     * Palauttaa kuvauksen
     * @return String kuvaus
     */
    public String getKuvaus() {
        return kuvaus;
    }

    /**
     * Palauttaa nähtävyydet
     * @return String nahtavyydet
     */
    public String getNahtavyydet() {
        return nahtavyydet;
    }

}





