package lk.ijse.CarHire.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.CarHire.dao.DaoFactory;
import lk.ijse.CarHire.dao.custom.CustomerDao;
import lk.ijse.CarHire.dto.CarDto;
import lk.ijse.CarHire.dto.CustomerDto;
import lk.ijse.CarHire.dto.tm.CustomerTm;
import lk.ijse.CarHire.entity.CarEntity;
import lk.ijse.CarHire.entity.CustomerEntity;
import lk.ijse.CarHire.service.custom.CustomerService;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    CustomerDao customerDao = (CustomerDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CUSTOMER);

    @Override
    public String saveCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerDto.getId());
        customerEntity.setName(customerDto.getName());
        customerEntity.setNic(customerDto.getNic());
        customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setMobileno(customerDto.getMobileno());
        return String.valueOf(customerDao.save(customerEntity));
    }

    @Override
    public String updateCustomer(CustomerDto customerDto) {
        try {
            CustomerEntity entityToUpdate = customerDao.findById(customerDto.getId()); // Replace with your method to find a customer by ID
            if (entityToUpdate != null) {
                entityToUpdate.setName(customerDto.getName());
                entityToUpdate.setAddress(customerDto.getAddress());
                entityToUpdate.setNic(customerDto.getNic());
                entityToUpdate.setMobileno(customerDto.getMobileno());

                boolean updated = customerDao.update(entityToUpdate);

                if (updated) {
                    return "Customer updated successfully.";
                } else {
                    return "Failed to update customer.";
                }
            } else {
                return "Customer not found.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while updating customer.";
        }
    }

    @Override
    public String deleteCustomer(CustomerDto customerDto) {
        try {
            CustomerEntity customerEntity = customerDao.findById(customerDto.getId());

            if (customerEntity != null) {
                boolean isDeleted = customerDao.delete(customerEntity);

                if (isDeleted) {
                    return "SUCCESS";
                } else {
                    return "FAILURE";
                }
            } else {
                return "NOT_FOUND";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    @Override
    public List getAllCustomers() {
        List<CustomerEntity> customerEntities = customerDao.getAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (CustomerEntity customerEntity : customerEntities){
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customerEntity.getId());
            customerDto.setName(customerEntity.getName());
            customerDto.setAddress(customerEntity.getAddress());
            customerDto.setNic(customerEntity.getNic());
            customerDto.setMobileno(customerEntity.getMobileno());
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }

    @Override
    public CustomerDto searchCustomer(String id) {
        CustomerEntity customerEntity = customerDao.search(Integer.valueOf(id));
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerEntity.getId());
        customerDto.setName(customerEntity.getName());
        customerDto.setAddress(customerEntity.getAddress());
        customerDto.setNic(customerEntity.getNic());
        customerDto.setMobileno(customerEntity.getMobileno());
        return customerDto;
    }
}