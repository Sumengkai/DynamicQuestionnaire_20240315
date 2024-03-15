
package com.example.Dynamic_Questionnaire.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.People;
import com.example.Dynamic_Questionnaire.Entity.PeopleChoose;
import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;
import com.example.Dynamic_Questionnaire.Entity.WriteDate;
import com.example.Dynamic_Questionnaire.Repository.OptionsDao;
import com.example.Dynamic_Questionnaire.Repository.PeopleChooseDao;
import com.example.Dynamic_Questionnaire.Repository.PeopleDao;
import com.example.Dynamic_Questionnaire.Repository.QuestionnaireNameDao;
import com.example.Dynamic_Questionnaire.Repository.TopicTitleDao;
import com.example.Dynamic_Questionnaire.Repository.WriteDateDao;
import com.example.Dynamic_Questionnaire.Service.Interface.QuestionnaireService;
import com.example.Dynamic_Questionnaire.Vo.DynamicQuestionnaireVo;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

//@Transactional
@Service
public class QuestionnaireImpl implements QuestionnaireService {
	@Autowired
	QuestionnaireNameDao questionnaireNameDao;
	@Autowired
	TopicTitleDao topicTitleDao;
	@Autowired
	OptionsDao optionsDao;
	@Autowired
	PeopleChooseDao chooseCountDao;
	@Autowired
	PeopleDao peopleDao;
	@Autowired
	WriteDateDao writeDateDao;
	@Autowired
	PeopleChooseDao peopleChooseDao;

	// ------------------------------------------
	private QuestionnaireRes checkQuestionnaireName(QuestionnaireReq req) {
		if (!StringUtils.hasText(req.getQuestionnaireName())) {
			return new QuestionnaireRes("請填入問卷名稱");
		}
		return null;

	}

	private boolean checkTimeIsBefore(LocalDate startDate, LocalDate endDate) {
		if (endDate.isBefore(startDate)) {
			return false;
		}
		return true;
	}

	private boolean checkOpenOrClorse(LocalDate startDate, LocalDate endDate) {
		if ((startDate.isBefore(LocalDate.now()) && endDate.isAfter(LocalDate.now()))
				|| startDate.equals(LocalDate.now()) || endDate.equals(LocalDate.now())) {
			return true;
		}
		return false;

	}

	private void autoUpdateOpenOrClosure() {

		// 找出所有問卷
		List<QuestionnaireName> questionnaireNameInfo = questionnaireNameDao.findAll();

		// 預備接<QuestionnaireName>的List
		List<QuestionnaireName> finalQuestionnaireNameInfo = new ArrayList<>();

		// 迴圈每個問卷，並且確定每個問卷是開啟或關閉
		for (var item : questionnaireNameInfo) {
			item.setStateOpenOrClosure(checkOpenOrClorse(item.getStartDate(), item.getEndDate()));
			finalQuestionnaireNameInfo.add(item);
		}
		questionnaireNameDao.saveAll(finalQuestionnaireNameInfo);
	}

	// =============================================================================
	// ----新增"問卷" ps.2022/12/23成功
	@Override
	public QuestionnaireRes creatQuestionnaireName(QuestionnaireReq req) {

		// 先new出問卷名稱的類別
		QuestionnaireName questionnaireName = new QuestionnaireName();

		// 私有方法:確認問卷名稱是否有輸入
		QuestionnaireRes res = checkQuestionnaireName(req);
		if (res != null) {
			return res;
		}

		res = new QuestionnaireRes();

		// 日期有輸入為true
		boolean checkStartDate = req.getStartDate() != null;
		boolean checkEndDate = req.getEndDate() != null;

		// 新增時只有結束日期防呆
		if (!checkStartDate && checkEndDate) {
			return new QuestionnaireRes("請輸入開始日期");
		}

		// 兩個日期都有輸入 (防呆)
		if (checkStartDate && checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();

			// 私有方法:確認開始時間是否大於結束時間
			boolean checkTimeIsBefore = checkTimeIsBefore(startDate, endDate);
			if (!checkTimeIsBefore) {
				return new QuestionnaireRes("開始時間不能大於結束時間");
			}
		}

		// 當兩個日期都沒有時，掉進去 ps.因為開始時間預設今天、結束時間預設開始+7，所以開啟或關閉一定為true
		if (!checkStartDate && !checkEndDate) {

			// 預設開始日期今天
			LocalDate startDate = LocalDate.now();
			// 預設結束日期是開始日期+7
			LocalDate endDate = startDate.plus(7, ChronoUnit.DAYS);

			// 有描述
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, true, req.getDescription(), LocalDateTime.now());
				questionnaireNameDao.save(questionnaireName);

			} else {
				// 沒有描述
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, true, "無", LocalDateTime.now());
				questionnaireNameDao.save(questionnaireName);
			}
		}

		// 當兩個日期都有
		if (checkStartDate && checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();

			// 私有方法:確認是否開放
			boolean checkOpenOrClorse = checkOpenOrClorse(startDate, endDate);

			// 有描述
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, req.getDescription(), LocalDateTime.now());
				questionnaireNameDao.save(questionnaireName);

			} else {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, "無", LocalDateTime.now());
				questionnaireNameDao.save(questionnaireName);
			}
		}

		// 上面都沒擋掉，一定只有開始日期
		if (checkStartDate && !checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = startDate.plus(7, ChronoUnit.DAYS);

			// 私有方法:確認是否開放
			boolean checkOpenOrClorse = checkOpenOrClorse(startDate, endDate);

			// 有描述
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, req.getDescription(), LocalDateTime.now());
				questionnaireNameDao.save(questionnaireName);

			} else {
				// 沒有描述
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, "無", LocalDateTime.now());
				questionnaireNameDao.save(questionnaireName);
			}
		}

		// 先new出問題類別
		TopicTitle topicTitle = new TopicTitle();

		// 先new出選項類別
		Options question = new Options();
		if (req.getDynamicQuestionnaireVoList() == null) {
			return new QuestionnaireRes(questionnaireName, "新增問卷成功");
		}

		// 對於外部請求先封裝進voList
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = req.getDynamicQuestionnaireVoList();
		// 預備接< 問題類別 >的List
		List<TopicTitle> topicTitleList = new ArrayList<>();
		// 預備接< 選項類別 >的List
		List<Options> optionsList = new ArrayList<>();

		// 迴圈封裝的vo ps.開箱
		for (var voItem : dynamicQuestionnaireVoList) {
			if (!StringUtils.hasText(voItem.getQuestionName()) || !StringUtils.hasText(voItem.getTopicTitleName())) {
				return new QuestionnaireRes("問卷新增成功，但題目新增失敗，如需修改請前往編輯模式");
			}
			boolean checkEssential = voItem.isEssential();// 單選為false
			boolean checkOnlyOrMany = voItem.isOnlyOrMany();// 唯一為false

			// 新增題目標題
			topicTitle = new TopicTitle(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
					voItem.getTopicTitleName(), checkEssential, checkOnlyOrMany);

			// 先將全部接起來
			topicTitleList.add(topicTitle);

			// 切割題目"選項" ps.傳進來的東西有可能只有一個選項，那就沒有";"，所以有才要切割
			if (voItem.getQuestionName().contains(";")) {

				// 切割成陣列 ex:選項 : 選項A;選項B
				String[] optionsNameArray = voItem.getQuestionName().split(";");

				// 迴圈切割完後的陣列
				for (var item : optionsNameArray) {
					// 去除空白
					String optionsName = item.trim();
					question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
							topicTitle.getTopicUuid(), optionsName);

					// 先將全部接起來
					optionsList.add(question);

				}

			} else {
				// 如果前端傳進來沒有";"，不用切割，直接儲存開箱後的"選項名稱"
				question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
						topicTitle.getTopicUuid(), voItem.getQuestionName());

				// 先將全部接起來
				optionsList.add(question);
			}

		}
		// 將接完後的List全部儲存資料庫
		if (!dynamicQuestionnaireVoList.isEmpty()) {
			topicTitleDao.saveAll(topicTitleList);
			optionsDao.saveAll(optionsList);
		}
		return new QuestionnaireRes(questionnaireName, "新增問卷、題目、選項成功");
	}

	/*
	 * =============================================================================
	 */
	// ----修改問卷 ps.2022/12/26成功
	@Override
	public QuestionnaireRes updateQuestionnaireName(QuestionnaireReq req) {

		// 有輸入問卷名稱true
		boolean checkQuestionnaireName = StringUtils.hasText(req.getQuestionnaireName());

		// 有輸入描述true
		boolean checkDescription = StringUtils.hasText(req.getDescription());

		// 有輸入日期true
		boolean checkStartDate = req.getStartDate() != null;

		// 有輸入日期true
		boolean checkEndDate = req.getEndDate() != null;

		// 對於外部請求先封裝進voList
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = req.getDynamicQuestionnaireVoList();

		// 防呆
		if (!checkStartDate && checkEndDate) {
			return new QuestionnaireRes("請輸入開始日期");
		}

		// 防呆
		if (checkEndDate && checkStartDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();
			boolean checkTimeIsBefore = checkTimeIsBefore(startDate, endDate);
			if (!checkTimeIsBefore) {
				return new QuestionnaireRes("開始時間不能大於結束時間");
			}
		}

		// 請求到問卷名稱的uuid
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());

		// 判斷資料庫是否有同一個主kay
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("找不到你的問卷，此防呆訊息用不到");
		}

		// 取值
		QuestionnaireName questionnaireName = questionnaireNameOp.get();

		// 因為要判斷這個該問卷有沒有人寫過，只要有就不能修改了
		List<WriteDate> peopleChoose = writeDateDao.findByQuestionnaireUuid(questionnaireName.getQuestionnaireUuid());

		if (!peopleChoose.isEmpty()) {
			return new QuestionnaireRes("有人填過問卷固不能修改");
		}

		// 有開始日期、沒有結束日期
		if (checkStartDate && !checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = questionnaireName.getEndDate();
			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("開始日期不能大於你原本問卷關閉的日期");
			}
		}

		// 上面都沒擋掉，代表一定會修改某個東西

		// 有修改問卷名稱
		if (checkQuestionnaireName) {
			questionnaireName.setQuestionnaireName(req.getQuestionnaireName());
		}

		// 有修改開始日期
		if (checkStartDate) {
			questionnaireName.setStartDate(req.getStartDate());
		}
		// 有修改結束日期
		if (checkEndDate) {
			questionnaireName.setEndDate(req.getEndDate());
		}

		// 有修改描述
		if (checkDescription) {
			questionnaireName.setDescription(req.getDescription());
		}
		// 判斷開啟或關閉
		questionnaireName.setStateOpenOrClosure(
				checkOpenOrClorse(questionnaireName.getStartDate(), questionnaireName.getEndDate()));
		// 存進資料庫
		questionnaireNameDao.save(questionnaireName);

		// 預備接<TopicTitle>類別
		List<TopicTitle> topicTitleList = new ArrayList<>();
		// 預備接<Options>類別
		List<Options> optionsList = new ArrayList<>();

		// 不管輸入什麼問題跟選項，先刪除所有原本問題、選項
		topicTitleDao.deleteByQuestionnaireUuid(questionnaireNameUuid);
		optionsDao.deleteByQuestionnaireUuid(questionnaireNameUuid);

		// 先new出Options的類別
		Options question = new Options();
		// 先new出TopicTitle的類別
		TopicTitle topicTitle = new TopicTitle();

		// 迴圈新的封箱vo，並且開箱
		for (var voItem : dynamicQuestionnaireVoList) {
			if (!StringUtils.hasText(voItem.getQuestionName()) || !StringUtils.hasText(voItem.getTopicTitleName())) {
				return new QuestionnaireRes("請輸入至少一個選項或問題");
			}

			// 單選為flase
			boolean checkEssential = voItem.isEssential();
			// 唯一為false
			boolean checkOnlyOrMany = voItem.isOnlyOrMany();

			// 開箱
			// 題目標題
			topicTitle = new TopicTitle(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
					voItem.getTopicTitleName(), checkEssential, checkOnlyOrMany);

			// 先全部接起來
			topicTitleList.add(topicTitle);

			// 切割題目"選項" ps.傳進來的東西有可能只有一個選項，那就沒有";"，所以有才要切割
			if (voItem.getQuestionName().contains(";")) {

				// 切割成陣列 ex:選項 : 選項A;選項B
				String[] optionsNameArray = voItem.getQuestionName().split(";");

				// 迴圈陣列
				for (var item : optionsNameArray) {
					// 去除前後空白
					String optionsName = item.trim();
					question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
							topicTitle.getTopicUuid(), optionsName);

					// 先全部接起來
					optionsList.add(question);
				}

			} else {
				// 如果前端傳進來沒有";"，不用切割，直接儲存開箱後的"選項名稱"
				question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
						topicTitle.getTopicUuid(), voItem.getQuestionName());

				// 先全部接起來
				optionsList.add(question);
			}
		}
		// 將接完後的List全部儲存資料庫
		topicTitleDao.saveAll(topicTitleList);
		optionsDao.saveAll(optionsList);
		return new QuestionnaireRes(questionnaireName, "修改成功");

	}

	/*
	 * =============================================================================
	 */
	// ----顯示問卷列表之後看用戶要不要修改 ps.2022/12/25成功
	// req (問卷名稱、開始日期、結束日期)

	@Override
	public QuestionnaireRes getQuestionnaireList(QuestionnaireReq req) {

		// 自動更新狀態 ps.開啟或關閉
//		autoUpdateOpenOrClosure();

		// 有輸入問卷名稱true
		boolean checkSearchQname = StringUtils.hasText(req.getSearchQuestionnaireName());

		// 有輸入開始日期true
		boolean checkSearchStartDate = req.getSearchStartDate() != null;

		// 有輸入結束日期true
		boolean checkSearchEndDate = req.getSearchEndDate() != null;

		// 問卷名稱資訊
		List<QuestionnaireName> questionnaireNameInfo = new ArrayList<>();

		// 1.三個都沒輸入
		if (!checkSearchQname && !checkSearchStartDate && !checkSearchEndDate) {
			// 回傳全部資料
			questionnaireNameInfo = questionnaireNameDao.findAllByOrderByCreatTimeDesc();
		}

		// 2.三者都有輸入
		if (checkSearchQname && checkSearchStartDate && checkSearchEndDate) {

			// 這裡轉日期可以不用 ps.方便閱讀
			LocalDate startDate = req.getSearchStartDate();
			LocalDate endDate = req.getSearchEndDate();
			// 防呆
			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("開始日期不可大於結束時間");
			}

			// 回傳模糊搜尋、時間區間後的資料
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByCreatTimeDesc(
							req.getSearchQuestionnaireName(), startDate, endDate);

		}
		
		// 3.有問卷名稱、有開始日期、沒有結束日期
		if (checkSearchQname && checkSearchStartDate && !checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();
			// 回傳模糊搜尋、時間區間後的資料
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameContainingAndStartDateGreaterThanEqualOrderByCreatTimeDesc(
							req.getSearchQuestionnaireName(), startDate);
		}
		
		// 4.只有問卷名稱
		if (checkSearchQname && !checkSearchStartDate && !checkSearchEndDate) {
			// 回傳模糊搜尋的資料
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameContainingOrderByCreatTimeDesc(req.getSearchQuestionnaireName());
		}
		
		// 5.有問卷名稱、有結束日期、沒有開始日期
		if (checkSearchQname && checkSearchEndDate && !checkSearchStartDate) {
			LocalDate endDate = req.getSearchEndDate();
			// 回傳模糊搜尋、時間區間後的資料
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameContainingAndEndDateLessThanEqualOrderByCreatTimeDesc(
							req.getSearchQuestionnaireName(), endDate);
		}
		// 6.沒有問卷名稱、沒有開始日期、有結束日期
		if (!checkSearchQname && checkSearchEndDate && !checkSearchStartDate) {
			LocalDate endDate = req.getSearchEndDate();

			// 回傳模糊搜尋、時間區間後的資料
			questionnaireNameInfo = questionnaireNameDao.findByEndDateLessThanEqualOrderByCreatTimeDesc(endDate);
		}

		// 7.沒有問卷名稱、有開始日期、有結束日期
		if (!checkSearchQname && checkSearchStartDate && checkSearchEndDate) {

			LocalDate startDate = req.getSearchStartDate();

			LocalDate endDate = req.getSearchEndDate();

			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("開始日期不可大於結束日期");
			}

			// 回傳時間區間資料
			questionnaireNameInfo = questionnaireNameDao.findByStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByCreatTimeDesc(startDate, endDate);
		}
		
		// 8.沒有問卷名稱、沒有結束日期、有開始日期
		if (!checkSearchQname && checkSearchStartDate && !checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();
			// 回傳時間區間資料
			questionnaireNameInfo = questionnaireNameDao.findByStartDateGreaterThanEqualOrderByCreatTimeDesc(startDate);
		}
		if (questionnaireNameInfo.isEmpty()) {
			return new QuestionnaireRes("查無資料");
		}
		return new QuestionnaireRes(questionnaireNameInfo);
	}

	/*
	 * =============================================================================
	 */
	// 
	// ----搜尋完問卷標題點進去看到裡面的問題 ps.2022/12/25成功
	// req (問卷uuid)
	@Override
	public QuestionnaireRes getTopicAndOptions(QuestionnaireReq req) {

		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		// 判斷資料庫是否有同一個主kay
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("找不到你的問卷");
		}
		QuestionnaireName questionnaireName = questionnaireNameOp.get();
		List<TopicTitle> topicTitleInfo = topicTitleDao.findByQuestionnaireUuid(questionnaireNameUuid);
		if (topicTitleInfo.isEmpty()) {
			return new QuestionnaireRes("該問卷沒有題目");
		}
		List<Options> questionInfo = optionsDao.findByQuestionnaireUuid(questionnaireNameUuid);
		if (questionInfo.isEmpty()) {
			return new QuestionnaireRes("該問卷無任何人做答");
		}
		return new QuestionnaireRes(questionnaireName, topicTitleInfo, questionInfo);
	}

	/*
	 * =============================================================================
	 */
	// 刪除整份問卷、req(問卷uuid)
//	@Transactional
	@Override
	public QuestionnaireRes deleteQuestionnaireName(QuestionnaireReq req) {
		// 前端接進來是字串
		List<String> questionnaireNameUuidListString = req.getQuestionnaireNameUuidList();
		if (questionnaireNameUuidListString.isEmpty()) {
			return new QuestionnaireRes("刪除失敗");
		}
		// 預備接所有問卷名稱的uuid
		List<UUID> questionnaireNameUuidList = new ArrayList<>();

		for (var item : questionnaireNameUuidListString) {
			UUID questionnaireNameUuid = UUID.fromString(item);
			questionnaireNameUuidList.add(questionnaireNameUuid);
		}
		// 刪除資料庫同一張問卷底下的所有資料
		questionnaireNameDao.deleteByQuestionnaireUuidIn(questionnaireNameUuidList);
		topicTitleDao.deleteByQuestionnaireUuidIn(questionnaireNameUuidList);
		optionsDao.deleteByQuestionnaireUuidIn(questionnaireNameUuidList);
		writeDateDao.deleteByQuestionnaireUuidIn(questionnaireNameUuidList);
		peopleChooseDao.deleteByQuestionnaireUuidIn(questionnaireNameUuidList);
		return new QuestionnaireRes("刪除成功");
	}

	/*
	 * =============================================================================
	 */
	// 進入修改模式，給用戶看他原本的值，封裝回vo
	@Override  
	public QuestionnaireRes getVoList(QuestionnaireReq req) {
		// 請求問卷的uuid
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());

		// 判斷資料庫是否有同一個主kay (這裡可以不用)
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("找不到你的問卷，這個防呆訊息其實可以不用");
		}
		QuestionnaireName questionnaireName = questionnaireNameOp.get();
		// 預備封裝回去
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = new ArrayList<>();

		// 找出同張問卷底下的所有問題
		List<TopicTitle> topicList = topicTitleDao.findByQuestionnaireUuid(questionnaireNameUuid);

		// 迴圈問題
		for (var topicItem : topicList) {

			// 先預設一個空字串
			String optionsName = "";
			// 找出同一個問題底下的選項
			List<Options> optionsList = optionsDao.findByTopicUuid(topicItem.getTopicUuid());
			for (var optionsItem : optionsList) {
				// 將所有選項串接起來，以";"做區隔
				optionsName += optionsItem.getQuestionName() + ";";
			}
			// 因為接回去在結尾時會多一個";"，所以要再多一個字串去切割ex:選項A;選項B;
			String finalQname = optionsName.substring(0, optionsName.length() - 1);
			DynamicQuestionnaireVo dynamicQuestionnaireVo = new DynamicQuestionnaireVo(topicItem.getTopicName(),
					finalQname, topicItem.isEssential(), topicItem.isOnlyOrMany());
			// 封裝回去
			dynamicQuestionnaireVoList.add(dynamicQuestionnaireVo);
		}
		return new QuestionnaireRes(dynamicQuestionnaireVoList, questionnaireName);
	}

	/*
	 * =============================================================================
	 */
	// 新增人物資訊
	@Override
	public QuestionnaireRes creatPeople(QuestionnaireReq req) {

		// 防呆
		if (!StringUtils.hasText(req.getName()) || !StringUtils.hasText(req.getManOrGirl())
				|| !StringUtils.hasText(req.getEmail()) || !StringUtils.hasText(req.getPhone()) || req.getAge() < 1) {
			return new QuestionnaireRes("請輸入正確參數");
		}

		// 判斷有沒有符合輸入男或女
		List<String> manOrGirlList = new ArrayList<>(List.of("男", "女"));
		if (!manOrGirlList.stream().anyMatch(item -> item.equalsIgnoreCase(req.getManOrGirl()))) {
			return new QuestionnaireRes("請輸入正確性別");
		}

//		if (!req.getEmail().matches("^[A-Za-z0-9]+@gmail.com")) {
//			return new QuestionnaireRes("信箱格式錯誤");
//		}
		if (!req.getPhone().matches("[0-9]{4}[0-9]{6}")) {
			return new QuestionnaireRes("手機格式錯誤 ex:097x03x82x");
		}

		People people = new People(UUID.randomUUID(), req.getName(), req.getManOrGirl(), req.getEmail(), req.getPhone(),
				req.getAge());

		peopleDao.save(people);

		return new QuestionnaireRes(people, "成功");

	}
	/*
	 * =============================================================================
	 */

	@Override
	public QuestionnaireRes creatWriteDateAndCount(QuestionnaireReq req) {

		// 用戶做問卷時，會留下他本人的身分證 PS.這裡用UUID代替
		UUID peopleid = UUID.fromString(req.getPeopleId());

		// 請求問卷名稱的UUID
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());

		// 問卷名稱取值
		QuestionnaireName questionnaireNameInfo = questionnaireNameDao.findById(questionnaireNameUuid).get();

		// 用戶取值
		People peopleInfo = peopleDao.findById(peopleid).get();

		// 新增填寫日期
		WriteDate writeDate = new WriteDate(UUID.randomUUID(), peopleInfo.getPeopleUuid(),
				questionnaireNameInfo.getQuestionnaireUuid(), peopleInfo.getName(), LocalDateTime.now());

		// 儲存填寫時間
		writeDateDao.save(writeDate);

		if (req.getOptionsUuidList() == null) {
			return new QuestionnaireRes("並無任何作答，謝謝您的填寫");
		}

		// 用戶填完問卷會得到很多的選項的UUID
		List<String> optionsList = req.getOptionsUuidList();

		// 預備接<PeopleChoose>類別
		List<PeopleChoose> peopleChooseList = new ArrayList<>();

		// 預備接<Options>類別
		List<Options> optionsInfoList = new ArrayList<>();

		// 迴圈用戶選的選項 ps.裡面放的是字串型態的uuid
		for (String optionsItem : optionsList) {

			// 將每個選項的字串轉成uuid
			UUID optionsUuid = UUID.fromString(optionsItem);

			// 選項接值
			Options optionsInfo = optionsDao.findById(optionsUuid).get();

			// 用戶選擇新增
			PeopleChoose peopleChoose = new PeopleChoose(UUID.randomUUID(), optionsInfo.getQuestionnaireUuid(),
					optionsInfo.getTopicUuid(), optionsInfo.getOptionsUuid(), peopleInfo.getPeopleUuid(),
					optionsInfo.getQuestionName());

			// 用戶選擇新增，先存進List
			peopleChooseList.add(peopleChoose);

			// 將該選項選的人數+1
			optionsInfo.setTotalPeople(optionsInfo.getTotalPeople() + 1);
			if (peopleInfo.getManOrGirl().contains("男")) {
				optionsInfo.setMan(optionsInfo.getMan() + 1);
			} else {
				optionsInfo.setGirl(optionsInfo.getGirl() + 1);
			}
			// 選項先存進List
			optionsInfoList.add(optionsInfo);
		}

		// ---計算人數
		// 儲存用戶選擇
		peopleChooseDao.saveAll(peopleChooseList);
		// 儲存選項(男或女、總人數)
		optionsDao.saveAll(optionsInfoList);

		// ---計算百分比
		double topicTitleTotal = 0;

		// 翻出所有用戶的選項
		peopleChooseList = peopleChooseDao.findAll();

		// 用戶的所有選擇
		for (var chooseItem : peopleChooseList) {

			// 計算該問題的總人數 ps.每跑完該問題底下的選項時歸零
			topicTitleTotal = 0;

			// 找出該問題底下的所有選項
			optionsInfoList = optionsDao.findByTopicUuid(chooseItem.getTopicUuid());

			// 計算該問題底下有幾個選項 ps.計數器
			int optionsTotalPeople = 0;
			// 迴圈該問題底下的選項
			for (var optionsItem : optionsInfoList) {

				// 計算該問題的總人數
				topicTitleTotal += optionsItem.getTotalPeople();
				// 當跑完上層迴圈時，代表該問題底下的所有選項人數已加總完成
				optionsTotalPeople++;
				// 因為問題底下的選項有多少個，就要計算幾次，當計算完時才會進來
				if (optionsTotalPeople == optionsInfoList.size()) {

					// 跑該問題底下的每個選項，並計算百分比儲存進去
					for (var optionsItem2 : optionsInfoList) {

						// 計算百分比 (該問題總人數/該選項的總人數)*100
						double optionsPercentage = (optionsItem2.getTotalPeople() / topicTitleTotal) * 100;
						optionsItem2.setPercentage(Math.floor(optionsPercentage * 10.0) / 10.0);

						optionsDao.save(optionsItem2);
					}
				}

			}
		}
		return new QuestionnaireRes(writeDate, "成功");
	}

	/*
	 * =============================================================================
	 */

	@Override
	public QuestionnaireRes getPeopleAndWriteDateInfo(QuestionnaireReq req) {
		QuestionnaireRes res = new QuestionnaireRes();
		// 請求問卷名稱的UUID
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		List<WriteDate> writeDateInfo = writeDateDao
				.findByQuestionnaireUuidOrderByWriteDateTimeDesc(questionnaireNameUuid);
		if (writeDateInfo.isEmpty()) {
			res.setMessage("該問卷無人作答");
		}
		res.setWriteListInfo(writeDateInfo);
		return res;
	}

	/*
	 * =============================================================================
	 */
	@Override
	public QuestionnaireRes getQuestionnaireFeedback(QuestionnaireReq req) {

		UUID writeDateUuid = UUID.fromString(req.getWriteDateUuid());

		WriteDate writeDate = writeDateDao.findById(writeDateUuid).get();
		People people = peopleDao.findById(writeDate.getPeopleId()).get();
		QuestionnaireName questionnaireNameInfo = questionnaireNameDao.findById(writeDate.getQuestionnaireUuid()).get();

		List<TopicTitle> topicTitleInfo = topicTitleDao
				.findByQuestionnaireUuid(questionnaireNameInfo.getQuestionnaireUuid());
		List<Options> optionsInfo = optionsDao.findByQuestionnaireUuid(questionnaireNameInfo.getQuestionnaireUuid());
		List<PeopleChoose> chooseInfo = peopleChooseDao.findByPeopleId(people.getPeopleUuid());
		List<String> chooseOptionsForPeople = new ArrayList<>();
		for (var optionsItem : optionsInfo) {
			for (var chooseItem : chooseInfo) {
				if (optionsItem.getOptionsUuid().equals(chooseItem.getOptionsUuid())) {
					chooseOptionsForPeople.add(optionsItem.getQuestionName());// 用戶的選擇
				}
			}
		}

		// 人物資訊、填寫日期、問卷名稱、問題名稱、選項、用戶選的選項
		return new QuestionnaireRes(people, writeDate, questionnaireNameInfo, topicTitleInfo, optionsInfo,
				chooseOptionsForPeople);
	}

}
