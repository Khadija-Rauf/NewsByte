package com.a2.newsbytes.newspaper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewspaperService {
    @Autowired
    private NewspaperRepository newspaperRepository;


    public Newspaper getNewspaperByName(String name){
        return newspaperRepository.findByName(name);
    }

    public Newspaper getNewspaperByType(String type){
        return newspaperRepository.findByType(type);
    }

}
