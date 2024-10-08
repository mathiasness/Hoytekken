package hoytekken.app.model.components.eventBus;

import hoytekken.app.model.components.GameState;

/**
 * Event for when the game state changes
 * @param oldState the old game state
 * @param newState the new game state
 */
public record GameStateEvent(GameState oldState, GameState newState) implements IEvent {}
