
public abstract class Assessment {
    private final String title;
    private final double maxMark;
    private final boolean aiPermitted;

    protected Assessment(String title, double maxMark, boolean aiPermitted) {
        this.title = title;
        this.maxMark = maxMark;
        this.aiPermitted = aiPermitted;
    }

    public String getTitle() { return title; }
    public double getMaxMark() { return maxMark; }
    public boolean isAiPermitted() { return aiPermitted; }

    public abstract double getPercentage();

    public abstract double getWeight(boolean msc);

    public boolean isMscOnly() { return getWeight(false) == 0; }

    public double getMark() { return getPercentage() / 100.0 * maxMark; }

    protected static double check(double pct) throws InvalidMarkException {
        if (pct < 0 || pct > 100) throw new InvalidMarkException("Mark out of range: " + pct);
        return pct;
    }

    @Override
    public String toString() {
        return String.format("%-32s %6.2f%%  (%s)", title, getPercentage(), aiPermitted ? "AI" : "no AI");
    }
}