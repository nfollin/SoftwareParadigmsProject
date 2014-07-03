package com.gwu.controller;
import java.util.List;

import com.google.gson.JsonObject;
import com.gwu.model.*;
import com.gwu.tools.SpringMongoConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "rest")
public class ImageController {

	public ImageController(){
		
	}
    @RequestMapping(value = "image", method = RequestMethod.POST)
    	
    public @ResponseBody ImageArray putImageAndReturn(@RequestBody Image image) {
    	ApplicationContext ctx = 
	             new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
      
        ImageDBObject imageObject = new ImageDBObject();
		imageObject.setData(image.getBase64Encoding());
		imageObject.setLat(image.getLat());
		imageObject.setLongitude(image.getLongitude());
		mongoOperation.save(imageObject);
		
		Query getImagesByLocation = new Query(
				Criteria.where("lat").is(imageObject.getLat()).andOperator(
						Criteria.where("longitude").is(imageObject.getLongitude())));
		List<ImageDBObject> list = (mongoOperation.find(getImagesByLocation,ImageDBObject.class));
		System.out.println("size= "+ list.size());
		Image[] response = new Image[list.size()];
		for(int i = 0; i < list.size(); i++){
			ImageDBObject currentObject = list.get(i);
			Image tmp = new Image();
			tmp.setBase64Encoding(currentObject.getDataAsString());
			tmp.setLat((float) currentObject.getLat());
			tmp.setLongitude((float)currentObject.getLongitude());
			 response[i]=tmp;
			 //System.out.println(tmp.toString());
		}
		
		
		ImageArray jsonResponse = new ImageArray();
		jsonResponse.setArray(response);
		//System.out.println(jsonResponse.toString());
		return jsonResponse;
    }


}

