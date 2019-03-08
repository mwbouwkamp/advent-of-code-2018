package Day23;

public class CoordRange {
    long start, end, value;

    public CoordRange(long start, long end, long value) {
        this.start = start;
        this.end = end;
        this.value = value;
    }

    boolean inRange(long value) {
        return value >= start && value <= end;
    }
}
