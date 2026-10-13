
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final Path FILE_PATH =
        Paths.get("data", "students.txt");

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();

        try {
            Files.createDirectories(FILE_PATH.getParent());

            if (!Files.exists(FILE_PATH)) {
                return students;
            }

            try (BufferedReader reader = Files.newBufferedReader(
                    FILE_PATH, StandardCharsets.UTF_8)) {
                String line;

                while ((line = reader.readLine()) != null) {
                    try {
                        String[] parts = line.split("\t", -1);

                        if (parts.length != 5) {
                            System.err.println(
                                "Skipping invalid student record.");
                            continue;
                        }

                        Student student = new Student(
                            parts[0],
                            parts[1],
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4])
                        );

                        students.add(student);
                    } catch (NumberFormatException e) {
                        System.err.println(
                            "Skipping record with invalid marks.");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(
                "Could not load student records: " + e.getMessage());
        }

        return students;
    }

    public static boolean saveStudents(List<Student> students) {
        try {
            Files.createDirectories(FILE_PATH.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(
                    FILE_PATH,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {

                for (Student student : students) {
                    writer.write(
                        student.getId() + "\t"
                        + student.getName() + "\t"
                        + student.getJavaMarks() + "\t"
                        + student.getMathsMarks() + "\t"
                        + student.getEnglishMarks()
                    );
                    writer.newLine();
                }
            }

            return true;
        } catch (IOException e) {
            System.err.println(
                "Could not save student records: " + e.getMessage());
            return false;
        }
    }
}
