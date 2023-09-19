package lk.ijse.CarHire.service.custom;

import lk.ijse.CarHire.dto.CustomerDto;
import lk.ijse.CarHire.service.SuperService;

import java.util.List;

public interface CustomerService<T> extends SuperService {
    String saveCustomer(CustomerDto customerDto);
    String updateCustomer(CustomerDto customerDto);
    String deleteCustomer(CustomerDto customerDto);
    List<T> getAllCustomers();
    public T searchCustomer(String id);
}