package br.dev.mhc.financialassistantapi.utils;

import java.util.Random;

public class Tool {
	
	public static String stringGenerator(Integer numberOfCharacters) {
		char[] vet = new char[numberOfCharacters];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private static char randomChar() {
		Random rand = new Random();

		int opt = rand.nextInt(3);

		// gera um digito
		if (opt == 0) {
			return (char) (rand.nextInt(10) + 48);
		}
		// gera letra maiúscula
		else if (opt == 1) {
			return (char) (rand.nextInt(26) + 65);
		}
		// gera letra minúscula
		else {
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
