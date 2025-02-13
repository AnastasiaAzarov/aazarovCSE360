package main;

import exception.ValidationException;
import java.util.Scanner;
import java.util.List;

public class QuestionConsole {
    private QuestionService questionService;
    private Scanner scanner;

    // Constructor
    public QuestionConsole() {
        Questions questions = new Questions();
        Answers answers = new Answers();
        this.questionService = new QuestionService(questions, answers);
        this.scanner = new Scanner(System.in);
    }

    // Main menu loop
    public void start() {
        boolean keepRunning = true;
        
        while (keepRunning) {
            try {
                showMainMenu();
                int choice = getNumberFromUser("Enter your choice: ");
                
                // Process user's choice
                switch (choice) {
                    case 1:
                        askNewQuestion();
                        break;
                    case 2:
                        viewAllQuestions();
                        break;
                    case 3:
                        answerQuestion();
                        break;
                    case 4:
                        viewAnswersForQuestion();
                        break;
                    case 5:
                        markAnswerAsAccepted();
                        break;
                    case 6:
                        createFollowUpQuestion();
                        break;
                    case 7:
                        viewUnresolvedQuestions();
                        break;
                    case 8:
                        keepRunning = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
                
                if (keepRunning) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
                waitForEnter();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                waitForEnter();
            }
        }
        scanner.close();
    }

    // Display the main menu
    private void showMainMenu() {
        System.out.println("\n=== Question and Answer System ===");
        System.out.println("1. Ask a new question");
        System.out.println("2. View all questions");
        System.out.println("3. Answer a question");
        System.out.println("4. View answers for a question");
        System.out.println("5. Mark answer as accepted");
        System.out.println("6. Create follow-up question");
        System.out.println("7. View unresolved questions");
        System.out.println("8. Exit");
        System.out.println("================================");
    }

    // Ask a new question
    private void askNewQuestion() throws ValidationException {
        System.out.println("\n--- Ask a New Question ---");
        
        System.out.println("Enter your student ID: ");
        String studentId = scanner.nextLine();
        
        System.out.println("Enter your question: ");
        String questionText = scanner.nextLine();

        Question newQuestion = questionService.createNewQuestion(questionText, studentId);
        System.out.println("Question created successfully with ID: " + newQuestion.getId());
    }

    // View all questions
    private void viewAllQuestions() {
        List<Question> questions = questionService.getUnresolvedQuestions();
        
        if (questions.isEmpty()) {
            System.out.println("No questions found.");
            return;
        }

        System.out.println("\n--- All Questions ---");
        for (Question q : questions) {
            System.out.println("\nQuestion ID: " + q.getId());
            System.out.println("Asked by: " + q.getStudentId());
            System.out.println("Question: " + q.getText());
            System.out.println("Status: " + (q.isResolved() ? "Resolved" : "Unresolved"));
        }
    }

    // Answer a question
    private void answerQuestion() throws ValidationException {
        System.out.println("\n--- Answer a Question ---");
        
        int questionId = getNumberFromUser("Enter question ID: ");
        List<Answer> existingAnswers = questionService.getAnswersForQuestion(questionId);
        
        // Show existing answers
        if (!existingAnswers.isEmpty()) {
            System.out.println("\nExisting answers:");
            for (Answer a : existingAnswers) {
                System.out.println("- " + a.getText());
            }
        }

        System.out.println("\nEnter your student ID: ");
        String studentId = scanner.nextLine();
        
        System.out.println("Enter your answer: ");
        String answerText = scanner.nextLine();

        // Create the answer (you'll need to implement this in QuestionService)
        questionService.addAnswer(questionId, answerText, studentId);
        System.out.println("Answer added successfully!");
    }

    // View answers for a question
    private void viewAnswersForQuestion() {
        System.out.println("\n--- View Answers ---");
        
        int questionId = getNumberFromUser("Enter question ID: ");
        List<Answer> answers = questionService.getAnswersForQuestion(questionId);
        
        if (answers.isEmpty()) {
            System.out.println("No answers found for this question.");
            return;
        }

        for (Answer a : answers) {
            System.out.println("\nAnswer ID: " + a.getId());
            System.out.println("Answered by: " + a.getStudentId());
            System.out.println("Answer: " + a.getText());
            System.out.println("Status: " + (a.isAccepted() ? "Accepted" : "Not accepted"));
        }
    }

    // Mark answer as accepted
    private void markAnswerAsAccepted() throws ValidationException {
        System.out.println("\n--- Mark Answer as Accepted ---");
        
        int questionId = getNumberFromUser("Enter question ID: ");
        int answerId = getNumberFromUser("Enter answer ID: ");

        // You'll need to implement this in QuestionService
        questionService.acceptAnswer(questionId, answerId);
        System.out.println("Answer marked as accepted!");
    }

    // Create a follow-up question
    private void createFollowUpQuestion() throws ValidationException {
        System.out.println("\n--- Create Follow-up Question ---");
        
        int originalQuestionId = getNumberFromUser("Enter original question ID: ");
        
        System.out.println("Enter your student ID: ");
        String studentId = scanner.nextLine();
        
        System.out.println("Enter your follow-up question: ");
        String questionText = scanner.nextLine();

        Question newQuestion = questionService.createFollowUpQuestion(
            originalQuestionId, questionText, studentId
        );
        System.out.println("Follow-up question created with ID: " + newQuestion.getId());
    }

    // View unresolved questions
    private void viewUnresolvedQuestions() {
        System.out.println("\n--- Unresolved Questions ---");
        questionService.displayUnresolvedQuestionsWithAnswers();
    }

    // Helper method to get number input from user
    private int getNumberFromUser(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    // Helper method to wait for Enter key
    private void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}