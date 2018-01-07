package Client;

import Base.InvestmentHistory;
import Base.Loan;
import Base.Transfer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ClientController
{
    private ObservableList<String> observableList;
    private List<InvestmentHistory> listInvHist;
    private List<Transfer> listTransHist;
    private int doubleClick, currentListEl, listSize, viewLimit, listViewSize, histInv;
    private Client client;

    @FXML
    private Button home;
    @FXML
    private Label accNo, balance, errHomePage;
    @FXML
    private Label errDataTransfer, accNoToTran, resourcesTran, infoTran;
    @FXML
    private Label  accNoToTranEnd, resourcesTranEnd, accNoToEnd, amountEnd, transferTitleEnd;
    @FXML
    private TextField accNoToTr, amountTr, amountAfterCommaTr;
    @FXML
    private TextArea transferTitle;
    @FXML
    private PasswordField newPassword, newPasswordRepeat;
    @FXML
    private Label errChangePass;
    @FXML
    private Label nameLab, peselLab, cityLab, streetLab, zipCodeLab, idNumberLab, phoneNumLab, emailLab, errData, errDataEdit;
    @FXML
    private TextField name, lastName, pesel, city, street, zipCode, idNumber, phoneNum, email, emailRepeated;
    @FXML
    private Slider loanAmount, loanMonths;
    @FXML
    private TextField workPlace, salary;
    @FXML
    private Label instolmentLab, loanAmLab, loanMoLab, errLoanReq;
    @FXML
    private Label errGetInvestment;
    @FXML
    private TextField amountInv, amountAfterCommaInv;
    @FXML
    private RadioButton investmentOp1, investmentOp2, investmentOp3, investmentOp4;
    @FXML
    private Label amountLH, instalmentLH, rateLH, dateLH, dateToLH, errGetLH;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label historyListTitle, errMoreHist, errGetInvestmentHistory;

    @FXML
    private AnchorPane currentPane;
    @FXML
    private AnchorPane greetingPane;
    @FXML
    private AnchorPane transferPane, endTransferPane, errTransHistryPane;
    @FXML
    private AnchorPane homePane;
    @FXML
    private AnchorPane changePasswordPane;
    @FXML
    private AnchorPane personaDataPane, personaDataEditPane, personaDataEndPane;
    @FXML
    private AnchorPane loanPane, loanReqPane, loanReqEndPane, loanHistoryPane;
    @FXML
    private AnchorPane investmentPane, getInvestmentPane, getInvestmentEndPane, investmentHistoryPane;


    public void setControllerClient(Client client){
        this.client = client;
        currentPane = greetingPane;
        observableList = FXCollections.observableArrayList();
        listView.setItems(observableList);
        viewLimit = 5;
    }

    @FXML
    public void handleLogOut() throws IOException
    {
        client.logOut();

        Parent homePageParent = FXMLLoader.load(getClass().getResource("LoginFX.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) home.getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    public void handleMainPane()
    {   currentPane.setVisible(false);
        currentPane = homePane;
        currentPane.setVisible(true);

        accNo.setText(client.accNo);
        balance.setText(client.balance);
    }

    @FXML
    public void handleBalanceRefresh() throws IOException
    {
        String errorCode;

        errorCode = client.getBalance();

        if(errorCode.equals("0"))
        {
            balance.setText(client.balance);
            errHomePage.setText("Odświeżno.");
        }
        else
            errHomePage.setText("Wystąpił problem z bazą danych, spróbuj ponowanie za chwile.");

    }

    @FXML
    public void handleTransferPane()
    {   currentPane.setVisible(false);
        currentPane = transferPane;
        currentPane.setVisible(true);

        accNoToTran.setText(client.accNo);
        resourcesTran.setText(client.balance);
        accNoToTr.setText("");
        amountTr.setText("");
        amountAfterCommaTr.setText("");
        transferTitle.setText("");
        errDataTransfer.setText("");
    }

    @FXML
    public void handleSendTransfer() throws IOException
    {
        String errorCode;

        errorCode = client.sendTransfer(accNoToTr.getText(), amountTr.getText(), amountAfterCommaTr.getText(), transferTitle.getText());

        if (errorCode.equals("0"))
        {
            currentPane.setVisible(false);
            currentPane = endTransferPane;
            currentPane.setVisible(true);

            errorCode = client.getBalance();

            if(!errorCode.equals("0"))
                infoTran.setText("W tej chwili nie można odświeżyć stanu konta.");
            else
                infoTran.setText("");

            accNoToTranEnd.setText(client.accNo);
            resourcesTranEnd.setText(client.balance);
            accNoToEnd.setText(accNoToTr.getText());
            amountEnd.setText(amountTr.getText() + "." + amountAfterCommaTr.getText());
            transferTitleEnd.setText(transferTitle.getText());
        }
        else if (errorCode.equals("1"))
            errDataTransfer.setText("Nie posiadasz wystarczających środków na koncie.");
        else if (errorCode.equals("2"))
            errDataTransfer.setText("Podane konto odbiorcy nie istnieje.");
        else if (errorCode.equals("3"))
            errDataTransfer.setText("Wystąpił problem z baza danych, spróboj ponownie za chwile.");
        else if (errorCode.equals("4"))
            errDataTransfer.setText("Wystąpił problem z serwerem, spróboj ponownie za chwile.");
        else if (errorCode.equals("5"))
            errDataTransfer.setText("Sprawdź wprowadzone dane:\n1.	Wszystkie pola są obowiązkowe.\n2.	Numer konta powinien miec 9 cyfr.\n3. 	Kwota może mieć tylko dwie liczby po przecinku.");
    }

    @FXML
    public void handleChangePasswordPane()
     {
         currentPane.setVisible(false);
         currentPane = changePasswordPane;
         currentPane.setVisible(true);

         errChangePass.setText("");
         doubleClick = 0;
     }

     @FXML
    public void handleChangePass() throws IOException
     {
         String errorCode;
        if(doubleClick == 1)
        {
            doubleClick = 0;
            errorCode = client.changePassword(newPassword.getText(), newPasswordRepeat.getText());

            if (errorCode.equals("0"))
                handleLogOut();
            else if (errorCode.equals("1"))
                errChangePass.setText("Wystąpił problem z baza danych, spróboj ponownie za chwile.");
            else if (errorCode.equals("2"))
                errChangePass.setText("Wprowadzone hasło nie spenia wymagan:\nHasło musi zawierac co najmniej jedna duża litere oraz cyfre.");
            else if (errorCode.equals("3"))
                errChangePass.setText("Nie poprawnie powtórzono hasło.");
        }
        else
        {
            errChangePass.setText("Kliknij jeszcze raz aby potwierdzić wybór.");
            doubleClick++;
        }

     }

     @FXML
    public void handleViewPersonaData()
    {
        currentPane.setVisible(false);
        currentPane = personaDataPane;
        currentPane.setVisible(true);

        client.getPersonalData();

        if(client.personalData == null)
        {
            nameLab.setText("");
            peselLab.setText("");
            cityLab.setText("");
            streetLab.setText("");
            zipCodeLab.setText("");
            idNumberLab.setText("");
            phoneNumLab.setText("");
            emailLab.setText("");
            errData.setText("Wystąpił problem z pobraniem danych spróbuj ponownie, za chwilę.");
        }
        else
        {
            nameLab.setText(client.personalData.firstName + " " + client.personalData.lastName);
            peselLab.setText(client.personalData.pesel);
            cityLab.setText(client.personalData.city);
            streetLab.setText(client.personalData.street);
            zipCodeLab.setText(client.personalData.zipCode);
            idNumberLab.setText(client.personalData.idNumber);
            phoneNumLab.setText(client.personalData.phoneNumber);
            emailLab.setText(client.personalData.email);
            emailRepeated.setText(client.personalData.email);
            errData.setText("");
        }
    }

    @FXML
    public void handleEditPersonaData()
    {
        if(client.personalData == null)
            errData.setText("Nie można edtować danych w tej chwili.");
        else
        {
            currentPane.setVisible(false);
            currentPane = personaDataEditPane;
            currentPane.setVisible(true);
            doubleClick = 0;

            name.setText(client.personalData.firstName);
            lastName.setText(client.personalData.lastName);
            pesel.setText(client.personalData.pesel);
            city.setText(client.personalData.city);
            street.setText(client.personalData.street);
            zipCode.setText(client.personalData.zipCode);
            idNumber.setText(client.personalData.idNumber);
            phoneNum.setText(client.personalData.phoneNumber);
            email.setText(client.personalData.email);
            errDataEdit.setText("");
        }
    }

    @FXML
    public void handleChangePersonaData()
    {
        String errorCode;

        if(doubleClick == 1)
        {
            errorCode = client.changePersonalData(name.getText(),
                    lastName.getText(),
                    pesel.getText(),
                    city.getText(),
                    street.getText(),
                    zipCode.getText(),
                    idNumber.getText(),
                    phoneNum.getText(),
                    email.getText(),
                    emailRepeated.getText());

            if (errorCode.equals("0"))
            {
                currentPane.setVisible(false);
                currentPane = personaDataEndPane;
                currentPane.setVisible(true);
            }
            else if (errorCode.equals("1"))
                errDataEdit.setText("Wniosek nie został przyjety. Wystąpił problem z bazą danych.");
            else if (errorCode.equals("2"))
                errDataEdit.setText("Wniosek o dodanie danych został już przyjęty i oczekuje na akceptacje.");
            else if (errorCode.equals("-2"))
                errDataEdit.setText("Nie poprawnie powtórzono adres e-mail.");
            else if (errorCode.equals("-3"))
                errDataEdit.setText("Sprawdz czy dane zostały wprowadzone poprawinie.");

            doubleClick = 0;
        }
        else
        {
            errDataEdit.setText("Kliknij jeszcze raz aby potwierdzić.");
            doubleClick++;
        }
    }

    @FXML
    public void handleLoanPane()
    {
        currentPane.setVisible(false);
        currentPane = loanPane;
        currentPane.setVisible(true);

        errGetLH.setText("");
    }

    @FXML
    public void handleLoanHistoryPane()
    {
        Loan loan;

        loan = client.getLoanHistory();

        if(loan == null)
            errGetLH.setText("Nie posiadasz żadnych pożyczek.");
        else
        {
            currentPane.setVisible(false);
            currentPane = loanHistoryPane;
            currentPane.setVisible(true);

            amountLH.setText(loan.amount);
            instalmentLH.setText(loan.instalment);
            rateLH.setText(loan.bankRate);
            dateLH.setText(loan.date);
            dateToLH.setText(loan.dateTo);
        }
    }

    @FXML
    public void handleLoanReqPane()
    {
        currentPane.setVisible(false);
        currentPane = loanReqPane;
        currentPane.setVisible(true);

        doubleClick = 0;
        errLoanReq.setText("");
        instolmentLab.setText("");
        workPlace.setText("");
        salary.setText("");

        calculateInstolment();
    }

    @FXML
    public void handleSendLoanReq()
    {
        String err;
        if(doubleClick == 1)
        {
            doubleClick = 0;
            err = client.sendReqLoan(loanAmLab.getText(), loanMoLab.getText(), workPlace.getText(), salary.getText(), instolmentLab.getText());
            errLoanReq.setText(err);

            if (err.equals("-2"))
                errLoanReq.setText("Dane nie zostały wprowadzone poprawnie:\n 1. Wszystkie pola są obowiązkowe\n 2. Średni dochód nie może zawierać przecinka ani kropki");
            else if (err.equals("-1"))
                errLoanReq.setText("Wystąpił problem podczas wysyłania wniosku, wyślij wniosek ponownie za chwilę.");
            else if (err.equals("0"))
            {
                currentPane.setVisible(false);
                currentPane = loanReqEndPane;
                currentPane.setVisible(true);
            }
            else if (err.equals("1"))
                errLoanReq.setText("Wystąpił problem z serwerem, wyślij wniosek ponownie za chwilę.");
            else if (err.equals("2"))
                errLoanReq.setText("Już masz zaciągniętą pożyczkę w naszym banku.");
        }
        else
        {
            doubleClick ++;
            errLoanReq.setText("Kliknij ponownie aby potwierdzić wybór.");
        }
    }

    @FXML
    public void calculateInstolment()
    {

        loanAmount.setValue((int)loanAmount.getValue() / 100 * 100);
        loanAmLab.setText(Integer.toString((int)loanAmount.getValue()));
        loanMoLab.setText(Integer.toString((int)loanMonths.getValue()));
        instolmentLab.setText(Integer.toString((int)(loanAmount.getValue()*1.05)/(int)loanMonths.getValue()));
    }

    @FXML
    public void handleInvestmentPane()
    {
        currentPane.setVisible(false);
        currentPane = investmentPane;
        currentPane.setVisible(true);

        errGetInvestmentHistory.setText("");
    }

    @FXML
    public void handleGetInvestmentPane()
    {
        currentPane.setVisible(false);
        currentPane = getInvestmentPane;
        currentPane.setVisible(true);

        errGetInvestment.setText("");
        amountInv.setText("");
        amountAfterCommaInv.setText("");
        doubleClick = 0;
    }

    @FXML
    public void sendReqInvestment()
    {
        String error;
        String time = "0";

        if(doubleClick == 1)
        {
            doubleClick = 0;

            if (investmentOp1.isSelected())
                time = "1";
            else if (investmentOp2.isSelected())
                time = "3";
            else if (investmentOp3.isSelected())
                time = "6";
            else if (investmentOp4.isSelected())
                time = "12";

            error = client.sendReqInvestment(amountInv.getText(), amountAfterCommaInv.getText(), time);

            if (error.equals("0"))
            {
                currentPane.setVisible(false);
                currentPane = getInvestmentEndPane;
                currentPane.setVisible(true);
            } else if (error.equals("1"))
                errGetInvestment.setText("Wystąpił problem z serwerem, wyślij wniosek ponownie za chwilę.");
            else if (error.equals("2"))
                errGetInvestment.setText("Nie masz wystarczających środków na koncie, wprowadź mniejszą kwote.");
            else if (error.equals("-1"))
                errGetInvestment.setText("Wystąpił problem z serwerem, wyślij wniosek ponownie za chwilę.");
            else if (error.equals("-2"))
                errGetInvestment.setText("Nie poprawnie wypełnione pola. Musi być wybrana jedna z opcji,\nkwota może zawierać tylko dwie liczby, pamiętaj że po przecinku\nmogą znaleźć się tylko dwie pozycje. ");
            else if (error.equals("-3"))
                errGetInvestment.setText("Wprowadzona kwota nie może przekraczać 10 000 zł i nie może\nbyć mniejsza niż 500 zł.");
        }
        else
        {
            doubleClick ++;
            errGetInvestment.setText("Kliknij ponownie aby potwierdzić wybór.");
        }
    }

    @FXML
    public void getInvestmentHistory()
    {
       listInvHist = client.getInvestmentHistory();
       histInv = 1;

       if(listTransHist == null)
           errGetInvestmentHistory.setText("Nie ma danych do historii.");
       else
       {
           currentPane.setVisible(false);
           currentPane = investmentHistoryPane;
           currentPane.setVisible(true);

           errMoreHist.setText("");
           historyListTitle.setText("Historia lokat");
           listSize =  listInvHist.size();

           if(listView.getItems().size() > 0)
            listView.getItems().remove(0,currentListEl % (viewLimit + 1) + 1);

           currentListEl = 0;
           loadInvestmentHistory();
       }
    }

    @FXML
    public void getTransferHistory()
    {
        listTransHist = client.getTranserHistory();
        histInv = 0;

        if (listTransHist == null)
        {
            currentPane.setVisible(false);
            currentPane = errTransHistryPane;
            currentPane.setVisible(true);
        }
        else
        {
            currentPane.setVisible(false);
            currentPane = investmentHistoryPane;
            currentPane.setVisible(true);

            errMoreHist.setText("");
            historyListTitle.setText("Historia operacji");
            listSize = listTransHist.size();

            if(listView.getItems().size() > 0)
                listView.getItems().remove(0,currentListEl % (viewLimit + 1) + 1);

            currentListEl = 0;
            loadTransferHistory();
        }
    }

    @FXML
    public void handleMoreHistory()
    {
        if(currentListEl < listSize)
        {
            listView.getItems().remove(0,currentListEl % (viewLimit + 1) + 1);
            errMoreHist.setText("");

            if(histInv == 1)
                loadInvestmentHistory();
            else if(histInv == 0)
                loadTransferHistory();
        }
        else
            errMoreHist.setText("To już wszystkie dane.");

    }

    @FXML
    public void handleLessHistory()
    {
        if(currentListEl - viewLimit > 0)
        {
            listView.getItems().remove(0, currentListEl % (viewLimit + 1) + 2);
            currentListEl = currentListEl -(currentListEl % (viewLimit + 1) + viewLimit + 1);
            errMoreHist.setText("");

            if(histInv == 1)
                loadInvestmentHistory();
            else if(histInv == 0)
                loadTransferHistory();
        }
        else
            errMoreHist.setText("Nie ma wcześniejszych danych.");

    }

    private void loadInvestmentHistory()
    {
        InvestmentHistory el;
        String status = "-1", space;

        listViewSize = 0;

        listView.getItems().add("\tKwota\t\tData założenia\t\tData zamknięcia\tOprocentowanie\tKwota końcowa\tStatus");

        while(listViewSize < viewLimit && currentListEl < listSize)
        {
            el = listInvHist.get(currentListEl);

            if(el.status.equals("1"))
                status = "Zamknięta";
            else if (el.status.equals("0"))
                status = "Otwarta";

            if(el.amount.length()<5)
                space = "\t\t\t";
            else
                space = "\t\t";

            listView.getItems().add("\t" + el.amount + space + el.dateFrom + "\t\t" + el.dateTo + "\t\t\t" + el.rate + "\t\t\t\t" + el.finalAmount + "\t\t" + status);
            currentListEl++;
            listViewSize++;
        }
    }

    private void loadTransferHistory()
    {
        Transfer el;
        String space;

        listViewSize = 0;

        listView.getItems().add("\tData\t\t\t\tNr konta odbiorcy\t\tNr konto nadawcy\t\tKwota\tTytuł\t");

        while(listViewSize < viewLimit && currentListEl < listSize)
        {
            el = listTransHist.get(currentListEl);

            if(el.amount.length()<5)
                space = "\t\t\t";
            else
                space = "\t\t";

            listView.getItems().add("\t" + el.date + "\t\t" + el.accNoTo + "\t\t\t" + el.accNoFrom + "\t\t\t" + el.amount + space + el.title);
            currentListEl++;
            listViewSize++;
        }
    }
}
