package agenda;

import java.time.LocalDate;
import java.util.*;

/**
 * Description : An agenda that stores events
 */
public class Agenda {

    //The list of event
    List<Event> eventList = new ArrayList<>();

    /**
     * Adds an event to this agenda
     *
     * @param e the event to add
     */
    public void addEvent(Event e) {
        eventList.add(e);
    }

    /**
     * Computes the events that occur on a given day
     *
     * @param day the day to test
     * @return an iterator to the events that occur on that day
     */
    public List<Event> eventsInDay(LocalDate day) {
        List<Event> list = new ArrayList<>();
        for(Event event : eventList){
            if(event.isInDay(day)) list.add(event);
        }
        return list;
    }

    /**
     * Trouver les événements de l'agenda en fonction de leur titre
     * @param title le titre à rechercher
     * @return les événements qui ont le même titre
     */
    public List<Event> findByTitle(String title) {
        List<Event> list = new ArrayList<>();
        for(Event event : eventList){
            if(event.getTitle().equals(title))list.add(event);
        }
        return list;
    }
}
