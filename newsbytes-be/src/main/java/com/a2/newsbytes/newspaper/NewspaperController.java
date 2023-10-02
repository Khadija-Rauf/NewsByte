package com.a2.newsbytes.newspaper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/newspapers")
public class NewspaperController {
    @Autowired
    private NewspaperService newspaperService;
}
