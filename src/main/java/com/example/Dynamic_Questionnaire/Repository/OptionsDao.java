package com.example.Dynamic_Questionnaire.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;

@Transactional
@Repository
public interface OptionsDao extends JpaRepository<Options, UUID> {

	public List<Options> findByTopicUuid(UUID topicUuid);

	public List<Options> findByQuestionnaireUuid(UUID questionnaireUuid);

	// 刪除整份問卷
	public void deleteByQuestionnaireUuid(UUID questionnaireUuid);

	// 刪除題目標題
	public void deleteByTopicUuid(UUID topicUuid);

	// 刪除整份問卷
	public void deleteByQuestionnaireUuidIn(List<UUID> questionnaireUuid);

	public Optional<Options> findByQuestionName(String name);
}
