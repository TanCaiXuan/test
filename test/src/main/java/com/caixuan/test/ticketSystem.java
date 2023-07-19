package com.caixuan.test;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ticketSystem extends Application {
    private ToggleGroup popcornToggleGroup;
    private ToggleGroup showtimeToggleGroup;
    private ComboBox<String> movieComboBox;
    private TextField seatChosen;
    private  GridPane grid;
    private VBox showtimeBox;
    private  TextField movieName;

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane bdpane = new BorderPane();
        bdpane.setTop(movieInfo());
        Scene scene = new Scene(bdpane, 720, 600);
        stage.setTitle("Movie Ticketing System");
        stage.setScene(scene);
        stage.show();

    }

    public GridPane movieInfo(){

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.BASELINE_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        GridPane popcornList  = popcornMenu();

        Label nameRequest = new Label("Movie Name : ");
        movieName = new TextField();
        movieName.setPromptText("Please enter Movie name here");

        Label experience = new Label("Select your Experience : ");
        movieComboBox = new ComboBox<>();
        movieComboBox.getItems().addAll(
                "Beanie ( RM 19.90 ) ",
                "Classic ( RM 15.90 ) ",
                "Deluxe ( RM 23.90 )",
                "Family-Friendly ( RM 23.90 ) ",
                "Flexound ( RM 25.90 ) ",
                "IMAX ( RM 25.90 ) ",
                "Indulge ( RM 120.00 ) ",
                "Infinity ( RM 120.00) ",
                "Junior ( RM 15.90 )",
                "Onyx ( RM 89.90 )");


        Label showTime = new Label("Session : ");
        showtimeBox = new VBox(5);
        showtimeBox.setPadding(new Insets(0, 0, 0, 10));
        showtimeToggleGroup = new ToggleGroup();
        String[] showtimes = {"11:00 AM", "01:30 PM", "4:00 PM", "06:30 PM", "09:00 PM"};
        for (String showtime : showtimes) {
            RadioButton radioButton = new RadioButton(showtime);
            radioButton.setToggleGroup(showtimeToggleGroup);
            showtimeBox.getChildren().add(radioButton);
        }

        Label fnb = new Label("Food & Beverage");
        Label seat = new Label("Seat : ");
        seatChosen = new TextField();
        seatChosen.setPromptText("row and number eg F6,F7");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> handleSubmitButtonClicked());

        grid.add(nameRequest,0,0);
        grid.add(movieName,1,0);
        grid.add(experience,0,1);
        grid.add(movieComboBox,1,1);
        grid.add(showTime,0,2);
        grid.add(showtimeBox,1,2);
        grid.add(seat,0,3);
        grid.add(seatChosen,1,3);
        grid.add(fnb,0,4);
        grid.add(popcornList,1,4);
        grid.add(submitButton,3,5);

        return grid;
    }
    public GridPane popcornMenu(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.BASELINE_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        //do the popcorn image part
        Image popcorn1 = new Image(ticketSystem.class.getResource("popcorn1.png").toString());
        Image popcorn2 = new Image(ticketSystem.class.getResource("popcorn2.png").toString());
        Image popcorn3 = new Image(ticketSystem.class.getResource("popcorn3.png").toString());

        ImageView pc1View = new ImageView(popcorn1);
        ImageView pc2View = new ImageView(popcorn2);
        ImageView pc3View = new ImageView(popcorn3);

        Label pc1Label1 = new Label("Royal Popcorn Combo – Member Special");
        Label pc1Label2 = new Label("Royal Popcorn");
        Label pc1Label3 = new Label("Royal Popcorn Combo");

        popcornToggleGroup= new ToggleGroup();
        RadioButton pc1 = new RadioButton("RM 19.90 ");
        pc1.setToggleGroup(popcornToggleGroup);

        RadioButton pc2 = new RadioButton("RM 17.90 ");
        pc2.setToggleGroup(popcornToggleGroup);

        RadioButton pc3 = new RadioButton("RM 21.90 ");
        pc3.setToggleGroup(popcornToggleGroup);

        VBox pc1box = new VBox(pc1View,pc1Label1,pc1);
        VBox pc2box = new VBox(pc2View,pc1Label2,pc2);
        VBox pc3box = new VBox(pc3View,pc1Label3,pc3);

        grid.add(pc1box,0,0);
        grid.add(pc2box,1,0);
        grid.add(pc3box,2,0);

        return grid;
    }
    private void handleSubmitButtonClicked(){

        if (movieComboBox.getValue() == null ||
                showtimeToggleGroup.getSelectedToggle() == null ||
                seatChosen.getText().isEmpty() ||
                popcornToggleGroup.getSelectedToggle() == null) {
            showAlert("Movie Ticketing System", "Some problem's here","Please fill in all the fields.");
            return;
        }
        String Name = movieName.getText();
        String movie = movieComboBox.getValue();
        double ticketPrice = calculateTicketPrice(movie);

        RadioButton showtimeRadioButton = (RadioButton) showtimeToggleGroup.getSelectedToggle();
        String showtime = showtimeRadioButton.getText();
        String seats = seatChosen.getText();
        RadioButton popcornRadioButton = (RadioButton) popcornToggleGroup.getSelectedToggle();
        String popcorn = popcornRadioButton.getText();

        double beveragePrice =calculatePopcornPrice(popcorn);

        String[] seatArray = seats.split(",");
        int numberOfTickets = seatArray.length;

        double totalTicketPrice = numberOfTickets * ticketPrice;

        double totalAmount = beveragePrice+totalTicketPrice;
        String bpLabel="";
        if (beveragePrice ==19.90 )
            bpLabel = " Royal Popcorn Combo – Member Special";
        if (beveragePrice ==17.90 )
            bpLabel = " Royal Popcorn";
        if (beveragePrice ==21.90 )
            bpLabel = " Royal Popcorn Combo";


        String message = "You selected "+Name+" with "+ movie + " experience at " + showtime + " for " +numberOfTickets +" seat(s) and a" + bpLabel +". The total is RM " +String.format("%.2f", totalAmount);


        showAlert("Thank You!","Confirmation",message);



    }

    private double calculatePopcornPrice(String popcorn){
        double popcornPrice =0.0;
        switch (popcorn){
            case "RM 19.90 ":
                popcornPrice = 19.90;
                break;
            case "RM 17.90 ":
                popcornPrice = 17.90;
                break;
            case "RM 21.90 ":
                popcornPrice = 21.90;
                break;
        }
        return popcornPrice;
    }
    private double calculateTicketPrice(String movie) {
        double ticketPrice =0.0;
        switch (movie) {
            case "Beanie ( RM 19.90 ) ":
                ticketPrice = 19.90;
                break;

            case "Classic ( RM 15.90 ) ":
                ticketPrice = 15.90;
                break;

            case "Deluxe ( RM 23.90 )":
                ticketPrice = 23.90;
                break;

            case  "Family-Friendly ( RM 23.90 ) ":
                ticketPrice = 23.90;
                break;

            case  "Flexound ( RM 25.90 ) ":
                ticketPrice = 25.90;
                break;

            case "IMAX ( RM 25.90 ) ":
                ticketPrice = 25.90;
                break;

            case  "Indulge ( RM 120.00 ) ":
                ticketPrice = 120.00;
                break;

            case "Infinity ( RM 120.00) ":
                ticketPrice = 120.00;
                break;

            case "Junior ( RM 15.90 )":
                ticketPrice = 15.90;
                break;
            case  "Onyx ( RM 89.90 )":
                ticketPrice = 89.90;
                break;
        }
        return ticketPrice;
    }
    private void showAlert(String title, String header,String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }




    public static void main(String[] args) {
        launch();
    }
}