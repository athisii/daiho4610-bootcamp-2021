package com.tothenew.services.user;

import com.tothenew.entities.cart.Cart;
import com.tothenew.entities.order.Order;
import com.tothenew.entities.order.OrderProduct;
import com.tothenew.entities.order.OrderStatus;
import com.tothenew.entities.order.orderstatusenum.FromStatus;
import com.tothenew.entities.order.orderstatusenum.ToStatus;
import com.tothenew.entities.product.Category;
import com.tothenew.entities.product.ParentCategory;
import com.tothenew.entities.product.Product;
import com.tothenew.entities.product.ProductVariation;
import com.tothenew.entities.token.VerificationToken;
import com.tothenew.entities.user.*;
import com.tothenew.exception.*;
import com.tothenew.objects.*;
import com.tothenew.repos.AddressRepository;
import com.tothenew.repos.VerificationTokenRepository;
import com.tothenew.repos.order.OrderProductRepository;
import com.tothenew.repos.order.OrderRepository;
import com.tothenew.repos.order.OrderStatusRepository;
import com.tothenew.repos.product.*;
import com.tothenew.repos.user.CustomerRepository;
import com.tothenew.repos.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.From;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
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
    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private ModelMapper modelMapper;

    public void registerNewCustomer(CustomerDto customerDto) throws EmailExistsException {

        if (checkIfEmailExists(customerDto.getEmail())) {
            throw new EmailExistsException("Email already registered");
        }
        Customer newCustomer = new Customer();
        modelMapper.map(customerDto, newCustomer);
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

    public void resendToken(EmailDto emailDto) throws GenericNotFoundException {
        User user = userService.findUserByEmail(emailDto.getEmail());
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

    public void resetPassword(EmailDto emailDto) {
        User user = userService.findUserByEmail(emailDto.getEmail());
        if (!user.isActive()) {
            throw new UserActivationException("User not activated");
        }
        userService.sendResetPasswordMessage(user);
    }

    public User viewProfile(String email) {
        return userService.findUserByEmail(email);
    }


    public List<Address> addresses(String email) {
        User user = userService.findUserByEmail(email);
        return addressRepository.findByUser(user);
    }

    public void updateProfile(String email, UpdateProfileDto updateProfileDto) {

        User user = userService.findUserByEmail(email);
        modelMapper.map(updateProfileDto, user);
        userRepository.save(user);
    }

    public void updatePassword(String email, ResetPasswordDto resetPasswordDto) {
        userService.updatePassword(email, resetPasswordDto);
    }

    public void addAddress(String email, AddressDto addressDto) {
        User user = userService.findUserByEmail(email);
        Address address = new Address();
        modelMapper.map(addressDto, address);
        address.setUser(user);
        user.getAddresses().add(address);
        userRepository.save(user);
    }

    public void deleteAddress(Long addressId) {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        addressOptional.orElseThrow(() -> new GenericNotFoundException("Address not found for id: " + addressId));
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
        pCategoryOpt.orElseThrow(() -> new GenericNotFoundException("Not found for category with id: " + categoryId));
        return pCategoryOpt.get().getCategories();
    }

    public Category getFilterDetailsOfACategory(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        categoryOptional.orElseThrow(() -> new GenericNotFoundException("Not found for category with id: " + categoryId));
        return categoryOptional.get();
    }

    public Product getProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.ifPresentOrElse(product -> {
            if (!product.isActive() || product.isDeleted()) {
                throw new GenericNotFoundException("Not found for product with id: " + productId);
            }
        }, () -> {
            throw new GenericNotFoundException("Not found for product with id: " + productId);
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
            parentCategoryOptional.orElseThrow(() -> new GenericNotFoundException("Not found for category with id: " + categoryId));
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
                throw new GenericNotFoundException("Not found for product with id: " + productId);
            }
        }, () -> {
            throw new GenericNotFoundException("Not found for product with id: " + productId);
        });
        return productOptional.get().getCategory().getProducts();

    }


    public void addProductVariationToCart(Long productVariationId, int quantity, String email) {

        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariationId);
        productVariationOptional.orElseThrow(() -> new GenericNotFoundException("No Product Variation found with id: " + productVariationId));
        ProductVariation productVariation = productVariationOptional.get();
        if (quantity < 1 || quantity > productVariation.getQuantityAvailable()) {
            throw new ProductVariationExistException("Quantity should be greater than 0 and should not exceeds quantityAvailable");
        }
        Product product = productVariation.getProduct();
        if (!productVariation.isActive() || product.isDeleted() || !product.isActive()) {
            throw new GenericNotFoundException("Product/ProductVariation id: " + (productVariation.isActive() ? product.getId() : productVariation.getId()) + " is either deleted or not active!");
        }
        Customer customer = (Customer) userService.findUserByEmail(email);
        Optional<Cart> cartOptional = cartRepository.findByCustomerAndProductVariation(customer, productVariation);
        if (cartOptional.isPresent()) {
            //throw new ProductExistException("Product already added to cart");
            //Or just increment the quantity.
            Cart cart = cartOptional.get();
            int totalQuantity = cart.getQuantity() + quantity;
            int quantityAvailable = productVariation.getQuantityAvailable();
            if (totalQuantity >= quantityAvailable) {
                totalQuantity = quantityAvailable;
            }
            cart.setQuantity(totalQuantity);
            cartRepository.save(cart);
            return;
        }
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setProductVariation(productVariation);
        cart.setQuantity(quantity);
        cartRepository.save(cart);

    }

    public List<Cart> viewCart(String email) {
        Customer customer = (Customer) userService.findUserByEmail(email);
        return customer.getCarts();
    }

    public void deleteProductVariationInCart(Long productVariationId, String email) {
        Customer customer = (Customer) userService.findUserByEmail(email);
        Optional<Cart> cartOptional = cartRepository.findByKey(customer.getId(), productVariationId);
        cartOptional.orElseThrow(() -> new GenericNotFoundException("No product variation found in cart with id: " + productVariationId));
        cartRepository.delete(cartOptional.get());
    }

    public void updateProductVariationInCart(Long productVariationId, int quantity, String email) {
        if (quantity < 1) {
            throw new ProductVariationExistException("Quantity should be greater than 0");
        }
        Customer customer = (Customer) userService.findUserByEmail(email);
        Optional<Cart> cartOptional = cartRepository.findByKey(customer.getId(), productVariationId);
        cartOptional.orElseThrow(() -> new GenericNotFoundException("No product variation found in cart with id: " + productVariationId));
        Cart cart = cartOptional.get();
        if (quantity > cart.getProductVariation().getQuantityAvailable()) {
            throw new ProductVariationExistException("Quantity should not exceeds quantityAvailable");
        }
        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    public void deleteCart(String email) {
        Customer customer = (Customer) userService.findUserByEmail(email);
        cartRepository.deleteByCustomerId(customer.getId());
    }

    public Long orderAllProducts(String email) {
        Customer customer = (Customer) userService.findUserByEmail(email);
        List<Cart> carts = customer.getCarts();
        //All the validations are done inside  placeOrder()
        Long orderId = placeOrder(customer, carts);

        //Empty the cart if order placed.
        cartRepository.deleteByCustomerId(customer.getId());
        return orderId;
    }

    public Long orderPartialProducts(String email, List<Long> productVariationIds) {
        Customer customer = (Customer) userService.findUserByEmail(email);

        //Collecting only valid productVariationIds
        List<Cart> carts = customer.getCarts()
                .stream()
                .filter(cart -> productVariationIds.contains(cart.getProductVariation().getId()))
                .collect(Collectors.toList());
        if (carts.isEmpty()) {
            throw new GenericNotFoundException("No Product Variation found in the cart.");
        }
        //All the validations are done inside  placeOrder()
        Long orderId = placeOrder(customer, carts);

        //Remove all the ordered products(productVariation Id) in the cart.
        //deleteAllProductsInCartByCustomer(email);
        productVariationIds.forEach(productVariationId ->
                cartRepository.deleteByCustomerIdAndProductVariationId(customer.getId(), productVariationId));
        return orderId;
    }


    public Long directOrder(String email, Long productVariationId, int quantity) {
        if (quantity < 1) {
            throw new ProductVariationExistException("Quantity should be greater than 0");
        }
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariationId);
        productVariationOptional.orElseThrow(() -> new GenericNotFoundException("No product variation found with id: " + productVariationId));
        ProductVariation productVariation = productVariationOptional.get();
        Customer customer = (Customer) userService.findUserByEmail(email);
        Order order = new Order();
        order.setCustomer(customer);
        setOrderAddress(customer, order);

        //All the validations are done inside  makeOrderProductAndOrderStatus()
        OrderProduct orderProduct = makeOrderProductAndOrderStatus(order, productVariation, quantity);

        order.setAmountPaid(productVariation.getPrice() * quantity);
        order.setOrderProducts(Collections.singletonList(orderProduct));
        orderRepository.save(order);
        return order.getId();
    }

    private Long placeOrder(Customer customer, List<Cart> carts) {
        if (carts.isEmpty()) {
            throw new GenericNotFoundException("Cart is empty.");
        }
        Order order = new Order();
        order.setCustomer(customer);
        AtomicInteger totalAmount = new AtomicInteger();
        List<OrderProduct> orderProducts = new ArrayList<>();
        carts.forEach(cart -> {
            ProductVariation productVariation = cart.getProductVariation();
            int quantity = cart.getQuantity();
            //amountPaid for order
            totalAmount.addAndGet(productVariation.getPrice() * quantity);
            //All the validations are done inside  makeOrderProductAndOrderStatus()
            OrderProduct orderProduct = makeOrderProductAndOrderStatus(order, productVariation, quantity);
            orderProducts.add(orderProduct); //Collecting to to save by one go.
        });
        //Save only when everything is OK.
        order.setAmountPaid(totalAmount.get());
        //Set setOrderAddress(order, customer)
        setOrderAddress(customer, order);
        order.getOrderProducts().addAll(orderProducts);
        orderRepository.save(order);
        return order.getId();
    }

    private OrderProduct makeOrderProductAndOrderStatus(Order order, ProductVariation productVariation, int quantity) {
        if (quantity > productVariation.getQuantityAvailable()) {
            throw new ProductVariationExistException("Quantity should not exceed available quantity");
        }
        Product product = productVariation.getProduct();
        if (!productVariation.isActive() || product.isDeleted() || !product.isActive()) {
            throw new GenericNotFoundException("Product/ProductVariation id: " + (productVariation.isActive() ? product.getId() : productVariation.getId()) + " is either deleted or not active!");
        }
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setPrice(productVariation.getPrice() * quantity);
        orderProduct.setProductVariation(productVariation);
        orderProduct.setOrder(order);
        orderProduct.setProductVariationMetadata(productVariation.getMetadata());
        orderProduct.setQuantity(quantity);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setFromStatus(FromStatus.ORDER_PLACED);
        orderStatus.setTransitionNotesComments("Order placed");
        orderStatus.setOrderProduct(orderProduct); //Set Order Product

        orderProduct.setOrderStatus(orderStatus);  //Set Order Status to take cascading effect when creating.
        return orderProduct;
    }

    private void setOrderAddress(Customer customer, Order order) {
        if (customer.getAddresses().size() < 1) {
            throw new GenericNotFoundException("Customer should have at least one address");
        }
        Address customerAddress = customer.getAddresses().get(0);
        order.setCustomerAddressCity(customerAddress.getCity());
        order.setCustomerAddressCountry(customerAddress.getCountry());
        order.setCustomerAddressState(customerAddress.getState());
        order.setCustomerAddressLabel(customerAddress.getLabel());
        order.setCustomerAddressZipCode(customerAddress.getZipCode());
        order.setCustomerAddressAddressLine(customerAddress.getAddressLine());
    }

    public Page<Order> getAllOrders(String email, Pageable pageable) {
        Customer customer = (Customer) userService.findUserByEmail(email);
        return orderRepository.findByCustomer(customer, pageable);
    }

    public Order getOrderById(String email, Long orderId) {
        Customer customer = (Customer) userService.findUserByEmail(email);
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        orderOptional.orElseThrow(() -> new GenericNotFoundException("No order found with id: " + orderId));
        Order order = orderOptional.get();
        if (!order.getCustomer().getId().equals(customer.getId())) {
            throw new GenericNotFoundException("No order found with id: " + orderId);
        }
        return order;
    }

    public void cancelOrder(Long orderProductId, String email) {
        OrderStatus orderStatus = getOrderStatus(orderProductId, email);
        FromStatus fromStatus = orderStatus.getFromStatus();
        if (fromStatus == FromStatus.ORDER_PLACED) {
            orderStatus.setToStatus(ToStatus.CANCELLED);
            orderStatus.setTransitionNotesComments("Order has been cancelled");
            orderStatusRepository.save(orderStatus);
            return;
        }
        throw new OrderStatusException("You cannot cancel the order now.");

    }

    public void returnOrder(Long orderProductId, String email) {
        OrderStatus orderStatus = getOrderStatus(orderProductId, email);
        FromStatus fromStatus = orderStatus.getFromStatus();
        if (fromStatus == FromStatus.DELIVERED) {
            orderStatus.setToStatus(ToStatus.RETURN_REQUESTED);
            orderStatus.setTransitionNotesComments("Request for return of ordered product.");
            orderStatusRepository.save(orderStatus);
            return;
        }
        throw new OrderStatusException("You cannot return the order now.");

    }

    private OrderStatus getOrderStatus(Long orderProductId, String email) {
        Customer customer = (Customer) userService.findUserByEmail(email);
        Optional<OrderProduct> orderProductOptional = orderProductRepository.findById(orderProductId);
        orderProductOptional.orElseThrow(() -> new GenericNotFoundException("No Order Product found with id: " + orderProductId));
        OrderProduct orderProduct = orderProductOptional.get();
        if (!orderProduct.getOrder().getCustomer().getId().equals(customer.getId())) {
            throw new GenericNotFoundException("No Order Product found with id: " + orderProductId);
        }
        return orderProduct.getOrderStatus();
    }
}

