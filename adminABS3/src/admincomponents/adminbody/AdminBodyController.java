package admincomponents.adminbody;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import loginPage.loginPageController;
import model.Bank;
import model.classesForTables.CustomerTableObj;
import model.classesForTables.LoanTableObj;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AdminBodyController {

    @FXML
    private TableView<LoanTableObj> loansTable;

    @FXML
    private TableColumn<LoanTableObj, String> id;

    @FXML
    private TableColumn<LoanTableObj, String> owner;

    @FXML
    private TableColumn<LoanTableObj, String> category;

    @FXML
    private TableColumn<LoanTableObj, String> amount;

    @FXML
    private TableColumn<LoanTableObj, String> time;

    @FXML
    private TableColumn<LoanTableObj, String> interest;

    @FXML
    private TableColumn<LoanTableObj, String> rate;

    @FXML
    private TableColumn<LoanTableObj, String> status;

    @FXML
    private TableColumn<LoanTableObj, String> pending;

    @FXML
    private TableColumn<LoanTableObj, String> active;

    @FXML
    private TableColumn<LoanTableObj, String> risk;

    @FXML
    private TableColumn<LoanTableObj, String> finished;

    @FXML
    private TableView<CustomerTableObj> customersInformationTable;

    @FXML
    private TableColumn<CustomerTableObj, String> customerName;

    @FXML
    private TableColumn<CustomerTableObj, String> customerBalance;

    @FXML
    private TableColumn<CustomerTableObj, String> loansWeInvestedIn;

    @FXML
    private TableColumn<CustomerTableObj, String> loansWeCreated;

    ObservableList<CustomerTableObj> customersInformation;
    ObservableList<LoanTableObj> loansInformation;

    private loginPageController mainController;
    private Bank businessLogic;
    private Stage primaryStage;
    private ObservableList<String> namesToAddToComboBox;

    @FXML
    public void initialize() {
        //initialize customersInformationTable
        customerName.setCellValueFactory(new PropertyValueFactory<CustomerTableObj, String>("customerName"));
        customerBalance.setCellValueFactory(new PropertyValueFactory<CustomerTableObj, String>("customerBalance"));
        loansWeInvestedIn.setCellValueFactory(new PropertyValueFactory<CustomerTableObj, String>("loansWeInvestedIn"));
        loansWeCreated.setCellValueFactory(new PropertyValueFactory<CustomerTableObj, String>("loansWeCreated"));
        customersInformation = null;
        //initialize loansTable
        id.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("id"));
        owner.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("owner"));
        category.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("category"));
        amount.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("amount"));
        time.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("time"));
        interest.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("interest"));
        rate.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("rate"));
        status.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("status"));
        pending.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("pending"));
        active.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("active"));
        risk.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("risk"));
        finished.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("finished"));
        loansInformation = null;
    }

    public void updateLoansInformationForTable() {
        if (loansInformation != null) {
            loansInformation.clear();
        }
        loansInformation = businessLogic.getLoansInformationForTable();
        loansTable.setItems(loansInformation);
    }

    public void updateCustomerInformationForTable() {
        if (customersInformation != null) {
            customersInformation.clear();
        }
        customersInformation = businessLogic.getCustomersInformationForTable();
        customersInformationTable.setItems(customersInformation);
    }

    public void setMainController(loginPageController mainController) {
        this.mainController = mainController;
    }

    public void setBusinessLogic(Bank businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void increaseYazButtonPressed(ActionEvent event) {
        String finalUrl = HttpUrl
                .parse(Constants.RAISE_TIME_LINE)
                .newBuilder()
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                System.out.println("Failed to raise time line");
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                System.out.println("Successfully raised the time line.");
            }
        });

        //updateAdminTables(); it happens every two seconds either way
    }
    public void updateAdminTables() {
        updateCustomerInformationForTable();//update the customer table in the admin view
        updateLoansInformationForTable(); // update loans table in the admin view
    }
/*
    @FXML
    void loadFileButtonPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml data file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        } else {
            String absolutePath = selectedFile.getAbsolutePath();
            String validFileRead = businessLogic.loadSystemDetailsFromFile(absolutePath);
            if (validFileRead.equals("The file and data were loaded successfully")) {
                //update customers table
                updateCustomerInformationForTable();//update the customer table in the admin view
                //update file path label
                mainController.changeFileText(absolutePath); //change file path in the header
                //update combo box
                namesToAddToComboBox = businessLogic.getNamesOfCustomers(); //get names of customers
                namesToAddToComboBox.add("Admin");//adding admin as one of the combo box options
                mainController.updateUserModeComboBox(namesToAddToComboBox);//informing the main controller so he can inform the header controller
                //update loans table
                updateLoansInformationForTable();
            } else {
                mainController.changeFileText(validFileRead);
            }


        }


    }
*/
}
