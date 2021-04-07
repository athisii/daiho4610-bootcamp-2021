package com.tothenew.services.user;

import com.tothenew.entities.product.*;
import com.tothenew.entities.user.Role;
import com.tothenew.entities.user.Seller;
import com.tothenew.entities.user.User;
import com.tothenew.entities.user.UserRole;
import com.tothenew.exception.*;
import com.tothenew.objects.*;
import com.tothenew.objects.product.*;
import com.tothenew.repos.product.CategoryMetadataFieldRepository;
import com.tothenew.repos.product.CategoryRepository;
import com.tothenew.repos.product.ProductRepository;
import com.tothenew.repos.product.ProductVariationRepository;
import com.tothenew.repos.user.SellerRepository;
import com.tothenew.repos.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    private Pageable getPageable(PagingAndSortingDto pagingAndSortingDto) {
        Sort sort = Sort.by(pagingAndSortingDto.getOrder(), pagingAndSortingDto.getSortBy());
        return PageRequest.of(pagingAndSortingDto.getOffset(), pagingAndSortingDto.getMax(), sort);
    }

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
        User user = userService.findUserByEmail(emailDto.getEmail());
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
        categoryOptional.orElseThrow(() -> new CategoryExistException("No category found for id:" + categoryId));
        Category category = categoryOptional.get();
        String productName = createProductDto.getName();
        String brand = createProductDto.getBrand();
        Seller seller = (Seller) userService.findUserByEmail(email);

        if (checkIfProductExist(productName, seller.getId(), brand, categoryId)) {
            throw new ProductExistException("Same product exists");
        }
        Product product = new Product();
        ModelMapper mm = new ModelMapper();
        mm.map(createProductDto, product);
        product.setSeller(seller);
        product.setCategory(category);
        productRepository.save(product);
//        userService.sendProductActivationMessage(product);
    }


    private boolean checkIfProductExist(String productName, Long sellerId, String brand, Long categoryId) {
        return productRepository.findByNameSellerIdBrandCategoryId(productName, sellerId, brand, categoryId) != null;
    }


    public Page<Product> getAllProducts(PagingAndSortingDto pagingAndSortingDto, String email) {
        Seller seller = (Seller) userService.findUserByEmail(email);
        return productRepository.findBySellerId(seller.getId(), getPageable(pagingAndSortingDto));
//        return seller.getProducts();
    }

    public Product getProductById(String email, Long productId) {
        Seller seller = (Seller) userService.findUserByEmail(email);
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.orElseThrow(() -> new ProductExistException("No product found for id: " + productId));
        Product product = productOptional.get();
        if (product.getSeller().getId().equals(seller.getId())) {
            return product;
        }
        throw new ProductExistException("No product found for id: " + productId);
    }

    public void deleteProduct(String email, Long productId) {
        Seller seller = (Seller) userService.findUserByEmail(email);
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.orElseThrow(() -> new ProductExistException("No product found for id: " + productId));
        Product product = productOptional.get();
        if (product.getSeller().getId().equals(seller.getId())) {
            product.setDeleted(true);
            productRepository.save(product);
        }
        throw new ProductExistException("No product found for id: " + productId);

    }


    public void updateProduct(UpdateProductDto updateProductDto, String email) {
        Seller seller = (Seller) userService.findUserByEmail(email);
        Long productId = updateProductDto.getProductId();

        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.orElseThrow(() -> new ProductExistException("No product found for id: " + productId));
        Product product = productOptional.get();
        if (product.getSeller().getId().equals(seller.getId())) {
            String oldName = product.getName();
            String brand = product.getBrand();
            Long categoryId = product.getCategory().getId();
            String productName = updateProductDto.getName();

            if (productName != null && !productName.isBlank()) {
                if (checkIfProductExist(productName, seller.getId(), brand, categoryId)) {
                    throw new ProductExistException("Same product exists");
                }
                ModelMapper mm = new ModelMapper();
                mm.map(updateProductDto, product);
                productRepository.save(product);
                return;
            }
            ModelMapper mm = new ModelMapper();
            mm.map(updateProductDto, product);
            product.setName(oldName);
            productRepository.save(product);
        }
        throw new ProductExistException("No product found for id: " + productId);

    }

    public ProductVariation getProductVariationById(String email, Long productVariationId) {
        Seller seller = (Seller) userService.findUserByEmail(email);
        Optional<ProductVariation> pvOptional = productVariationRepository.findById(productVariationId);
        pvOptional.orElseThrow(() -> new ProductExistException("No product variation found for id: " + productVariationId));
        ProductVariation productVariation = pvOptional.get();
        if (productVariation.getProduct().getSeller().getId().equals(seller.getId()) && !productVariation.getProduct().isDeleted()) {
            return productVariation;
        }
        throw new ProductExistException("No product variation found for id: " + productVariationId);
    }

    public Page<ProductVariation> getAllProductVariationForProductById(PagingAndSortingDto pagingAndSortingDto, String email, Long productId) {
        Seller seller = (Seller) userService.findUserByEmail(email);
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.orElseThrow(() -> new ProductExistException("No product found for id: " + productId));
        Product product = productOptional.get();
        if (product.getSeller().getId().equals(seller.getId()) && !product.isDeleted()) {
            return productVariationRepository.findByProductId(product.getId(), getPageable(pagingAndSortingDto));
        }
        throw new ProductExistException("No product found for id: " + productId);
    }

    public void addProductVariation(CreateProductVariationDto createProductVariationDto, String email) {
        Long productId = createProductVariationDto.getProductId();
        Seller seller = (Seller) userService.findUserByEmail(email);
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.orElseThrow(() -> new ProductExistException("No product found for id: " + productId));
        Product product = productOptional.get();

        if (!product.getSeller().getId().equals(seller.getId()) || !product.isActive() || product.isDeleted()) {
            throw new ProductExistException("No product found for id: " + productId);
        }
        Category category = product.getCategory();
        List<ProductVariationMetadataFieldValueDto> metadataFieldIdValues = createProductVariationDto.getMetadata();
        validate(category, metadataFieldIdValues); //throws exceptions
        ProductVariation productVariation = new ProductVariation();
        productVariation.setPrice(createProductVariationDto.getPrice());
        productVariation.setQuantityAvailable(createProductVariationDto.getQuantityAvailable());
        productVariation.setProduct(product);
        productVariation.setPrimaryImageName(createProductVariationDto.getPrimaryImageName());
        String metadata = buildMetadata(metadataFieldIdValues);
        productVariation.setMetadata(metadata);
        productVariationRepository.save(productVariation);

    }


    public void updateProductVariation(UpdateProductVariationDto updateProductVariationDto, String email) {
        Long productVariationId = updateProductVariationDto.getProductVariationId();
        Seller seller = (Seller) userService.findUserByEmail(email);
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariationId);
        productVariationOptional.orElseThrow(() -> new ProductExistException("No product variation found for id: " + productVariationId));
        ProductVariation productVariation = productVariationOptional.get();
        Product product = productVariation.getProduct();

        if (!product.getSeller().getId().equals(seller.getId()) || !product.isActive() || product.isDeleted()) {
            throw new ProductExistException("No product variation found for id: " + productVariationId);
        }

        Category category = product.getCategory();
        List<ProductVariationMetadataFieldValueDto> metadataFieldIdValues = updateProductVariationDto.getUpdateMetadata();
        validate(category, metadataFieldIdValues);
        ModelMapper mm = new ModelMapper();
        mm.map(updateProductVariationDto, productVariation);
        String metadata = buildMetadata(metadataFieldIdValues);
        productVariation.setMetadata(metadata);
        productVariationRepository.save(productVariation);
    }

    private void validate(Category category, List<ProductVariationMetadataFieldValueDto> metadataFieldIdValues) {
        Long categoryId = category.getId();
        List<Long> categoryMFIds = category.getCategoryMetadataFieldValues()
                .stream()
                .map(cmfv -> cmfv.getCategoryMetadataField().getId())
                .collect(Collectors.toList());
        List<CategoryMetadataFieldValues> categoryMetadataFieldValues = category.getCategoryMetadataFieldValues();
        for (ProductVariationMetadataFieldValueDto mv : metadataFieldIdValues) {
            Long metadataFieldId = mv.getMetadataFieldId();
            if (!categoryMFIds.contains(metadataFieldId)) {
                throw new CategoryMetadataFieldException("Category Metadata Field not found with id: " + metadataFieldId);
            }
            CategoryMetadataFieldValues cMFV = categoryMetadataFieldValues.stream()
                    .filter(cmfv -> cmfv.getCategory().getId().equals(categoryId) && cmfv.getCategoryMetadataField().getId().equals(metadataFieldId))
                    .findFirst().get();
            String requestValue = mv.getValue();
            Optional<String> result = Arrays.stream(cMFV.getValue().split(",")).filter(value -> value.equals(requestValue)).findFirst();
            if (result.isEmpty()) {
                throw new CategoryMetadataFieldException("Metadata Field Value should be within the possible values! Given value: " + requestValue);
            }
        }
    }

    private String buildMetadata(List<ProductVariationMetadataFieldValueDto> metadataFieldIdValues) {
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
        return sb.toString();
    }
}
