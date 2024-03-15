package com.example.Dynamic_Questionnaire.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dynamic_Questionnaire.Service.Interface.QuestionnaireService;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

@CrossOrigin
@RestController
public class QuestionnaireController {
	@Autowired
	private QuestionnaireService questionnaireNameService;

//---------------------------------------------------------------
	/* 新增問卷--------------------------------------------------- */
	@PostMapping(value = "/api/creatQuestionnaireName")
	public QuestionnaireRes creatQuestionnaireName(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.creatQuestionnaireName(req);
	}

	/* 修改問卷--------------------------------------------------- */
	@PostMapping(value = "updateQuestionnaireName")
	public QuestionnaireRes updateQuestionnaireName(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.updateQuestionnaireName(req);
	}

	/* 搜索問卷列表----------------------------------------------- */
	@PostMapping(value = "getQuestionnaireList")
	public QuestionnaireRes getQuestionnaireList(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.getQuestionnaireList(req);
	}

	/* 封裝回去，以便用戶編輯-------------------------------------- */
	@PostMapping(value = "getVoList")
	public QuestionnaireRes getVoList(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.getVoList(req);
	}

	/* 刪除問卷--------------------------------------------------- */
	@PostMapping(value = "deleteQuestionnaireName")
	public QuestionnaireRes deleteQuestionnaireName(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.deleteQuestionnaireName(req);
	}

	/* 新增填寫日期、計算選項總數、百分比-------------------------- */
	@PostMapping(value = "creatWriteDateAndCount")
	public QuestionnaireRes creatWriteDateAndCount(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.creatWriteDateAndCount(req);
	}

	/* 新增人物資訊----------------------------------------------- */
	@PostMapping(value = "creatPeople")
	public QuestionnaireRes creatPeople(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.creatPeople(req);
	}

	/* 統計------------------------------------------------------- */
	@PostMapping(value = "getTopicAndOptions")
	public QuestionnaireRes getTopicAndOptions(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.getTopicAndOptions(req);
	}

	/* 打印出用戶填問卷日期----------------------------------------- */
	@PostMapping(value = "getPeopleAndWriteDateInfo")
	public QuestionnaireRes getPeopleAndWriteDateInfo(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.getPeopleAndWriteDateInfo(req);
	}

	/* 問卷回饋---------------------------------------------------- */
	@PostMapping(value = "getQuestionnaireFeedback")
	public QuestionnaireRes getQuestionnaireFeedback(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.getQuestionnaireFeedback(req);
	}
	
	
}
