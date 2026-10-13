
public class Student {
    private String id;
    private String name;
    private double javaMarks;
    private double mathsMarks;
    private double englishMarks;

    public Student(String id, String name,
                   double javaMarks,
                   double mathsMarks,
                   double englishMarks) {
        this.id = id;
        this.name = name;
        this.javaMarks = javaMarks;
        this.mathsMarks = mathsMarks;
        this.englishMarks = englishMarks;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getJavaMarks() {
        return javaMarks;
    }

    public double getMathsMarks() {
        return mathsMarks;
    }

    public double getEnglishMarks() {
        return englishMarks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double javaMarks,
                         double mathsMarks,
                         double englishMarks) {
        this.javaMarks = javaMarks;
        this.mathsMarks = mathsMarks;
        this.englishMarks = englishMarks;
    }

    public double getTotal() {
        return javaMarks + mathsMarks + englishMarks;
    }

    public double getPercentage() {
        return getTotal() / 3.0;
    }

    public String getGrade() {
        if (!isPassed()) {
            return "F";
        }

        double percentage = getPercentage();

        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "E";
    }

    public boolean isPassed() {
        return javaMarks >= 35
            && mathsMarks >= 35
            && englishMarks >= 35;
    }

    public void display() {
        System.out.printf(
            "%-12s %-20s %8.2f %8.2f %8.2f %9.2f %10.2f %-6s %-6s%n",
            id, name, javaMarks, mathsMarks, englishMarks,
            getTotal(), getPercentage(), getGrade(),
            isPassed() ? "PASS" : "FAIL"
        );
    }
}
