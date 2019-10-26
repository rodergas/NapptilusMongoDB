package project.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import project.POJO.OompaLoompa;
import project.projections.OompaLoompaProjection;

public interface OompaLoompaRepositoryMongoDB extends MongoRepository<OompaLoompa, String> {
	Page<OompaLoompaProjection> findAllProjectedBy(Pageable page);
}
