package com.example.Dynamic_Questionnaire.Entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "topic_title")
public class TopicTitle {
	@Id
	@Column(name = "topic_uuid")
	@Type(type = "uuid-char")
	private UUID topicUuid;
	
	@Column(name = "questionnaire_uuid")
	@Type(type = "uuid-char")
	private UUID questionnaireUuid;
	
	@Column(name = "topic_name")
	private String topicName;
	
	@Column(name = "essential")
	private boolean essential;
	
	@Column(name = "only_or_many")
	private boolean onlyOrMany;

	public TopicTitle() {

	}

	public TopicTitle(UUID topicUuid, UUID questionnaireUuid, String topicName, boolean essential, boolean onlyOrMany) {
		this.topicUuid = topicUuid;
		this.questionnaireUuid = questionnaireUuid;
		this.topicName = topicName;
		this.essential = essential;
		this.onlyOrMany = onlyOrMany;

	}

	public UUID getTopicUuid() {
		return topicUuid;
	}

	public void setTopicUuid(UUID topicUuid) {
		this.topicUuid = topicUuid;
	}

	public UUID getQuestionnaireUuid() {
		return questionnaireUuid;
	}

	public void setQuestionnaireUuid(UUID questionnaireName) {
		this.questionnaireUuid = questionnaireName;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public boolean isEssential() {
		return essential;
	}

	public void setEssential(boolean essential) {
		this.essential = essential;
	}

	public boolean isOnlyOrMany() {
		return onlyOrMany;
	}

	public void setOnlyOrMany(boolean onlyOrMany) {
		this.onlyOrMany = onlyOrMany;
	}

}
