package demo.service;

import demo.domain.Order;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailServiceTest {

    private EmailService emailService;
    private Order order;

    @BeforeEach
    void setup() {
        emailService = new EmailService();
        order = new Order();
    }

    @Test
    void shouldBeInstanceOfEmailService() {
        MatcherAssert.assertThat(emailService, instanceOf(EmailService.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Runtime exception", "Runtime", "exception"})
    public void whenExceptionThrown_thenAssertionSucceeds(String expectedMessage) {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            emailService.sendEmail(order);
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void isEmailSent() {
        String emailStatus = "Email sent successfully";
        assertTrue(emailService.sendEmail(order, emailStatus));
    }

}