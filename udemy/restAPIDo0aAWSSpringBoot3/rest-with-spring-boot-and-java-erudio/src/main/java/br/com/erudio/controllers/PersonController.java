package br.com.erudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.services.PersonServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin
@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing people")
public class PersonController {
	
	@Autowired
	private PersonServices service;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML} )
	@Operation(summary = "Finds all people", description = "Finds all People", tags = {"People"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = {
									@Content(
											mediaType = "application/json",
											array = @ArraySchema(schema = @Schema( implementation = PersonVO.class)))
							}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unahthotrized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),					
	})	
	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "size", defaultValue = "12") Integer size, 
		@RequestParam(value = "direction", defaultValue = "asc") String direction) 
	{
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@GetMapping(value = "/findPersonByName/{firstName}",
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML} )
	@Operation(summary = "Finds people by Name", description = "Finds people by Name", tags = {"People"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = {
									@Content(
											mediaType = "application/json",
											array = @ArraySchema(schema = @Schema( implementation = PersonVO.class)))
							}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unahthotrized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),					
	})	
	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findPersonsByName(
		@PathVariable(value = "firstName") String firstName,
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "size", defaultValue = "12") Integer size, 
		@RequestParam(value = "direction", defaultValue = "asc") String direction) 
	{
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findPersonsByName(firstName, pageable));
	}

	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}",
		 produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Finds a Person", description = "Finds a Person", tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = PersonVO.class))
					}),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unahthotrized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),					
	})	
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br"} )
	@PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Adds a new Person by passing a JSON, XML or YAML", description = "Adds a new Person by passing a JSON, XML or YAML", tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
							@Content(schema = @Schema( implementation = PersonVO.class))
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unahthotrized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),					
	})	
	public PersonVO create(@RequestBody PersonVO PersonVO) {
		return service.create(PersonVO);
	}
	
	@PostMapping(value = "/v2", consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public PersonVOV2 createV2(@RequestBody PersonVOV2 PersonVO) {
		return service.createV2(PersonVO);
	}	
	
	@PutMapping(
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Updates a Person by passing a JSON, XML or YAML", description = "Updates a Person by passing a JSON, XML or YAML", tags = {"People"},
	responses = {
			@ApiResponse(description = "Updated", responseCode = "200",
					content = {
							@Content(schema = @Schema( implementation = PersonVO.class))
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unahthotrized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),					
	})	
	public PersonVO update(@RequestBody PersonVO PersonVO) {
		return service.update(PersonVO);
	}
	
	@PatchMapping(value = "/{id}",
		 produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Disable a specific person by your ID", description = "Disable a specific person by your ID", tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = PersonVO.class))
					}),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unahthotrized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),					
	})	
	public PersonVO disablePerson(@PathVariable(value = "id") Long id) {
		return service.disablePerson(id);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes a Person by passing in a JSON, XML or YAML", description = "Deletes a Person by passing in a JSON, XML or YAML", tags = {"People"},
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unahthotrized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),					
	})		
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}