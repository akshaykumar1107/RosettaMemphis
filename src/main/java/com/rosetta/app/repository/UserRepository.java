package com.rosetta.app.repository;

import com.rosetta.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository is redundant. Only used for custom DAO.
public interface UserRepository extends JpaRepository<User, Long>
{
}
