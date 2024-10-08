package hoytekken.app.model.components.eventBus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventBusTest {
    private EventBus eventBus;
    private IEventListener mockListener;
    private IEvent mockEvent;
    
    @BeforeEach
    public void setUp() {
        eventBus = new EventBus();
        mockListener = mock(IEventListener.class); 
        mockEvent = mock(IEvent.class);
    }

    @Test
    public void sanityTest(){
        assertTrue(true);
        assertNotNull(eventBus);
        assertNotNull(mockListener);
        assertNotNull(mockEvent);
    }

    @Test
    public void testConstructor(){
        assertEquals(0, eventBus.getListeners().size());
    }

    @Test
    public void testAddListener(){
        assertEquals(0, eventBus.getListeners().size());
        eventBus.addListener(mockListener);

        assertEquals(1, eventBus.getListeners().size());
        assertTrue(eventBus.getListeners().contains(mockListener));
    }

    @Test
    public void testRemoveListener(){
        assertEquals(0, eventBus.getListeners().size());
        eventBus.addListener(mockListener);

        assertEquals(1, eventBus.getListeners().size());
        assertTrue(eventBus.getListeners().contains(mockListener));

        eventBus.removeListener(mockListener);
        assertEquals(0, eventBus.getListeners().size());
        assertFalse(eventBus.getListeners().contains(mockListener));
    }

    @Test
    public void testEmitEvent(){
        eventBus.addListener(mockListener);
        eventBus.emitEvent(mockEvent);
        verify(mockListener, times(1)).handleEvent(mockEvent);
    }

}