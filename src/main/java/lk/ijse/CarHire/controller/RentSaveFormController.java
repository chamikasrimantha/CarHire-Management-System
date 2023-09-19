package lk.ijse.CarHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.CarHire.dao.custom.impl.CarDaoImpl;
import lk.ijse.CarHire.dao.custom.impl.CustomerDaoImpl;
import lk.ijse.CarHire.dto.CarCategoryDto;
import lk.ijse.CarHire.dto.CarDto;
import lk.ijse.CarHire.dto.CustomerDto;
import lk.ijse.CarHire.dto.RentDto;
import lk.ijse.CarHire.entity.CarEntity;
import lk.ijse.CarHire.entity.CustomerEntity;
import lk.ijse.CarHire.service.ServiceFactory;
import lk.ijse.CarHire.service.custom.CarService;
import lk.ijse.CarHire.service.custom.CustomerService;
import lk.ijse.CarHire.service.custom.RentService;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class RentSaveFormController {

    public TextField txtid;
    public ComboBox<String> cmbCarID;
    public ComboBox<String> cmbCustId;
    public DatePicker dateFrom;
    public DatePicker dateTo;
    public ComboBox<String> cmbIsReturn;
    public TextField txtTotal;
    public TextField txtCustName;
    public TextField txtCarPerDayRent;
    public AnchorPane rootNode;
    public TextField txtCarBrand;
    public TextField txtCustAddress;
    public TextField txtCustMobileNo;
    public TextField txtCarVehicleNo;

    RentService rentService = (RentService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.RENT);
    CustomerService<CustomerDto> customerService = (CustomerService<CustomerDto>) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CUSTOMER);
    CarService<CarDto> carService = (CarService<CarDto>) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CAR);


    public void initialize(){
        System.out.println("Rent Save Form Loaded!");
        cmbIsReturn.getItems().addAll(
                "Yes",
                "No"
        );
        setCustomerIds();
        setCarIds();
    }

    private void setCustomerIds() {
        List<CustomerDto> allCustomers = customerService.getAllCustomers();
        try {
            ObservableList<String> customerIdObservableList = FXCollections.observableArrayList();
            allCustomers.forEach(customerDto -> customerIdObservableList.add(String.valueOf(customerDto.getId())));
            cmbCustId.setItems(customerIdObservableList);
        } catch (Exception e){

        }
    }

    private void setCarIds() {
        List<CarDto> allCars = carService.getAllCars();
        try{
            ObservableList<String> carIdObservableList = FXCollections.observableArrayList();
            allCars.forEach(carDto -> carIdObservableList.add(String.valueOf(carDto.getId())));
            cmbCarID.setItems(carIdObservableList);
        } catch (Exception e){

        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            RentDto rentDto = new RentDto();
            rentDto.setId(Integer.valueOf(txtid.getText()));
            rentDto.setFromDate(Date.valueOf(dateFrom.getValue()));
            rentDto.setToDate(Date.valueOf(dateTo.getValue()));
            rentDto.setPerDayRent(Double.valueOf(txtCarPerDayRent.getText()));
            rentDto.setIsReturn(cmbIsReturn.getValue());
            rentDto.setTotal(Double.valueOf(txtTotal.getText()));

            CarDto carDto = carService.searchCar(cmbCarID.getValue());
            rentDto.setCarDto(carDto);

            CustomerDto customerDto = customerService.searchCustomer(cmbCustId.getValue());
            rentDto.setCustomerDto(customerDto);

            rentService.saveRent(rentDto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Rent Saved!");
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
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtid.setText("");
        cmbCustId.setValue("");
        txtCustName.setText("");
        txtCustAddress.setText("");
        txtCustMobileNo.setText("");
        cmbCarID.setValue("");
        txtCarPerDayRent.setText("");
        txtCarBrand.setText("");
        txtCarVehicleNo.setText("");
        cmbIsReturn.setValue("");
        txtTotal.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/view/rent_form.fxml"));
        this.rootNode.getChildren().clear();
        this.rootNode.getChildren().add(root1);
    }

    public void cmbCarIdOnAction(ActionEvent actionEvent) {
        try{
            String carId = cmbCarID.getValue();
            CarDaoImpl carDao = new CarDaoImpl();
            CarEntity carEntity = carDao.search(Integer.valueOf(carId));
            if (carEntity!=null){
                txtCarBrand.setText(carEntity.getBrand());
                txtCarVehicleNo.setText(carEntity.getVehicleNo());
                txtCarPerDayRent.setText(String.valueOf(carEntity.getPricePerDay()));
            } else {
                clearFields();
                new Alert(Alert.AlertType.ERROR, "Car Not Found!").show();
            }
        } catch (Exception e){

        }
    }

    public void cmbCustIdOnAction(ActionEvent actionEvent) {
        try {
            String custId = cmbCustId.getValue();
            CustomerDaoImpl customerDao = new CustomerDaoImpl();
            CustomerEntity customerEntity = customerDao.search(Integer.valueOf(custId));
            if (customerEntity!=null){
                txtCustName.setText(customerEntity.getName());
                txtCustAddress.setText(customerEntity.getAddress());
                txtCustMobileNo.setText(String.valueOf(customerEntity.getMobileno()));
            } else {
                clearFields();
                new Alert(Alert.AlertType.ERROR, "Customer Not Found!").show();
            }
        } catch (Exception e){

        }
    }
}