package hoytekken.app.model.components.eventBus;

/**
 * Interface that represents an event listener
 */
public interface IEventListener {
    
    /**
     * Handles an event
     * @param event the event to handle
     */
    void handleEvent (IEvent event);
}
