package com.example.Dynamic_Questionnaire.Entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "people_choose")
public class PeopleChoose {
	@Id
	@Column(name = "count_uuid")
	@Type(type = "uuid-char")
	private UUID countUuid;

	@Column(name = "questionnaire_uuid")
	@Type(type = "uuid-char")
	private UUID questionnaireUuid;

	@Column(name = "topic_uuid")
	@Type(type = "uuid-char")
	private UUID topicUuid;

	@Column(name = "options_uuid")
	@Type(type = "uuid-char")
	private UUID optionsUuid;

	@Column(name = "people_id")
	@Type(type = "uuid-char")
	private UUID peopleId;
	
	@Column(name = "question_name")
	private String questionName;

	public PeopleChoose() {
	}

	public PeopleChoose(UUID countUuid, UUID questionnaireUuid, UUID topicUuid, UUID optionsUuid, UUID peopleId,String questionName) {
		this.countUuid = countUuid;
		this.questionnaireUuid = questionnaireUuid;
		this.topicUuid = topicUuid;
		this.optionsUuid = optionsUuid;
		this.peopleId = peopleId;
		this.questionName=questionName;

	}

	public PeopleChoose(UUID countUuid, UUID questionnaireUuid, UUID optionsUuid) {
		this.countUuid = countUuid;
		this.questionnaireUuid = questionnaireUuid;

		this.optionsUuid = optionsUuid;

	}

	public UUID getCountUuid() {
		return countUuid;
	}

	public void setCountUuid(UUID countUuid) {
		this.countUuid = countUuid;
	}

	public UUID getQuestionnaireUuid() {
		return questionnaireUuid;
	}

	public void setQuestionnaireUuid(UUID questionnaireUuid) {
		this.questionnaireUuid = questionnaireUuid;
	}

	public UUID getTopicUuid() {
		return topicUuid;
	}

	public void setTopicUuid(UUID topicUuid) {
		this.topicUuid = topicUuid;
	}

	public UUID getOptionsUuid() {
		return optionsUuid;
	}

	public void setOptionsUuid(UUID optionsUuid) {
		this.optionsUuid = optionsUuid;
	}

	public UUID getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(UUID peopleId) {
		this.peopleId = peopleId;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	

}
