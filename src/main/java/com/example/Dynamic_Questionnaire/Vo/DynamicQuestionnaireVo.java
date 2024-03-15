package com.example.Dynamic_Questionnaire.Vo;

import java.util.UUID;

import com.example.Dynamic_Questionnaire.Entity.PeopleChoose;
import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;

public class DynamicQuestionnaireVo {

	private String topicTitleName;
	private String questionName;
	private boolean essential;
	private boolean onlyOrMany;
	private UUID topicUuid;
	private UUID questionNameUuid;

	public DynamicQuestionnaireVo() {

	}

	public DynamicQuestionnaireVo(String topicTitleName, String questionName, boolean essential, boolean onlyOrMany,
			UUID topicUuid, UUID questionNameUuid) {
		this.topicTitleName = topicTitleName;
		this.questionName = questionName;
		this.essential = essential;
		this.onlyOrMany = onlyOrMany;
		this.topicUuid = topicUuid;
		this.questionNameUuid = questionNameUuid;
	}

	public DynamicQuestionnaireVo(String topicTitleName, String questionName, boolean essential, boolean onlyOrMany) {
		this.topicTitleName = topicTitleName;
		this.questionName = questionName;
		this.essential = essential;
		this.onlyOrMany = onlyOrMany;
	}

	public String getTopicTitleName() {
		return topicTitleName;
	}

	public void setTopicTitleName(String topicTitleName) {
		this.topicTitleName = topicTitleName;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
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

	public UUID getTopicUuid() {
		return topicUuid;
	}

	public void setTopicUuid(UUID topicUuid) {
		this.topicUuid = topicUuid;
	}

	public UUID getQuestionNameUuid() {
		return questionNameUuid;
	}

	public void setQuestionNameUuid(UUID questionNameUuid) {
		this.questionNameUuid = questionNameUuid;
	}

}
