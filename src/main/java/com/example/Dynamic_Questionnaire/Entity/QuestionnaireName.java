package com.example.Dynamic_Questionnaire.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "questionnaire")
public class QuestionnaireName {
	@Id
	@Column(name = "questionnaire_uuid")
	@Type(type = "uuid-char")
	private UUID questionnaireUuid;
	@Column(name = "questionnaire_name")
	private String questionnaireName;
	@Column(name = "start_date")
	private LocalDate startDate;
	@Column(name = "end_date")
	private LocalDate endDate;
	@Column(name = "state_open_or_closure")
	private boolean stateOpenOrClosure;
	@Column(name = "description")
	private String description;
	@Column(name = "creat_time")
	private LocalDateTime creatTime;

	public QuestionnaireName() {
	}

	public QuestionnaireName(UUID questionnaireUuid, String questionnaireName, LocalDate startDate, LocalDate endDate,
			boolean stateOpenOrClosure, String description, LocalDateTime creatTime) {
		this.questionnaireUuid = questionnaireUuid;
		this.questionnaireName = questionnaireName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.stateOpenOrClosure = stateOpenOrClosure;
		this.description = description;
		this.creatTime = creatTime;
	}

	public QuestionnaireName(UUID questionnaireUuid, String questionnaireName, LocalDate startDate, LocalDate endDate,
			String description) {
		this.questionnaireUuid = questionnaireUuid;
		this.questionnaireName = questionnaireName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isStateOpenOrClosure() {
		return stateOpenOrClosure;
	}

	public void setStateOpenOrClosure(boolean stateOpenOrClosure) {
		this.stateOpenOrClosure = stateOpenOrClosure;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getQuestionnaireUuid() {
		return questionnaireUuid;
	}

	public void setQuestionnaireUuid(UUID questionnaireUuid) {
		this.questionnaireUuid = questionnaireUuid;
	}

	public LocalDateTime getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(LocalDateTime creatTime) {
		this.creatTime = creatTime;
	}

}
