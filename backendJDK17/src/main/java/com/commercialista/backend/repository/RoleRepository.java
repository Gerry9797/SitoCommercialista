package com.commercialista.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.commercialista.backend.models.ERole;
import com.commercialista.backend.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
  
	@Query(value = "DELETE FROM user_roles WHERE user_id = ?1 ", nativeQuery = true)
	@Modifying
	int deleteByUserId(Long userId);
}
