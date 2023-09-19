package lk.ijse.CarHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.CarHire.dao.custom.impl.CarCategoryDaoImpl;
import lk.ijse.CarHire.dto.CarCategoryDto;
import lk.ijse.CarHire.dto.CustomerDto;
import lk.ijse.CarHire.dto.tm.CarCategoryTm;
import lk.ijse.CarHire.entity.CarCategoryEntity;
import lk.ijse.CarHire.entity.CustomerEntity;
import lk.ijse.CarHire.service.ServiceFactory;
import lk.ijse.CarHire.service.custom.CarCategoryService;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarCategoryFormController {

    public TextField txtid;
    public TextField txtname;
    public TableView<CarCategoryTm> tblCarCategory;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colName;

    CarCategoryService carCategoryService = (CarCategoryService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.CAR_CATEGORY);

    public void initialize(){
        System.out.println("CarCategory Form Loaded!");
        setCellValueFactory();
        List<CarCategoryDto> carCategoryDtoList = loadAllCarCategories();
        setTableData(carCategoryDtoList);
    }

    private void setTableData(List<CarCategoryDto> carCategoryDtoList) {
        ObservableList<CarCategoryTm> observableList = FXCollections.observableArrayList();
        for (CarCategoryDto carCategoryDto : carCategoryDtoList){
            var tm = new CarCategoryTm();
            tm.setId(carCategoryDto.getId());
            tm.setName(carCategoryDto.getName());
            observableList.add(tm);
        }
        tblCarCategory.setItems(observableList);
    }

    private List<CarCategoryDto> loadAllCarCategories() {
        List<CarCategoryDto> carCategoryDtoList = new ArrayList<>();

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()){
            Query<CarCategoryEntity> query = session.createQuery("FROM CarCategoryEntity", CarCategoryEntity.class);
            List<CarCategoryEntity> carCategoryEntities = query.getResultList();
            for (CarCategoryEntity carCategoryEntity : carCategoryEntities){
                CarCategoryDto carCategoryDto = new CarCategoryDto();
                carCategoryDto.setId(carCategoryEntity.getId());
                carCategoryDto.setName(carCategoryEntity.getName());
                carCategoryDtoList.add(carCategoryDto);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return carCategoryDtoList;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            Integer id = Integer.valueOf(txtid.getText());
            String name = txtname.getText();

            CarCategoryDto carCategoryDto = new CarCategoryDto();
            carCategoryDto.setId(id);
            carCategoryDto.setName(name);
            carCategoryService.saveCarCategory(carCategoryDto);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "CarCategory Saved!!");
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
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            Integer id = Integer.valueOf(txtid.getText());
            String name = txtname.getText();

            CarCategoryDto carCategoryDto = new CarCategoryDto();
            carCategoryDto.setId(id);
            carCategoryDto.setName(name);
            carCategoryService.updateCarCategory(carCategoryDto);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "CarCategory Updated!!");
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
                }
            }
        });
    }

    private void Delete() throws IOException{
        Integer carcategoryId = Integer.valueOf(txtid.getText());
        CarCategoryDto carCategoryToDelete = new CarCategoryDto();
        carCategoryToDelete.setId(carcategoryId);
        carCategoryService.deleteCarCategory(carCategoryToDelete);
        initialize();
        clearFields();
    }


    public void txtidOnAction(ActionEvent actionEvent) {
        try {
            Integer cacategoryId = Integer.valueOf(txtid.getText());
            CarCategoryDaoImpl carCategoryDao = new CarCategoryDaoImpl();
            CarCategoryEntity carCategoryEntity = carCategoryDao.search(cacategoryId);
            if (carCategoryEntity!=null){
                txtname.setText(carCategoryEntity.getName());
            } else {
                clearFields();
                new Alert(Alert.AlertType.ERROR, "CarCategory Not Found!").show();
            }
        } catch (Exception e){

        }
    }
}