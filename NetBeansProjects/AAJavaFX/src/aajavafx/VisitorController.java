/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package aajavafx;

import aajavafx.entities.Company;
import entitiesproperty.VisitorsProperty;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import aajavafx.entities.Visitors;
import com.google.gson.Gson;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Rolandas
 */
public class VisitorController implements Initializable {
    
    @FXML
    private TextField visitorIDField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private ComboBox companyBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TableView<VisitorsProperty> tableVisitors;
    @FXML
    private TableColumn<VisitorsProperty, String> visitorIDColumn;
    @FXML
    private TableColumn<VisitorsProperty, String> firstNameColumn;
    @FXML
    private TableColumn<VisitorsProperty, String> lastNameColumn;
    @FXML
    private TableColumn<VisitorsProperty, String> emailColumn;
    @FXML
    private TableColumn<VisitorsProperty, String> phoneColumn;
    @FXML
    private TableColumn<VisitorsProperty, Integer> companyIDColumn;
    
    private Desktop desktop = Desktop.getDesktop();
    
    //this will store the list of companies for use in the combo box
    ArrayList<Company> companies = new ArrayList<>();
    
    //we can store the root directory of visitors and company here, where most methods are based
    private static String VisitorsRootURL = "http://localhost:8080/MainServerREST/api/visitors/";
    private static String CompanyRootURL = "http://localhost:8080/MainServerREST/api/company";
    private static String postHTML = "http://localhost:8080/MainServerREST/api/visitorschedule/upload";
    
    File imageFile;
    ErrorHandling eH = new ErrorHandling();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //instead of invisible, make the text fields locked for editing at first
        visitorIDField.setEditable(false);
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        emailField.setEditable(false);
        phoneNumberField.setEditable(false);
        companyBox.setDisable(true);
        
        visitorIDColumn.setCellValueFactory(cellData -> cellData.getValue().visIdProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().visFirstnameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().visLastnameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().visEmailProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().visPhoneProperty());
        companyIDColumn.setCellValueFactory(cellData -> cellData.getValue().companyCompIdProperty().asObject());
        
        try{
            //Populate table
            getCompanyList();
            tableVisitors.setItems(getVisitors());
            ObservableList<String> companyList = FXCollections.observableArrayList();
            for(Company c: companies) {
                companyList.add(c.getCompName());
            }
            companyBox.setItems(companyList);
            companyBox.getItems().add("Add company");
        } catch (IOException ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableVisitors.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                visitorIDField.setText(""+newSelection.visIdProperty().getValue());
                firstNameField.setText(newSelection.getVisFirstname());
                lastNameField.setText(newSelection.getVisLastname());
                emailField.setText(newSelection.getVisEmail());
                phoneNumberField.setText(newSelection.getVisPhone());
                companyBox.setValue(getCompanyNameFromID(newSelection.getCompanyCompId()));
            }
        });
    }
    
    @FXML
    private void handleNewVisitor(ActionEvent event) {
        visitorIDField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneNumberField.clear();
        visitorIDField.setEditable(true);
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        emailField.setEditable(true);
        phoneNumberField.setEditable(true);
        companyBox.setDisable(false);
    }
    
    @FXML
    private void handleRemoveVisitor(ActionEvent event) {
        //remove is annotated with @DELETE on server so we use a HttpDelete object
        try {
            String idToDelete = visitorIDField.getText();
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN","password");
            provider.setCredentials(AuthScope.ANY, credentials);
            HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
            //add the id to the end of the URL so this will call the method at MainServerREST/api/visitors/id
            HttpDelete delete = new HttpDelete(VisitorsRootURL + idToDelete);
            HttpResponse response = httpClient.execute(delete);
            System.out.println("response from server " + response.getStatusLine());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            //populate table
            tableVisitors.setItems(getVisitors());
        } catch (IOException ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleEditVisitor(ActionEvent event) {
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        emailField.setEditable(true);
        phoneNumberField.setEditable(true);
        companyBox.setDisable(false);
    }
    
    @FXML
    private void handleSave(ActionEvent event) {
        boolean isValid = false;
        // Check if email is in right format
        try {
            InternetAddress internetaddress = new InternetAddress(emailField.getText());
            internetaddress.validate();
            isValid = true;
            System.out.println("Email is valid format");
        } catch(AddressException e) {
            System.out.println("Email is invalid - " + emailField.getText());
        }
        if (isValid == true) {
            if(imageFile != null) {
                String visitorId = visitorIDField.getText();
                visitorIDField.clear();
                String firstName = firstNameField.getText();
                firstNameField.clear();
                String lastName = lastNameField.getText();
                lastNameField.clear();
                String email = emailField.getText();
                emailField.clear();
                String phoneNumber = phoneNumberField.getText();
                phoneNumberField.clear();
                Integer companyId = getCompanyIDFromName(companyBox.getValue().toString());
                
                try {
                    Gson gson = new Gson();
                    CredentialsProvider provider = new BasicCredentialsProvider();
                    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
                    provider.setCredentials(AuthScope.ANY, credentials);
                    HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
                    HttpEntityEnclosingRequestBase HttpEntity = null; //this is the superclass for post, put, get, etc
                    if(visitorIDField.isEditable()) { //then we are posting a new record
                        HttpEntity = new HttpPost(VisitorsRootURL); //so make a http post object
                    } else { //we are editing a record
                        HttpEntity = new HttpPut(VisitorsRootURL); //so make a http put object
                    }
                    Company company = getCompany(companyId);
                    Visitors visitor = new Visitors(visitorId, firstName, lastName, email, phoneNumber, company);
                    
                    String jsonString = new String(gson.toJson(visitor));
                    System.out.println("json string: " + jsonString);
                    StringEntity postString = new StringEntity(jsonString);
                    
                    HttpEntity.setEntity(postString);
                    HttpEntity.setHeader("Content-type", "application/json");
                    HttpResponse response = httpClient.execute(HttpEntity);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if(statusCode == 204) {
                        System.out.println("New visitor posted successfully");
                    } else{
                        System.out.println("Server error: " + response.getStatusLine());
                    }
                    visitorIDField.setEditable(false);
                    firstNameField.setEditable(false);
                    lastNameField.setEditable(false);
                    emailField.setEditable(false);
                    phoneNumberField.setEditable(false);
                    companyBox.setEditable(false);
                    visitorIDField.clear();
                    firstNameField.clear();
                    lastNameField.clear();
                    emailField.clear();
                    tableVisitors.setItems(getVisitors());
                } catch (UnsupportedEncodingException ex) {
                    System.out.println(ex);
                } catch (IOException e) {
                    System.out.println(e);
                } catch (JSONException je) {
                    System.out.println("JSON error: "+je);
                }
                
                FileInputStream fis = null;
                try {
                    
                    fis = new FileInputStream(imageFile);
                    CredentialsProvider provider = new BasicCredentialsProvider();
                    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
                    provider.setCredentials(AuthScope.ANY, credentials);
                    HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
                    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                    FileBody fileBody = new FileBody(new File(imageFile.getName())); //image should be a String
                    builder.addPart("file", new InputStreamBody(fis, imageFile.getName()));
                    //builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    
                    // server back-end URL
                    HttpPost httppost = new HttpPost(postHTML);
                    //MultipartEntity entity = new MultipartEntity();
                    // set the file input stream and file name as arguments
                    //entity.addPart("file", new InputStreamBody(fis, inFile.getName()));
                    HttpEntity entity = builder.build();
                    httppost.setEntity(entity);
                    // execute the request
                    HttpResponse response = httpClient.execute(httppost);
                    
                    int statusCode = response.getStatusLine().getStatusCode();
                    HttpEntity responseEntity = response.getEntity();
                    String responseString = EntityUtils.toString(responseEntity, "UTF-8");
                    
                    System.out.println("[" + statusCode + "] " + responseString);
                    
                } catch (ClientProtocolException e) {
                    System.err.println("Unable to make connection");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.err.println("Unable to read file");
                    e.printStackTrace();
                } finally {
                    try {
                        if (fis != null) fis.close();
                    } catch (IOException e) {}
                }
            } else {
                eH.popUpMessage("Record not saved", "Please attach a photo for this visitor");
            }
            
        } else {
            eH.popUpMessage("Invalid email address", "Please enter valid email address");
        }
    }
    
    @FXML
    private void handleGoBack(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 879, 599);
            stage.setScene(scene);
            
            stage.setTitle("Main menu");
            stage.show();
            
        } catch (Exception ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleImageButton(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        fileChooser.setTitle("Open Resource File");
        imageFile = fileChooser.showOpenDialog(stage);
        if(imageFile != null) {
            try {
                System.out.println("abs path: " + imageFile.getAbsolutePath());
                System.out.println("can oath: " + imageFile.getCanonicalPath());
                FileInputStream fis = new FileInputStream(imageFile.getCanonicalPath());
                Image image = new Image(fis);
                imageView.setImage(image);
            }
            catch(IOException ie) {
                System.out.println("IO Error: "+ie);
            }
        }
        /*if(file != null) {
        openFile(file);
        
        }*/
    }
    
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    VisitorController.class.getName()).log(
                            Level.SEVERE, null, ex
                    );
        }
    }
    
    private static void configureFileChooser(final FileChooser fileChooser){
        fileChooser.setTitle("Select Visitor Photo");
        fileChooser.setInitialDirectory(
                new File("src/resources")
        );
    }
    
    public ObservableList<VisitorsProperty> getVisitors() throws IOException, JSONException {
        Visitors myVisitors = new Visitors();
        Gson gson = new Gson();
        ObservableList<VisitorsProperty> visitorsProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();
        
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet(VisitorsRootURL);
        
        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);
        
        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            myVisitors = gson.fromJson(jo.toString(), Visitors.class);
            System.out.println(myVisitors.getVisId());
            visitorsProperty.add(new VisitorsProperty(myVisitors.getVisId(), myVisitors.getVisFirstname(),
                    myVisitors.getVisLastname(), myVisitors.getVisEmail(), myVisitors.getVisPhone(),
                    myVisitors.getCompanyCompId().getCompId()));
        }
        return visitorsProperty;
    }
    
    //download the list of available companies from the server so we know what we can select from
    public void getCompanyList() throws IOException, JSONException {
        Gson gson = new Gson();
        JSONObject jo = new JSONObject();
        
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet(CompanyRootURL);
        
        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);
        
        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            Company company = gson.fromJson(jo.toString(), Company.class);
            companies.add(company);
            System.out.println(company.getCompId());
        }
    }
    
    public String getCompanyNameFromID(int ID) {
        String s = "No company";
        for(Company c: companies) {
            if(c.getCompId() == ID) {
                return c.getCompName();
            }
        }
        return s;
    }
    
    public Integer getCompanyIDFromName(String compName) {
        Integer i = 1;
        for(Company c: companies) {
            if(c.getCompName().equalsIgnoreCase(compName)) {
                return c.getCompId();
            }
        }
        return i;
    }
    
    public Company getCompany(Integer companyID) {
        Company company = null;
        for(Company c: companies) {
            if(c.getCompId() == companyID) {
                return c;
            }
        }
        return company;
    }
}
