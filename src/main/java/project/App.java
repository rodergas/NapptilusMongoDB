package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import project.repository.OompaLoompaRepositoryMongoDB;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableCaching
public class App  {
	
    public static void main( String[] args ) {
    	SpringApplication.run(App.class, args);
    }
}
