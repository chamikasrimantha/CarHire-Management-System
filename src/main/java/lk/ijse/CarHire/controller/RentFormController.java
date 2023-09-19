package lk.ijse.CarHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.CarHire.dto.CarDto;
import lk.ijse.CarHire.dto.RentDto;
import lk.ijse.CarHire.dto.tm.RentTm;
import lk.ijse.CarHire.entity.CarEntity;
import lk.ijse.CarHire.entity.RentEntity;
import lk.ijse.CarHire.service.ServiceFactory;
import lk.ijse.CarHire.service.custom.CarService;
import lk.ijse.CarHire.service.custom.RentService;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RentFormController {


    public TextField txtid;
    public TableView<RentTm> tblRent;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colFromDate;
    public TableColumn<?, ?> colToDate;
    public TableColumn<?, ?> colPerDayRent;
    public TableColumn<?, ?> colAdvancedPayment;
    public TableColumn<?, ?> colRefunDeposit;
    public TableColumn<?, ?> colBalance;
    public TableColumn<?, ?> colTotal;
    public AnchorPane rootNode;

    RentService rentService = (RentService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.RENT);

    public void initialize(){
        System.out.println("Rent Form Loaded!");
        setCellValueFactory();
        List<RentDto> rentDtoList = loadAllRents();
        setTableData(rentDtoList);
    }
    private void setTableData(List<RentDto> rentDtoList) {
        ObservableList<RentTm> observableList = FXCollections.observableArrayList();
        for (RentDto rentDto : rentDtoList){
            var tm = new RentTm();
            tm.setId(rentDto.getId());
            tm.setToDate(rentDto.getToDate());
            tm.setFromDate(rentDto.getFromDate());
            tm.setPerDayRent(rentDto.getPerDayRent());
            tm.setTotal(rentDto.getTotal());
            observableList.add(tm);
        }
        tblRent.setItems(observableList);
    }

    private List<RentDto> loadAllRents() {
        List<RentDto> rentDtoList = new ArrayList<>();
        try(Session session = SessionFactoryConfiguration.getInstance().getSession()){
            Query<RentEntity> query = session.createQuery("FROM RentEntity", RentEntity.class);
            List<RentEntity> rentEntities = query.getResultList();
            for (RentEntity rentEntity : rentEntities){
                RentDto rentDto = new RentDto();
                rentDto.setId(rentEntity.getId());
                rentDto.setToDate(rentEntity.getToDate());
                rentDto.setFromDate(rentEntity.getFromDate());
                rentDto.setPerDayRent(rentEntity.getPerDayRent());
                rentDto.setTotal(rentEntity.getTotal());
                rentDtoList.add(rentDto);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return rentDtoList;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFromDate.setCellValueFactory(new PropertyValueFactory<>("fromDate"));
        colToDate.setCellValueFactory(new PropertyValueFactory<>("toDate"));
        colPerDayRent.setCellValueFactory(new PropertyValueFactory<>("perDayRent"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/view/rent_save_form.fxml"));
        this.rootNode.getChildren().clear();
        this.rootNode.getChildren().add(root1);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/view/rent_save_form.fxml"));
        this.rootNode.getChildren().clear();
        this.rootNode.getChildren().add(root1);
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
        Integer rentId = Integer.valueOf(txtid.getText());
        RentDto rentToDelete = new RentDto();
        rentToDelete.setId(rentId);
        rentService.deleteRent(rentToDelete);
        initialize();
        clearFields();
    }

    private void clearFields() {
        txtid.setText("");
    }
}