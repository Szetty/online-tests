package edu.onlinetests.backend.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.onlinetests.backend.persistence.QuestionStatisticDAO;
import edu.onlinetests.backend.persistence.TestDAO;
import edu.onlinetests.backend.service.TestService;
import edu.onlinetests.model.Category;
import edu.onlinetests.model.Question;
import edu.onlinetests.model.QuestionStatistic;
import edu.onlinetests.model.TestResult;
import edu.onlinetests.model.User;
import edu.onlinetests.utils.SessionUtils;

@Component
public class TestServiceImpl implements TestService {

	private TestDAO testDAO;
	private QuestionStatisticDAO statisticDAO;
	
	@Autowired
	public TestServiceImpl(TestDAO testDAO, QuestionStatisticDAO statisticDAO) {
		this.testDAO = testDAO;
		this.statisticDAO = statisticDAO;
	}
	
	@Override
	public void storeTestResult(TestResult testResult) {
		testDAO.storeTestResult(testResult);
		
	}

	@Override
	public List<TestResult> getBestResultsOfCategory(Category category) {
		return testDAO.getBestResultsOfCategory(category);
	}

	@Override
	public List<TestResult> getOwnResults(User user) {
		return testDAO.getOwnResults(user);
	}

	@Override
	public int evaluateQuiz(Map<Question, String> answersForQuestions, Category categoryForTest) {
		int correctAnswers = 0;
		for (Entry<Question,String> entry : answersForQuestions.entrySet()) {
			generateStatistic(entry.getKey(), entry.getValue());
			if(entry.getKey().getCorrectAnswer().equals(entry.getValue())) {
				correctAnswers++;
			}
		}
		generateTestResult(correctAnswers, answersForQuestions.size(), categoryForTest);
		return correctAnswers;
	}

	private void generateTestResult(int correctAnswers, int numberOfQuestions, Category category) {
		final TestResult tr = new TestResult();
		tr.setScore((float)correctAnswers / (float)numberOfQuestions);
		tr.setUser(SessionUtils.getSessionUser());
		tr.setCategory(category);
		new Thread() {
			public void run() {
				testDAO.storeTestResult(tr);
			};
		}.start();
	}

	private void generateStatistic(Question question, String answer) {
		final QuestionStatistic questionStatistic = question.getQuestionStatistic() != null ? question.getQuestionStatistic() : new QuestionStatistic();
		if(questionStatistic.getQuestion() == null) {
			questionStatistic.setQuestion(question);
		}
		switch (answer) {
		case "A":
			questionStatistic.setAnswerA(questionStatistic.getAnswerA() + 1);
			break;
		case "B":
			questionStatistic.setAnswerB(questionStatistic.getAnswerB() + 1);
			break;
		case "C":
			questionStatistic.setAnswerC(questionStatistic.getAnswerC() + 1);
			break;
		case "D":
			questionStatistic.setAnswerD(questionStatistic.getAnswerD() + 1);
			break;
		}
		questionStatistic.setAnswerNumber(questionStatistic.getAnswerNumber() + 1);
		new Thread() {
			public void run() {
				statisticDAO.storeQuestionStatistic(questionStatistic);
			};
		}.start();
	}

}
