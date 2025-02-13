package main;

import exception.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class Answers {
    private List<Answer> allAnswers;

    // Constructor
    public Answers() {
        this.allAnswers = new ArrayList<>();
    }

    // Add a new answer
    public void createAnswer(Answer answer) throws ValidationException {
        // Check if the answer is valid
        checkAnswer(answer);
        // Add to list if valid
        allAnswers.add(answer);
    }

    // Get all answers
    public List<Answer> getAllAnswers() {
        return new ArrayList<>(allAnswers);
    }

    // Get answers for a specific question
    public List<Answer> getAnswersForQuestion(int questionId) {
        List<Answer> questionAnswers = new ArrayList<>();
        for (Answer answer : allAnswers) {
            if (answer.getQuestionId() == questionId) {
                questionAnswers.add(answer);
            }
        }
        return questionAnswers;
    }

    // Find an answer by its ID
    public Answer getAnswerById(int id) {
        for (Answer answer : allAnswers) {
            if (answer.getId() == id) {
                return answer;
            }
        }
        return null;
    }

    // Update an answer's text
    public void updateAnswer(int id, String newText) throws ValidationException {
        Answer answer = getAnswerById(id);
        if (answer != null) {
            checkAnswerText(newText);
            answer.setText(newText);
        }
    }

    // Delete an answer
    public void deleteAnswer(int id) {
        Answer answerToRemove = null;
        for (Answer answer : allAnswers) {
            if (answer.getId() == id) {
                answerToRemove = answer;
                break;
            }
        }
        if (answerToRemove != null) {
            allAnswers.remove(answerToRemove);
        }
    }

    // Mark an answer as accepted
    public void acceptAnswer(int answerId, int questionId) throws ValidationException {
        Answer answer = getAnswerById(answerId);
        if (answer == null) {
            throw new ValidationException("Answer not found");
        }
        if (answer.getQuestionId() != questionId) {
            throw new ValidationException("This answer belongs to a different question");
        }
        answer.setAccepted(true);
    }

    // Basic validation checks
    private void checkAnswer(Answer answer) throws ValidationException {
        if (answer == null) {
            throw new ValidationException("Answer cannot be empty");
        }
        checkAnswerText(answer.getText());
        checkStudentId(answer.getStudentId());
        checkQuestionId(answer.getQuestionId());
    }
    //check if answer is correct format
    private void checkAnswerText(String text) throws ValidationException {
        if (text == null || text.length() < 5) {
            throw new ValidationException("Answer must be at least 5 characters long");
        }
        if (text.length() > 2000) {
            throw new ValidationException("Answer is too long (max 2000 characters)");
        }
    }
    //check if student entered ID
    private void checkStudentId(String studentId) throws ValidationException {
        if (studentId == null || studentId.isEmpty()) {
            throw new ValidationException("Student ID is required");
        }
    }

    private void checkQuestionId(int questionId) throws ValidationException {
        if (questionId <= 0) {
            throw new ValidationException("Invalid question ID");
        }
    }

    // Get accepted answers
    public List<Answer> getAcceptedAnswers() {
        List<Answer> acceptedAnswers = new ArrayList<>();
        for (Answer answer : allAnswers) {
            if (answer.isAccepted()) {
                acceptedAnswers.add(answer);
            }
        }
        return acceptedAnswers;
    }

    // Count total answers
    public int getAnswerCount() {
        return allAnswers.size();
    }

    // Clear all answers
    public void clearAllAnswers() {
        allAnswers.clear();
    }
}