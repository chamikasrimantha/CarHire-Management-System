package lk.ijse.CarHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.CarHire.service.ServiceFactory;
import lk.ijse.CarHire.service.custom.CustomerService;
import javafx.stage.Stage;
import lk.ijse.CarHire.dao.custom.impl.CustomerDaoImpl;
import lk.ijse.CarHire.dto.CustomerDto;
import lk.ijse.CarHire.dto.tm.CustomerTm;
import lk.ijse.CarHire.entity.CustomerEntity;
import lk.ijse.CarHire.service.custom.CustomerService;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFormController {
    public TextField txtid;
    public TextField txtname;
    public TextField txtaddress;
    public TextField txtmobileno;
    public TextField txtnic;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colName;
    public TableColumn<?, ?> colAddress;
    public TableColumn<?, ?> colMobileNo;
    public TableColumn<?, ?> colNic;
    CustomerService customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CUSTOMER);

    public void initialize() throws Exception {
        System.out.println("Customer Form Loaded!");
        setCellValueFactory();
        List<CustomerDto> customerDtoList = loadAllCustomers();
        setTableData(customerDtoList);
    }

    public List<CustomerDto> loadAllCustomers() throws Exception{
        List<CustomerDto> customerDtoList = new ArrayList<>();

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            // Use HQL (Hibernate Query Language) to retrieve all customer entities
            Query<CustomerEntity> query = session.createQuery("FROM CustomerEntity", CustomerEntity.class);
            List<CustomerEntity> customerEntities = query.getResultList();

            // Convert CustomerEntity objects to CustomerDto objects
            for (CustomerEntity customerEntity : customerEntities) {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setId(customerEntity.getId());
                customerDto.setName(customerEntity.getName());
                customerDto.setAddress(customerEntity.getAddress());
                customerDto.setNic(customerEntity.getNic());
                customerDto.setMobileno(customerEntity.getMobileno());
                customerDtoList.add(customerDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions or log errors as needed
        }

        return customerDtoList;
    }

    private void setTableData(List<CustomerDto> customerDtoList) {
        ObservableList<CustomerTm> observableList = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDtoList) {
            var tm = new CustomerTm();
            tm.setId(customerDto.getId());
            tm.setName(customerDto.getName());
            tm.setAddress(customerDto.getAddress());
            tm.setMobileno(customerDto.getMobileno());
            tm.setNic(customerDto.getNic());

            observableList.add(tm);
        }

        tblCustomer.setItems(observableList);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("mobileno"));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            Integer id = Integer.valueOf(txtid.getText());
            String name = txtname.getText();
            String address = txtaddress.getText();
            Long nic = Long.valueOf(txtnic.getText());
            Integer mobileno = Integer.valueOf(txtmobileno.getText());

            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(id);
            customerDto.setName(name);
            customerDto.setAddress(address);
            customerDto.setMobileno(mobileno);
            customerDto.setNic(nic);

            customerService.saveCustomer(customerDto);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer Saved!!");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        clearFields();
                        initialize();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtid.setText("");
        txtname.setText("");
        txtnic.setText("");
        txtaddress.setText("");
        txtmobileno.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            Integer id = Integer.valueOf(txtid.getText());
            String name = txtname.getText();
            String address = txtaddress.getText();
            Long nic = Long.valueOf(txtnic.getText());
            Integer mobileno = Integer.valueOf(txtmobileno.getText());

            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(id);
            customerDto.setName(name);
            customerDto.setAddress(address);
            customerDto.setMobileno(mobileno);
            customerDto.setNic(nic);
            customerService.updateCustomer(customerDto);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer Updated!!");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        clearFields();
                        initialize();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you really want to delete?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Delete();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void Delete() throws Exception {
        Integer customerId = Integer.valueOf(txtid.getText());
        CustomerDto customerToDelete = new CustomerDto();
        customerToDelete.setId(customerId);
        customerService.deleteCustomer(customerToDelete);
        initialize();
        clearFields();
    }

    public void txtidOnAction(ActionEvent actionEvent) {
        try {
            Integer customerId = Integer.valueOf(txtid.getText());
            CustomerDaoImpl customerDao = new CustomerDaoImpl();
            CustomerEntity customerEntity = customerDao.search(customerId);

            if (customerEntity!=null){
                txtname.setText(customerEntity.getName());
                txtaddress.setText(customerEntity.getAddress());
                txtmobileno.setText(String.valueOf(customerEntity.getMobileno()));
                txtnic.setText(String.valueOf(customerEntity.getNic()));
            } else {
                clearFields();
                new Alert(Alert.AlertType.ERROR, "Customer Not Found!").show();
            }
        } catch (Exception e) {

        }
    }

}