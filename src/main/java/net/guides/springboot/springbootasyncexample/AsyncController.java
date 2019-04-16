package net.guides.springboot.springbootasyncexample;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.classic.spi.STEUtil;
import net.guides.springboot.springbootasyncexample.model.User;
import net.guides.springboot.springbootasyncexample.service.GitHubLookupService;

@Controller
@RequestMapping("/test")
public class AsyncController {
	@Autowired
	private GitHubLookupService gitHubLookupService;

	private static final HashMap<Integer, String> pollMap = new HashMap<Integer, String>();

	@RequestMapping(method = RequestMethod.GET, value = "getUser")
	public ResponseEntity<Integer> fetchAccDetails(@RequestParam String user) {
		try {
			CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Integer>(123,HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "poll")
	public ResponseEntity<String> poll(@RequestParam Integer txnId) {

		if (gitHubLookupService.pollMap.get(123) != null) {
			return new ResponseEntity<String>(gitHubLookupService.pollMap.get(123), HttpStatus.OK);
		}

		return new ResponseEntity<String>("IN_PROGRESS", HttpStatus.OK);
	}

}
