package com.tothenew.services.user;

import com.tothenew.entities.product.Category;
import com.tothenew.entities.product.ParentCategory;
import com.tothenew.entities.product.Product;
import com.tothenew.entities.token.VerificationToken;
import com.tothenew.entities.user.*;
import com.tothenew.exception.*;
import com.tothenew.objects.*;
import com.tothenew.repos.AddressRepository;
import com.tothenew.repos.product.CategoryRepository;
import com.tothenew.repos.product.ParentCategoryRepository;
import com.tothenew.repos.product.ProductRepository;
import com.tothenew.repos.user.CustomerRepository;
import com.tothenew.repos.user.UserRepository;
import com.tothenew.repos.VerificationTokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ParentCategoryRepository parentCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewCustomer(CustomerDto customerDto) throws EmailExistsException {

        if (checkIfEmailExists(customerDto.getEmail())) {
            throw new EmailExistsException("Email already registered");
        }
        Customer newCustomer = new Customer();
        ModelMapper mm = new ModelMapper();
        mm.map(customerDto, newCustomer);
        newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        newCustomer.setContact(customerDto.getPhone().getValue());
        customerDto.getAddress().setUser(newCustomer);
        newCustomer.getAddresses().add(customerDto.getAddress());
        Role role_customer = userService.getRole(UserRole.CUSTOMER);
        newCustomer.getRoles().add(role_customer);
        customerRepository.save(newCustomer);
        userService.sendActivationMessage(newCustomer, UserRole.CUSTOMER);
    }

    public void confirmRegisteredCustomer(String token) throws InvalidTokenException {
        //If token got expired, new token will be generated and resend to the user.
        String result = userService.validateVerificationToken(token);
        if (result.equals("invalidToken")) {
            throw new InvalidTokenException("Invalid Token");
        }
        User user = userService.getUser(token);
        user.setActive(true);
        userRepository.save(user);
        userService.sendActivatedMessage(user.getEmail());
        VerificationToken oldToken = verificationTokenRepository.findByToken(token);
        verificationTokenRepository.delete(oldToken);

    }

    public void resendToken(EmailDto emailDto) throws UserNotFoundException {
        User user = userRepository.findByEmail(emailDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException("No such email found!");
        }
        if (user.isActive()) {
            throw new UserActivationException("User already activated");
        }
        VerificationToken oldToken = verificationTokenRepository.findTokenByUserId(user.getId());
        verificationTokenRepository.delete(oldToken);
        userService.sendActivationMessage(user, UserRole.CUSTOMER);

    }


    private boolean checkIfEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public void confirmResetPassword(ResetPasswordDto resetPasswordDto, String token) {
        userService.resetPassword(resetPasswordDto, token);

    }

    public void resetPassword(EmailDto emailDto) {
        User user = userRepository.findByEmail(emailDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException("No such email found!");
        }
        if (!user.isActive()) {
            throw new UserActivationException("User not activated");
        }
        userService.sendResetPasswordMessage(user);
    }

    public User viewProfile(String email) {
        return userRepository.findByEmail(email);
    }


    public List<Address> addresses(String email) {
        User user = userService.findUserByEmail(email);
        return addressRepository.findByUser(user);
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

    public void addAddress(String email, AddressDto addressDto) {
        User user = userRepository.findByEmail(email);
        Address address = new Address();
        ModelMapper mm = new ModelMapper();
        mm.map(addressDto, address);
        address.setUser(user);
        user.getAddresses().add(address);
        userRepository.save(user);
    }

    public void deleteAddress(Long addressId) {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        addressOptional.orElseThrow(() -> new AddressNotFoundException("Address not found for id: " + addressId));
        addressOptional.ifPresent(address -> addressRepository.delete(address));
    }

    public void updateAddress(AddressDto addressDto, Long addressId) {
        userService.updateAddress(addressDto, addressId);
    }

    public List<?> getAllCategories(Long categoryId) {
        if (categoryId == null) {
            return parentCategoryRepository.findAll();
        }
        Optional<ParentCategory> pCategoryOpt = parentCategoryRepository.findById(categoryId);
        pCategoryOpt.orElseThrow(() -> new CategoryExistException("Not found for category with id: " + categoryId));
        return pCategoryOpt.get().getCategories();
    }

    public Product getProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.ifPresentOrElse(product -> {
            if (!product.isActive() || product.isDeleted()) {
                throw new ProductExistException("Not found for product with id: " + productId);
            }
        }, () -> {
            throw new ProductExistException("Not found for product with id: " + productId);
        });
        return productOptional.get();
    }

    public List<Product> getAllProducts(Long categoryId) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get().getProducts()
                    .stream()
                    .filter(Product::isActive)
                    .collect(Collectors.toList());
        } else {
            Optional<ParentCategory> parentCategoryOptional = parentCategoryRepository.findById(categoryId);
            parentCategoryOptional.orElseThrow(() -> new CategoryExistException("Not found for category with id: " + categoryId));
            return parentCategoryOptional.get()
                    .getCategories()
                    .stream()
                    .map(Category::getProducts)
                    .flatMap(List::stream)
                    .filter(Product::isActive)
                    .collect(Collectors.toList());
        }
    }

    public List<Product> getSimilarProducts(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.ifPresentOrElse(product -> {
            if (!product.isActive() || product.isDeleted()) {
                throw new ProductExistException("Not found for product with id: " + productId);
            }
        }, () -> {
            throw new ProductExistException("Not found for product with id: " + productId);
        });
        Product product = productOptional.get();
        Category category = product.getCategory();
        return category.getProducts();

    }
}

