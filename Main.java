
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GradeManager manager = new GradeManager();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   STUDENT GRADE MANAGEMENT SYSTEM");
        System.out.println("========================================");

        boolean running = true;

        while (running) {
            displayMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    manager.viewAllStudents();
                    break;
                case 3:
                    manager.searchStudent(readId());
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    manager.displayGradeReport(readId());
                    break;
                case 7:
                    manager.displayClassStatistics();
                    break;
                case 8:
                    running = false;
                    System.out.println("Thank you for using the system!");
                    break;
                default:
                    System.out.println(
                        "Invalid choice. Enter a number from 1 to 8.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--------------- MAIN MENU ---------------");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Display Grade Report");
        System.out.println("7. Class Statistics");
        System.out.println("8. Exit");
        System.out.println("------------------------------------------");
    }

    private static void addStudent() {
        System.out.println("\n--- Add Student ---");

        String id = readId();
        String name = readName();

        double javaMarks = readMarks("Java marks: ");
        double mathsMarks = readMarks("Mathematics marks: ");
        double englishMarks = readMarks("English marks: ");

        Student student = new Student(
            id, name, javaMarks, mathsMarks, englishMarks
        );

        manager.addStudent(student);
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");

        String id = readId();

        if (manager.findStudent(id) == null) {
            System.out.println("Student not found.");
            return;
        }

        String name = readName();
        double javaMarks = readMarks("New Java marks: ");
        double mathsMarks = readMarks("New Mathematics marks: ");
        double englishMarks = readMarks("New English marks: ");

        manager.updateStudent(
            id, name, javaMarks, mathsMarks, englishMarks
        );
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");

        String id = readId();
        Student student = manager.findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student: " + student.getName());
        String confirmation = readNonEmpty(
            "Are you sure you want to delete this student? (yes/no): "
        );

        if (confirmation.equalsIgnoreCase("yes")) {
            manager.deleteStudent(id);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static String readId() {
        return readNonEmpty("Enter student ID: ");
    }

    private static String readName() {
        while (true) {
            String name = readNonEmpty("Enter student name: ");

            if (name.matches("[\\p{L}][\\p{L} .'-]*")) {
                return name;
            }

            System.out.println(
                "Enter a valid name using letters and ordinary name punctuation.");
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty() && !value.contains("\t")) {
                return value;
            }

            System.out.println("Input cannot be empty or contain tabs.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readMarks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                double marks = Double.parseDouble(input);

                if (Double.isFinite(marks) && marks >= 0 && marks <= 100) {
                    return marks;
                }

                System.out.println(
                    "Marks must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println(
                    "Enter valid numeric marks, such as 85 or 76.5.");
            }
        }
    }
}
