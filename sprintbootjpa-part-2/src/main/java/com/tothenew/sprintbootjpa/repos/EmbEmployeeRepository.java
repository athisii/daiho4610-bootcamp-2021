package com.tothenew.sprintbootjpa.repos;

import com.tothenew.sprintbootjpa.entities.EmbEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmbEmployeeRepository extends JpaRepository<EmbEmployee, Integer> {
}
