package com.duoc.backend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.util.HtmlUtils;


@RestController
public class SecuredController {

    @RequestMapping("greetings")
    public String greetings(@RequestParam(value = "name", defaultValue = "World") String name) {
        // escapa cualquier carácter para prevenir XSS
        String safeName = HtmlUtils.htmlEscape(name);
        return "Hello {" + safeName + "}";
    }
}