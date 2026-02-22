# overview script
the project uses the MVC arch, and the model defines the core logic and data structures required for each method. the interface CalendarApi specified all the operation that can be called by the controller, and the InMemoryCalendar class implements this interface and has the concrete implementations

so, the calendar support 3 types of operation on 2 type of instance, which mean we can perform either creation, modification or querying on either single event or event in series. the model encourage separation between parsing and the core logic, most methods expect a data carrier object to be built properly in the controller part, and the model would use those fields to navigate the logic.

for event creation, we adopted the builder pattern to ensure immutability, and we have a hashmap to map eventId to the each event instance. for event series creation, we handled 2 type only, either repeat weekday for N occurrence or repeat weekday until an endDate, we use priority queue and while loop to compute the actual date, and we track event in series by mapping a seriesId to a list of events. 

for editing, we first check if the event is in series using the hashmap, so we can determine if we are editing single event or not, and then we check if we are changing start time of an event or not, if yes, we have to split the original series to a new series, if we are not changing for the entire series. 

for querying, we just go through each event in the hashmap ,and check if we can find a match. finally, we also have a csvExporter class that directly write on the file based on google calendar format

# Normalizer: 
## constructor:
initialize the local settings obj
## normalizeTimes: 
give allDay event a time, give event with optional end an concrete endTime
and return event with proper start and end time
## resolveStatus: 
set as status: public 

# RecurrenceExpander: 
## expand: 
use heap to build the n occurrence if rule.count present ,otherwise we build output dates using hashset : wanted incrementally , until we reach the endDate
## map: 
turn short hand to enum value in weekdays and return a hashset (contain enum value of DayOfWeek only)

# UniquenessIndex (ensure uniqueness of event by checking combo key in hashset)
has a hashset  : triples (store unique tuples)  (O(1) lookup)
## key: 
build key from event PPK 
## addOrThrow: 
add to triples if key not exist in triples, otherwise throw key eixst error  
## replaceOrThrow
if newKey does not dupl0icate existing event, we remove old event key in triples, and insert the new key into that (this happened when any event properties were changed!)
(used in patchApplier )

# SeriesIndex 
## createSeries: 
register list of eventId to an sid in a hashmap
## seriesOf
return which series does this event belongs to
## following
return eventIDs that start and after the cutoff
## all
a list of event ids all event in a series, given a series id
## detach 
detach an event from its original series
remove series if empty
## splitFollowing
split a series at the anchor event, use new sid to record the floowing list of evenetids
and return the new sid

# SelectorResolver 
the global key value store for evnet is passed in (eventid : event) to the constructor
use case: 
finding the anchor event in a series
return an event object
## resolve
if endTime is provided, we walk through the values of ById and find the first match of the unique tuple.
otherwise, we build a list of candidates that matches the (subject,start) subset, and if there are more than one candidate, we return ambiguous modification operation(Searching for an event requires specifying enough of its properties to uniquely identify it. If multiple events have the same properties that were specified, editing should not be possible.)
only one candidate is allow when only start is provided! 

# PatchApplier
constructor take the event key value store and unqieu index object( hashset operation )
## apply 
get the event from the key value store( always ), so we need the ventID whenever we need to locate the event
then we have a list of variables that contain all the desired values for the new event object( combination from the patch and original event )
then we use builder class to build  the new event 

then we should update the uniqueness index object:
we get the original event key and compose the new event key,  and replace the old key with new key ,since the event is edited(original event should not exist anymore)
then we replace the new event in the key value store with the same eventId

total 2 place to update, the key value store and the unique index object (maintain uniqueness for all objects)

# InMemoryCalendar
## adjustPatchForEvent (private static method) (normalization of the patch object) 
create a new event object
copy non time field first
if startTime changed, update start time to new start , if end time dont change, preserve duration of original event, otherwise, new end wshould be newStart + duration
new eventPatch has all the thing the new event should have (and we create new event at the patchApplier)
return the new eventPatch

## Create
we get a eventDraft object that stores required param to create an event
we normalize the draft to change empty end time to default end time
we check uniqueness of to be created event
we create an event based on the param 
we insert it to the global key value store  
return the eventId
## CreateSeries: 
we take a draft object which stores the neccesayr params to create an event series, then we determine if the time for all event in the serie ,and we compute the actual dates in series with recurrence expander, using the recurrence rule, where we specifies the weekday to repeat, and either the period or N.

then for each date we create event object and check uniqueness, then we insert it to the global event store and build a list of eventids, and we use a hashmap to map sid to a list of eventids, return the sid

## UpdateBySelector
create resolver instance first, and pass in the global key value store for events
the resolve.resolve return the anchor event that matches the unique tuple 
then we find the series the anchor event belongs to,
if none, then the event is a single event, otherwise, we should use the request scope (following or entire)

we compute boolean changeStart to indicate if we are changing startTime of event, which is crucial for event in series( splitting will happen )

we use enum effective to determine scope
for single event, if it is in in series an startTime change, we need tor detach it from original series
otherwise just apply patch to this event with patchApplier

for following, if startTime changed ,we call splitFollowing with anchor event, which it create a new series with event starting from anchor and after, and return thew new sid to us, and we call .all to get back all events for this new sid 
and we apply patch to each original event

for entire series, we just apply patch to each event, they would still be in the same series
## eventsOn
convert date to date time (start ,end )
delegate to eventsOverlapping
## eventsOverlapping
go through values for al events, filter evnet that overlaps desired time range! 
e.start < to , e.end > from
## statusAt
cannot in any event range (check event store )
## allEvents
return all events in a list
## exportCsv
Validates targetFile is not null
Gets all events (sorted by start time)
Delegates to CsvExporter to write the file
Returns the absolute path of the written file
# CsvExporter
constructor take a settings object( default values )
we define the format firs , then we write the header row (required columns)
then we work on each event,  we compute value for each column ,and write the row
and we goes to a newline for new event entry

[[google calendar format]]


how does it know which command to call 
change part of the series (form .. end change subject)

how to export csv


should not use hashmap for event?  