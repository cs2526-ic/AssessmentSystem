import java.util.ArrayList;
import java.util.List;
import assessments.AssessmentFeed;

public class FeedLoader {

    public static List<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (String cid : AssessmentFeed.getCIDs()) {
            try {
                boolean msc = AssessmentFeed.getStudentType(cid).trim().equalsIgnoreCase("MSc");
                Student s = new Student(AssessmentFeed.getStudentName(cid), cid, msc);

                for (String name : AssessmentFactory.ASSESSMENTS) {
                    try {
                        Assessment a = AssessmentFactory.create(cid, name);
                        if (a.isMscOnly() && !msc) continue;
                        s.addResult(a);
                    } catch (IllegalArgumentException e) {
                        if (msc) System.out.println("Missing " + name + " for " + cid + ": " + e.getMessage());
                    } catch (InvalidMarkException e) {
                        System.out.println("Invalid " + name + " for " + cid + ": " + e.getMessage());
                    }
                }
                students.add(s);
            } catch (IllegalArgumentException e) {
                System.out.println("Skipped student " + cid + ": " + e.getMessage());
            }
        }
        return students;
    }
}