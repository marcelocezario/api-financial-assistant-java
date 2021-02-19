package br.dev.mhc.financialassistantapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.mhc.financialassistantapi.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
