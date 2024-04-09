package com.example.matkailusovellus_harjoitustyo;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * MatkailusovellusOhjelma luokka, joka toteuttaa Matkailusovellus ja
 * Matkakohteet -luokkien toiminnallisuuden.
 * Käyttäjä voi kirjautua sisään, tarkastella matkakohteita, lisätä tulevia matkoja sekä
 * tallentaa ja tarkastella matkamuistoja.
 * @author Minna Nurminen
 * @version 1
 * 7.4.2024
 */
public class MatkailusovellusOhjelma extends Application {

    /**
     * Ohjelman ensisijainen näkymä
     */
    private Stage primaryStage;
    /**
     * Painike, jolla käyttäjä kirjautuu ulos sovelluksesta
     */
    private Button ulos = new Button("Kirjaudu ulos");
    /**
     * Painike, jolla päästään näkymissä taaksepäin
     */
    private Button takaisin = new Button("Takaisin");
    /**
     * Tekstikenttä käyttäjänimelle
     */
    private TextField tfkayttaja = new TextField();
    /**
     * Säiliö matkamuistojen näyttämiseen
     */
    private FlowPane muistotContainer = new FlowPane();
    /**
     * Lista käyttäjistä
     */
    private ArrayList<Matkailusovellus> kayttajat = new ArrayList<>();
    /**
     * Käyttäjän paikka listassa
     */
    private int kayttajaIndex;
    /**
     * Listanäkymä matkakohteiden näyttämiseen
     */
    private ListView<Matkakohteet> matkakohteetList = new ListView<>();
    /**
     * Tiedosto tietojen tallentamiseen
     */
    private File matkailuTiedosto = new File("matkailuTiedosto.dat");
    /**
     * Lista matkakohteista
     */
    private ArrayList<Matkakohteet> matkakohteet = new ArrayList<>();
    /**
     * ScrollPane sisällön näyttämiseen
     */
    private ScrollPane scrollPane= new ScrollPane();
    /**
     * Lista matkamuistoille
     */
    private List<String> matkamuistot = new ArrayList<>();
    /**
     * Päivämäärän valitsin matkapäivän valitsemiseen
     */
    private DatePicker matkanPaivamaara = new DatePicker();
    /**
     * Nykyinen käyttäjä
     */
    private Matkailusovellus uusiKayttaja;

    /**
     * Ohjelmaikunnan käynnistys
     * @param primarystage Stage primarystage pääikkuna
     * @throws IOException jos lataaminen epäonnistuu
     */
    public void start(Stage primarystage) throws IOException {

        primaryStage = primarystage;

        avaaTiedosto();
        if (matkailuTiedosto.length() != 0) {
            lueTiedot();
        }

        luoMatkakohteet();

        primarystage.setTitle("Matkailusovellus");
        primarystage.setScene(aloitusnaytto());
        primarystage.show();
    }

    /**
     * Luo matkakohteet ja lisää ne listaan.
     * Sisältää Matkakohteet luokan tiedot:
     * nimi, sijainti, kuvaus ja nähtävyydet.
     */
    private void luoMatkakohteet(){
        matkakohteet.add(new Matkakohteet("Amsterdam","Alankomaat, Eurooppa","Amsterdam on Alankomaiden pääkaupunki. Sen asukasluku on noin 910 000. ","\nAmsterdamin kanaalit\nAnne Frankin talo\nVan Gogh -museo\nDam-aukio"));
        matkakohteet.add(new Matkakohteet("Barcelona", "Espanja, Eurooppa", "Barcelona on kaupunki Koillis-Espanjassa ja se on Espanjan toiseksi suurin kaupunki. Sen asukasluku on noin 1,6 miljoonaa", "\nSagrada Família - keskeneräinen kirkko\nLa Rambla - tunnetuin katu\nParc de la Ciutadella - puisto\nArc de Triomf – Barcelonan riemukaari"));
        matkakohteet.add(new Matkakohteet("Dubai", "Yhdistyneet arabiemiraatit, Aasia", "Dubai on suurin kaupunki Yhdistyneissä arabiemiirikunnissa. Dubai on tunnettu lukuisista pilvenpiirtäjistä, satamista ja aurinkorannoista. Sen pinta-ala on noin 35 neliökilometriä.", "\nBurj Khalifa - maailman korkein rakennus\nBurj al-arab - hotelli\nDubai Mall - ostoskeskus\nThe Palm Jumeirah - tekosaari"));
        matkakohteet.add(new Matkakohteet("Hongkong", "Kiina, Aasia", "Hongkong on yksi maailman liberaaleimmista talouksista ja tärkeimmistä kansainvälisen talouden keskuksista. Hongkongin elintason nousu kuului maailman nopeimpiin. Hongkongilla on yksi maailman korkeimpia elinajanodotteita.", "\nHonk Kong Disneyland\nVictoria Peak\nTian Tan Buddha - patsas\nHong Kong Science Museum"));
        matkakohteet.add(new Matkakohteet("Lontoo", "Englanti, Eurooppa", "Lontoo on Yhdistyneen kuningaskunnan ja Englannin pääkaupunki. Suur-Lontoon pinta-ala on 1 572 neliökilometriä. Lontooksi käsitetään yleensä niin sanottu Suur-Lontoon alue, joka koostuu 32 kaupunkipiiristä sekä Lontoon Citystä", "\nBig Ben - kellotorni\nBuckinghamin palatsi\nLondon Eye - maailmanpyörä\nBritish Museum"));
        matkakohteet.add(new Matkakohteet("Pariisi", "Ranska, Eurooppa", "Pariisi on Ranskan ja Île-de-Francen alueen pääkaupunki. Se on yksi maailman suosituimmista turistikaupungeista. Suur-Pariisiin saapuu yli 45 miljoonaa turistia vuodessa. Kaupunkia pidetään yhtenä maailman tärkeimmistä kulttuurin ja muodin keskuksista.", "\nEiffel-torni\nLouvre - museo\nNotre-Damen katedraali\nRiemukaari"));
        matkakohteet.add(new Matkakohteet("Rooma", "Italia, Eurooppa", "Rooma on Italian pääkaupunki ja väkiluvultaan Italian suurin kaupunki. Se sijaitsee lähellä Italian länsirannikkoa, Tiberjoen varrella. Rooma, joka tunnetaan myös ikuisena kaupunkina, on lähes 3 000 vuoden ikäinen.", "\nColosseum\nVatikaani\nTrevin suihkulähde\nForum Romanum\nPantheon - katollinen kirkko"));
        matkakohteet.add(new Matkakohteet("Singapore", "Singaporen tasavalta, Aasia", "Singapore on yksi maailman tiheimmin asutuista kaupungeista; sen pääsaarella asuu yli viisi miljoonaa ihmistä. Singapore on noussut 2000-luvulla maailman johtavaksi älykaupungiksi.", "\nGardens by the Bay - puutarha\nRaffles-hotelli\nMerlion Park\nSingapore Flyer - maailmanpyörä\nSingaporen kansallismuseo"));
        matkakohteet.add(new Matkakohteet("Tokio", "Japani, Aasia", "Tokio on Japanin pääkaupunki. Tokio on Japanin politiikan, talouden ja kulttuurin keskuksena sekä Japanin keisarin asuinpaikkana. Japanin asukasluku on noin 14 miljoonaa.", "\nHarajuku - kaupunginosa\nKeisarillinen palatsi\nFuji-vuori\nHiroshiman rauhanpuisto\nSenso-ji– Vanhin temppeli Tokiossa"));
    }

    /**
     * Metodi katsoo tekstikenttään syötetyn käyttäjänimen perusteella,
     * onko käyttäjää jo olemassa
     * @return boolean loytyiko
     */
    public boolean etsiKayttaja() {
        boolean loytyiko = false;
        for (int i = 0; i < kayttajat.size(); i++) {
            if (kayttajat.get(i).getKayttaja().equals(tfkayttaja.getText())) {
                loytyiko = true;
                kayttajaIndex = i;
                break;
            }
            else{
                loytyiko=false;
                kayttajaIndex=i+1;
            }
        }
        return loytyiko;
    }

    /**
     * Tallentaa uudet ja muutetut tiedot matkailuTiedostoon
     */
    public void tallennaTiedot() {
        try {
            FileOutputStream kirjota = new FileOutputStream("matkailuTiedosto.dat");
            ObjectOutputStream stream = new ObjectOutputStream(kirjota);
            stream.flush();
            stream.writeObject(kayttajat);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tallentaa uusiKayttaja käyttäjän tiedot käyttäjälistaan kayttajat
     */
    public void tallennaKayttajanTiedot(){
        Matkailusovellus uusiKayttaja = new Matkailusovellus(tfkayttaja.getText());
        kayttajat.add(uusiKayttaja);
    }

    /**
     * Tarkistaa onko matkailuTiedosto jo olemassa,
     * jos tiedosto ei ole olemassa niin luodaan
     * @throws RuntimeException jos uuden tiedoston luominen epäonnistuu
     */
    public void avaaTiedosto() {
        if (!matkailuTiedosto.exists()) {
            try {
                matkailuTiedosto.createNewFile();} catch (IOException e) {
                throw new RuntimeException(e);}}}

    /**
     * Metodi lukee tiedostosta tallennetut käyttäjätiedot ja päivittää ne
     * matkailusovelluksen käyttäjälistalle kayttajat.
     */
    public void lueTiedot() {
        try {
            FileInputStream fis = new FileInputStream(matkailuTiedosto);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.kayttajat = (ArrayList<Matkailusovellus>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Kirjautuu käyttäjän sisään.
     * Jos käyttäjää ei löydy, tallennetaan käyttäjän tiedot ja siirrytään valikkonäkymään.
     * Jos käyttäjä löytyy siirrytään suoraan uuteen näkymään.
     */
    private void kirjauduSisaan() {

        if (matkailuTiedosto.length() == 0 || etsiKayttaja() == false){
            tallennaKayttajanTiedot();
            System.out.println("Tallennetaan käyttäjän tiedot");
            tallennaTiedot();
        }
        else {
            System.out.println("Tervetuloa takaisin");
        }
        Scene valikkoNakyma = valikkoNakyma();
        Stage nykyinenIkkuna = (Stage) tfkayttaja.getScene().getWindow();
        nykyinenIkkuna.setScene(valikkoNakyma);
    }

    /**
     * Luo toiminnallisuuden takaisin -painikkeelle.
     * Painaessa painiketta tyhjennetään matkakohteiden lista ja muistojen säiliö,
     * sekä vaihdetaan valikkonäkymään.
     * @return button takaisin
     */
    private Button takaisinPainike(){
        takaisin.setOnAction(e -> {
            matkakohteetList.getItems().clear();
            muistotContainer.getChildren().clear();
            primaryStage.setScene(valikkoNakyma());
        });
        return takaisin;
    }

    /**
     * Luo aloitusnäytön ja siihen liittyvät komponentit,
     * kuten otsikon, käyttäjänimi-kentän ja kirjautumispainikkeen.
     * @return Scene-olio
     */
    public Scene aloitusnaytto() {

        // Paneelin luonti
        GridPane paneeli = new GridPane();
        paneeli.setHgap(2);
        paneeli.setVgap(5);

        // Varjo tekstien ulkonäköön
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(5);
        dropShadow.setSpread(0.6);

        // Otsikko aloitusnäyttöön
        Text otsikko = new Text("Matkailusovellus");
        otsikko.setFont(Font.font("Arial",  50));
        otsikko.setFill(Color.WHITE);
        otsikko.setEffect(dropShadow);

        // Käyttäjänimen teksti ja teksikenttä
        Text kayttajaTeksti = new Text("  Anna käyttäjänimesi:");
        kayttajaTeksti.setFont(Font.font("Arial", 15));
        kayttajaTeksti.setFill(Color.WHITE);
        kayttajaTeksti.setEffect(dropShadow);
        tfkayttaja.setPromptText("Käyttäjänimi");

        // Kirjautumispainike
        Button kirjauduButton = new Button("Kirjaudu sisään");
        kirjauduButton.setOnAction(e -> kirjauduSisaan());

        paneeli.add(kayttajaTeksti, 0, 1);
        paneeli.add(tfkayttaja, 1, 1);
        paneeli.setAlignment(Pos.CENTER);
        Text ohje = new Text("Luo uusi käyttäjä antamalla uusi käyttäjänimi");
        ohje.setFont(Font.font("Arial", 12));

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(otsikko, paneeli, ohje, kirjauduButton);
        vBox.setAlignment(Pos.CENTER);

        // Taustakuva näkymään
        Image image = new Image("file:src/main/resources/images/aloitusNakyma.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,backgroundSize);
        vBox.setBackground(new Background(backgroundImage));
        Scene kehys = new Scene(vBox, 550, 450);
        return kehys;
    }

    /**
     * Valikkonäyttö ja siinä olevat komponentit.
     * Valikon kautta käyttäjä voi siirtyä erinäkymiin: matkakohteisiin, matkamuistoihin ja tuleviin matkoihin.
     * @return Scene-olio
     */
    private Scene valikkoNakyma() {

        uusiKayttaja = kayttajat.get(kayttajaIndex);

        // Kirjaudu ulos-painikeen asettaminen
        HBox hbox = new HBox();
        hbox.getChildren().add(ulos);
        hbox.setAlignment(Pos.TOP_RIGHT);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);

        ulos.setOnAction(e -> {
            System.out.println("Käyttäjän muutokset tallennettu");
            tallennaKayttajanTiedot();
            tallennaTiedot();
            tfkayttaja.clear();
            primaryStage.setScene(aloitusnaytto());
        });

        ImageView liikkuvaKuva = new ImageView(new Image("file:src/main/resources/images/lentokone_animaatio.png"));
        liikkuvaKuva.setFitWidth(50);
        liikkuvaKuva.setFitHeight(50);

        liikkuvaKuva.setTranslateX(-290);
        liikkuvaKuva.setTranslateY(70);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(12), liikkuvaKuva);
        transition.setToX(300);
        transition.setToY(70);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(5);
        dropShadow.setSpread(0.5);

        Text otsikko = new Text("Tervetuloa " + uusiKayttaja.getKayttaja());
        otsikko.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        otsikko.setFill(Color.WHITE);
        otsikko.setEffect(dropShadow);

        StackPane otsikonPane = new StackPane();
        otsikonPane.getChildren().addAll(liikkuvaKuva, otsikko);

        Text ohjeValikkoon = new Text("Valitse mihin haluat siirtyä");
        ohjeValikkoon.setFont(Font.font("Arial",  16));

        Hyperlink matkakohteetLinkki = new Hyperlink("Matkakohteet");
        matkakohteetLinkki.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        matkakohteetLinkki.setTextFill(Color.WHITE);
        matkakohteetLinkki.setEffect(dropShadow);

        Hyperlink matkamuistotLinkki = new Hyperlink("Matkamuistot");
        matkamuistotLinkki.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        matkamuistotLinkki.setTextFill(Color.WHITE);
        matkamuistotLinkki.setEffect(dropShadow);

        Hyperlink tulevatMatkatLinkki = new Hyperlink("Tulevat matkat");
        tulevatMatkatLinkki.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        tulevatMatkatLinkki.setTextFill(Color.WHITE);
        tulevatMatkatLinkki.setEffect(dropShadow);

        VBox.setMargin(otsikko,new Insets(20,0,20,0));

        // Kuvat hyperlinkkien alle
        ImageView matkakohteetKuva = new ImageView(new Image("file:src/main/resources/images/valikko2.png"));
        matkakohteetKuva.setFitWidth(140);
        matkakohteetKuva.setFitHeight(140);

        ImageView matkamuistotKuva = new ImageView(new Image("file:src/main/resources/images/valikko3.png"));
        matkamuistotKuva.setFitWidth(140);
        matkamuistotKuva.setFitHeight(140);

        ImageView tulevatMatkatKuva = new ImageView(new Image("file:src/main/resources/images/valikko1.png"));
        tulevatMatkatKuva.setFitWidth(140);
        tulevatMatkatKuva.setFitHeight(140);

        StackPane kohteetPane = new StackPane();
        kohteetPane.getChildren().addAll(matkakohteetKuva, matkakohteetLinkki);
        StackPane muistotPane = new StackPane();
        muistotPane.getChildren().addAll(matkamuistotKuva, matkamuistotLinkki);
        StackPane matkatPane = new StackPane();
        matkatPane.getChildren().addAll(tulevatMatkatKuva, tulevatMatkatLinkki);

        // Toiminnot hyperlinkkien klikkauksille
        matkakohteetLinkki.setOnAction(e -> {
            primaryStage.setScene(matkakohteetNakyma());
        });
        matkamuistotLinkki.setOnAction(e -> {
            primaryStage.setScene(matkamuistotNakyma());
        });
        tulevatMatkatLinkki.setOnAction(e -> {
            primaryStage.setScene(tulevatMatkatNakyma());
        });

        HBox valikot = new HBox();
        valikot.getChildren().addAll(kohteetPane, muistotPane, matkatPane);
        valikot.setAlignment(Pos.CENTER);
        valikot.setPadding(new Insets(10));
        valikot.setSpacing(30);

        VBox.setMargin(valikot, new Insets(30, 0, 0, 0));
        VBox layout = new VBox();
        layout.getChildren().addAll(hbox, otsikonPane, ohjeValikkoon, valikot);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setSpacing(10);

        // taustakuva
        Image image = new Image("file:src/main/resources/images/valikkoNakyma.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,backgroundSize);
        layout.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(layout, 550, 450);
        return scene;
    }

    /**
     * Matkakohteet näkymä, joka sisältää matkakohteiden listan
     * ja valitun kohteen tietojen näyttämisen.
     * @return Scene-olio
     */
    private Scene matkakohteetNakyma() {

        ListView<String> matkakohteetList = new ListView<>();
        for (Matkakohteet kohde : matkakohteet) {
            matkakohteetList.getItems().add(kohde.getNimi());
        }

        scrollPane.setContent(matkakohteetList);
        TextFlow matkakohdeTiedot = new TextFlow();
        matkakohdeTiedot.setPrefWidth(200);

        matkakohteetList.setOnMouseClicked(e -> {
            String valittuKohdeNimi = matkakohteetList.getSelectionModel().getSelectedItem();
            if (valittuKohdeNimi != null) {
                Matkakohteet valittuKohde = haeMatkakohdeNimella(valittuKohdeNimi);
                if (valittuKohde != null) {
                    paivitaMatkakohdeTiedot(matkakohdeTiedot, valittuKohde);
                }
            }
        });

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(5);
        dropShadow.setSpread(0.5);

        Text otsikko = new Text("Matkakohteet");
        otsikko.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        otsikko.setFill(Color.WHITE);
        otsikko.setEffect(dropShadow);

        Button takaisin = takaisinPainike();
        HBox takaisinYla = new HBox();
        takaisinYla.getChildren().add(takaisin);
        takaisinYla.setAlignment(Pos.TOP_LEFT);
        takaisinYla.setPadding(new Insets(10));
        takaisinYla.setSpacing(10);

        VBox oikeaPuoli = new VBox();
        oikeaPuoli.getChildren().add(scrollPane);
        oikeaPuoli.setAlignment(Pos.CENTER_LEFT);
        oikeaPuoli.setPadding(new Insets(10));
        oikeaPuoli.setSpacing(10);

        HBox tietoja = new HBox();
        tietoja.getChildren().addAll(oikeaPuoli, matkakohdeTiedot);
        tietoja.setAlignment(Pos.CENTER);
        tietoja.setPadding(new Insets(10));
        tietoja.setSpacing(10);

        VBox layout = new VBox();
        layout.getChildren().addAll(takaisinYla, otsikko,tietoja);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setSpacing(10);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Image image = new Image("file:src/main/resources/images/matkakohteetNakyma.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,backgroundSize);
        layout.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(layout, 550, 450);
        return scene;
    }

    /**
     * Päivittää matkakohteen tiedot TextFlow-olioon valitun kohteen perusteella.
     * @param tiedot TextFlow tiedot
     * @param valittuKohde Matkakohteet valittukohde
     */
    private void paivitaMatkakohdeTiedot(TextFlow tiedot, Matkakohteet valittuKohde) {
        tiedot.getChildren().clear();
        Text nimi = new Text(valittuKohde.getNimi() + "\n\n");
        nimi.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nimi.setFill(Color.WHITE);
        Text sijainti = new Text("Sijainti: " + valittuKohde.getSijainti() + "\n\n");
        Text kuvaus = new Text("Kuvaus: " + valittuKohde.getKuvaus() + "\n\n");
        Text nahtavyydet = new Text("Nähtävyydet: " + valittuKohde.getNahtavyydet() + "\n\n");

        nahtavyydet.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        nahtavyydet.setFill(Color.WHITE);
        kuvaus.setFill(Color.WHITE);
        kuvaus.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        sijainti.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        sijainti.setFill(Color.WHITE);

        tiedot.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 10px;");
        tiedot.getChildren().addAll(nimi, sijainti, kuvaus, nahtavyydet);
    }

    /**
     * Hakee matkakohteen nimen perusteella ja palauttaa sen
     * @param nimi String nimi
     * @return kohde, jos kohdetta ei löydy palautetaan null
     */
    private Matkakohteet haeMatkakohdeNimella(String nimi) {
        for (Matkakohteet kohde : matkakohteet) {
            if (kohde.getNimi().equals(nimi)) {
                return kohde;
            }
        }
        return null;
    }

    /**
     * Näkymä matkamuistojen hallintaan ja näyttämiseen
     * @return Scene-olio, sisältää matkamuistojen näkymän
     */
    private Scene matkamuistotNakyma(){

        paivitaMuistotNakyma(muistotContainer);
        VBox layout = new VBox();
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setSpacing(10);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(5);
        dropShadow.setSpread(0.5);

        Text otsikko = new Text("Matkamuistot");
        otsikko.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        otsikko.setFill(Color.WHITE);
        otsikko.setEffect(dropShadow);

        Button lisaaMuistoButton = new Button("Lisää muisto");
        lisaaMuistoButton.setOnAction(e -> {
            lisaaMuistoIkkuna();
        });
        muistotContainer.setPadding(new Insets(10));

        Button takaisin = takaisinPainike();
        HBox hbox = new HBox();
        hbox.getChildren().addAll(takaisin);
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
        layout.getChildren().addAll(hbox,otsikko, lisaaMuistoButton, muistotContainer);
        // Taustakuva
        Image image = new Image("file:src/main/resources/images/matkamuistotNakyma.jpeg");
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,backgroundSize);
        layout.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(layout, 550, 450);
        return scene;
    }

    /**
     * Näyttää muiston sisällön erillisessä ikkunassa.
     * Ikkunassa myös poista -painike, jolla muiston voi poistaa.
     * @param muisto String muisto
     */
    private void naytaMuistonSisalto(String muisto) {
        Stage muistonIkkuna = new Stage();
        muistonIkkuna.setTitle("Matkamuisto");

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));

        // Erotellaan muiston otsikko ja sisältö
        String[] osat = muisto.split(":");
        String otsikko = osat[0];
        String sisalto = osat[1];

        Text otsikkoTeksti = new Text(otsikko);
        otsikkoTeksti.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        otsikkoTeksti.setFill(Color.BLACK);

        Text sisaltoTeksti = new Text(sisalto);
        sisaltoTeksti.setFont(Font.font("Arial", 16));
        sisaltoTeksti.setFill(Color.BLACK);
        sisaltoTeksti.setTextAlignment(TextAlignment.CENTER);
        TextFlow textFlow = new TextFlow(sisaltoTeksti);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        // Poista muisto listalta
        Button poistaButton = new Button("Poista");
        poistaButton.setOnAction(event -> {
            // poistetaan muisto käyttäjän muistoista
            uusiKayttaja.getMatkamuistot().remove(muisto);
            // Päivitetään muistotContainer poistamalla poistettu muisto sieltä
            muistotContainer.getChildren().removeIf(node -> {
                if (node instanceof Hyperlink) {
                    Hyperlink hyperlink = (Hyperlink) node;
                    return hyperlink.getText().equals(otsikko);
                }
                return false;
            });
            muistonIkkuna.close();
        });

        Image image = new Image("file:src/main/resources/images/muistonTausta.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,backgroundSize);
        layout.setBackground(new Background(backgroundImage));

        HBox hBox = new HBox(poistaButton);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        layout.getChildren().addAll(otsikkoTeksti, textFlow, hBox);
        Scene scene = new Scene(layout, 400, 300);
        muistonIkkuna.setScene(scene);
        muistonIkkuna.show();
    }

    /**
     * Avaa ikkunan uuden matkamuiston lisäämistä varten.
     * Sisältää kaikki ikkunan komponentit.
     */
    private void lisaaMuistoIkkuna() {
        Stage lisaaMuistoStage = new Stage();
        lisaaMuistoStage.setTitle("Lisää muisto");

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));

        TextField otsikkoInput = new TextField();
        otsikkoInput.setPromptText("Syötä otsikko");

        TextArea muistoInput = new TextArea();
        muistoInput.setPromptText("Syötä muisto");

        Button tallennaButton = new Button("Tallenna");
        tallennaButton.setOnAction(e -> {
            tallennaMuisto(otsikkoInput.getText(), muistoInput.getText());
            muistotContainer.getChildren().clear();
            paivitaMuistotNakyma(muistotContainer);
            lisaaMuistoStage.close();
        });

        layout.getChildren().addAll(otsikkoInput, muistoInput, tallennaButton);
        Scene scene = new Scene(layout, 300, 200);
        lisaaMuistoStage.setScene(scene);
        lisaaMuistoStage.show();
    }

    /**
     * Tallentaa uuden matkamuiston käyttäjän matkamuistoihin
     * @param otsikko String otsikko
     * @param muisto String muisto
     */
    private void tallennaMuisto(String otsikko, String muisto) {
        if (otsikko.isEmpty() || muisto.isEmpty()) {
            // Näytetään virheilmoitus, jos otsikko tai muisto on tyhjä
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe");
            alert.setHeaderText(null);
            alert.setContentText("Otsikko tai muisto ei voi olla tyhjä!");
            alert.showAndWait();
        } else {
            // Tallennetaan muisto
            uusiKayttaja.setMatkamuistot(otsikko + ": " + muisto);
        }

    }

    /**
     * Päivittää matkamuistojen näkymän muistotContainerin avulla.
     * @param muistotContainer FlowPane muistot säiliö
     */
    private void paivitaMuistotNakyma(FlowPane muistotContainer) {

        muistotContainer.setPadding(new Insets(30, 30, 30, 30));
        muistotContainer.setVgap(10);
        muistotContainer.setHgap(10);
        muistotContainer.setAlignment(Pos.CENTER);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(5);
        dropShadow.setSpread(0.6);

        for (String muisto : uusiKayttaja.getMatkamuistot()) {
            String otsikko = muisto.split(":")[0];

            Hyperlink otsikkoLinkki = new Hyperlink(otsikko);
            otsikkoLinkki.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            otsikkoLinkki.setTextFill(Color.WHITE);
            otsikkoLinkki.setEffect(dropShadow);
            otsikkoLinkki.setOnAction(event -> {
                naytaMuistonSisalto(muisto);
            });
            muistotContainer.getChildren().add(otsikkoLinkki);
        }
    }

    /**
     * Tulevat matkat -näkymä ja sen komponentit.
     * Näkymässä voi lisätä uuden tulevan matkan näkyviin.
     * @return Scene-olio
     */
    private Scene tulevatMatkatNakyma(){

        VBox layout = new VBox();
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(5);
        dropShadow.setSpread(0.5);

        Text otsikko = new Text("Tulevat matkat");
        otsikko.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        otsikko.setFill(Color.WHITE);
        otsikko.setEffect(dropShadow);

        // Lisää uusi tuleva matka
        Label matkanNimiLabel = new Label("Matkakohde:");
        matkanNimiLabel.setTextFill(Color.WHITE);
        matkanNimiLabel.setEffect(dropShadow);
        TextField matkanNimiInput = new TextField();
        matkanNimiInput.setPrefWidth(100);

        Label lahtoPaivaLabel = new Label("Lähtöpäivä:");
        lahtoPaivaLabel.setTextFill(Color.WHITE);
        lahtoPaivaLabel.setEffect(dropShadow);
        matkanPaivamaara.setPrefWidth(100);

        // Näytä lisätyt matkat
        VBox matkatTeksti = new VBox();
        matkatTeksti.setPrefWidth(400);
        matkatTeksti.setPadding(new Insets(10));
        matkatTeksti.setSpacing(15);
        matkatTeksti.setStyle("-fx-font-size: 14px;");
        matkatTeksti.setAlignment(Pos.CENTER);

        paivitaMatkatTeksti(matkatTeksti);

        Button lisaaMatkaButton = new Button("Lisää matka");
        lisaaMatkaButton.setOnAction(e -> {
            String matkanNimi = matkanNimiInput.getText();
            LocalDate lahtoPaiva = matkanPaivamaara.getValue();

            if (matkanNimi.isEmpty() || lahtoPaiva == null) {
                // Näytä virheilmoitus, jos kaikki tiedot eivät ole täytetty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Virhe");
                alert.setHeaderText(null);
                alert.setContentText("Kaikki tiedot eivät ole täytetty");
                alert.showAndWait();
                return;
            }

            if (lahtoPaiva.isBefore(LocalDate.now())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Virhe");
                alert.setHeaderText(null);
                alert.setContentText("Matka on jo alkanut");
                alert.showAndWait();
                return;
            }

            // Luo uusi tuleva matka ja lisää se listaan
            tallennaMatka(matkanNimi, lahtoPaiva);
            paivitaMatkatTeksti(matkatTeksti);

            matkanNimiInput.clear();
            matkanPaivamaara.setValue(null);
        });

        Button takaisin = takaisinPainike();
        HBox hbox = new HBox();
        hbox.getChildren().add(takaisin);
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);

        HBox tiedot = new HBox(10);
        tiedot.getChildren().addAll(matkanNimiLabel, matkanNimiInput, lahtoPaivaLabel, matkanPaivamaara, lisaaMatkaButton);
        tiedot.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(hbox,otsikko, tiedot, matkatTeksti);

        Image image = new Image("file:src/main/resources/images/tulevatMatkatNakyma.jpeg");
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,backgroundSize);
        layout.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(layout, 550, 450);
        return scene;
    }

    /**
     * Tallentaa uuden matkan ja päivittää käyttäjän tulevat matkat.
     * @param matkanNimi String matkan nimi
     * @param matkanPaivamaara LocalDate matkan päivämäärä
     */
    private void tallennaMatka(String matkanNimi, LocalDate matkanPaivamaara) {
        int paivienMaara = Matkailusovellus.palautaPaivienMaara(matkanPaivamaara, LocalDate.now());
        String matkaTiedot = matkanNimi + " - Matkalle lähtöön " + paivienMaara + " päivää";
        uusiKayttaja.tallennaTulevatMatkat(matkaTiedot);
    }

    /**
     * Päivittää käyttäjän tulevat matkat tekstikenttään
     * @param matkatTeksti Vbox matkat teksti
     */
    private void paivitaMatkatTeksti(VBox matkatTeksti) {
        matkatTeksti.getChildren().clear();

        List<String> tulevatMatkat = uusiKayttaja.getTulevatMatkat();
        // Järjestää tulevat matkat päivien määrän mukaan
        Collections.sort(tulevatMatkat, new Comparator<String>() {
            public int compare(String matka1, String matka2) {
                int paivat1 = Integer.parseInt(matka1.split(" ")[4]);
                int paivat2 = Integer.parseInt(matka2.split(" ")[4]);
                return Integer.compare(paivat1, paivat2);
            }
        });

        for (String matka : uusiKayttaja.getTulevatMatkat()) {
            Text matkaTeksti = new Text(matka);
            matkaTeksti.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            matkaTeksti.setFill(Color.WHITE);
            matkatTeksti.getChildren().add(matkaTeksti);

        }
    }

    /**
     * Main-metodi, käynnistää sovelluksen
     * @param args komentorivin argumentit
     */
    public static void main(String[] args) {
        launch(args);
    }


}
