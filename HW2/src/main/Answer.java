package main;

public class Answer {
    private int id;
    private int questionId;
    private String text;
    private String studentId;
    private boolean isAccepted;

    // constructor
    public Answer(int id, int questionId, String text, String studentId) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.studentId = studentId;
        this.isAccepted = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getQuestionId() { return questionId; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getStudentId() { return studentId; }
    public boolean isAccepted() { return isAccepted; }
    public void setAccepted(boolean accepted) { isAccepted = accepted; }
}