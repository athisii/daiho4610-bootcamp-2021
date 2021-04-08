package com.tothenew;

import com.tothenew.entities.product.*;
import com.tothenew.entities.user.*;
import com.tothenew.repos.RoleRepository;
import com.tothenew.repos.product.*;
import com.tothenew.repos.user.UserRepository;
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

    @Autowired
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    private ParentCategoryRepository parentCategoryRepository;

    @Autowired
    private CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;


    @Transactional
    @Override
    public void run(ApplicationArguments args) {
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
            admin.setImageUrl("image/path/1");


            Seller seller = new Seller();
            seller.setEmail("seller1@tothenew.com");
            seller.setPassword(passwordEncoder.encode("password"));
            seller.getRoles().add(role_seller);
            seller.setFirstName("John");
            seller.setLastName("Wick");
            seller.setActive(true);
            seller.setCompanyContact("7005703425");
            seller.setCompanyName("TO THE NEW");
            seller.setImageUrl("image/path/2");


            Customer customer = new Customer();
            customer.setEmail("customer1@tothenew.com");
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
            userRepository.saveAll(List.of(admin, seller, customer));


            //Product
            ParentCategory fashion = new ParentCategory("Fashion");
            ParentCategory electronic = new ParentCategory("Electronics");

            CategoryMetadataField cmf1 = new CategoryMetadataField("Size");
            CategoryMetadataField cmf2 = new CategoryMetadataField("Colour");
            CategoryMetadataField cmf3 = new CategoryMetadataField("Length");


            Category shoe = new Category("Shoe");
            shoe.setParentCategory(fashion);
            Category shirt = new Category("Shirt");
            shirt.setParentCategory(fashion);
            Category tshirt = new Category("Tshirt");
            tshirt.setParentCategory(fashion);
            Category mobile = new Category("Mobile");
            mobile.setParentCategory(electronic);
            Category laptop = new Category("Laptop");
            laptop.setParentCategory(electronic);

            CategoryMetadataFieldValues cmfv1 = new CategoryMetadataFieldValues();
            cmfv1.setCategory(shoe);
            cmfv1.setCategoryMetadataField(cmf1);
            cmfv1.setValue("7,8,9,10");
            CategoryMetadataFieldValues cmfv2 = new CategoryMetadataFieldValues();
            cmfv2.setCategory(shoe);
            cmfv2.setCategoryMetadataField(cmf2);
            cmfv2.setValue("Black,Brown,Red");

            fashion.getCategories().addAll(List.of(shirt, shoe, tshirt));
            electronic.getCategories().addAll(List.of(mobile, laptop));
            parentCategoryRepository.saveAll(List.of(fashion, electronic));
            categoryMetadataFieldRepository.saveAll(List.of(cmf1, cmf2, cmf3));
            categoryMetadataFieldValuesRepository.saveAll(List.of(cmfv1, cmfv2));


        }
    }
}
