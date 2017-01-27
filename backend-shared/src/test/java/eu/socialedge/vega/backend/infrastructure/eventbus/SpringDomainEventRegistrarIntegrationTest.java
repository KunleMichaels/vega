package eu.socialedge.vega.backend.infrastructure.eventbus;

import eu.socialedge.vega.backend.ddd.DomainEvent;
import eu.socialedge.vega.backend.ddd.DomainEventHandler;
import eu.socialedge.vega.backend.ddd.DomainEventPublisher;
import eu.socialedge.vega.backend.ddd.DomainEventRegistrar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@SpringBootTest(properties = {"server.port=-1"})
@DirtiesContext
public class SpringDomainEventRegistrarIntegrationTest {

    @Autowired
    private DomainEventPublisher springDomainEventPublisher;

    @Autowired
    private DomainEventRegistrar springDomainEventRegistrar;

    private @Mock DomainEventHandler<DomainEvent> handler;
    private @Mock DomainEventHandler<DomainEvent> anotherHandler;

    private @Mock DomainEvent event;
    private @Mock DomainEvent anotherEvent;

    @Test
    public void shouldHandlePublishedEvent() {
        springDomainEventRegistrar.registerEventHandler(handler, DomainEvent.class);

        springDomainEventPublisher.publish(event);

        for (int i = 0; i < 1000; i++) {}

        verify(handler).handleEvent(event);
    }

    @Test
    public void shouldHandleEventEveryTimeItIsPublished() {
        springDomainEventRegistrar.registerEventHandler(handler, DomainEvent.class);

        springDomainEventPublisher.publish(event);
        springDomainEventPublisher.publish(event);

        verify(handler, times(2)).handleEvent(event);
    }

    @Test
    public void shouldHandleAllPublishedEvents() {
        springDomainEventRegistrar.registerEventHandler(handler, DomainEvent.class);

        springDomainEventPublisher.publish(event);
        springDomainEventPublisher.publish(anotherEvent);

        verify(handler).handleEvent(event);
        verify(handler).handleEvent(anotherEvent);
    }

    @Test
    public void shouldHandleAllPublishedEventsByAllEligibleHandlers() {
        springDomainEventRegistrar.registerEventHandler(handler, DomainEvent.class);
        springDomainEventRegistrar.registerEventHandler(anotherHandler, DomainEvent.class);

        springDomainEventPublisher.publish(event);
        springDomainEventPublisher.publish(anotherEvent);

        verify(handler).handleEvent(event);
        verify(handler).handleEvent(anotherEvent);
        verify(anotherHandler).handleEvent(event);
        verify(anotherHandler).handleEvent(anotherEvent);
    }
}