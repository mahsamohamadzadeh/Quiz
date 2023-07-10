package com.project.quiz.repository;

import com.project.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionDao extends JpaRepository<Question,Integer> {
    List<Question> findByCategory(String category);

    @Query(nativeQuery = true,value = "select * from question q where q.category=:category order by RANDOM() lIMIT :numQ")
    List<Question> findRandomQuestionByCategory(String category, int numQ);
}
