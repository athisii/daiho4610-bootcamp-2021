package com.tothenew.services;

import com.tothenew.entities.user.Customer;
import com.tothenew.entities.user.Role;
import com.tothenew.entities.user.Seller;
import com.tothenew.exception.EmailExistsException;
import com.tothenew.objects.CustomerDto;
import com.tothenew.repos.CustomerRepository;
import com.tothenew.repos.UserRepository;
import com.tothenew.exception.UserAlreadyExistException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer registerNewCustomer(CustomerDto customerDto)
            throws EmailExistsException {

        if (emailExists(customerDto.getEmail())) {
            throw new EmailExistsException("Email already registered");
        }
        Customer newCustomer = new Customer();
        ModelMapper mm = new ModelMapper();
        mm.map(customerDto, newCustomer);
        newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        newCustomer.getRoles().add(new Role("ROLE_CUSTOMER"));
        return customerRepository.save(newCustomer);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}

