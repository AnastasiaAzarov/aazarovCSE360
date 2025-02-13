package main;

import exception.ValidationException;
import java.util.*;

public class Questions {
    private List<Question> allQuestions;

    // Constructor
    public Questions() {
        this.allQuestions = new ArrayList<>();
    }

    // Add a new question
    public void createQuestion(Question question) throws ValidationException {
        // Check if the question is valid
        checkQuestion(question);
        // Add to list if valid
        allQuestions.add(question);
    }

    // Get all questions
    public List<Question> getAllQuestions() {
        return new ArrayList<>(allQuestions);
    }

    // Find a question by its ID
    public Question getQuestionById(int id) {
        for (Question question : allQuestions) {
            if (question.getId() == id) {
                return question;
            }
        }
        return null;
    }

    // Update a question's text
    public void updateQuestion(int id, String newText) throws ValidationException {
        Question question = getQuestionById(id);
        if (question != null) {
            checkQuestionText(newText);
            question.setText(newText);
        }
    }

    // Delete a question
    public void deleteQuestion(int id) {
        Question questionToRemove = null;
        for (Question question : allQuestions) {
            if (question.getId() == id) {
                questionToRemove = question;
                break;
            }
        }
        if (questionToRemove != null) {
            allQuestions.remove(questionToRemove);
        }
    }

    // Basic validation checks
    private void checkQuestion(Question question) throws ValidationException {
        if (question == null) {
            throw new ValidationException("Question cannot be empty");
        }
        checkQuestionText(question.getText());
        checkStudentId(question.getStudentId());
    }

    private void checkQuestionText(String text) throws ValidationException {
        if (text == null || text.length() < 10) {
            throw new ValidationException("Question must be at least 10 characters long");
        }
        if (text.length() > 1000) {
            throw new ValidationException("Question is too long (max 1000 characters)");
        }
    }

    private void checkStudentId(String studentId) throws ValidationException {
        if (studentId == null || studentId.isEmpty()) {
            throw new ValidationException("Student ID is required");
        }
    }

    // Check if a question exists
    public boolean exists(int id) {
        for (Question question : allQuestions) {
            if (question.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // Count total questions
    public int getQuestionCount() {
        return allQuestions.size();
    }

    // Clear all questions
    public void clearAllQuestions() {
        allQuestions.clear();
    }

    // Get unresolved questions
    public List<Question> getUnresolvedQuestions() {
        List<Question> unresolved = new ArrayList<>();
        for (Question question : allQuestions) {
            if (!question.isResolved()) {
                unresolved.add(question);
            }
        }
        return unresolved;
    }

    // Get questions by student ID
    public List<Question> getQuestionsByStudent(String studentId) {
        List<Question> studentQuestions = new ArrayList<>();
        for (Question question : allQuestions) {
            if (question.getStudentId().equals(studentId)) {
                studentQuestions.add(question);
            }
        }
        return studentQuestions;
    }
}