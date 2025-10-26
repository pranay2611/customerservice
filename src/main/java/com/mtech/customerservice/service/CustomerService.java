package com.mtech.customerservice.service;

import com.mtech.customerservice.entity.Customer;
import com.mtech.customerservice.entity.KycStatus;
import com.mtech.customerservice.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        customer.setCreatedAt(LocalDateTime.now());
        if (customer.getKycStatus() == null) {
            customer.setKycStatus(KycStatus.PENDING);
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(StringUtils.isEmpty(customerDetails.getName()) ? customer.getName() : customerDetails.getName());
            customer.setEmail(StringUtils.isEmpty(customerDetails.getEmail()) ? customer.getEmail() : customerDetails.getEmail());
            customer.setPhone(StringUtils.isEmpty(customerDetails.getPhone()) ? customer.getPhone() : customerDetails.getPhone());
            customer.setKycStatus(Objects.isNull(customerDetails.getKycStatus()) ? customer.getKycStatus() : customerDetails.getKycStatus());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
