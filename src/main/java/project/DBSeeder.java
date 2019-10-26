package project;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import project.POJO.OompaLoompa;
import project.repository.OompaLoompaRepositoryMongoDB;

@Component
public class DBSeeder implements CommandLineRunner {
	
	private final OompaLoompaRepositoryMongoDB oompaLoompaRepositoryMongoDB;
	
	public DBSeeder(OompaLoompaRepositoryMongoDB oompaLoompaRepositoryMongoDB) {
		this.oompaLoompaRepositoryMongoDB = oompaLoompaRepositoryMongoDB;
	}
	
	@Override
    public void run(String... args) {
	
		OompaLoompa ol = new OompaLoompa("Robert", 20,"Carpenter",130,40.0 ,  "He likes helping his teammates");
		
		oompaLoompaRepositoryMongoDB.deleteAll();
		
		oompaLoompaRepositoryMongoDB.insert(Arrays.asList(ol,ol,ol,ol,ol));
		
	}
	
}
