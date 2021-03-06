package edu.onlinetests.backend.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.onlinetests.model.Category;
import edu.onlinetests.model.Question;

@Repository
public interface QuestionDAO {

	List<Question> getQuestionsOfCategory(Category category);
	
}
