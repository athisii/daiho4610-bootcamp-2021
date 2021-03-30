package com.tothenew;

import com.tothenew.entities.user.*;
import com.tothenew.repos.RoleRepository;
import com.tothenew.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
            roleRepository.saveAll(List.of(role_admin, role_seller, role_customer));

            User admin = new Admin();
            admin.setEmail("admin@tothenew.com");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setFirstName("admin");
            admin.setLastName("");
            admin.setLastName("");
            admin.getRoles().add(role_admin);
            admin.setActive(true);
            userRepository.save(admin);
//
//
            Seller seller = new Seller();
            seller.setEmail("seller@gmail.com");
            seller.setPassword(passwordEncoder.encode("password"));
            seller.getRoles().add(role_seller);
            seller.setFirstName("John");
            seller.setLastName("Wick");
            seller.setActive(true);
            seller.setCompanyContact("7005703425");


            Customer customer = new Customer();
            customer.setEmail("athisiiekhe12@gmail.com");
            customer.setPassword(passwordEncoder.encode("password"));
            customer.getRoles().add(role_customer);
            customer.setFirstName("Rose");
            customer.setLastName("Williams");
            customer.setActive(true);
            customer.setContact("8132817645");
            customer.setImageUrl("image/path/3");


            Address address1 = new Address();
            address1.setCity("New Delhi");
            address1.setState("Delhi");
            address1.setCountry("India");
            address1.setAddressLine("Sant Nagar");
            address1.setZipCode("110084");
            address1.setLabel("Home");
            address1.setUser(seller);

            Address address2 = new Address();
            address2.setCity("Mao");
            address2.setState("Manipur");
            address2.setCountry("India");
            address2.setAddressLine("Makhan Khullen");
            address2.setZipCode("795014");
            address2.setLabel("Home");
            address2.setUser(customer);

            seller.getAddresses().add(address1);
            customer.getAddresses().add(address2);


            userRepository.save(seller);
            userRepository.save(customer);
//
//
////
        }
    }
}
