import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
 * One row per student: Name | CID | Type | one column per assessment | AI avg | Non-AI avg | Module mark.
 * A row is shown with a red background when the student meets the AI discrepancy condition.
 */
public class ResultsWindow {

    public static void show(List<Student> students) {
        List<String> titles = AssessmentFactory.ASSESSMENTS;

        ArrayList<String> header = new ArrayList<>(List.of("Name", "CID", "Type"));
        header.addAll(titles);
        header.add("AI-permitted avg");
        header.add("Non-AI avg");
        header.add("Module mark");

        JPanel grid = new JPanel(new GridLayout(0, header.size(), 2, 2));   // 0 rows = as many as needed
        for (String h : header) grid.add(cell("<b>" + h + "</b>", false));

        for (Student s : students) {
            boolean red = s.hasAiDiscrepancy();          // warning only: marks are unchanged

            grid.add(cell(s.getName(), red));
            grid.add(cell(s.getCid(), red));
            grid.add(cell(s.getType(), red));
            for (String t : titles) {
                Assessment a = s.getResult(t);
                grid.add(cell(a == null ? "N/A" : twoDp(a.getPercentage()) + "%", red));
            }
            grid.add(cell(avg(s.average(true)), red));
            grid.add(cell(avg(s.average(false)), red));
            grid.add(cell("<b>" + twoDp(s.moduleMark()) + "%</b>"
                    + (s.isComplete() ? "" : "<br>(incomplete)"), red));
        }

        JFrame frame = new JFrame("Module results - Exam Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(grid, BorderLayout.CENTER);            // no scroll pane: columns shrink to fit the window
        frame.add(new JLabel("  Red row = AI-permitted average at least "
                + twoDp(Student.AI_DISCREPANCY_THRESHOLD) + " points above non-AI average"), BorderLayout.SOUTH);
        frame.setSize(1300, 110 + 60 * students.size());
        frame.setVisible(true);
    }

    private static JPanel cell(String html, boolean red) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if (red) panel.setBackground(Color.RED);
        JLabel label = new JLabel("<html><center>" + html + "</center></html>", SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private static String avg(double value) {          // average() returns -1 when there are none
        return value < 0 ? "N/A" : twoDp(value) + "%";
    }

    private static String twoDp(double value) {
        return String.format("%.2f", value);
    }
}