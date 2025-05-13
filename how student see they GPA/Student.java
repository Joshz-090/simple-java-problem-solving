import java.util.*;

public class Student {
    private int studentId; // Changed to camelCase for consistency
    private String studentName;
    private Map<String, Double> grades;
    private Map<String, Integer> courseCredits;

    public Student(int studentId, String studentName) {
        setStudentId(studentId);
        setStudentName(studentName);
        this.grades = new LinkedHashMap<>();
        this.courseCredits = new LinkedHashMap<>();
        initializeCourseCredits();
    }

    private void initializeCourseCredits() {
        courseCredits.put("Static and Probability", 3);
        courseCredits.put("Object Oriented Programming", 3);
        courseCredits.put("Operating System", 4);
        courseCredits.put("Fundamentals of Software", 4);
        courseCredits.put("Computer Architecture", 3);
        courseCredits.put("Data Structure", 3);
    }

    // Properly validated setters
    public void setStudentId(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be positive");
        }
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        this.studentName = studentName.trim();
    }

    // Getters
    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public Map<String, Double> getGrades() {
        return new LinkedHashMap<>(grades); // Return a copy to maintain encapsulation
    }

    public boolean addGrade(String course, double grade) {
        if (!courseCredits.containsKey(course)) {
            return false;
        }
        if (grade < 0 || grade > 100) {
            return false;
        }
        grades.put(course, grade);
        return true;
    }

    private String getLetterGrade(double grade) {
        if (grade >= 90)
            return "A+";
        if (grade >= 85)
            return "A";
        if (grade >= 80)
            return "A-";
        if (grade >= 75)
            return "B+";
        if (grade >= 70)
            return "B";
        if (grade >= 65)
            return "B-";
        if (grade >= 60)
            return "C+";
        if (grade >= 55)
            return "C";
        if (grade >= 50)
            return "C-";
        if (grade >= 40)
            return "D";
        return "F";
    }

    private double getGradePoints(String letterGrade) {
        switch (letterGrade) {
            case "A+":
            case "A":
                return 4.0;
            case "A-":
                return 3.75;
            case "B+":
                return 3.5;
            case "B":
                return 3.0;
            case "B-":
                return 2.75;
            case "C+":
                return 2.5;
            case "C":
                return 2.0;
            case "C-":
                return 1.75;
            case "D":
                return 1.0;
            default:
                return 0.0;
        }
    }

    public double calculateGPA() {
        double totalGradePoints = 0;
        int totalCredits = 0;

        for (Map.Entry<String, Double> entry : grades.entrySet()) {
            String course = entry.getKey();
            double grade = entry.getValue();
            int credits = courseCredits.get(course);

            String letterGrade = getLetterGrade(grade);
            double gradePoints = getGradePoints(letterGrade);

            totalGradePoints += gradePoints * credits;
            totalCredits += credits;
        }

        return totalCredits == 0 ? 0.0 : totalGradePoints / totalCredits;
    }

    public void printReportCard() {
        System.out.println("\n📄 Report Card for: " + getStudentName() + " (ID: " + getStudentId() + ")");
        System.out.println("-----------------------------------------------------------------");
        System.out.printf("| %-30s | %-5s | %-3s | %-6s |\n", "Course", "Grade", "Mark", "Credits");
        System.out.println("-----------------------------------------------------------------");

        for (Map.Entry<String, Double> entry : getGrades().entrySet()) {
            String course = entry.getKey();
            double grade = entry.getValue();
            String letter = getLetterGrade(grade);
            int credits = courseCredits.get(course);
            System.out.printf("| %-30s | %5.1f | %3s | %6d |\n", course, grade, letter, credits);
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.printf("| %-42s | %6.2f |\n", "GPA (on 4.0 scale)", calculateGPA());
        System.out.println("-----------------------------------------------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        Student student = new Student(id, name);

        // Demonstrate setter usage
        System.out.print("\nWould you like to update student name? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter new student name: ");
            student.setStudentName(scanner.nextLine());
        }

        // Add grades using the properly encapsulated method
        for (String course : student.courseCredits.keySet()) {
            while (true) {
                System.out.print("Enter grade for " + course + " (0-100): ");
                try {
                    double grade = scanner.nextDouble();
                    scanner.nextLine();
                    if (student.addGrade(course, grade)) {
                        break;
                    }
                    System.out.println("Invalid grade! Must be between 0-100.");
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a numeric value!");
                    scanner.nextLine();
                }
            }
        }

        // Demonstrate getter usage
        System.out.println("\nStudent Information:");
        System.out.println("ID: " + student.getStudentId());
        System.out.println("Name: " + student.getStudentName());

        student.printReportCard();
        scanner.close();
    }
}