package com.example.RecipeHub.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PaginationUtil {
	public static Pageable generatePageable(Integer page, Integer size, String sortBy, String direction) {
		if(sortBy == null || sortBy.isEmpty()) return PageRequest.of(page, size);
		else if(direction.equals("desc")) return PageRequest.of(page, size, Sort.by(Direction.DESC ,sortBy));
		else if(direction.equals("asc")) return PageRequest.of(page, size, Sort.by(Direction.ASC ,sortBy));
		else return PageRequest.of(page, size, Sort.by(sortBy));
	} 
}
