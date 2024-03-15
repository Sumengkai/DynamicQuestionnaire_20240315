package com.example.Dynamic_Questionnaire.Repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dynamic_Questionnaire.Entity.PeopleChoose;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;
import com.example.Dynamic_Questionnaire.Entity.WriteDate;

@Transactional
@Repository
public interface WriteDateDao extends JpaRepository<WriteDate, UUID> {
	public void deleteByQuestionnaireUuid(UUID questionnaireUuid);

	public List<WriteDate> findByQuestionnaireUuid(UUID questionnaireUuid);
	public List<WriteDate> findByQuestionnaireUuidOrderByWriteDateTimeDesc(UUID questionnaireUuid);

	public List<WriteDate> findByOrderByWriteDateTimeDesc();

	// 刪除整份問卷資訊
	public void deleteByQuestionnaireUuidIn(List<UUID> questionnaireUuid);
}
