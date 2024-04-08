module com.example.matkailusovellus_harjoitustyo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.matkailusovellus_harjoitustyo to javafx.fxml;
    exports com.example.matkailusovellus_harjoitustyo;
}