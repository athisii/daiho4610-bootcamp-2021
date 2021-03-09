package com.ttn.restfulwebservices.I18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/i18n")
@RestController
public class I18NController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/q1")
    public String greetInternalized() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/q2")
    public String helloUser(@RequestParam String username) {
        return messageSource.getMessage("hello.message", null, LocaleContextHolder.getLocale()) + " " + username;
    }
}
