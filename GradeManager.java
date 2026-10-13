
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GradeManager {
    private final List<Student> students;

    public GradeManager() {
        students = FileManager.loadStudents();
    }

    public boolean addStudent(Student student) {
        if (findStudent(student.getId()) != null) {
            System.out.println("Student ID already exists.");
            return false;
        }

        students.add(student);

        if (!FileManager.saveStudents(students)) {
            students.remove(student);
            System.out.println("Student could not be saved.");
            return false;
        }

        System.out.println("Student added successfully.");
        return true;
    }

    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        printHeader();

        for (Student student : students) {
            student.display();
        }
    }

    public Student findStudent(String id) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }

        return null;
    }

    public void searchStudent(String id) {
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        printHeader();
        student.display();
    }

    public boolean updateStudent(String id, String newName,
                                 double javaMarks,
                                 double mathsMarks,
                                 double englishMarks) {
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return false;
        }

        String oldName = student.getName();
        double oldJava = student.getJavaMarks();
        double oldMaths = student.getMathsMarks();
        double oldEnglish = student.getEnglishMarks();

        student.setName(newName);
        student.setMarks(javaMarks, mathsMarks, englishMarks);

        if (!FileManager.saveStudents(students)) {
            student.setName(oldName);
            student.setMarks(oldJava, oldMaths, oldEnglish);
            System.out.println("Update could not be saved.");
            return false;
        }

        System.out.println("Student updated successfully.");
        return true;
    }

    public boolean deleteStudent(String id) {
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return false;
        }

        students.remove(student);

        if (!FileManager.saveStudents(students)) {
            students.add(student);
            System.out.println("Deletion could not be saved.");
            return false;
        }

        System.out.println("Student deleted successfully.");
        return true;
    }

    public void displayGradeReport(String id) {
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\n========== STUDENT GRADE REPORT ==========");
        System.out.println("Student ID : " + student.getId());
        System.out.println("Name       : " + student.getName());
        System.out.printf("Java       : %.2f/100%n",
                          student.getJavaMarks());
        System.out.printf("Mathematics: %.2f/100%n",
                          student.getMathsMarks());
        System.out.printf("English    : %.2f/100%n",
                          student.getEnglishMarks());
        System.out.printf("Total      : %.2f/300%n",
                          student.getTotal());
        System.out.printf("Percentage : %.2f%%%n",
                          student.getPercentage());
        System.out.println("Grade      : " + student.getGrade());
        System.out.println("Result     : "
                           + (student.isPassed() ? "PASS" : "FAIL"));
        System.out.println("==========================================");
    }

    public void displayClassStatistics() {
        if (students.isEmpty()) {
            System.out.println("No records available for statistics.");
            return;
        }

        double totalPercentage = 0;
        int passed = 0;
        int failed = 0;

        Student topper = students.get(0);

        for (Student student : students) {
            totalPercentage += student.getPercentage();

            if (student.isPassed()) {
                passed++;
            } else {
                failed++;
            }

            if (student.getPercentage() > topper.getPercentage()) {
                topper = student;
            }
        }

        double average = totalPercentage / students.size();

        System.out.println("\n========== CLASS STATISTICS ==========");
        System.out.println("Total students : " + students.size());
        System.out.println("Passed         : " + passed);
        System.out.println("Failed         : " + failed);
        System.out.printf("Class average  : %.2f%%%n", average);
        System.out.println("Top performer  : " + topper.getName()
                           + " (" + topper.getId() + ")");
        System.out.printf("Top percentage : %.2f%%%n",
                           topper.getPercentage());
        System.out.println("=======================================");
    }

    private void printHeader() {
        System.out.printf(
            "%-12s %-20s %8s %8s %8s %9s %10s %-6s %-6s%n",
            "ID", "NAME", "JAVA", "MATHS", "ENGLISH",
            "TOTAL", "PERCENT", "GRADE", "RESULT"
        );

        System.out.println(
            "-----------------------------------------------------------------------------------------------"
        );
    }
}
