package com.ttn.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class UserVController {


    //URI versioning
    @GetMapping("/v1/user")

    public UserV1 userV1() {
        return new UserV1(1, "Saurabh");
    }

    @GetMapping("/v2/user")
    public UserV2 userV2() {
        return new UserV2(1, "Daiho", "Ekhe", 25, "M");
    }

    //Request Parameter versioning
    @GetMapping(value = "/user/param", params = "version=1")
    public UserV1 paramV1() {
        return new UserV1(1, "Saurabh");
    }

    @GetMapping(value = "/user/param", params = "version=2")
    public UserV2 paramV2() {
        return new UserV2(1, "Daiho", "Ekhe", 25, "M");
    }

    //Custom Header Versioning
    @GetMapping(value = "/user/header", headers = "X-API-VERSION=1")
    public UserV1 headerV1() {
        return new UserV1(1, "Saurabh");
    }

    @GetMapping(value = "/user/header", headers = "X-API-VERSION=2")
    public UserV2 headerV2() {
        return new UserV2(1, "Daiho", "Ekhe", 25, "M");
    }

    //MimeType Versioning
    @GetMapping(value = "/user/produces", produces = "application/com.ttn.app-v1+json")
    public UserV1 producesV1() {
        return new UserV1(1, "Saurabh");
    }

    @GetMapping(value = "/user/produces", produces = "application/com.ttn.app-v2+json")
    public UserV2 producesV2() {
        return new UserV2(1, "Daiho", "Ekhe", 25, "M");
    }

}
