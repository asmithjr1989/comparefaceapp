package com.revature.controller;

import com.revature.service.CompareFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CompareFaceController {

    private CompareFaceService compareFaceService;

    @Autowired
    public CompareFaceController(CompareFaceService compareFaceService){
        this.compareFaceService = compareFaceService;
    }

    @RequestMapping("/same-face")
    public String getSameFace(){
        return compareFaceService.compareSameFace("will.jpeg","will2.jpeg");
    }

    @RequestMapping("/different-face")
    public String getDifferentFace(){
        return compareFaceService.compareDiffFaces("will1.jpeg","robert1.jpeg");
    }
}
