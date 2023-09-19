package lk.ijse.CarHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.CarHire.dao.custom.impl.CarDaoImpl;
import lk.ijse.CarHire.dto.CarCategoryDto;
import lk.ijse.CarHire.dto.CarDto;
import lk.ijse.CarHire.dto.tm.CarTm;
import lk.ijse.CarHire.entity.CarEntity;
import lk.ijse.CarHire.service.ServiceFactory;
import lk.ijse.CarHire.service.custom.CarCategoryService;
import lk.ijse.CarHire.service.custom.CarService;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarFormController {

    public TextField txtid;
    public TextField txtbrand;
    public TextField txtmodel;
    public TextField txtpriceperday;
    public TextField txtvehicleno;
    public TextField txtyear;
    public TableView<CarTm> tblCar;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colBrand;
    public TableColumn<?, ?> colModel;
    public TableColumn<?, ?> colVehicleNo;
    public TableColumn<?, ?> colYear;
    public TableColumn<?, ?> colPricePerDay;
    public ComboBox<String> cmbCarCategoryId;

    CarService carService = (CarService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CAR);
    CarCategoryService<CarCategoryDto> carCategoryService = (CarCategoryService<CarCategoryDto>) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CAR_CATEGORY);

    public void initialize(){
        System.out.println("Car Form Loaded!");
        setCellValueFactory();
        List<CarDto> carDtoList = loadAllCars();
        setTableData(carDtoList);
        setCarCategoryIds();
    }

    private void setCarCategoryIds() {
        List<CarCategoryDto> allCarCategories = carCategoryService.getAllCarCategories();
        try {
            ObservableList<String> carCategoryIdObservableList = FXCollections.observableArrayList();
            allCarCategories.forEach(carCategoryDto -> carCategoryIdObservableList.add(String.valueOf(carCategoryDto.getId())));
            cmbCarCategoryId.setItems(carCategoryIdObservableList);
        } catch (Exception e){

        }
    }

    private void setTableData(List<CarDto> carDtoList) {
        ObservableList<CarTm> observableList = FXCollections.observableArrayList();
        for (CarDto carDto : carDtoList){
            var tm = new CarTm();
            tm.setId(carDto.getId());
            tm.setBrand(carDto.getBrand());
            tm.setModel(carDto.getModel());
            tm.setYear(carDto.getYear());
            tm.setVehicleNo(carDto.getVehicleNo());
            tm.setPricePerDay(carDto.getPricePerDay());
            observableList.add(tm);
        }
        tblCar.setItems(observableList);
    }

    private List<CarDto> loadAllCars() {
        List<CarDto> carDtoList = new ArrayList<>();

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()){
            Query<CarEntity> query = session.createQuery("FROM CarEntity", CarEntity.class);
            List<CarEntity> carEntities = query.getResultList();
            for (CarEntity carEntity : carEntities){
                CarDto carDto = new CarDto();
                carDto.setId(carEntity.getId());
                carDto.setBrand(carEntity.getBrand());
                carDto.setModel(carEntity.getModel());
                carDto.setYear(carEntity.getYear());
                carDto.setVehicleNo(carEntity.getVehicleNo());
                carDto.setPricePerDay(carEntity.getPricePerDay());
                carDtoList.add(carDto);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return carDtoList;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        colPricePerDay.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            CarDto carDto = new CarDto();
            carDto.setId(Integer.valueOf(txtid.getText()));
            carDto.setBrand(txtbrand.getText());
            carDto.setModel(txtmodel.getText());
            carDto.setYear(txtyear.getText());
            carDto.setVehicleNo(txtvehicleno.getText());
            carDto.setPricePerDay(Double.valueOf(txtpriceperday.getText()));

            CarCategoryDto carCategoryDto = carCategoryService.searchCarCategory(cmbCarCategoryId.getValue());
            carDto.setCarCategoryDto(carCategoryDto);

            carService.saveCar(carDto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Car Saved!");
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
        txtbrand.setText("");
        txtmodel.setText("");
        txtvehicleno.setText("");
        txtyear.setText("");
        txtpriceperday.setText("");
        cmbCarCategoryId.setValue("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            CarDto carDto = new CarDto();
            carDto.setId(Integer.valueOf(txtid.getText()));
            carDto.setBrand(txtbrand.getText());
            carDto.setModel(txtmodel.getText());
            carDto.setYear(txtyear.getText());
            carDto.setVehicleNo(txtvehicleno.getText());
            carDto.setPricePerDay(Double.valueOf(txtpriceperday.getText()));

            CarCategoryDto carCategoryDto = carCategoryService.searchCarCategory(cmbCarCategoryId.getValue());
            carDto.setCarCategoryDto(carCategoryDto);

            carService.updateCar(carDto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Car Updated!");
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

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you really want to delete?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Delete();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void Delete() throws IOException{
        Integer carId = Integer.valueOf(txtid.getText());
        CarDto carToDelete = new CarDto();
        carToDelete.setId(carId);
        carService.deleteCar(carToDelete);
        initialize();
        clearFields();
    }

    public void txtidOnAction(ActionEvent actionEvent) {
        try {
            Integer carId = Integer.valueOf(txtid.getText());
            CarDaoImpl carDao = new CarDaoImpl();
            CarEntity carEntity = carDao.search(carId);
            if (carEntity!=null){
                txtbrand.setText(carEntity.getBrand());
                txtmodel.setText(carEntity.getModel());
                txtyear.setText(carEntity.getYear());
                txtvehicleno.setText(carEntity.getVehicleNo());
                txtpriceperday.setText(String.valueOf(carEntity.getPricePerDay()));
                cmbCarCategoryId.setValue(String.valueOf(carEntity.getCarCategoryEntity().getId()));
            } else {
                clearFields();
                new Alert(Alert.AlertType.ERROR, "CarCategory Not Found!").show();
            }
        } catch (Exception e){

        }
    }
}