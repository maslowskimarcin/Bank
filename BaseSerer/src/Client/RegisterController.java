package Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController
{
    private User user;
    @FXML
    Label frontText, errorText;
    @FXML
    TextField name, lastName, pesel, city, street, houseNo1, houseNo2, zipCode, idNumber, phoneNum, email, emailRepeated;

    public void setControllerRegister(User user)
    {
        this.user = user;
    }

    @FXML
    public void handleGetBack() throws IOException
    {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("LoginFX.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) frontText.getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    public void handleSendRequest() throws Exception
    {
        String errorCode;

        errorCode = user.register(name.getText(),
                                    lastName.getText(),
                                    pesel.getText(),
                                    city.getText(),
                                    street.getText() + houseNo1.getText() + "/" + houseNo2.getText(),
                                    zipCode.getText(),
                                    idNumber.getText(),
                                    phoneNum.getText(),
                                    email.getText(),
                                    emailRepeated.getText());
        if (errorCode.equals("0"))
        {
            Parent homePageParent = FXMLLoader.load(getClass().getResource("EndRegisterFX.fxml"));
            Scene homePageScene = new Scene(homePageParent);
            Stage appStage = (Stage) frontText.getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        }
        else if (errorCode.equals("1"))
            errorText.setText("Wniosek nie został przyjety.");
        else if (errorCode.equals("-2"))
            errorText.setText("Nie poprawnie powtórzono adres e-mail.");
        else if (errorCode.equals("-3"))
            errorText.setText("Sprawdz czy dane zostały wprowadzone poprawinie.");
        else if (errorCode.equals("-4"))
            errorText.setText("Nie można połaczyc sie z serwerem!");

    }
}
