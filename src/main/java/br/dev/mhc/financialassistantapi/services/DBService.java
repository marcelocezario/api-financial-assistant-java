package br.dev.mhc.financialassistantapi.services;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DBService {

	@Value("${dbseed.admin_user.email}")
	private String adminEmail;

	@Value("${dbseed.admin_user.password}")
	private String adminPassword;

	public void databaseSeeding() throws ParseException {
	}
}
