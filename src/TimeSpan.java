import exceptions.IllegalInputTimeSpanException;
import exceptions.IllegalScaleFactorException;
import exceptions.NegativeTimeSpanException;

public class TimeSpan {
    private int hours;
    private int minutes;

    public TimeSpan() {
        this(0, 0);
    }

    public TimeSpan(int hours, int minutes) {
        validateInput(hours, minutes);
        int totalMinutes = hours * 60 + minutes;
        if (totalMinutes < 0) {
            throw new NegativeTimeSpanException("Time span cannot be negative");
        }
        this.hours = totalMinutes / 60;
        this.minutes = totalMinutes % 60;
    }

    private void validateInput(int hours, int minutes) {
        if (hours < 0 || minutes < 0) {
            throw new IllegalInputTimeSpanException("Hours and minutes cannot be negative");
        }
        if (minutes >= 60) {
            throw new IllegalInputTimeSpanException("Minutes must be less than 60");
        }
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void add(int hours, int minutes) {
        validateInput(hours, minutes);
        int totalMinutes = this.hours * 60 + this.minutes + hours * 60 + minutes;
        if (totalMinutes < 0) {
            throw new NegativeTimeSpanException("Resulting time span cannot be negative");
        }
        this.hours = totalMinutes / 60;
        this.minutes = totalMinutes % 60;
    }

    public void add(TimeSpan span) {
        if (span == null) {
            throw new IllegalInputTimeSpanException("TimeSpan cannot be null");
        }
        add(span.hours, span.minutes);
    }

    public void subtract(TimeSpan span) {
        if (span == null) {
            throw new IllegalInputTimeSpanException("TimeSpan cannot be null");
        }
        int currentTotal = this.hours * 60 + this.minutes;
        int otherTotal = span.hours * 60 + span.minutes;
        int diff = currentTotal - otherTotal;
        if (diff < 0) {
            throw new NegativeTimeSpanException("Resulting time span cannot be negative");
        }
        this.hours = diff / 60;
        this.minutes = diff % 60;
    }

    public void scale(int factor) {
        if (factor <= 0) {
            throw new IllegalScaleFactorException("Scale factor must be positive");
        }
        int totalMinutes = (this.hours * 60 + this.minutes) * factor;
        if (totalMinutes < 0) {
            throw new NegativeTimeSpanException("Scaled time span cannot be negative");
        }
        this.hours = totalMinutes / 60;
        this.minutes = totalMinutes % 60;
    }

    @Override
    public String toString() {
        return hours + "h " + minutes + "m";
    }
}
