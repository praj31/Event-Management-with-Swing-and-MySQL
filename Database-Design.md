# Database Design

## Relations / Tables

1. People
2. Events
3. OrganizedBy
4. BookedBy

### Relation People

Stores details for the login. After verification, the user is directed to respected window.

Attributes:

- ID (int) PK  
   ID of the user

- Name (varchar(50))  
   Name of the user

* designation (int)  
   Designation to help in directing the user after verification. This field can contain values in range 1-3. 1 denotes a student, 2 denotes an organizer and 3 denotes the admin.

### Relation Events

Stores details of events. An event has mutiple attributes.

Attributes:

- evid (int) PK  
   Event ID (auto generated)

- eventTitle (varchar(30))  
   Title of the event

- eventDescription (varchar(256))  
   Description of the event

- eventType (varchar(1))  
   Type of the event: online/physical. The field stores 'P' or 'O'.

- eventTypeData (varchar(256))  
   The URL in case of online event and location in case of physical event.

- eventDate (date)  
   The date of the event.

- eventTime (time)  
   The time of the event.

- numParticipants (int)  
   The number of participants for the event.

- repeating (varchar(50))  
   Whether the event is repeating or not. The data of it accordingly.

### Relation OrganizedBy

A composite relation consisting of pairs of id of a organizer and event id of the event hosted by that organizer.

Attributes:

- id (int) PK referenced from people
- evid (int) PK referenced from events

### Relation BookedBy

A composite relation consisting of pairs of id of a student and event id of the event that the student has participated in.

Attributes:

- id (int) PK referenced from people
- evid (int) PK referenced from events
