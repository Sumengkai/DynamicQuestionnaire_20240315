package com.example.Dynamic_Questionnaire.Service.Interface;

import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

public interface QuestionnaireService {
	// 新增問券標題 req (問卷名、開始時間、結束時間、描述、#Vo(題目標題、是否必填、多選單選、選項)) ok!
	public QuestionnaireRes creatQuestionnaireName(QuestionnaireReq req);

	// 修改問卷標題 req (問卷uuid、問卷名、開始時間、結束時間、描述、題目標題、是否必填、多選單選、選項) ok!
	public QuestionnaireRes updateQuestionnaireName(QuestionnaireReq req);

	// 搜索問卷列表 req (問卷名稱、開始日期、結束日期) ok!
	public QuestionnaireRes getQuestionnaireList(QuestionnaireReq req);
	
	// 封裝回去，以便用戶編輯 req(問卷uuid) ok!
	public QuestionnaireRes getVoList(QuestionnaireReq req);

	// 搜索問卷標題和選項 req (問卷uuid) ps.打印出整張問卷、問題、選項 ok!
	public QuestionnaireRes getTopicAndOptions(QuestionnaireReq req);

	// 刪除問卷 req (問卷uuid) ok!
	public QuestionnaireRes deleteQuestionnaireName(QuestionnaireReq req);

	// 新增用戶 req(姓名、男女、年齡、信箱、手機) ok!
	public QuestionnaireRes creatPeople(QuestionnaireReq req);

	// 新增填寫日期、計算選項總數、百分比 req(用戶id、問卷uuid)(List<string> 選項uuid) ok!
	public QuestionnaireRes creatWriteDateAndCount(QuestionnaireReq req);

	// 查看填寫日期，後面查看問卷回饋會用到 req
	public QuestionnaireRes getPeopleAndWriteDateInfo(QuestionnaireReq req);

	// 查看問卷反饋 req(填寫日期uuid)
	public QuestionnaireRes getQuestionnaireFeedback(QuestionnaireReq req);
	


}
