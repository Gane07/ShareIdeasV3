package com.shareideas.ideas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shareideas.ideas.model.Ideas;

@Repository
public interface IdeasRepository extends JpaRepository<Ideas,Long>{
	
//	public Optional<List<Ideas>> getByUsername(String username);

	public Optional<List<Ideas>> getByUsername(String username);
	
	public Optional<List<Ideas>> getByTitle(String title);
	
	public boolean deleteByUsername(String username);

	public Optional<Ideas> getByUsernameAndTitle(String username,String title);
	
	public Optional<List<Ideas>> getByUsernameNot(String username);
	
	public Ideas getById(Long id);
	
}
