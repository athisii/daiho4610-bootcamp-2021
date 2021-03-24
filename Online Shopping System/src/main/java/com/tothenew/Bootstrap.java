package com.tothenew;

import com.tothenew.entities.user.*;
import com.tothenew.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() < 1) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


            Role role_admin = new Role();
            role_admin.setAuthority("ROLE_ADMIN");
            Role role_seller = new Role();
            role_seller.setAuthority("ROLE_SELLER");
            Role role_customer = new Role();
            role_customer.setAuthority("ROLE_CUSTOMER");


            User admin = new Admin();
            admin.setEmail("admin@ttn.com");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.getRoles().add(role_admin);
            role_admin.getUsers().add(admin);


            User seller = new Seller();
            seller.setEmail("seller@ttn.com");
            seller.setPassword(passwordEncoder.encode("password"));
            seller.getRoles().add(role_seller);
            role_seller.getUsers().add(seller);


            User customer = new Customer();
            customer.setEmail("customer@ttn.com");
            customer.setPassword(passwordEncoder.encode("password"));
            customer.getRoles().add(role_customer);
            role_customer.getUsers().add(customer);


            Address address1 = new Address();
            address1.setCity("New Delhi");
            address1.setState("Delhi");
            address1.setCountry("India");
            address1.setAddressLine("Sant Nagar");
            address1.setZipCode("110084");
            address1.setLabel("Home");

            Address address2 = new Address();
            address2.setCity("Mao");
            address2.setState("Manipur");
            address2.setCountry("India");
            address2.setAddressLine("Makhan Khullen");
            address2.setZipCode("795014");
            address2.setLabel("Home");

            seller.getAddresses().add(address1);
            customer.getAddresses().add(address2);

            userRepository.save(admin);
            userRepository.save(seller);
            userRepository.save(customer);

            System.out.println("Total users saved::" + userRepository.count());

        }
    }
}
