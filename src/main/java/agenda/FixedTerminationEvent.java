package agenda;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Description : A repetitive event that terminates after a given date, or after
 * a given number of occurrences
 */
public class FixedTerminationEvent extends RepetitiveEvent {

    //The termination date
    private LocalDate terminationInclusive;

    //The number of occurences
    private long numberOfOccurences;

    /**
     * Constructs a fixed terminationInclusive event ending at a given date
     *
     * @param title                the title of this event
     * @param start                the start time of this event
     * @param duration             the duration of this event
     * @param frequency            one of :
     *                             <UL>
     *                             <LI>ChronoUnit.DAYS for daily repetitions</LI>
     *                             <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     *                             <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     *                             </UL>
     * @param terminationInclusive the date when this event ends
     */
    public FixedTerminationEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency, LocalDate terminationInclusive) {
        super(title, start, duration, frequency);
        this.terminationInclusive = terminationInclusive;
    }

    /**
     * Constructs a fixed termination event ending after a number of iterations
     *
     * @param title               the title of this event
     * @param start               the start time of this event
     * @param duration            the duration of this event
     * @param frequency           one of :
     *                            <UL>
     *                            <LI>ChronoUnit.DAYS for daily repetitions</LI>
     *                            <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     *                            <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     *                            </UL>
     * @param numberOfOccurrences the number of occurrences of this repetitive event
     */
    public FixedTerminationEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency, long numberOfOccurrences) {
        super(title, start, duration, frequency);
        this.numberOfOccurences = numberOfOccurrences;
    }

    /**
     * @return the termination date of this repetitive event
     */
    public LocalDate getTerminationDate() {
        if (Objects.isNull(terminationInclusive)) {
            LocalDateTime date = this.getStart();
            for (int i = 1; i < numberOfOccurences; i++) {
                date = date.plus(getFrequency().getDuration());
            }
            this.terminationInclusive = date.toLocalDate();
        }
        return terminationInclusive;
    }

    public long getNumberOfOccurrences() {
        if (numberOfOccurences == 0) {
            if (getFrequency().ordinal() == ChronoUnit.DAYS.ordinal()) {
                numberOfOccurences = ChronoUnit.DAYS.between(this.getStart().toLocalDate(), getTerminationDate()) + 1;
            }
            if (getFrequency().ordinal() == ChronoUnit.WEEKS.ordinal()) {
                numberOfOccurences = ChronoUnit.WEEKS.between(this.getStart().toLocalDate(), getTerminationDate()) + 1;
            }
            if (getFrequency().ordinal() == ChronoUnit.MONTHS.ordinal()) {
                numberOfOccurences = ChronoUnit.MONTHS.between(this.getStart().toLocalDate(), getTerminationDate()) + 1;
            }
        }
        return numberOfOccurences;
    }

    @Override
    public boolean isInDay(LocalDate aDay) {
        if(!(aDay.isBefore(this.getStart().toLocalDate()) || aDay.isAfter(this.getStart().plus(this.getDuration()).toLocalDate())))return true;
        LocalDateTime temp = this.getStart();
        while((temp.isBefore(aDay.atStartOfDay()) && temp.isBefore(getTerminationDate().atStartOfDay()))) {
            temp = temp.plus(getFrequency().getDuration());
            if (!(aDay.isBefore(temp.toLocalDate()) || aDay.isAfter(temp.plus(this.getDuration()).toLocalDate()))) {
                if(!getException().contains(aDay)) return true;
            }
        }
        return false;
    }
}
