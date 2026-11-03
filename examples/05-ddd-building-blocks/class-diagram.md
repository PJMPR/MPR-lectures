# Diagram klas

```mermaid
classDiagram
    direction LR

    namespace DomainModel {
        class ProgramEvent
        class ProgramEventId
        class EventTitle
        class RoomId
        class TimeSlot
        class Schedule
        class RoomRequirements
        class Room
    }

    namespace DomainService {
        class RoomSuitabilityPolicy
    }

    namespace DomainEvent {
        class ProgramEventScheduled
    }

    namespace Application {
        class ScheduleProgramEventHandler
    }

    namespace Ports {
        class ProgramEventRepository
        class DomainEventPublisher
    }

    class ProgramEvent {
        <<Entity>>
        -ProgramEventId id
        -EventTitle title
        -RoomRequirements requirements
        -ProgramEventStatus status
        -Schedule schedule
        +confirmSpeaker()
        +scheduleIn(RoomId, TimeSlot)
        +reschedule(RoomId, TimeSlot)
        +publish()
        +withdraw()
    }

    class ProgramEventId { <<Value Object>> }
    class EventTitle { <<Value Object>> }
    class RoomId { <<Value Object>> }
    class TimeSlot {
        <<Value Object>>
        +overlaps(TimeSlot) boolean
    }
    class Schedule { <<Value Object>> }
    class RoomRequirements { <<Value Object>> }
    class Room { <<Entity snapshot>> }
    class RoomSuitabilityPolicy {
        <<Domain Service>>
        +isSuitable(RoomRequirements, Room) boolean
    }
    class ProgramEventRepository {
        <<Repository>>
        +findById(ProgramEventId) Optional~ProgramEvent~
        +save(ProgramEvent)
    }
    class ScheduleProgramEventHandler {
        <<Application Service>>
        +handle(ScheduleProgramEvent)
    }
    class ProgramEventScheduled { <<Domain Event>> }
    class DomainEventPublisher { <<Port>> }

    ProgramEvent *-- ProgramEventId
    ProgramEvent *-- EventTitle
    ProgramEvent *-- RoomRequirements
    ProgramEvent *-- Schedule
    Schedule *-- RoomId
    Schedule *-- TimeSlot
    Room *-- RoomId
    RoomSuitabilityPolicy ..> RoomRequirements
    RoomSuitabilityPolicy ..> Room
    ScheduleProgramEventHandler --> ProgramEventRepository
    ScheduleProgramEventHandler --> DomainEventPublisher
    ScheduleProgramEventHandler ..> ProgramEvent
    ScheduleProgramEventHandler ..> ProgramEventScheduled
```

Handler aplikacyjny zależy od portów, a decyzje pozostają w modelu. `RoomSuitabilityPolicy` jest usługą domenową, ponieważ decyzja o dopasowaniu nie należy naturalnie wyłącznie do punktu programu ani wyłącznie do sali.
