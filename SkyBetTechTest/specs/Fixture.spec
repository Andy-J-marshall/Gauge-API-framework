Fixtures endpoint
=================

    | homeTeam    | awayTeam          |
    |-------------|-------------------|
    | Arsenal     | Leeds United      |
    | Aston Villa | Manchester United |

All returned fixtures have a fixtureId
--------------------------------------
* Get all fixtures
* Response is returned with status code "200"
* Assert 3 fixtures are returned in the response and they have a fixtureId

Newly created fixture has team ID of HOME
-----------------------------------------
tags: CleanupAfterTest
* Store a new fixture for a completed match with preset data
* Response is returned with status code "200"
* Get all fixtures
* Assert the last created Fixture has team ID of HOME in the first object

Delete the last created fixture ID
----------------------------------
* Store a new fixture for a completed match with home team <homeTeam> and away team <awayTeam>
* Response is returned with status code "200"
* Find and delete the most recently created fixture
* Assert the most recently created fixture no longer exists

Multiple goals can be successfully created for a fixture
--------------------------------------------------------
tags: CleanupAfterTest
* Store a new fixture for a completed match with "5" goals
* Response is returned with status code "200"
* Get all fixtures
* Check there are "5" goals in the last created fixture ID