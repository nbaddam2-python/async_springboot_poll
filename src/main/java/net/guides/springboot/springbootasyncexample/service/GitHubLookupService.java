package net.guides.springboot.springbootasyncexample.service;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.guides.springboot.springbootasyncexample.model.User;

@Service
public class GitHubLookupService {

	private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);
	public static final HashMap<Integer, String> pollMap = new HashMap<Integer, String>();
    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        logger.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(10000L);
        pollMap.put(123, "SUCEESS");
        CompletableFuture<User> future = CompletableFuture.completedFuture(results);
        return future;
    }
}
