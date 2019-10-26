package project.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import project.POJO.OompaLoompa;
import project.projections.OompaLoompaProjection;
import project.service.OompaLoompaService;

@RestController
public class OompaLoompaController {
	
	private final OompaLoompaService oompaLoompaService;
	
	public OompaLoompaController(OompaLoompaService oompaLoompaService) {
		this.oompaLoompaService = oompaLoompaService;
	}

	/*
	 * Use size and page parameters for pagination
	 * e.g. /oompaLoompas?size=2&page=2
	 */
	@GetMapping("/oompaLoompas")
    public Page<OompaLoompaProjection> getAll(Pageable page) {
       return oompaLoompaService.getAllOompaLoompas(page);
    }

    @PostMapping(value = "/oompaLoompas")
    public ResponseEntity<Void> insertOompaLoompa(@Valid @RequestBody OompaLoompa oompaLoompa) {
    	OompaLoompa insertedOompaLoompa = oompaLoompaService.insertOompaLoompa(oompaLoompa);

    	URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(insertedOompaLoompa.getId())
                .toUri();
        	    	
    	return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/oompaLoompas/{id}")
    public ResponseEntity<Void> updateOompaLoompa(@Valid @RequestBody OompaLoompa oompaLoompa,
    		@PathVariable String id) {
    	
    	return oompaLoompaService.saveOompaLoompa(oompaLoompa, id)
	    		.map(ol -> ResponseEntity.noContent().<Void>build())
	    		.orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/oompaLoompas/{id}")
    public ResponseEntity<OompaLoompa> getById(@PathVariable String id) {
        return oompaLoompaService.getOompaLoompaById(id)
     		   .map(ResponseEntity::ok)
     		   .orElse(ResponseEntity.notFound().build());
    }
}
