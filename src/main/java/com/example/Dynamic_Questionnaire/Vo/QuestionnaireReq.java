package com.example.Dynamic_Questionnaire.Vo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QuestionnaireReq {
	private String questionnaireName; // 問卷名稱

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate startDate; // 開啟日期

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate endDate; // 關閉日期

	private String description; // 問卷描述

	private String questionnaireNameUuid; // 修改問卷名稱用到的UUID

	private String topicTitle;// 題目名稱

	private String necessaryOrNot;// 必要非必要

	private String onlyOrMany;// 單選或多個

	private DynamicQuestionnaireVo dynamicQuestionnaireVo;

	private List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList; // 新增問卷、問題、選項

	private String searchQuestionnaireName;// 搜尋問卷

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate searchStartDate;// 搜尋開始日期

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate searchEndDate;// 搜尋結束日期

	private String topicUuid;// 修改問題標題用到的UUID

	private boolean necessaryOrNotBoolean; // 修改問題會用到的布林(必填?)

	private boolean onlyOrManyBoolean;// 修改問題會用到的布林(單選?)

	private String optionsUuid;// 選項UUID 修改選項會用到

	private String questionName;// 選項名稱 新增、、修改選項名稱會用到

	private List<String> optionsUuidList;// 計算用，裝著選項的UUID

	private String peopleId;// 身分ID

	private String manOrGirl;// 男或女

	private String name;// 用戶姓名

	private String email;// 信箱

	private String phone;// 手機

	private int age;// 年齡

	private String writeDateUuid;// 填寫日期uuid

	private List<String> questionnaireNameUuidList;// 刪除整份問卷
//----------------------------------------------------------------------	

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuestionnaireNameUuid() {
		return questionnaireNameUuid;
	}

	public void setQuestionnaireNameUuid(String questionnaireNameUuid) {
		this.questionnaireNameUuid = questionnaireNameUuid;
	}
	// ---

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getNecessaryOrNot() {
		return necessaryOrNot;
	}

	public void setNecessaryOrNot(String necessaryOrNot) {
		this.necessaryOrNot = necessaryOrNot;
	}

	public String getOnlyOrMany() {
		return onlyOrMany;
	}

	public void setOnlyOrMany(String onlyOrMany) {
		this.onlyOrMany = onlyOrMany;
	}
	// --

	public DynamicQuestionnaireVo getDynamicQuestionnaireVo() {
		return dynamicQuestionnaireVo;
	}

	public void setDynamicQuestionnaireVo(DynamicQuestionnaireVo dynamicQuestionnaireVo) {
		this.dynamicQuestionnaireVo = dynamicQuestionnaireVo;
	}

	public List<DynamicQuestionnaireVo> getDynamicQuestionnaireVoList() {
		return dynamicQuestionnaireVoList;
	}

	public void setDynamicQuestionnaireVoList(List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList) {
		this.dynamicQuestionnaireVoList = dynamicQuestionnaireVoList;
	}

	// ---
	public String getSearchQuestionnaireName() {
		return searchQuestionnaireName;
	}

	public void setSearchQuestionnaireName(String searchQuestionnaireName) {
		this.searchQuestionnaireName = searchQuestionnaireName;
	}

	public LocalDate getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(LocalDate searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public LocalDate getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(LocalDate searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	// ---
	public String getTopicUuid() {
		return topicUuid;
	}

	public void setTopicUuid(String topicUuid) {
		this.topicUuid = topicUuid;
	}

	public boolean isNecessaryOrNotBoolean() {
		return necessaryOrNotBoolean;
	}

	public void setNecessaryOrNotBoolean(boolean necessaryOrNotBoolean) {
		this.necessaryOrNotBoolean = necessaryOrNotBoolean;
	}

	public boolean isOnlyOrManyBoolean() {
		return onlyOrManyBoolean;
	}

	public void setOnlyOrManyBoolean(boolean onlyOrManyBoolean) {
		this.onlyOrManyBoolean = onlyOrManyBoolean;
	}
	// ---

	public String getOptionsUuid() {
		return optionsUuid;
	}

	public void setOptionsUuid(String optionsUuid) {
		this.optionsUuid = optionsUuid;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public List<String> getOptionsUuidList() {
		return optionsUuidList;
	}

	public void setOptionsUuidList(List<String> optionsUuidList) {
		this.optionsUuidList = optionsUuidList;
	}

	public String getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}

	public String getManOrGirl() {
		return manOrGirl;
	}

	public void setManOrGirl(String manOrGirl) {
		this.manOrGirl = manOrGirl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getWriteDateUuid() {
		return writeDateUuid;
	}

	public void setWriteDateUuid(String writeDateUuid) {
		this.writeDateUuid = writeDateUuid;
	}

	public List<String> getQuestionnaireNameUuidList() {
		return questionnaireNameUuidList;
	}

	public void setQuestionnaireNameUuidList(List<String> questionnaireNameUuidList) {
		this.questionnaireNameUuidList = questionnaireNameUuidList;
	}

}
