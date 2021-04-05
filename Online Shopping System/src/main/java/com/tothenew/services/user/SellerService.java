package com.tothenew.services.user;

import com.tothenew.entities.product.Category;
import com.tothenew.entities.product.Product;
import com.tothenew.entities.user.Role;
import com.tothenew.entities.user.Seller;
import com.tothenew.entities.user.User;
import com.tothenew.entities.user.UserRole;
import com.tothenew.exception.*;
import com.tothenew.objects.*;
import com.tothenew.objects.product.CreateProductDto;
import com.tothenew.objects.product.CreateProductVariationDto;
import com.tothenew.objects.product.UpdateProductDto;
import com.tothenew.repos.product.CategoryRepository;
import com.tothenew.repos.product.ProductRepository;
import com.tothenew.repos.user.SellerRepository;
import com.tothenew.repos.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class SellerService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void registerNewSeller(SellerDto sellerDto)
            throws UserAlreadyExistException {

        if (emailExists(sellerDto.getEmail())) {
            throw new EmailExistsException("Email already registered");
        }

        Seller newSeller = new Seller();
        ModelMapper mm = new ModelMapper();
        mm.map(sellerDto, newSeller);
        newSeller.setPassword(passwordEncoder.encode(newSeller.getPassword()));
        newSeller.setCompanyContact(sellerDto.getPhone().getValue());
        sellerDto.getCompanyAddress().setUser(newSeller);
        newSeller.setAddresses(Collections.singletonList(sellerDto.getCompanyAddress()));

        Role role_seller = userService.getRole(UserRole.SELLER);
        newSeller.getRoles().add(role_seller);

        sellerRepository.save(newSeller);
        userService.sendActivationMessage(newSeller, UserRole.SELLER);
    }

    public void resendToken(EmailDto emailDto) throws UserNotFoundException {
        User user = userRepository.findByEmail(emailDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException("No such email found!");
        }
        if (user.isActive()) {
            userService.sendResetPasswordMessage(user);
        } else {
            throw new UserActivationException("Account not activated, waiting for approval!");
        }

    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User viewProfile(String email) {
        return userService.findUserByEmail(email);
    }

    public void updateProfile(String email, UpdateProfileDto updateProfileDto) {
        User user = userService.findUserByEmail(email);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updateProfileDto, user);
        userRepository.save(user);
    }

    public void updatePassword(String email, ResetPasswordDto resetPasswordDto) {
        userService.updatePassword(email, resetPasswordDto);
    }

    public void updateAddress(AddressDto addressDto, Long addressId) {
        userService.updateAddress(addressDto, addressId);
    }

    public void addProduct(CreateProductDto createProductDto, String email) {
        Long categoryId = createProductDto.getCategory();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        categoryOptional.orElseThrow(() -> new CategoryExistException("Not found category with id:" + categoryId));

        categoryOptional.ifPresent(category -> {

            String productName = createProductDto.getName();
            String brand = createProductDto.getBrand();
            Seller seller = (Seller) userRepository.findByEmail(email);

            if (checkIfProductExist(productName, seller.getId(), brand, categoryId)) {
                throw new ProductExistException("Same product exists");
            }
            Product product = new Product();
            ModelMapper mm = new ModelMapper();
            mm.map(createProductDto, product);
            product.setSeller(seller);
            product.setCategory(category);
            productRepository.save(product);
            userService.sendProductActivationMessage(product);
        });
    }


    private boolean checkIfProductExist(String productName, Long sellerId, String brand, Long categoryId) {
        return productRepository.findByNameSellerIdBrandCategoryId(productName, sellerId, brand, categoryId) != null;
    }

    public void addProductVariation(CreateProductVariationDto cpvd, String email) {
        System.out.println(cpvd);

    }


    public List<Product> getAllProducts(String email) {
        Seller seller = (Seller) userService.findUserByEmail(email);
        return seller.getProducts();
    }

    public Product getProductById(String email, Long productId) {
        Seller seller = (Seller) userService.findUserByEmail(email);
        Optional<Product> optionalProduct = seller.getProducts()
                .stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
        optionalProduct.orElseThrow(() -> new ProductExistException("Not found for product with id: " + productId));
        return optionalProduct.get();
    }

    public void deleteProduct(String email, Long productId) {
        Seller seller = (Seller) userService.findUserByEmail(email);
        boolean result = seller.getProducts().removeIf(product -> product.getId().equals(productId));
        if (result) {
            productRepository.deleteById(productId);
            return;
        }
        throw new ProductExistException("Not found for product with id: " + productId);
    }

    public void updateProduct(UpdateProductDto updateProductDto, String email) {
        Seller seller = (Seller) userService.findUserByEmail(email);
        Long productId = updateProductDto.getProductId();
        Optional<Product> optionalProduct = seller.getProducts()
                .stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
        optionalProduct.orElseThrow(() -> new ProductExistException("Not found for product with id: " + productId));

        Product product = optionalProduct.get();
        String oldName = product.getName();
        String brand = product.getBrand();
        Long categoryId = product.getCategory().getId();
        String productName = updateProductDto.getName();

        if (productName != null && !productName.isBlank()) {
            if (checkIfProductExist(productName, seller.getId(), brand, categoryId)) {
                throw new ProductExistException("Same product exists");
            }
            System.out.println(updateProductDto);
            ModelMapper mm = new ModelMapper();
            mm.map(updateProductDto, product);
            productRepository.save(product);
            return;
        }
        System.out.println(updateProductDto);
        ModelMapper mm = new ModelMapper();
        mm.map(updateProductDto, product);
        product.setName(oldName);
        productRepository.save(product);

    }

}
