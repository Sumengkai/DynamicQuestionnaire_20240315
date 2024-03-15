package com.example.Dynamic_Questionnaire.Repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dynamic_Questionnaire.Entity.PeopleChoose;
import com.example.Dynamic_Questionnaire.Entity.Options;

@Transactional
@Repository
public interface PeopleChooseDao extends JpaRepository<PeopleChoose, UUID> {
	public List<PeopleChoose> findByOptionsUuid(UUID opUuid);

	public void deleteByQuestionnaireUuid(UUID questionnaireUuid);

	public List<PeopleChoose> findByQuestionnaireUuid(UUID questionnaireUuid);

	public List<PeopleChoose> findByPeopleId(UUID propleId);

	// 刪除整份問卷
	public void deleteByQuestionnaireUuidIn(List<UUID> questionnaireUuid);
}
