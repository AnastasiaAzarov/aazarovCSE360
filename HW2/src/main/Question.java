package main;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int id;
    private String text;
    private String studentId;
    private boolean isResolved;
    private Integer parentQuestionId;
    private List<String> keywords;

    public Question(int id, String text, String studentId) {
        this(id, text, studentId, null);
    }

    public Question(int id, String text, String studentId, Integer parentQuestionId) {
        this.id = id;
        this.text = text;
        this.studentId = studentId;
        this.isResolved = false;
        this.parentQuestionId = parentQuestionId;
        this.keywords = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getStudentId() { return studentId; }
    public boolean isResolved() { return isResolved; }
    public void setResolved(boolean resolved) { isResolved = resolved; }
    public Integer getParentQuestionId() { return parentQuestionId; }
    public void setParentQuestionId(Integer parentQuestionId) { this.parentQuestionId = parentQuestionId; }
    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }
}