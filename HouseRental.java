package houserental;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Mirza Tanzim Sami
 */
public class HouseRental extends Application {

    static Connection con;
    static PreparedStatement state;
    static ResultSet result;
    static String pass = null;

    static ListView list;
    static String type;
    static boolean logged = false;


    /*This is the code to call the methods that create and populate the StackPane*/
    StackPane root = new StackPane();
    StackPane sign = new StackPane();
    StackPane log = new StackPane();
    StackPane sresult = new StackPane();
    StackPane acc = new StackPane();


    /*This is the code to create the scenes*/
    Scene main = new Scene(root, 1286, 652);
    Scene signup = new Scene(sign, 1288, 652);
    Scene login = new Scene(log, 1288, 652);
    Scene search = new Scene(sresult, 1289, 652);
    Scene account = new Scene(acc, 1288, 652);

    public static void main(String[] args) throws Exception {
        con = dbConnector();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        /*This is the code for all the elements in the main scene
        *
        *
        *
        *
         */
 /*This is the code for the Text being used as heading*/
        Text text = buildText("WELCOME, find your dream home today !!!", 0.0, -150.0, "Varenda", 65, 0);
        /*This is the code for the image being used as the backdrop*/
        final ImageView selectedImage = new ImageView();
        Image img1 = new Image(new FileInputStream("C:/Users/Mirza Tanzim Sami/Documents/BRACU/CSE310/Project/3.jpg"));
        selectedImage.setImage(img1);
        selectedImage.setScaleX(0.67);
        selectedImage.setScaleY(0.65);

        /*This is the code for the search field*/
        TextField searchCity = buildTextField("Search by CITY or ADDRESS or ZIP or Desired RENT or DATE e.g day/month/year", 20, 0.0, 60.0, 500.0, 50.0);

        /*This is the code for the Sign up Button*/
        Button btn0 = buttonBuilder("Sign up", 400.0, -280.0, 1.8, 1.8);

        /*This is the code to handle the event in case of btn0 click*/
        btn0.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(signup);
        });

        /*This is the code for the Login Button*/
        Button btn1 = buttonBuilder("Login", 550.0, -280.0, 1.8, 1.8);

        /*This is the code to handle the event in case of btn1 click*/
        btn1.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(login);
        });

        /*This is the code for the Search Button*/
        Button btn2 = buttonBuilder("Search", 0.0, 180.0, 1.8, 1.8);

        /*This is the code to handle the event in case of btn2 click*/
        btn2.setOnAction((ActionEvent event) -> {
            String q = searchCity.getText();
            System.out.println(q);
            primaryStage.setScene(search);
            /*This is the code for the content in searchArea field*/
            try {
                if (logged == true) {
                    //sresult.getChildren().add(getData(q));
                    sresult.getChildren().add(getTable(q));
                } else {
                    primaryStage.setScene(login);
                }

            } catch (Exception ex) {
                Logger.getLogger(HouseRental.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        /*
        *
        *
        *
        *
         */
 /*This is the code for all the elements in the sigup page
        *
        *
        *
        *
         */
 /*This is call the method to build the Text heading in the sigup page*/
        Text signtext = buildText("Let's complete the signup!!!", 0.0, -200.0, "Varenda", 65, 1);

        /*This is the code to build the Text Fields in the sigup page*/
        TextField sfname = buildTextField("Enter your First Name", 20, 0.0, 0.0, 400.0, 0.0);
        TextField slname = buildTextField("Enter your Last Name", 20, 0.0, 40.0, 400.0, 0.0);
        TextField smail = buildTextField("Enter your e-mail", 20, 0.0, 80.0, 400.0, 0.0);
        TextField spass = buildTextField("Enter your password", 20, 0.0, 120.0, 400.0, 0.0);

        /*This is the code for the Alerts in signup scene*/
        Alert salert0 = buildAlert("ERRORS IN THE REQUIRED FIELDS", "You have entered incorrect information", "Type in the fields correctly!");
        Alert salert1 = buildAlert("Signup Complete!", "You have successfully singed up", "Signup is complete, now let's find your dream home");

        /*This is the code for the Submit Button in signup scene*/
        Button ssubmit = buttonBuilder("Submit", 0.0, 160.0, 1.8, 1.8);

        /*This is the code to handle the event in case of Submit click*/
        ssubmit.setOnAction((ActionEvent event) -> {

            if (sfname.getText().isEmpty() || slname.getText().isEmpty() || smail.getText().isEmpty() || spass.getText().isEmpty()) {
                salert0.showAndWait();
            } else {
                try {
                    state = con.prepareStatement("INSERT INTO tenant (FirstName,LastName,Email,Password) VALUES ('" + sfname.getText() + "','" + slname.getText() + "','" + smail.getText() + "','" + spass.getText() + "') ");
                    state.executeUpdate();
                    salert1.showAndWait();
                    logged = true;
                    primaryStage.setScene(account);

                } catch (SQLException ex) {
                    salert0.showAndWait();
                    Logger.getLogger(HouseRental.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        /*This is the code for the back Button in sigup page*/
        Button sback = buttonBuilder("Back", -550.0, -280.0, 1.8, 1.8);

        /*This is the code to handle the event in case of back click*/
        sback.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(main);
        });

        /*
        *
        *
        *
        *
         */
 /*This is the code for all the elements in the login page
        *
        *
        *
        *
         */
 /*This is call the method to build the Text heading in the login page*/
        Text logtext = buildText("Please log into your account!!!", 0.0, -200.0, "Varenda", 65, 1);

        /*This is the code to build the Text Field in the login page*/
        TextField lname = buildTextField("Enter your username", 20, 0.0, 0.0, 400.0, 0.0);
        TextField lpass = buildTextField("Enter your password", 20, 0.0, 40.0, 400.0, 0.0);

        /*This is the code for the Alerts in login scene*/
        Alert lalert0 = buildAlert("ERRORS IN THE REQUIRED FIELDS", "You have entered incorrect information", "Please type in the fields correctly!");

        Alert lalert1 = buildAlert("Login Successful!", "You have successfully logged in", "Log in is complete, now let's find your dream home");

        /*This is the code for the Submit Button in login scene*/
        Button lsubmit = buttonBuilder("Submit", 0.0, 100.0, 1.8, 1.8);

        /*This is the code to handle the event in case of Submit click*/
        lsubmit.setOnAction((ActionEvent event) -> {
            String q = lname.getText();
            String query = "SELECT Password FROM tenant WHERE Email = '" + q + "'";

            try {
                state = con.prepareStatement(query);

            } catch (SQLException ex) {
                Logger.getLogger(HouseRental.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                result = state.executeQuery();

            } catch (SQLException ex) {
                Logger.getLogger(HouseRental.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                result.next();
                pass = result.getString(1);

            } catch (SQLException ex) {
                Logger.getLogger(HouseRental.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (lname.getText().isEmpty() || lpass.getText().isEmpty()) {
                lalert0.showAndWait();
            } else if (pass.equals(lpass.getText())) {
                lalert1.showAndWait();
                logged = true;
                primaryStage.setScene(account);
            } else if (!pass.equals(lpass)) {
                lalert0.showAndWait();
            }

        });

        /*This is the code for the back Button in sigup page*/
        Button lback = buttonBuilder("Back", -550.0, -280.0, 1.8, 1.8);
        /*This is the code to handle the event in case of back click*/
        lback.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(main);
        });

        Button lsign = buttonBuilder("Sign up", 550.0, -280.0, 1.8, 1.8);
        /*This is the code to handle the event in case of back click*/
        lsign.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(signup);
        });

        /*
        *
        *
        *
        *
         */

 /*This is the code for all the elements in the search page
        *
        *
        *
        *
         */
 /*This is call the method to build the Text heading in the search page*/
        Text srtext = buildText("Here are the search results !!!", 0.0, -200.0, "Varenda", 65, 1);


        /*This is the code for the back Button in sigup page*/
        Button srback = buttonBuilder("Back", -550.0, -280.0, 1.8, 1.8);

        /*This is the code to handle the event in case of back click*/
        srback.setOnAction((ActionEvent event) -> {
            if (logged == false) {
                primaryStage.setScene(main);
            }
            if (logged == true) {
                primaryStage.setScene(account);
            }
        });

        /*
        *
        *
        *
        *
         */
 /*This is the code for all the elements in the account scene
        *
        *
        *
        *
         */
 /*This is the code for the Text being used as heading*/
        Text acctext = buildText("WELCOME, find your dream home today !!!", 0.0, -150.0, "Varenda", 65, 0);
        /*This is the code for the image being used as the backdrop*/
        final ImageView accselectedImage = new ImageView();
        Image accimg1 = new Image(new FileInputStream("C:/Users/Mirza Tanzim Sami/Documents/BRACU/CSE310/Project/3.jpg"));
        accselectedImage.setImage(accimg1);
        accselectedImage.setScaleX(0.67);
        accselectedImage.setScaleY(0.65);

        /*This is the code for the search field*/
        TextField accsearchCity = buildTextField("Search by CITY or ADDRESS or ZIP or Desired RENT or DATE e.g day/month/year", 20, 0.0, 60.0, 500.0, 50.0);

        /*This is the code for the Login Button*/
        Button accbtn0 = buttonBuilder("Sign Out", 550.0, -280.0, 1.8, 1.8);

        /*This is the code to handle the event in case of btn1 click*/
        accbtn0.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(main);
        });

        /*This is the code for the Search Button*/
        Button accbtn2 = buttonBuilder("Search", 0.0, 180.0, 1.8, 1.8);

        /*This is the code to handle the event in case of btn2 click*/
        accbtn2.setOnAction((ActionEvent event) -> {
            String q = accsearchCity.getText();
            primaryStage.setScene(search);
            /*This is the code for the content in searchArea field*/
            try {
                if (logged == true) {
                    //sresult.getChildren().add(getData(q));
                    sresult.getChildren().add(getTable(q));
                } else {
                    primaryStage.setScene(login);
                }

            } catch (Exception ex) {
                Logger.getLogger(HouseRental.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        /*
        *
        *
        *
        *
         */
 /*This is the code to populate the main scene*/
        root.getChildren().addAll(selectedImage);
        root.getChildren().add(btn0);
        root.getChildren().add(btn1);
        root.getChildren().add(btn2);
        root.getChildren().add(text);
        //root.getChildren().add(searchArea);
        root.getChildren().add(searchCity);
        //root.getChildren().add(searchRent);

        /*This is the code to populate the sigup scene*/
        sign.getChildren().add(signtext);
        sign.getChildren().add(sfname);
        sign.getChildren().add(slname);
        sign.getChildren().add(smail);
        sign.getChildren().add(spass);
        sign.getChildren().add(sback);
        sign.getChildren().add(ssubmit);

        /*This is the code to populate the login scene*/
        log.getChildren().add(logtext);
        log.getChildren().add(lback);
        log.getChildren().add(lname);
        log.getChildren().add(lpass);
        log.getChildren().add(lsubmit);
        log.getChildren().add(lsign);

        /*This is the code to populate the sresult scene*/
        sresult.getChildren().add(srtext);
        sresult.getChildren().add(srback);

        /*This is the code to populate the acc scene*/
        acc.getChildren().addAll(accselectedImage);
        acc.getChildren().add(accbtn0);
        acc.getChildren().add(accbtn2);
        acc.getChildren().add(acctext);
        acc.getChildren().add(accsearchCity);

        /*This is the code to create and show the stage*/
        primaryStage.setTitle("House Rental Service");
        primaryStage.setScene(main);
        primaryStage.show();

    }

    /*This is the method to create Text Headings*/
    public Text buildText(String prompt, Double x, Double y, String font, int fsize, int pickcolor) {
        Text t = new Text();
        t.setText(prompt);
        t.setTranslateX(x);
        t.setTranslateY(y);
        t.setFont(Font.font(font, fsize));

        if (pickcolor == 0) {
            t.setFill(Color.WHITE);
        } else if (pickcolor == 1) {
            t.setFill(Color.BLUE);
        }

        return t;
    }

    /*This is the method to create TextFields*/
    public TextField buildTextField(String prompt, int count, Double x, Double y, Double w, Double h) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefColumnCount(count);
        tf.setTranslateX(x);
        tf.setTranslateY(y);
        tf.setMaxWidth(w);
        tf.setMaxHeight(h);
        return tf;
    }

    /*This method to build buttons*/
    public Button buttonBuilder(String name, Double TX, Double TY, Double SX, Double SY) {
        Button button = new Button();
        button.setText(name);
        button.setTranslateX(TX);
        button.setTranslateY(TY);
        button.setScaleX(SX);
        button.setScaleY(SY);
        return button;
    }

    /*This method connects the database*/
    public static Connection dbConnector() throws Exception {

        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/cse310";
            String username = "root";
            String password = "riit";
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (Exception e) {
            System.out.println("Error in Database Connection");
        }
        return con;

    }

    public static TableView getTable(String q) throws Exception {

        String query = "SELECT * FROM houses WHERE City LIKE '" + q + "' or Zip LIKE '" + q + "'  or Address LIKE '" + q + "' or Rent LIKE '" + q + "' or Availability LIKE '" + q + "'";
        state = con.prepareStatement(query);
        result = state.executeQuery();

        TableView<Data> table = new TableView<>();
        final ObservableList<Data> olist = FXCollections.observableArrayList();

        TableColumn c1 = new TableColumn("Rent");
        c1.setMinWidth(80.0);
        c1.setCellValueFactory(new PropertyValueFactory<>("rent"));

        TableColumn c2 = new TableColumn("Available From");
        c2.setMinWidth(100.0);
        c2.setCellValueFactory(new PropertyValueFactory<>("availability"));

        TableColumn c3 = new TableColumn("Address");
        c3.setMinWidth(80.0);
        c3.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn c4 = new TableColumn("City");
        c4.setMinWidth(80.0);
        c4.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn c5 = new TableColumn("Zip");
        c5.setMinWidth(80.0);
        c5.setCellValueFactory(new PropertyValueFactory<>("zip"));

        TableColumn c6 = new TableColumn("Contact");
        c6.setMinWidth(80.0);
        c6.setCellValueFactory(new PropertyValueFactory<>("contact"));

        table.getColumns().addAll(c1, c2, c3, c4, c5, c6);
        table.setTranslateY(180.0);

        while (result.next()) {
            olist.add(new Data(
                    result.getString("Rent"),
                    result.getString("Availability"),
                    result.getString("Address"),
                    result.getString("City"),
                    result.getString("Zip"),
                    result.getString("Contact")
            ));
        }

        table.setItems(olist);

        return table;

    }

    public static Alert buildAlert(String title, String head, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(content);
        return alert;
    }

}
