package com.project.quiz.service;

import com.project.quiz.model.Question;
import com.project.quiz.model.QuestionWrapper;
import com.project.quiz.model.Quiz;
import com.project.quiz.model.Response;
import com.project.quiz.repository.QuestionDao;
import com.project.quiz.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category,int numQ,String title){

       List<Question> questions= questionDao.findRandomQuestionByCategory(category, numQ);

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<String>("success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionQuiz(Integer id){
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questionsFromDB=quiz.get().getQuestions();
        List<QuestionWrapper> questionWrappers=new ArrayList<>();
        for (Question q : questionsFromDB) {
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionWrappers.add(qw);
        }
        return new ResponseEntity<>(questionWrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses){
        Quiz quiz=quizDao.findById(id).get();
        List<Question> questions=quiz.getQuestions();
        int right=0;
        int i=0;
        for (Response response:responses){
            if (response.getResponse().equals(questions.get(i).getRightAnswer())){
                right ++;
                i++;
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);

    }
}
