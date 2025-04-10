package com.example.java.springboot.securitybatch2.Repository;

import com.example.java.springboot.securitybatch2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
