package com.example.Dynamic_Questionnaire.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "write_date")
public class WriteDate {
	@Id
	@Column(name = "write_uuid")
	@Type(type = "uuid-char")
	private UUID writeUuid;

	@Column(name = "people_id")
	@Type(type = "uuid-char")
	private UUID peopleId;

	@Column(name = "questionnaire_uuid")
	@Type(type = "uuid-char")
	private UUID questionnaireUuid;

	@Column(name = "name")
	private String name;

	@Column(name = "write_date_time")
	private LocalDateTime writeDateTime;

	public WriteDate() {
	}

	public WriteDate(UUID writeUuid, UUID peopleId, UUID questionnaireUuid, String name, LocalDateTime writeDateTime) {
		this.writeUuid = writeUuid;
		this.peopleId = peopleId;
		this.questionnaireUuid = questionnaireUuid;
		this.name = name;
		this.writeDateTime = writeDateTime;
	}

	public UUID getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(UUID peopleId) {
		this.peopleId = peopleId;
	}

	public UUID getQuestionnaireUuid() {
		return questionnaireUuid;
	}

	public void setQuestionnaireUuid(UUID questionnaireUuid) {
		this.questionnaireUuid = questionnaireUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getWriteDateTime() {
		return writeDateTime;
	}

	public void setWriteDateTime(LocalDateTime writeDateTime) {
		this.writeDateTime = writeDateTime;
	}

	public UUID getWriteUuid() {
		return writeUuid;
	}

	public void setWriteUuid(UUID writeUuid) {
		this.writeUuid = writeUuid;
	}

}
