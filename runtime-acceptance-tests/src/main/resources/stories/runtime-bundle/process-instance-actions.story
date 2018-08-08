Meta:

Narrative:
As a user
I want to perform operations on process instances

Scenario: cancel a process instance
Given the user is authenticated as an hruser
When the user starts a process with variables
And cancel the process
Then the process instance is cancelled

Scenario: try activate a cancelled process instance
Given any authenticated user
And any suspended process instance
When the user cancel the process
Then the process cannot be activated anymore

Scenario: show a process instance diagram
Given the user is authenticated as an hruser
When the user starts a process with variables
And open the process diagram
Then the diagram is shown

Scenario: show diagram for a process instance without graphic info
Given the user is authenticated as an hruser
When the user starts a process without graphic info
And open the process diagram
Then no diagram is shown