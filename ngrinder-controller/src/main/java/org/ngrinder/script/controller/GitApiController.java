package org.ngrinder.script.controller;

import lombok.RequiredArgsConstructor;
import org.ngrinder.model.User;
import org.ngrinder.script.model.GitConfig;
import org.ngrinder.script.service.FileEntryService;
import org.ngrinder.script.service.GitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;


/**
 * Git manipulation API controller.
 *
 * @since 3.5.0
 */
@RestController
@RequestMapping("/git/api")
@RequiredArgsConstructor
public class GitApiController {

	private final GitService gitService;

	private final FileEntryService fileEntryService;

	/**
	 * Get git config.
	 *
	 * @since 3.5.0
	 */
	@GetMapping("/config")
	public List<GitConfig> getGitHubConfig(User user) throws FileNotFoundException {
		return gitService.getGitHubConfig(user);
	}

	@GetMapping("/scripts")
	public List<String> getScripts(User user, GitConfig gitConfig, boolean refresh) {
		if (refresh) {
			gitService.evictGitHubScriptCache(user);
		}
		return gitService.getScripts(user, gitConfig);
	}

	@PostMapping("/config")
	public void createGitConfig(User user) {
		fileEntryService.createGitHubConfig(user);
	}
}
