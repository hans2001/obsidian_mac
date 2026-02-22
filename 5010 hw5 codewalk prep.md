how does the ical format get done ( understand that! ) 
# Commands
`create calendar --name <calName> --timezone area/location`
- Controller → HandleEvents.handleCreateCalendarEvent
- Validate pattern, parse tokens
- CalendarManager.createCalendar trims/uniqueness check → factory builds TimeZoneInMemoryCalendar

`edit calendar --name <name> --property <property> <value>`
- Controller (requires active calendar) → HandleEvents.handleEditCalendarEvent
- Regex decides name vs timezone edit
- CalendarManager renames or updates zone via calendar setters

`use calendar --name <name>`
- Controller → HandleEvents.handleUseCalendarEvent
- CalendarManager.getCalendar fetches instance; controller stores it as inUseCalendar

`copy event <event> on <dateTime> --target <calendar> to <dateTime>`
- Requires active calendar; handler parses command
- CalendarManager.copyEvent resolves source/target and delegates to EventCopier.copyEvent

`copy events on <date> --target <calendar> to <date>`
- Handler parses dates
- CalendarManager.copyEventsOn → EventCopier.copyEventsOn (convert + shift each event)

`copy events between <date> and <date> --target <calendar> to <date>`
- Handler parses range + destination start
- CalendarManager.copyEventsBetween → EventCopier.copyEventsBetween (select overlapping events, convert/offset, recreate)


create a 2min intro script ( focus on the changes! )

To Fulfil new requirements, we define interface that contains public methods that could support the commands, and created a new class call TimezoneInmemoryCalendar as that implements the  new interface! so it is basically the adapter class, and it delegate common functionalities to the adaptee class to fulfilled weak coupling!

For cross calendar operations such as copying event across each other, we have a class call calendarManager that use facade pattern, which provide the controller a single entry potin for cross calendar operations. here we have a key value store for calendars that map calendarName to calendar instance, the data structure ensures uniqueness of name and enable CRUD operation on calendar to be O(1).  the factory pattern we used to create calendar instance provide weak coupling. for event copying related op, we delegate to a calss called eventCopier for concrete implementation
