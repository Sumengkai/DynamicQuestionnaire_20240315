
package com.example.Dynamic_Questionnaire;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;
import com.example.Dynamic_Questionnaire.Repository.OptionsDao;
import com.example.Dynamic_Questionnaire.Repository.QuestionnaireNameDao;
import com.example.Dynamic_Questionnaire.Repository.TopicTitleDao;
import com.example.Dynamic_Questionnaire.Service.Interface.QuestionnaireService;
import com.example.Dynamic_Questionnaire.Vo.DynamicQuestionnaireVo;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

@SpringBootTest
class DynamicQuestionnaireApplicationTests {
	@Autowired
	QuestionnaireService questionnaireNameService;
	@Autowired
	TopicTitleDao topicTitleDao;
	@Autowired
	OptionsDao questionDao;

	@Test
	void contextLoads() {
	}

	// 查看"xxx"選的"選項"
	@Test
	void getQuestionnaireFeedback() {
		// req要丟參數進去
		QuestionnaireReq req = new QuestionnaireReq();
		// 請求post上面
		// 這裡要放 "填寫時間"的ID
		req.setWriteDateUuid("7c0f70ab-2190-4dc1-82b2-083c9375e892");

		QuestionnaireRes res = questionnaireNameService.getQuestionnaireFeedback(req);

		List<TopicTitle> topicTitleInfo = res.getTopicTitleInfoList();
		List<Options> optionsInfo = res.getOptionsInfoList();
		List<String> chooseOptionsForPeople = res.getChooseListForPeople();
		System.out.println("=======================================================");
		System.out.println(res.getQuestionnaireName().getQuestionnaireName() + " 問卷名稱");
		System.out.println(res.getPeople().getName() + " 用戶名子");
		System.out.println(res.getWriteDate().getWriteDateTime() + " 填寫時間");

		for (var tL : topicTitleInfo) {
			System.out.println(" ----------------------------------");
			System.out.println(tL.getTopicName() + " =>題目");
			for (var oL : optionsInfo) {
				if (tL.getTopicUuid().equals(oL.getTopicUuid())) {
					String options = oL.getQuestionName() + " 選項";
					System.out.printf(options);
					for (var cL : chooseOptionsForPeople) {
						if (oL.getQuestionName().equals(cL) && tL.getTopicUuid().equals(oL.getTopicUuid())) {
							String op = "勾勾";
							System.out.println(op);
						}
					}
					System.out.println("");
					options = "";
				}
			}
		}
		System.out.println("=======================================================");
	}

	// 新增"xxx"選的"選項"
	@Test
	void creatWriteDateAndCount() {
		QuestionnaireReq req = new QuestionnaireReq();
		// 這裡要放問卷的ID
		req.setQuestionnaireNameUuid("fc6c08ef-4fba-4197-80eb-0f95ab1d955b");
		// 放人的ID
		req.setPeopleId("27ca4252-22dc-4a68-9742-87fd015b061f");
		List<String> optionsList = new ArrayList<>();
		// 選項的ID
		optionsList.add("73255851-3ed9-46e5-8d5c-f62c96501fae");
		optionsList.add("e285c792-3293-44f9-9229-1dd6bf0ba856");
		optionsList.add("6eef5aca-3caf-479e-bc36-bc5fc9c090de");
		optionsList.add("f70f66cd-c1c9-42f6-9406-12fc7834a544");
		optionsList.add("bad4a667-1e3e-4bf3-ade2-270774691eb9");
		req.setOptionsUuidList(optionsList);
		QuestionnaireRes res = questionnaireNameService.creatWriteDateAndCount(req);
		System.out.println(res.getWriteDate().getName());
	}

	// 新增人物
	@Test
	public void creatPeople() {
		QuestionnaireReq req = new QuestionnaireReq();
		// 名字
		req.setName("蘇盟傑");
		// 性別
		req.setManOrGirl("男");
		// 信箱
		req.setEmail("a0973038822@gmail.com");
		// 手機
		req.setPhone("0973038822");
		// 年紀
		req.setAge(25);
		QuestionnaireRes res = questionnaireNameService.creatPeople(req);
		System.out.println(res.getMessage());
	}

	// 刪除"問卷"
	@Test
	public void deleteQnameTest() {
		List<String> dList = new ArrayList<>();
		// 放"問卷"的ID
		dList.add("問卷uuid");
		QuestionnaireReq req = new QuestionnaireReq();
		req.setQuestionnaireNameUuidList(dList);
		QuestionnaireRes res = questionnaireNameService.deleteQuestionnaireName(req);
		System.out.println(res.getMessage());
	}

	// 查看"題目"+"選項"
	@Test
	public void searchVoTest() {
		QuestionnaireReq req = new QuestionnaireReq();
		// 放"問卷"的ID
		req.setQuestionnaireNameUuid("fc6c08ef-4fba-4197-80eb-0f95ab1d955b");
		QuestionnaireRes res = questionnaireNameService.getVoList(req);
		List<DynamicQuestionnaireVo> voList = res.getDynamicQuestionnaireVoList();
		for (var voItem : voList) {
			System.out.println("題目 : " + voItem.getTopicTitleName() + " 必填嗎 : " + voItem.isEssential() + " 單選嗎 : "
					+ voItem.isOnlyOrMany() + " 選項 : " + voItem.getQuestionName());
		}
	}

	// 更新"問卷"+"題目"+"選項"
	@Test
	public void updateTest() {
		QuestionnaireReq req = new QuestionnaireReq();
		DynamicQuestionnaireVo reqVo = new DynamicQuestionnaireVo();
		DynamicQuestionnaireVo reqVo2 = new DynamicQuestionnaireVo();
		DynamicQuestionnaireVo reqVo3 = new DynamicQuestionnaireVo();
		DynamicQuestionnaireVo reqVo4 = new DynamicQuestionnaireVo();
		// 放"題目"
		reqVo.setTopicTitleName("第一份問卷的第一個題目=去哪裡");
		reqVo2.setTopicTitleName("第一份問卷的第二個題目=喝什麼");
		reqVo3.setTopicTitleName("第一份問卷的第三個題目=拉什麼");
		// 放"選項"
		reqVo.setQuestionName("1.高雄;2.台北;3.嘉義;4.台南;5.家裡蹲");
		reqVo2.setQuestionName("1.紅茶;2.豆漿;3.奶茶");
		reqVo3.setQuestionName("1.大便");
		// 放"必填"
		reqVo.setEssential(true);
		reqVo.setOnlyOrMany(true);
		List<DynamicQuestionnaireVo> reqVoList = new ArrayList<>();
		reqVoList.add(reqVo);
		reqVoList.add(reqVo2);
		reqVoList.add(reqVo3);
		// 放"問卷"的ID
		req.setQuestionnaireNameUuid("d0cc74d7-f08a-4f38-98e5-d05fa3c2c7b0");
		req.setDynamicQuestionnaireVoList(reqVoList);
		QuestionnaireRes res = questionnaireNameService.updateQuestionnaireName(req);
		System.out.println(res.getMessage());
	}

	// 新增"問卷"+"題目"+"選項"
	@Test
	public void creatTest() {
		QuestionnaireReq req = new QuestionnaireReq();
		// 放"問卷"
		req.setQuestionnaireName("第一份問卷");
		DynamicQuestionnaireVo reqVo = new DynamicQuestionnaireVo();
		DynamicQuestionnaireVo reqVo2 = new DynamicQuestionnaireVo();
		DynamicQuestionnaireVo reqVo3 = new DynamicQuestionnaireVo();
		// 放"題目"
		reqVo.setTopicTitleName("第一份問卷的第一個題目=吃什麼");
		reqVo2.setTopicTitleName("第一份問卷的第二個題目=喝什麼");
		reqVo3.setTopicTitleName("第一份問卷的第三個題目=拉什麼");
		// 放"選項"
		reqVo.setQuestionName("1.漢堡   ;   2.蛋糕 ;  3.三明治");
		reqVo2.setQuestionName("1.紅茶;2.豆漿;3.奶茶");
		reqVo3.setQuestionName("1.大便");
		LocalDate s = LocalDate.now();
		// 放"新增時間"
		req.setStartDate(s);
		List<DynamicQuestionnaireVo> reqVoList = new ArrayList<>();
		reqVoList.add(reqVo);
		reqVoList.add(reqVo2);
		reqVoList.add(reqVo3);
		req.setDynamicQuestionnaireVoList(reqVoList);
		QuestionnaireRes res = questionnaireNameService.creatQuestionnaireName(req);
		for (var item : reqVoList) {
			System.out.println(
					res.getQuestionnaireName() + " " + item.getTopicTitleName() + "問題內容:" + item.getQuestionName());
		}
	}

	// 暫時留著
	@Test
	public void getTopicAndOptions() {
		String uuidStr = "f9ad642c-80ad-42f4-b74f-c119e66725a3";
		QuestionnaireReq req = new QuestionnaireReq();
		req.setQuestionnaireNameUuid(uuidStr);
		QuestionnaireRes res = questionnaireNameService.getTopicAndOptions(req);
		List<TopicTitle> TL = res.getTopicTitleInfoList();
		List<Options> QL = res.getOptionsInfoList();
		System.out.println(res.getQuestionnaireName().getQuestionnaireName());
		for (var T : TL) {
			System.out.println(T.getTopicName() + "---------------------");
			for (var Q : QL) {
				if (T.getTopicUuid().equals(Q.getTopicUuid())) {
					System.out.println(Q.getQuestionName());
				}
			}
		}

	}
}
