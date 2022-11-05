package com.cookysys.social_media_project.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cookysys.social_media_project.services.ValidateService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/validate")
@RequiredArgsConstructor
public class ValidateController {

	private final ValidateService validateService;

	@GetMapping("/username/exists/@{username}")
	public boolean userNameExists(@PathVariable String username) {
		return validateService.userNameExists(username);
	}

	@GetMapping("/username/available/@{username}")
	public boolean userNameAvailable(@PathVariable String username) {
		return validateService.userNameAvailable(username);
	}

	@GetMapping("/tag/exists/{label}")
	public boolean labelExists(@PathVariable String label) {
		return validateService.labelExists(label);
	}

}
