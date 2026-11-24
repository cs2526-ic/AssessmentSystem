import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
 * One row per student: Name | CID | Type | one column per assessment | Module mark.
 * Each cell is a JPanel + JLabel so it can be recoloured later (panel.setBackground).
 */
public class ResultsWindow {

    public static void show(List<Student> students) {
        List<String> titles = AssessmentFactory.ASSESSMENTS;

        ArrayList<String> header = new ArrayList<>(List.of("Name", "CID", "Type"));
        header.addAll(titles);
        header.add("Module mark");

        JPanel grid = new JPanel(new GridLayout(0, header.size(), 2, 2));   // 0 rows = as many as needed
        for (String h : header) grid.add(cell("<b>" + h + "</b>"));

        for (Student s : students) {
            grid.add(cell(s.getName()));
            grid.add(cell(s.getCid()));
            grid.add(cell(s.getType()));
            for (String t : titles) {
                Assessment a = s.getResult(t);
                grid.add(cell(a == null ? "N/A" : twoDp(a.getPercentage()) + "%"));
            }
            grid.add(cell("<b>" + twoDp(s.moduleMark()) + "%</b>"
                    + (s.isComplete() ? "" : "<br>(incomplete)")));
        }

        JFrame frame = new JFrame("Module results - Exam Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(grid));
        frame.setSize(1100, 80 + 60 * students.size());
        frame.setVisible(true);
    }

    private static JPanel cell(String html) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel label = new JLabel("<html><center>" + html + "</center></html>", SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private static String twoDp(double value) {
        return String.format("%.2f", value);
    }
}