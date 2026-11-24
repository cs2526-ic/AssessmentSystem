import assessments.AssessmentFeed;
public class AIAssistedProject extends Assessment {
    private final double functionality;
    private final double verification;

    public AIAssistedProject(double maxMark, double functionality, double verification)
            throws InvalidMarkException {
        super(AssessmentFeed.AI_PROJECT, maxMark, true);
        this.functionality = check(functionality);
        this.verification = check(verification);
    }

    @Override
    public double getPercentage() { return 0.4 * functionality + 0.6 * verification; }

    @Override
    public double getWeight(boolean msc) { return msc ? 0.45 : 0.70; }
}