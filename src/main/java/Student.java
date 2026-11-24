import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private final String cid;
    private final boolean msc;
    private final ArrayList<Assessment> results = new ArrayList<>();

    public Student(String name, String cid, boolean msc) {
        this.name = name;
        this.cid = cid;
        this.msc = msc;
    }

    public String getName() { return name; }
    public String getCid()  { return cid; }
    public boolean isMsc()  { return msc; }
    public String getType() { return msc ? "MSc" : "UG"; }

    public List<Assessment> getResults() { return new ArrayList<>(results); }

    public Assessment getResult(String title) {
        for (Assessment a : results) if (a.getTitle().equals(title)) return a;
        return null;
    }

    public void addResult(Assessment a) throws InvalidMarkException {
        if (a.isMscOnly() && !msc)
            throw new InvalidMarkException(a.getTitle() + " is for MSc students only (" + name + ")");
        if (getResult(a.getTitle()) != null)
            throw new InvalidMarkException(name + " already has a result for " + a.getTitle());
        results.add(a);
    }

    public boolean removeResult(String title) {
        return results.removeIf(a -> a.getTitle().equals(title));
    }

    public double moduleMark() {
        double total = 0;
        for (Assessment a : results) total += a.getWeight(msc) * a.getPercentage();
        return total;
    }

    public boolean isComplete() {
        double w = 0;
        for (Assessment a : results) w += a.getWeight(msc);
        return Math.abs(w - 1.0) < 1e-9;
    }


    public double average(boolean ai) {
        double sum = 0;
        int n = 0;
        for (Assessment a : results) {
            if (a.isAiPermitted() == ai) { sum += a.getPercentage(); n++; }
        }
        return n == 0 ? -1 : sum / n;
    }


    public static final double AI_DISCREPANCY_THRESHOLD = 20.0;

    public boolean hasAiDiscrepancy() {
        if (average(true) < 0 || average(false) < 0) return false;
        return aiGap() >= AI_DISCREPANCY_THRESHOLD - 1e-9;
    }

    public double aiGap() { return average(true) - average(false); }
}