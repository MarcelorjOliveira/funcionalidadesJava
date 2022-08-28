package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;

	public List<PersonVO> findAll() {

		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		persons.stream().forEach(p -> p.add( linkTo(methodOn(PersonController.class).findById( p.getKey() )).withSelfRel()) );
		
		return persons;
	}

	public PersonVO findById(Long id) {
		
		logger.info("Finding one PersonVO!");
		
		PersonVO PersonVO = new PersonVO();
		PersonVO.setFirstName("Leandro");
		PersonVO.setLastName("Costa");
		PersonVO.setAddress("Uberlândia - Minas Gerais - Brasil");
		PersonVO.setGender("Male");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
				
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class); 
		
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return vo;
		
	}
	
	public PersonVO create(PersonVO person) {

		logger.info("Creating one PersonVO!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		
		logger.info("Updating one PersonVO!");
		
		var entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one PersonVO!");

		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

		repository.delete(entity);
	}
	
	private PersonVO mockPerson(int i) {
		
		PersonVO PersonVO = new PersonVO();
		PersonVO.setFirstName("PersonVO name " + i);
		PersonVO.setLastName("Last name " + i);
		PersonVO.setAddress("Some address in Brasil " + i);
		PersonVO.setGender("Male");
		return PersonVO;
	}

	public PersonVOV2 createV2(PersonVOV2 person) {

		logger.info("Creating one PersonVO with V2!");
		
		var entity = mapper.convertVoToEntity(person);
		
		var vo = mapper.convertEntityToVo(repository.save(entity)); 
		
		return vo;
	}
}