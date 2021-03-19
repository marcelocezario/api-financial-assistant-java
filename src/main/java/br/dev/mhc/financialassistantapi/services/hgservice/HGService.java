package br.dev.mhc.financialassistantapi.services.hgservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import br.dev.mhc.financialassistantapi.services.hgservice.model.HGModel;

@Service
public class HGService {
	
	@Value("${api.hgfinance.key}")
	private String key;
	
//	private String urlApi = "https://api.hgbrasil.com/finance?key=" + key;
	
	public HGModel findCurrencyExchange() {
		
		String json;
		Gson gson = new Gson();
		RestTemplate restTemplate = new RestTemplate();
		
		UriComponents uri = UriComponentsBuilder
				.newInstance()
				.scheme("https")
				.host("api.hgbrasil.com")
				.path("finance")
				.queryParam("key", this.key)
				.build();
				
		json = restTemplate.getForObject(uri.toUriString(), String.class);
		
		return gson.fromJson(json, HGModel.class);
	}
}
