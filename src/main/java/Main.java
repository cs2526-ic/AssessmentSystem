import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Student> students = FeedLoader.loadStudents();
        SwingUtilities.invokeLater(() -> ResultsWindow.show(students));
    }
}