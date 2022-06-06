package AliAbuElhija_AlaaBadra.main;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import AliAbuElhija_AlaaBadra.controller.CompanyController;
import AliAbuElhija_AlaaBadra.model.Company;
import AliAbuElhija_AlaaBadra.view.CompanyJavaFX;

public class CompanyMain extends Application {

    @Override
    public void start(Stage stage) {
        Company model = createModel();
        CompanyJavaFX view = new CompanyJavaFX(stage);
        CompanyController controller = new CompanyController(model, view);
    }

    private Company createModel() {
        Company company = readCompanyFromFile();
       if(company == null) {
            System.out.println("Creating new data.");
            return new Company();
        }
        return company;
    }

    private Company readCompanyFromFile() {
        Company company = null;
        String fileName = "Company.data";
        System.out.println("Trying to load data from file " + fileName);
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            company = (Company) in.readObject();
            in.close();
            System.out.println("Data loaded.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not find file: " + fileName);
        }
        return company;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
