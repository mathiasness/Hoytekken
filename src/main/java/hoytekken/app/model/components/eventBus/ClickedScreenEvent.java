package hoytekken.app.model.components.eventBus;

/**
 * Event for when the screen is clicked
 * @param x the x coordinate of the click
 * @param y the y coordinate of the click
 */
public record ClickedScreenEvent(int x, int y) implements IEvent {}
