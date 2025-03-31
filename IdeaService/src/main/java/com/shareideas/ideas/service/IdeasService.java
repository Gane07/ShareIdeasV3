package com.shareideas.ideas.service;

import java.util.List;
import java.util.Optional;

import com.shareideas.ideas.model.Ideas;

public interface IdeasService {
	
	public Ideas addIdea(Ideas ideas);
	public List<Ideas> getIdea(String title);
	public List<Ideas> getAllIdeas(String username);
	public Ideas updateIdea(Ideas ideas);
	public boolean deleteIdea(String username,String title);
	public boolean deleteIdeasByUser(String username);
	public List<Ideas> getAllIdeasExceptUser(String username);
	public Ideas getIdeaByNameAndTitle(String username, String title);
	public Ideas giveIdeaByUserAndTitle(String username, String title);
	public Ideas getIdeasById(Long id);
	

}
