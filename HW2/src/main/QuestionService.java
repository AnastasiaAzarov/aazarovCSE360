package main;

import exception.ValidationException;
import java.util.*;

public class QuestionService {
    private Questions questions;
    private Answers answers;
    private static int nextId = 0;  // Counter for generating question IDs
    private static int nextAnswerId = 0;  // Counter for generating answer IDs

    // Constructor
    public QuestionService(Questions questions, Answers answers) {
        this.questions = questions;
        this.answers = answers;
    }

    // Add a new answer to a question
    public void addAnswer(int questionId, String answerText, String studentId) 
            throws ValidationException {
        // First check if the question exists
        Question question = questions.getQuestionById(questionId);
        if (question == null) {
            throw new ValidationException("Question not found");
        }

        // Create new answer
        Answer newAnswer = new Answer(
            getNextAnswerId(),
            questionId,
            answerText,
            studentId
        );

        // Save the answer
        answers.createAnswer(newAnswer);
    }

    // Mark an answer as accepted and resolve the question
    public void acceptAnswer(int questionId, int answerId) throws ValidationException {
        // Check if question exists
        Question question = questions.getQuestionById(questionId);
        if (question == null) {
            throw new ValidationException("Question not found");
        }

        // Try to accept the answer
        answers.acceptAnswer(answerId, questionId);

        // If we get here, the answer was accepted, so mark question as resolved
        question.setResolved(true);
    }

    // Display all unresolved questions and their answers
    public void displayUnresolvedQuestionsWithAnswers() {
        // Get all questions
        List<Question> allQuestions = questions.getAllQuestions();
        
        // Keep track if we found any unresolved questions
        boolean foundUnresolved = false;
        
        // For each question that's not resolved
        for (Question question : allQuestions) {
            if (!question.isResolved()) {
                foundUnresolved = true;
                
                // Display question information
                System.out.println("\nQuestion ID: " + question.getId());
                System.out.println("Asked by: " + question.getStudentId());
                System.out.println("Question: " + question.getText());
                
                // Get and display answers for this question
                List<Answer> questionAnswers = answers.getAnswersForQuestion(question.getId());
                
                if (questionAnswers.isEmpty()) {
                    System.out.println("No answers yet.");
                } else {
                    System.out.println("Answers:");
                    for (Answer answer : questionAnswers) {
                        System.out.println("- Answer ID: " + answer.getId());
                        System.out.println("  Answered by: " + answer.getStudentId());
                        System.out.println("  Answer: " + answer.getText());
                    }
                }
                System.out.println("------------------------");
            }
        }
        
        if (!foundUnresolved) {
            System.out.println("No unresolved questions found.");
        }
    }

    // Create a follow-up question
    public Question createFollowUpQuestion(int originalQuestionId, String newText, String studentId) 
            throws ValidationException {
        // Find the original question
        Question originalQuestion = questions.getQuestionById(originalQuestionId);
        
        // Check if original question exists
        if (originalQuestion == null) {
            throw new ValidationException("Original question not found");
        }
        
        // Create new question with reference to original
        Question newQuestion = new Question(
            getNextId(),
            newText,
            studentId,
            originalQuestionId  // This links the new question to the original
        );
        
        // Save the new question
        questions.createQuestion(newQuestion);
        
        return newQuestion;
    }

    // Create a new standalone question
    public Question createNewQuestion(String text, String studentId) throws ValidationException {
        // Create new question
        Question newQuestion = new Question(
            getNextId(),
            text,
            studentId
        );
        
        // Save the question
        questions.createQuestion(newQuestion);
        
        return newQuestion;
    }

    // Get all unresolved questions
    public List<Question> getUnresolvedQuestions() {
        List<Question> unresolved = new ArrayList<>();
        
        for (Question question : questions.getAllQuestions()) {
            if (!question.isResolved()) {
                unresolved.add(question);
            }
        }
        
        return unresolved;
    }

    // Get answers for a specific question
    public List<Answer> getAnswersForQuestion(int questionId) {
        return answers.getAnswersForQuestion(questionId);
    }

    // Generate a new unique ID for questions
    private synchronized int getNextId() {
        nextId = nextId + 1;  // Increment the counter
        return nextId;        // Return the new ID
    }

    // Generate a new unique ID for answers
    private synchronized int getNextAnswerId() {
        nextAnswerId = nextAnswerId + 1;  // Increment the counter
        return nextAnswerId;              // Return the new ID
    }
}