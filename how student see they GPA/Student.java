import java.util.*;

public class GPACalculator {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🎓 Welcome to the Advanced GPA Calculator 🎓");
        System.out.println("===========================================");

        // Create a new student
        Student student = createStudent();

        // Course selection and setup
        setupCourses(student);

        // Grade entry
        enterGrades(student);

        // Display results
        displayResults(student);

        scanner.close();
    }

    private static Student createStudent() {
        System.out.println("\n📝 Student Information");
        System.out.println("---------------------");

        String name = getInput("Enter student name: ",
                input -> !input.trim().isEmpty(),
                "Name cannot be empty!");

        return new Student(name);
    }

    private static void setupCourses(Student student) {
        System.out.println("\n📚 Course Setup");
        System.out.println("---------------");

        int numCourses = getIntInput("How many courses are you taking this semester? (1-10): ",
                1, 10);

        for (int i = 1; i <= numCourses; i++) {
            System.out.println("\nCourse #" + i);
            String courseName = getInput("Enter course name: ",
                    input -> !input.trim().isEmpty(),
                    "Course name cannot be empty!");

            int credits = getIntInput("Enter credit hours for " + courseName + " (1-5): ",
                    1, 5);

            student.addCourse(courseName, credits);
        }
    }

    private static void enterGrades(Student student) {
        System.out.println("\n📝 Grade Entry");
        System.out.println("--------------");

        Map<String, Integer> courses = student.getCourses();
        for (String course : courses.keySet()) {
            double grade = getDoubleInput("Enter grade for " + course + " (0-100): ",
                    0, 100);
            student.addGrade(course, grade);
        }
    }

    private static void displayResults(Student student) {
        System.out.println("\n📊 Results");
        System.out.println("----------");

        student.printReportCard();

        System.out.println("\nAdditional Information:");
        System.out.printf("Total Credits: %d\n", student.getTotalCredits());
        System.out.printf("GPA (4.0 scale): %.2f\n", student.calculateGPA());

        // Add some ASCII art for fun
        System.out.println("\n" + getGPAArt(student.calculateGPA()));
    }

    private static String getGPAArt(double gpa) {
        if (gpa >= 3.5)
            return "   ★ ★ ★ ★ ★\n" +
                    "  EXCELLENT WORK!\n" +
                    "   ★ ★ ★ ★ ★";
        else if (gpa >= 2.5)
            return "   ✓ ✓ ✓ ✓ ✓\n" +
                    "  GOOD PERFORMANCE\n" +
                    "   ✓ ✓ ✓ ✓ ✓";
        else
            return "   ✚ ✚ ✚ ✚ ✚\n" +
                    "  ROOM FOR IMPROVEMENT\n" +
                    "   ✚ ✚ ✚ ✚ ✚";
    }

    // Utility methods for input validation
    private static String getInput(String prompt, Validator<String> validator, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (validator.validate(input)) {
                return input.trim();
            }
            System.out.println(errorMsg);
        }
    }

    private static int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Please enter a number between %d and %d.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static double getDoubleInput(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Please enter a number between %.1f and %.1f.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    @FunctionalInterface
    private interface Validator<T> {
        boolean validate(T input);
    }
}

class Student {
    private String studentName;
    private Map<String, Integer> courses; // Course name to credit hours
    private Map<String, Double> grades; // Course name to grade

    public Student(String studentName) {
        this.studentName = studentName;
        this.courses = new LinkedHashMap<>();
        this.grades = new LinkedHashMap<>();
    }

    public void addCourse(String courseName, int credits) {
        courses.put(courseName, credits);
    }

    public boolean addGrade(String courseName, double grade) {
        if (!courses.containsKey(courseName)) {
            return false;
        }
        if (grade < 0 || grade > 100) {
            return false;
        }
        grades.put(courseName, grade);
        return true;
    }

    public Map<String, Integer> getCourses() {
        return new LinkedHashMap<>(courses);
    }

    public int getTotalCredits() {
        return courses.values().stream().mapToInt(Integer::intValue).sum();
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
                return 4.0;
            case "A":
                return 4.0;
            case "A-":
                return 3.75;
            case "B+":
                return 3.5;
            case "B":
                return 3.0;
            case "B-":
                return 2.5;
            case "C+":
                return 2.25;
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
            int credits = courses.get(course);

            String letterGrade = getLetterGrade(grade);
            double gradePoints = getGradePoints(letterGrade);

            totalGradePoints += gradePoints * credits;
            totalCredits += credits;
        }

        return totalCredits == 0 ? 0.0 : totalGradePoints / totalCredits;
    }

    public void printReportCard() {
        System.out.println("\n📄 Report Card for: " + studentName);
        System.out.println("=================================================================");
        System.out.printf("| %-30s | %-6s | %-5s | %-7s | %-5s |\n",
                "Course", "Grade", "Mark", "Credits", "Points");
        System.out.println("=================================================================");

        for (Map.Entry<String, Double> entry : grades.entrySet()) {
            String course = entry.getKey();
            double grade = entry.getValue();
            String letter = getLetterGrade(grade);
            int credits = courses.get(course);
            double points = getGradePoints(letter);

            System.out.printf("| %-30s | %6.1f | %-5s | %7d | %5.1f |\n",
                    course, grade, letter, credits, points);
        }
        
        System.out.println("=================================================================");
        System.out.printf("| %-58s | %5.2f |\n", "GPA (on 4.0 scale)", calculateGPA());
        System.out.println("=================================================================");
    }
}
