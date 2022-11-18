package agenda;

import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Description : A repetitive Event
 */
public class RepetitiveEvent extends Event {

    //The frenquency of the event
    private ChronoUnit frequency;

    //The exceptionList
    private Set<LocalDate> exceptionlist = new HashSet<>();


    /**
     * Constructs a repetitive event
     *
     * @param title the title of this event
     * @param start the start of this event
     * @param duration myDuration in seconds
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     */
    public RepetitiveEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency) {
        super(title, start, duration);
        this.frequency = frequency;
    }

    /**
     * Adds an exception to the occurrence of this repetitive event
     *
     * @param date the event will not occur at this date
     */
    public void addException(LocalDate date) {
        exceptionlist.add(date);
    }

    public Set<LocalDate> getException(){
        return exceptionlist;
    }

    /**
     *
     * @return the type of repetition
     */
    public ChronoUnit getFrequency() {
        return this.frequency;
    }

    @Override
    public boolean isInDay(LocalDate aDay) {
        if(!(aDay.isBefore(this.getStart().toLocalDate()) || aDay.isAfter(this.getStart().plus(this.getDuration()).toLocalDate())))return true;
        LocalDateTime temp = this.getStart();
        while((temp.isBefore(aDay.atStartOfDay()))){
            temp = temp.plus(getFrequency().getDuration());
            if (!(aDay.isBefore(temp.toLocalDate()) || aDay.isAfter(temp.plus(this.getDuration()).toLocalDate()))) {
                if(!getException().contains(aDay)) return true;
            }
        }
        return false;
    }
}
