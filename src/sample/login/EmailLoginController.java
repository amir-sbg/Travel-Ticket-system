package sample.login;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import model.connections.userInformationClient.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class EmailLoginController extends Controller
{
    @FXML
    private BorderPane mainPane;
    static Parent profileRoot;


    @FXML
    private JFXTextField email;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private Hyperlink enterWithPhoneNumberLink;

    @FXML
    private Label invalidEmailWarnLabel;

    @FXML
    protected void actionHandler(ActionEvent event) throws Exception{
        if (event.getSource () == loginButton)
        {
            boolean validPassword = super.isPasswordValid (), validEmail = true;

            if (email.getText ().length () == 0)
            {
                if (!invalidEmailWarnLabel.isVisible ())
                    invalidEmailWarnLabel.setVisible (true);

                validEmail = false;
            }
            else
            {
                if (invalidEmailWarnLabel.isVisible ())
                    invalidEmailWarnLabel.setVisible (false);
            }

            if (validEmail && validPassword)
            {
                // TODO : login here
                try {
                    profileRoot = FXMLLoader.load(getClass().getResource("/sample/Profile/profileView.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AnchorPane load = FXMLLoader.load(getClass().getResource("/sample/Loading/Loading.fxml"));
                mainPane.getChildren().add(load);

                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(f -> {
                    Stage stage;
                    stage = (Stage) loginButton.getScene().getWindow();
                    Scene scene = new Scene(profileRoot);
                    stage.setScene(scene);
                    stage.show();
                });
                pause.play();



                System.out.println ("log in");
                Client client = connect ();
                try {
                    Thread.sleep (3000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace ();
                }
                System.out.println (serverResponse (client));
//                System.out.println ("log in");
            }
        }
        else if (event.getSource () == enterWithPhoneNumberLink)
        {

            Stage stage;
            Parent root;

            stage = (Stage) enterWithPhoneNumberLink.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("loginView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if (event.getSource () == signUpLink){

            Stage stage;
            Parent root;

            stage = (Stage) signUpLink.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/sample/signup/signUpView_2.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        super.initialize (location, resources);
        invalidEmailWarnLabel.setVisible (false);
    }

    private Client connect ()
    {
        Client client = new Client ("127.0.0.1",email.getText ()
                , password.getText (),"Login");
        new Thread (client).start ();
        return client;
    }
}
