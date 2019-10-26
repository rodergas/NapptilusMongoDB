package project.POJO;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

public class OompaLoompa {
	
	@Id
	private String id;
	
	@NotNull(message="Name cannot be null")
	private String name;
	
	@NotNull(message="Age cannot be null")
	private Integer age;
	
	@NotNull(message="Job cannot be null")
	private String job;
	
	private Integer height;
	
	private Double weight; 
	
	private String description;
	
	public OompaLoompa() {
		super();
	}
	
	public OompaLoompa(@NotNull(message = "Name cannot be null") String name,
			@NotNull(message = "Age cannot be null") Integer age, @NotNull(message = "Job cannot be null") String job,
			Integer height, Double weight, String description) {
		super();
		this.name = name;
		this.age = age;
		this.job = job;
		this.height = height;
		this.weight = weight;
		this.description = description;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getJob() {
		return job;
	}
	
	public void setJob(String job) {
		this.job = job;
	}
	
	public Integer getHeight() {
		return height;
	}
	
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public Double getWeight() {
		return weight;
	}
	
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	

}
