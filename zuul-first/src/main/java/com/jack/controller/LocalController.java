package com.jack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本地转发Controller
 */
@RestController
public class LocalController {
    @GetMapping("/local/{name}")
    public String local(@PathVariable String name) {
        return name;
    }
}
