- Header columns (exact spelling/order used):
	- Subject, Start Date, Start Time, End Date, End Time, All Day Event, Description, Location, Private

- Date/time formats:
	- Start/End Date: MM/dd/yyyy (e.g., 05/12/2025)
	
	- Start/End Time: h:mm a (e.g., 10:00 AM)
	
	- All-day rows must leave Start Time and End Time empty.

- All-day detection:
	- True when start and end are on the same date AND match CalendarSettings’ all-day window (default 08:00–17:00). Otherwise False.

- Privacy:
	- Private column is "True" if Status == PRIVATE, else "False".

- Escaping rules (CSV):
	- Any value containing a comma, double-quote, or newline is wrapped in double quotes, and embedded quotes are doubled (" → "").

- Multi-day events:
	- Allowed. Start/End Date can differ. Times are present (not all-day).

- Timezone:
	- Assumed EST per assignment; no timezone column in CSV—ensure you feed EST-local timestamps.

- Ordering:
	- Not required by Google, but exporter sorts by start.

- One event = one row:
	- Each occurrence in a series is a separate row.

- Common gotchas:
	- All-day must have blank times; the date remains the same for start and end.

	- Ensure End is after Start (import may fail otherwise).

	- Subject cannot be empty.

	- Use correct AM/PM format and two-digit month/day (e.g., 01/05/2025, 9:00 AM is acceptable per h:mm a).

- File path behavior here:
	- Export writes to the provided path and returns its absolute path.