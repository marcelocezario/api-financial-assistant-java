package br.dev.mhc.financialassistantapi.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestResource {

	@Autowired
	BCryptPasswordEncoder pe;

	private void sleepTime(Long timeMilliseconds) {
		try {
			Thread.sleep(timeMilliseconds);
		} catch (Exception e) {

		}
	}

	@GetMapping
	public ResponseEntity<List<String>> test() {

		List<String> list = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			list.add(UUID.randomUUID().toString());
			list.add(pe.encode(""));
			sleepTime(1L);
		}

		return ResponseEntity.ok().body(list);
	}
}
