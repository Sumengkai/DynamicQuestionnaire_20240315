package com.example.Dynamic_Questionnaire.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.People;

@Repository
public interface PeopleDao extends JpaRepository<People, UUID> {

}
