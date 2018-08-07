Fixtures endpoint
=================

All returned fixtures have a fixtureId
--------------------------------------
* Get all fixtures
* Response is returned with status code "200"
* Assert 3 fixtures are returned in the response and they have a fixtureId

Newly created fixture has team ID of HOME
-----------------------------------------
tags: CleanupRequired
* Store a new fixture
* Response is returned with status code "200"
* Get all fixtures
* Assert the last created fixture has team ID of HOME in the first object

Delete the last created fixture ID
----------------------------------
* Store a new fixture
* Response is returned with status code "200"
* Find and delete the most recently created fixture
* Assert the most recently created fixture no longer exists


TODO
add a hook to delete recently created fixtures - DONE
improve reporting by logging the message
git ignore file
More flexibility in the post
Save fixture to datastore as an object and use it in subsequest GETs