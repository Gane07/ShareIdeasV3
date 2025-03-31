package com.shareideas.ideas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.shareideas.ideas.model.Ideas;
import com.shareideas.ideas.repository.IdeasRepository;

@Service
public class IdeasServiceImpl implements IdeasService {

	@Autowired
	IdeasRepository repo;
	
	@Autowired
    private RestTemplate restTemplate;
	

	@Override
	public Ideas addIdea(Ideas ideas) {

		Ideas idea = repo.save(ideas);

		return idea;
	}

	@Override
	public List<Ideas> getIdea(String title) {

		Optional<List<Ideas>> optional = repo.getByTitle(title);

		if (optional.isPresent()) {
			List<Ideas> idea = optional.get();
			return idea;
		} 
		else {
			return null;
		}

	}
	
	@Override
	public Ideas getIdeaByNameAndTitle(String username,String title) {
		
		Optional<Ideas> optional = repo.getByUsernameAndTitle(username,title);

		if (optional.isPresent()) {
			Ideas idea = optional.get();
			return idea;
		} 
		else {
			return null;
		}
		
	}

	@Override
	public List<Ideas> getAllIdeas(String username) {

		Optional<List<Ideas>> optional = repo.getByUsername(username);

		if (optional.isPresent()) {
			List<Ideas> list = optional.get();
			return list;
		} 
		else {
			return null;
		}

	}
	
	@Override
	public List<Ideas> getAllIdeasExceptUser(String username){
		
		Optional<List<Ideas>> optional = repo.getByUsernameNot(username);
		
		if(optional.isPresent()) {
			List<Ideas> userNot = optional.get();
			return userNot;
		}
		else {
			return null;
		}
		
	}
	
	@Override
	public Ideas getIdeasById(Long id) {
		
		Ideas idea = repo.getById(id);
		
		if(idea != null) {
			return idea;
		}
		else {
			return null;
		}
		
	}

	@Override
	public Ideas updateIdea(Ideas ideas) {

		Optional<Ideas> optional = repo.getByUsernameAndTitle(ideas.getUsername(),ideas.getTitle());

		if (optional.isPresent()) {
			Ideas idea = optional.get();
			idea.setDescription(ideas.getDescription());
			idea.setStatus(ideas.getStatus());
			idea.setDocument(ideas.getDocument());
			idea.setImage(ideas.getImage());
			Ideas updatedIdea = repo.save(idea);
			return updatedIdea;
		} 
		else {
			return null;
		}

	}

	@Override
	public boolean deleteIdea(String username,String title) {

		Optional<Ideas> optional = repo.getByUsernameAndTitle(username,title);

		if (optional.isPresent()) {
			Ideas idea = optional.get();
			Long ideaId = idea.getId();
			restTemplate.delete("http://COMMENT-SERVICE:8084/comments/deletebyid/" + ideaId);
			repo.delete(idea);
			return true;
		} 
		else {
			return false;
		}

	}
	
	public boolean deleteIdeasByUser(String username) {
		
        Optional<List<Ideas>> byUsername = repo.getByUsername(username);
        if(byUsername.isPresent()) {
        	List<Ideas> ideas = byUsername.get();
        	
        	ideas.forEach(idea -> restTemplate.delete("http://COMMENT-SERVICE:8084/comments/deletebyid/" + idea.getId()));
        	repo.deleteAll(ideas);
        	return true;
        }
        else {
        	return false;
        }
        
        
    }
	
	public Ideas giveIdeaByUserAndTitle(String username,String title) {
		
		Optional<Ideas> optional = repo.getByUsernameAndTitle(username,title);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			return null;
		}
		
	}

}
