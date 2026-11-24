import java.util.List;
import assessments.AssessmentFeed;

public class AssessmentFactory {
    public static final List<String> ASSESSMENTS = List.of(
            AssessmentFeed.PROGRAMMING_TEST,
            AssessmentFeed.AI_PROJECT,
            AssessmentFeed.DESIGN_VIVA);

    private static final double MAX_MARK = 100;

    public static Assessment create(String cid, String name) throws InvalidMarkException {
        if (name.equals(AssessmentFeed.PROGRAMMING_TEST)) {
            return new IndependentProgrammingTest(MAX_MARK,
                    raw(cid, name, AssessmentFeed.PROGRAM_MARK),
                    raw(cid, name, AssessmentFeed.EXPLAINED_SUCCESSFULLY) == 1.0);
        }
        if (name.equals(AssessmentFeed.AI_PROJECT)) {
            return new AIAssistedProject(MAX_MARK,
                    raw(cid, name, AssessmentFeed.FUNCTIONALITY_MARK),
                    raw(cid, name, AssessmentFeed.VERIFICATION_MARK));
        }
        if (name.equals(AssessmentFeed.DESIGN_VIVA)) {
            return new SoftwareDesignViva(MAX_MARK,
                    raw(cid, name, AssessmentFeed.DESIGN_MARK),
                    raw(cid, name, AssessmentFeed.EXPLANATION_MARK));
        }
        throw new IllegalArgumentException("Unknown assessment: " + name);
    }

    private static double raw(String cid, String assessment, String sub) {
        return AssessmentFeed.getResult(cid, assessment, sub);
    }
}