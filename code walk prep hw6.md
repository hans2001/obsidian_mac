reuse code from CLI part by delegating to CalendarFormService (call gneric service with dependency injections)
example:
applyCreateEvent (EventCreationRequest request,TimeZoneInMemoryCalendarInterface calendar)
applyEditEvent
EventEditRequest , TimeZoneInMemoryCalendarInterface


what does refreshEvents do? 
refreshEvents (src/main/java/calendar/controller/CalendarGuiController.java (line 174)) pulls the active calendar from the model, asks for every event on the currently selected date, converts them into **GuiEventSummary** objects, and then tells the view to show that collection. It’s called whenever the controller’s date, active calendar, or event set changes so the GUI list stays in sync with the underlying model.

Intro:
**CalendarGuiController** wires the Swing view to the calendar model by keeping a map of CalendarGuiCommand instances keyed by action names such as “prev-month,” “next-month,” and “create-calendar.”

When the view notifies the controller via the CalendarGuiFeatures interface (e.g., a navigation button, day selection, or calendar dropdown change), the controller either hands that action to executeCommand, which builds a **CalendarGuiCommandContext** and runs the appropriate command, or it handles the work directly for event creation/editing and date selection. 

The controller also tracks the currently displayed **GuiCalendar**, the selected LocalDate, and a knownCalendars set so it knows which calendar names the UI already shows; these data structures keep the month view, event list, and selector synchronized with the CalendarManager’s map of actual calendars and prevent duplicate dropdown entries. 

Thus the flow is: view triggers CalendarGuiFeatures, controller either delegates to a command or mutates state (selected date, active calendar), and the view is refreshed via refreshEvents() after any state change, keeping the GUI, controller, and model aligned.
