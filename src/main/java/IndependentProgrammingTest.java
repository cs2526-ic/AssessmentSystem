import assessments.AssessmentFeed;
public class IndependentProgrammingTest extends Assessment {
    private final double programMark;
    private final boolean explainedOk;

    public IndependentProgrammingTest(double maxMark, double programMark, boolean explainedOk)
            throws InvalidMarkException {
        super(AssessmentFeed.PROGRAMMING_TEST, maxMark, false);
        this.programMark = check(programMark);
        this.explainedOk = explainedOk;
    }

    @Override
    public double getPercentage() {
        return explainedOk ? programMark : Math.min(programMark, 40);
    }

    @Override
    public double getWeight(boolean msc) { return msc ? 0.25 : 0.30; }
}