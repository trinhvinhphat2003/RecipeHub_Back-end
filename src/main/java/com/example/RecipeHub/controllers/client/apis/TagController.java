package com.example.RecipeHub.controllers.client.apis;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.client.dtos.TagDTO;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.services.TagService;
import com.example.RecipeHub.utils.TagDefaultConstant;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/")
public class TagController {
	
	private final TagService tagService;
	
	public TagController(TagService tagService) {
		super();
		this.tagService = tagService;
	}

	@GetMapping("global/tags/{user_id}")
	public ResponseEntity<ArrayList<TagDTO>> getAllUserTags(@PathVariable("user_id") Long userId) {
		ArrayList<String> tagsDefault = TagDefaultConstant.TAGS_DEFAULT;
		ArrayList<TagDTO> response = tagService.getAllTagsByUserId(userId);
		Iterator<TagDTO> tagDtoIterator = response.iterator();
		while(tagDtoIterator.hasNext()) {
			TagDTO tagDTO = tagDtoIterator.next();
			if(tagsDefault.contains(tagDTO.getTagName().toLowerCase())) {
				response.remove(tagDTO);
				tagDtoIterator = response.iterator();
			}
		}
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("user/tag/{tag_id}") 
	public ResponseEntity<String> deleteOneTag(@AuthenticationPrincipal User user, @PathVariable("tag_id") Long tagId) {
		tagService.deleteOneTagByIdForUser(tagId, user.getUserId());
		return ResponseEntity.ok("this tag have been deleted");
	}

}
