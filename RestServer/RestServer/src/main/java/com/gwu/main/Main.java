package com.gwu.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.gwu.model.Image;
import com.gwu.model.ImageDBObject;
import com.gwu.tools.SpringMongoConfig;


public class Main {
	private static String testEncoding ="/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB\nAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEB\nAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/wAARCAmQDMADASIA\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD+quBW\nXALqZN3C7vdwD09jwD6cnLZltWWMyhnyXbozf72cDJ9sfVuvekuWBYyFdv7w7v8Aeccc/wCz+e7k\nkE0kb7t+du5MYVu3zHPGf5n0yc4rvcVJOL2f+bs/6v0unqfQGkCMld3zNjarfVh6nqOnseclsi4r\n8vkfLJHJtbafVsHrznGc9uRk5zWTDKG3v8qtxu+X/lkC2D19ifTqCTnJvxzoFcZZl/vt/By2f/r8\nkdOCQcMC0ygAj5dqr0bOOS3qenAzycZ681WVmRnG5lZjj5V9z6k+nY+mePvSSP8Ac2hu+d36k8/p\n+RqDJVi38SMu5WXnkv8AzIyefT05zofw18//AEqoBbiJ5PKnbz69SD1zjse/GavNIQAwCs38Uqv/\n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\n7to2uVPmPy/rn2IycHoRkEh5CHadq";

	public static void main(String [] args){
		ApplicationContext ctx = 
	             new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    
        Image image = new Image();
        image.setLat(13);
        image.setLongitude(15);
        image.setBase64Encoding(testEncoding);
        ImageDBObject imageObject = new ImageDBObject();
		imageObject.setData(image.getBase64Encoding());
		imageObject.setLat(image.getLat());
		imageObject.setLongitude(image.getLongitude());
		mongoOperation.save(imageObject);
	
		Query getImagesByLocation = new Query(
				Criteria.where("lat").is(imageObject.getLat()).andOperator(
						Criteria.where("longitude").is(imageObject.getLongitude())));
		List<ImageDBObject> list = (mongoOperation.find(getImagesByLocation,ImageDBObject.class));
		System.out.println(list.size());
		Image[] response = new Image[list.size()];
		for(int i = 0; i < list.size(); i++){
			ImageDBObject currentObject = list.get(i);
			Image tmp = new Image();
			tmp.setBase64Encoding(currentObject.getDataAsString());
			tmp.setLat((float) currentObject.getLat());
			tmp.setLongitude((float)currentObject.getLongitude());
			 response[i]=tmp;
			 System.out.println(tmp.toString());
		}
		mongoOperation.dropCollection(ImageDBObject.class);
	}
}
