package hoytekken.app.model.components.eventBus;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class that represents an event bus
 */
public class EventBus {
    // List of listeners, CopyOnWriteArrayList is used to avoid ConcurrentModificationException
    private CopyOnWriteArrayList<IEventListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * Method to add a listener to the event bus
     * 
     * @param listener the listener to be added
     */
    public void addListener(IEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Method to remove a listener from the event bus
     * 
     * @param listener the listener to be removed
     */
    public void removeListener(IEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Method to emit an event to all listeners
     * 
     * @param event the event to be emitted
     */
    public void emitEvent(IEvent event) {
        for (IEventListener listener : listeners) {
            listener.handleEvent(event);
        }
    }

    /**
     * Method to get the list of listeners
     * 
     * @return the list of listeners
     */
    CopyOnWriteArrayList<IEventListener> getListeners() {
        return listeners;
    }

}
