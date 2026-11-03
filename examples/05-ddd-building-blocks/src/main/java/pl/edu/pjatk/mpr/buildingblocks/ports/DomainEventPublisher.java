package pl.edu.pjatk.mpr.buildingblocks.ports;

public interface DomainEventPublisher {
    void publish(Object event);
}
