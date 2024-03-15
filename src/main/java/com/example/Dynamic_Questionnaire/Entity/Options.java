package com.example.Dynamic_Questionnaire.Entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "q_options")
public class Options {
	@Id
	@Column(name = "options_uuid")
	@Type(type = "uuid-char")
	private UUID optionsUuid;

	@Column(name = "questionnaire_uuid")
	@Type(type = "uuid-char")
	private UUID questionnaireUuid;

	@Column(name = "topic_uuid")
	@Type(type = "uuid-char")
	private UUID topicUuid;

	@Column(name = "question_name")
	private String questionName;

	@Column(name = "percentage")
	private double percentage;
	
	@Column(name = "man")
	private int man;

	@Column(name = "girl")
	private int girl;

	@Column(name = "total_people")
	private int totalPeople;

	public Options() {

	}

	public Options(UUID optionsUuid, UUID questionnaireName, UUID topicName, String questionName) {
		this.optionsUuid = optionsUuid;
		this.questionnaireUuid = questionnaireName;
		this.topicUuid = topicName;
		this.questionName = questionName;
	}

	public UUID getOptionsUuid() {
		return optionsUuid;
	}

	public void setOptionsUuid(UUID optionsUuid) {
		this.optionsUuid = optionsUuid;
	}

	public UUID getQuestionnaireUuid() {
		return questionnaireUuid;
	}

	public void setQuestionnaireUuid(UUID questionnaireName) {
		this.questionnaireUuid = questionnaireName;
	}

	public UUID getTopicUuid() {
		return topicUuid;
	}

	public void setTopicUuid(UUID topicName) {
		this.topicUuid = topicName;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public int getMan() {
		return man;
	}

	public void setMan(int man) {
		this.man = man;
	}

	public int getGirl() {
		return girl;
	}

	public void setGirl(int girl) {
		this.girl = girl;
	}

	public int getTotalPeople() {
		return totalPeople;
	}

	public void setTotalPeople(int totalPeople) {
		this.totalPeople = totalPeople;
	}

}
