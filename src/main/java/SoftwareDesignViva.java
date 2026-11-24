import assessments.AssessmentFeed;
public class SoftwareDesignViva extends Assessment {
    private final double design;
    private final double explanation;

    public SoftwareDesignViva(double maxMark, double design, double explanation)
            throws InvalidMarkException {
        super(AssessmentFeed.DESIGN_VIVA, maxMark, false);
        this.design = check(design);
        this.explanation = check(explanation);
    }

    @Override
    public double getPercentage() { return 0.5 * design + 0.5 * explanation; }

    @Override
    public double getWeight(boolean msc) { return msc ? 0.30 : 0.0; }
}