package com.shareideas.ideas.controller;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shareideas.ideas.model.Ideas;
import com.shareideas.ideas.model.IdeasDTO;
import com.shareideas.ideas.repository.IdeasRepository;
import com.shareideas.ideas.service.IdeasService;

@RestController
@RequestMapping("/ideas")
@CrossOrigin("*")
public class IdeasController {

	@Autowired
	IdeasService ideasService;

//	@PostMapping("/add")
//	public ResponseEntity<?> addUserIdea(@RequestBody Ideas ideas) {
//
//		Ideas idea = ideasService.addIdea(ideas);
//
//		if (idea != null) {
//			return new ResponseEntity<Ideas>(idea, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
//		}
//
//	}
	
	
	@Autowired
    private IdeasRepository ideaRepository;

    @GetMapping("/exceptuser/{username}")
    public ResponseEntity<?> getIdeasExceptUser(@PathVariable String username) {
        Optional<List<Ideas>> optional = ideaRepository.getByUsernameNot(username);
        // Sanitize data to replace problematic characters
        
        if(optional.isPresent()) {
        	List<Ideas> ideas = optional.get();
        	ideas.forEach(idea -> {
                idea.setTitle(sanitizeString(idea.getTitle()));
                idea.setUsername(sanitizeString(idea.getUsername()));
                System.out.println("Sanitized idea - Title: " + idea.getTitle() + ", Username: " + idea.getUsername());
            });
            return ResponseEntity.ok(ideas);
        }
        else{
        	return new ResponseEntity<String>("There is no users",HttpStatus.CONFLICT);
        }
        
    }

    private String sanitizeString(String input) {
        if (input == null) return null;
        // Replace en-dash (–) with hyphen (-) or remove non-ASCII if needed
        return input.replace("\u2013", "-");
        // Alternatively, strip all non-ASCII: return input.replaceAll("[^\\x00-\\xFF]", "");
    }
    
//    @GetMapping("/exceptuser/{username}")
//    public ResponseEntity<List<Idea>> getIdeasExceptUser(@PathVariable String username) {
//        List<Idea> ideas = ideaRepository.findByUsernameNot(username);
//        ideas.forEach(idea -> {
//            idea.setTitle(sanitizeString(idea.getTitle()));
//            idea.setUsername(sanitizeString(idea.getUsername()));
//            System.out.println("Sanitized idea - Title: " + idea.getTitle() + ", Username: " + idea.getUsername());
//        });
//        return ResponseEntity.ok(ideas);
//    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getIdeaImage(@PathVariable Long id) {
        Ideas idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));
        byte[] image = idea.getImage(); // Assuming image is stored as byte[]
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // No charset
                .body(image);
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<byte[]> getIdeaDocument(@PathVariable Long id) {
        Ideas idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));
        byte[] document = idea.getDocument(); // Assuming document is stored as byte[]
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF) // No charset
                .body(document);
    }
	
	
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUserIdea(@RequestBody IdeasDTO ideaRequest) {
        try {
            Ideas idea = new Ideas();
            idea.setUsername(ideaRequest.getUsername());
            idea.setTitle(ideaRequest.getTitle());
            idea.setDescription(ideaRequest.getDescription());
            idea.setStatus(ideaRequest.getStatus() != null ? ideaRequest.getStatus() : "Open");

            // Decode base64 strings to byte[] if provided
            if (ideaRequest.getDocument() != null && !ideaRequest.getDocument().isEmpty()) {
                try {
                    idea.setDocument(Base64.getDecoder().decode(ideaRequest.getDocument()));
                } catch (IllegalArgumentException e) {
                    return new ResponseEntity<>("Invalid document base64 data", HttpStatus.BAD_REQUEST);
                }
            }
            if (ideaRequest.getImage() != null && !ideaRequest.getImage().isEmpty()) {
                try {
                    idea.setImage(Base64.getDecoder().decode(ideaRequest.getImage()));
                } catch (IllegalArgumentException e) {
                    return new ResponseEntity<>("Invalid image base64 data", HttpStatus.BAD_REQUEST);
                }
            }

            Ideas savedIdea = ideasService.addIdea(idea);
            if (savedIdea != null) {
                return new ResponseEntity<>(savedIdea, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding idea: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
//	@GetMapping("/document/{id}")
//    public ResponseEntity<?> getIdeaDocument(@PathVariable Long id) {
//        Ideas idea = ideasService.getIdeasById(id);
//        if (idea != null && idea.getDocument() != null) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDispositionFormData("attachment", "document_" + idea.getTitle() + ".pdf");
//            return new ResponseEntity<>(idea.getDocument(), headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Idea or Document Not Found", HttpStatus.NOT_FOUND);
//        }
//    }
//    
//	@GetMapping("/image/{id}")
//    public ResponseEntity<?> getIdeaImage(@PathVariable Long id) {
//        Ideas idea = ideasService.getIdeasById(id);
//        if (idea != null && idea.getImage() != null) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//            return new ResponseEntity<>(idea.getImage(), headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Idea or Image Not Found", HttpStatus.NOT_FOUND);
//        }
//    }

	@GetMapping("get/{title}")
	public ResponseEntity<?> getIdea(@PathVariable String title) {

		List<Ideas> idea = ideasService.getIdea(title);

		if (idea != null) {
			return new ResponseEntity<List<Ideas>>(idea, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Title Not Found", HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("getbyuser/{username}/{title}")
	public ResponseEntity<?> getIdea(@PathVariable String username, @PathVariable String title) {

		Ideas idea = ideasService.getIdeaByNameAndTitle(username, title);

		if (idea != null) {
			return new ResponseEntity<Ideas>(idea, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Title Not Found", HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/getAll/{username}")
	public ResponseEntity<?> gettAllIdeas(@PathVariable String username) {

		List<Ideas> ideas = ideasService.getAllIdeas(username);

		if (ideas != null) {
			return new ResponseEntity<List<Ideas>>(ideas, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<?> getIdeasById(@PathVariable Long id) {

		Ideas ideas = ideasService.getIdeasById(id);

		if (ideas != null) {
			return new ResponseEntity<Ideas>(ideas, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
		}

	}

	
	
//
//	@GetMapping("/exceptuser/{username}")
//	public ResponseEntity<?> getIdeasExceptUser(@PathVariable String username) {
//
//		List<Ideas> ideas = ideasService.getAllIdeasExceptUser(username);
//
//		if (ideas != null) {
//			return new ResponseEntity<List<Ideas>>(ideas, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<String>("There Is No Other Users", HttpStatus.NOT_FOUND);
//		}
//
//	}

//	@PutMapping("/update")
//	public ResponseEntity<?> updateUserIdea(@RequestBody Ideas idea) {
//		Ideas updateIdea = ideasService.updateIdea(idea);
//
//		if (updateIdea != null) {
//			return new ResponseEntity<Ideas>(updateIdea, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
//		}
//
//	}
	
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserIdea(@RequestBody IdeasDTO ideaRequest) {
        try {
            Ideas existingIdea = ideasService.getIdeaByNameAndTitle(ideaRequest.getUsername(), ideaRequest.getTitle());
            if (existingIdea == null) {
                return new ResponseEntity<>("Idea Not Found", HttpStatus.NOT_FOUND);
            }

            if (ideaRequest.getDescription() != null) {
                existingIdea.setDescription(ideaRequest.getDescription());
            }
            if (ideaRequest.getStatus() != null) {
                existingIdea.setStatus(ideaRequest.getStatus());
            } else {
                existingIdea.setStatus("Open");
            }

            // Decode base64 strings to byte[] if provided
            if (ideaRequest.getDocument() != null && !ideaRequest.getDocument().isEmpty()) {
                try {
                    existingIdea.setDocument(Base64.getDecoder().decode(ideaRequest.getDocument()));
                } catch (IllegalArgumentException e) {
                    return new ResponseEntity<>("Invalid document base64 data", HttpStatus.BAD_REQUEST);
                }
            }
            if (ideaRequest.getImage() != null && !ideaRequest.getImage().isEmpty()) {
                try {
                    existingIdea.setImage(Base64.getDecoder().decode(ideaRequest.getImage()));
                } catch (IllegalArgumentException e) {
                    return new ResponseEntity<>("Invalid image base64 data", HttpStatus.BAD_REQUEST);
                }
            }

            Ideas updatedIdea = ideasService.updateIdea(existingIdea);
            if (updatedIdea != null) {
                return new ResponseEntity<>(updatedIdea, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Update Failed", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating idea: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }	
	
	@DeleteMapping("/delete/{username}/{title}")
	public ResponseEntity<?> deleteUserIdea(@PathVariable String username, @PathVariable String title) {

		boolean deleteIdea = ideasService.deleteIdea(username, title);

		if (deleteIdea) {
			return new ResponseEntity<String>("Idea Deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Idea Not Found", HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/deleteBy/{username}")
	public ResponseEntity<?> deleteUserIdeByName(@PathVariable String username) {

		boolean deleteIdea = ideasService.deleteIdeasByUser(username);

		if (deleteIdea) {
			return new ResponseEntity<String>("User Ideas Deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User Ideas Not Found", HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/getbyusernameandtitle/{username}/{title}")
	public ResponseEntity<?> getByUsernameAndPassword(@PathVariable String username,@PathVariable String title) {

		Ideas deleteIdea = ideasService.giveIdeaByUserAndTitle(username,title);

		if (deleteIdea != null) {
			return new ResponseEntity<Ideas>(deleteIdea, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User Ideas Not Found", HttpStatus.NOT_FOUND);
		}

	}

//	@DeleteMapping("/user/{username}")
//    public ResponseEntity<String> deleteUserIdeas(@PathVariable String username) {
//        ideasService.deleteIdeasByUser(username);
//        return ResponseEntity.ok("All ideas of user " + username + " deleted.");
//    }

}
