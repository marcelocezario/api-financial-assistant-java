package br.dev.mhc.financialassistantapi.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestResource {

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
			list.add(UUID.nameUUIDFromBytes(("adminUser").getBytes()).toString());
			list.add(UUID.nameUUIDFromBytes(("basicUser").getBytes()).toString());
			sleepTime(1L);
		}

		return ResponseEntity.ok().body(list);
	}
}
