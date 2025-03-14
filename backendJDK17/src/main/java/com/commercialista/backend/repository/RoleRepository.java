package com.commercialista.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.commercialista.backend.enums.ERole;
import com.commercialista.backend.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, ERole> {

	@Query(value = "DELETE FROM user_roles WHERE user_id = ?1 ", nativeQuery = true)
	@Modifying
	int deleteByUserId(UUID userId);

	@Modifying
	@Query(value = "DELETE FROM user_roles WHERE user_id IN (:userIds)", nativeQuery = true)
	int deleteByUserIdIn(@Param("userIds") List<UUID> userIds);

}
