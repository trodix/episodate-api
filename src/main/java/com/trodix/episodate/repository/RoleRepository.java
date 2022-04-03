package com.trodix.episodate.repository;

import java.util.Optional;

import com.trodix.episodate.core.entity.ERole;
import com.trodix.episodate.core.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
