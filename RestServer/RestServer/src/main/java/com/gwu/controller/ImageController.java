package com.gwu.controller;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.JsonObject;
import com.gwu.model.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "rest")
public class ImageController {


    @RequestMapping(value = "image", method = RequestMethod.POST)
    @ResponseBody	
    public Image[] putImageAndReturn(Image image) {
        System.out.println(image.toString());
       JsonObject result = new JsonObject();
        result.addProperty("test","value");

       Image [] response = new Image[2];
       response[0]= new Image(12,23,"ast");
       response[1]= new Image(12,23,"asdf");
       return response;
    }


}

