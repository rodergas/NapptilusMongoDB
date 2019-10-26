package project.service;

import java.util.Optional;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import project.POJO.OompaLoompa;
import project.projections.OompaLoompaProjection;
import project.repository.OompaLoompaRepositoryMongoDB;

@Service
public class OompaLoompaService {
	
	private final OompaLoompaRepositoryMongoDB oompaLoompaRepositoryMongoDB;
	
	
	public OompaLoompaService(OompaLoompaRepositoryMongoDB oompaLoompaRepositoryMongoDB) {
		this.oompaLoompaRepositoryMongoDB = oompaLoompaRepositoryMongoDB;
	}


	public Page<OompaLoompaProjection> getAllOompaLoompas(Pageable page) {
		return oompaLoompaRepositoryMongoDB.findAllProjectedBy(page);
	}
	
	@Cacheable(cacheNames="oompaLoompas", key="#id")
	public Optional<OompaLoompa> getOompaLoompaById(String id)  {
		return oompaLoompaRepositoryMongoDB.findById(id);
	}
		
	public OompaLoompa insertOompaLoompa(OompaLoompa oompaLoompa) {
		return oompaLoompaRepositoryMongoDB.insert(oompaLoompa);
	}
	
	@CachePut(cacheNames="oompaLoompas", key="#id")
	public Optional<OompaLoompa> saveOompaLoompa(OompaLoompa updateOompaLoompa, String id) {
		return oompaLoompaRepositoryMongoDB.findById(id)
			.map(ol -> {
				updateOompaLoompa.setId(ol.getId());
				return oompaLoompaRepositoryMongoDB.save(updateOompaLoompa);
			});
	}
}
