package com.tothenew.services.user;

import com.tothenew.entities.product.*;
import com.tothenew.entities.user.Role;
import com.tothenew.entities.user.Seller;
import com.tothenew.entities.user.User;
import com.tothenew.entities.user.UserRole;
import com.tothenew.exception.*;
import com.tothenew.objects.*;
import com.tothenew.objects.product.CreateProductDto;
import com.tothenew.objects.product.CreateProductVariationDto;
import com.tothenew.objects.product.ProductVariationMetadataFieldValueDto;
import com.tothenew.objects.product.UpdateProductDto;
import com.tothenew.repos.product.CategoryMetadataFieldRepository;
import com.tothenew.repos.product.CategoryRepository;
import com.tothenew.repos.product.ProductRepository;
import com.tothenew.repos.product.ProductVariationRepository;
import com.tothenew.repos.user.SellerRepository;
import com.tothenew.repos.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

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

    public void addProductVariation(CreateProductVariationDto createProductVariationDto) {
        Long productId = createProductVariationDto.getProductId();
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.orElseThrow(() -> new ProductExistException("Not found for product with id:" + productId));
        Product product = productOptional.get();
        if (!product.isActive()) {
            throw new ProductExistException("Not found for product with id: " + productId);
        }
        Category category = product.getCategory();
        Long categoryId = category.getId();
        List<Long> categoryMFIds = category.getCategoryMetadataFieldValues()
                .stream()
                .map(cmfv -> cmfv.getCategoryMetadataField().getId())
                .collect(Collectors.toList());
        List<CategoryMetadataFieldValues> categoryMetadataFieldValues = category.getCategoryMetadataFieldValues();

//        Check if MetadataField ids from requestDto exists in CategoryMetadataFieldValues of product's category id.
        List<ProductVariationMetadataFieldValueDto> metadataFieldIdValues = createProductVariationDto.getMetadata();
        for (ProductVariationMetadataFieldValueDto mv : metadataFieldIdValues) {
            Long metadataFieldId = mv.getMetadataFieldId();
            if (!categoryMFIds.contains(metadataFieldId)) {
                throw new CategoryMetadataFieldException("Not found for Category Metadata Field with id: " + metadataFieldId);
            }
            CategoryMetadataFieldValues cMFV = categoryMetadataFieldValues.stream()
                    .filter(cmfv -> cmfv.getCategory().getId().equals(categoryId) && cmfv.getCategoryMetadataField().getId().equals(metadataFieldId))
                    .findFirst().get();
            String requestValue = mv.getValue();
            Optional<String> result = Arrays.stream(cMFV.getValue().split(",")).filter(value -> value.equals(requestValue)).findFirst();
            if (result.isEmpty()) {
                throw new CategoryMetadataFieldException("Metadata Field Value should be within the possible values!");
            }
        }

        ProductVariation productVariation = new ProductVariation();
        productVariation.setPrice(createProductVariationDto.getPrice());
        productVariation.setQuantityAvailable(createProductVariationDto.getQuantityAvailable());
        productVariation.setProduct(product);
        productVariation.setPrimaryImageName(createProductVariationDto.getPrimaryImageName());
        StringBuilder sb = new StringBuilder("{");
        for (var mv : metadataFieldIdValues) {
            CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findById(mv.getMetadataFieldId()).get();
            sb.append("\"")
                    .append(categoryMetadataField.getName())
                    .append("\":\"")
                    .append(mv.getValue())
                    .append("\",");
        }
        sb.setCharAt(sb.length() - 1, '}');
        String metadata = sb.toString();
        productVariation.setMetadata(metadata);
        productVariationRepository.save(productVariation);
        System.out.println(productVariation);
        
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
