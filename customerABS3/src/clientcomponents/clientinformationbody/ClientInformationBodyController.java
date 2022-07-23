package clientcomponents.clientinformationbody;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import loginPage.loginPageController;
import model.Bank;
import model.Customer;
import model.classesForTables.LoanTableObj;
import model.classesForTables.TransactionTableObj;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;

public class ClientInformationBodyController {

    @FXML
    private TableView<LoanTableObj> loanerLoans;

    @FXML
    private TableView<LoanTableObj> lenderLoans;

    @FXML
    private TableView<TransactionTableObj> TransactionTable;


    @FXML
    private TableColumn<LoanTableObj, String> loaner_id;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_owner;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_category;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_amount;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_time;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_interest;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_rate;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_status;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_pending;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_active;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_risk;

    @FXML
    private TableColumn<LoanTableObj, String> loaner_finished;

    @FXML
    private TableColumn<LoanTableObj, String> lender_id;

    @FXML
    private TableColumn<LoanTableObj, String> lender_owner;

    @FXML
    private TableColumn<LoanTableObj, String> lender_category;

    @FXML
    private TableColumn<LoanTableObj, String> lender_amount;

    @FXML
    private TableColumn<LoanTableObj, String> lender_time;

    @FXML
    private TableColumn<LoanTableObj, String> lender_interest;

    @FXML
    private TableColumn<LoanTableObj, String> lender_rate;

    @FXML
    private TableColumn<LoanTableObj, String> lender_status;

    @FXML
    private TableColumn<LoanTableObj, String> lender_pending;

    @FXML
    private TableColumn<LoanTableObj, String> lender_active;

    @FXML
    private TableColumn<LoanTableObj, String> lender_risk;

    @FXML
    private TableColumn<LoanTableObj, String> lender_finished;

    @FXML
    private TableColumn<TransactionTableObj, String> time;

    @FXML
    private TableColumn<TransactionTableObj, String> sum;

    @FXML
    private TableColumn<TransactionTableObj, String> type;

    @FXML
    private TableColumn<TransactionTableObj, String> balanceBefore;

    @FXML
    private TableColumn<TransactionTableObj, String> balanceAfter;

    private loginPageController mainController;

    @FXML
    public void initialize() {
        //initialize loansTable
        loaner_id.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("id"));
        loaner_owner.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("owner"));
        loaner_category.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("category"));
        loaner_amount.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("amount"));
        loaner_time.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("time"));
        loaner_interest.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("interest"));
        loaner_rate.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("rate"));
        loaner_status.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("status"));
        loaner_pending.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("pending"));
        loaner_active.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("active"));
        loaner_risk.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("risk"));
        loaner_finished.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("finished"));

        lender_id.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("id"));
        lender_owner.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("owner"));
        lender_category.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("category"));
        lender_amount.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("amount"));
        lender_time.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("time"));
        lender_interest.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("interest"));
        lender_rate.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("rate"));
        lender_status.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("status"));
        lender_pending.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("pending"));
        lender_active.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("active"));
        lender_risk.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("risk"));
        lender_finished.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("finished"));

        time.setCellValueFactory(new PropertyValueFactory<TransactionTableObj, String>("time"));
        sum.setCellValueFactory(new PropertyValueFactory<TransactionTableObj, String>("sum"));
        type.setCellValueFactory(new PropertyValueFactory<TransactionTableObj, String>("type"));
        balanceBefore.setCellValueFactory(new PropertyValueFactory<TransactionTableObj, String>("balanceBefore"));
        balanceAfter.setCellValueFactory(new PropertyValueFactory<TransactionTableObj, String>("balanceAfter"));

    }



    private Customer getCurrentCustomer(){return mainController.getCurrentCustomer();}

    public void updateTables() {
        ObservableList<LoanTableObj> loanerLoansList;
        ObservableList<LoanTableObj> lenderLoansList;
        ObservableList<TransactionTableObj> transactionList;
        Customer currentCustomer = getCurrentCustomer();
        Bank businessLogic = mainController.getBusinessLogic();
        loanerLoansList= businessLogic.getLoans(currentCustomer.getLoansCustomerCreated());
        lenderLoansList = businessLogic.getLoans(businessLogic.getLoansCustomerInvestedIn(currentCustomer.getIdListOfLoansThatCustomerInvestedIn()));
        transactionList = currentCustomer.getCustomerAccount().getTransactions();
        loanerLoans.setItems(loanerLoansList);
        lenderLoans.setItems(lenderLoansList);
        TransactionTable.setItems(transactionList);

    }

    public void ChargeMoney(ActionEvent event){
        TextInputDialog chargeInput = new TextInputDialog();
        chargeInput.setTitle("Charge money");
        chargeInput.getDialogPane().setContentText("money to charge");
        chargeInput.showAndWait();
        String charge = chargeInput.getEditor().getText();

        String finalUrl = HttpUrl
                .parse(Constants.CHARGE_MONEY)
                .newBuilder()
                .addQueryParameter("customerName", mainController.getCustomerName())
                .addQueryParameter("sumToCharge", charge)
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                System.out.println("Failed to send request for today payment for loan");
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                System.out.println("Successfully paid for today");
                Platform.runLater(() -> {
                    updateTables();
                });
            }
        });

        //mainController.getBusinessLogic().putMoneyInAccount(Double.parseDouble(chargeInput.getEditor().getText()),mainController.getBusinessLogic().getCustomerIndex(mainController.getCurrentCustomer()));

    }

    public void withdrawMoney(ActionEvent event){
        TextInputDialog chargeInput = new TextInputDialog();
        chargeInput.setTitle("Charge money");
        chargeInput.getDialogPane().setContentText("money to charge");
        chargeInput.showAndWait();
        String charge = chargeInput.getEditor().getText();

        String finalUrl = HttpUrl
                .parse(Constants.WITHDRAW_MONEY)
                .newBuilder()
                .addQueryParameter("customerName", mainController.getCustomerName())
                .addQueryParameter("sumToCharge", charge)
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                System.out.println("Failed to send request for today payment for loan");
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                System.out.println("Successfully paid for today");
                Platform.runLater(() -> {
                    updateTables();
                });
            }
        });

  /*      TextInputDialog chargeInput = new TextInputDialog();
        chargeInput.setTitle("Charge money");
        chargeInput.getDialogPane().setContentText("money to charge");
        chargeInput.showAndWait();
        mainController.getBusinessLogic().takeMoneyFromAccount(Double.parseDouble(chargeInput.getEditor().getText()),mainController.getBusinessLogic().getCustomerIndex(mainController.getCurrentCustomer()));
        updateTables();*/
    }

    public void setBodyToScramble(){
        mainController.getRoot().setCenter(mainController.getClientScrambleComponent());
        mainController.getClientPaymentComponentController().updateTables();
    }


    public void setBodyToPayment(){
        mainController.getRoot().setCenter(mainController.getClientPaymentComponent());
        mainController.getClientPaymentComponentController().updateTables();
    }

    public void setMainController(loginPageController mainController) {this.mainController = mainController;}
}
