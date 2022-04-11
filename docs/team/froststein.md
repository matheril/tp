# Roy Tang - Project Portfolio Page

## Project - SplitLah
<hr>
SplitLah is a Command Line (CLI) program that is written in Java and meant to run on `Java 11` . SplitLah is an 
application for keeping track of expenses during group outing sessions, specifically for those 
who are budget conscious. It then proceeds to split the expenses according to the group members' individual costs for 
the activities they participated in, for a particular session.


## Summary of Contributions
<hr>

### Code Contributions
Code Contribution: [RepoSense Link](https://nus-cs2113-ay2122s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=froststein&tabRepo=AY2122S2-CS2113T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)
#### New Features
* [Session Create](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionCreateCommand.java)
  * What it does: Allows users to create sessions in the application.
  * Justification: This feature allows users to record the details of their outing and keep track of the expenditure of each activity.
* [Session Delete](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionDeleteCommand.java)
  * What it does: Allows users to delete sessions in the application.
  * Justification: This feature allows users to remove unnecessary sessions that were previously created.
* [Session Edit](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionEditCommand.java) 
  * What it does: Allows users to edit previously created sessions.
  * Justification: This features allows users to amend a mistake previously made when creating a session.
  * Highlights: This was challenging as the edits made had to be crossed check with existing sessions within the application.
                In addition, the supplied person list for the edit had to contain the existing persons in the session.
* [Session List](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionListCommand.java)
  * What it does: Allows users to view all previously created sessions.
  * Justification: This features allows users to see a list of all the outings that were recorded.
* [Manager](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/Manager.java)
  * Contains an instance of `Profile`, `Storage` and `TextUI` to support commands within SplitLah.
* [Profile](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/Profile.java)
  * The `Profile` class serves as a container and holds a list of all `Session` and `Group` objects created by the user.
  * It also keeps track of and issues new _unique identifiers_ for the creation of `Session`, `Activity` and `Group` objects.
* [Storage](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/storage/Storage.java)
  * What it does: Allows storing of data that was created during the runtime of the application.
  * Justification: This feature was implemented so that data entered by the user during the lifetime of the application would be stored.
  * Highlights: As our team found it troublesome to catch all the corner cases if we were to create a csv file. 
                We used the `Serializable` class in `java` to help us simplify file storage.

#### Enhancements to existing features:
* Separated dependency of parser class from command class by creating Command Parser classes for certain features.
  * These include [SessionCreateCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/SessionCreateCommandParser.java),
    [SessionDeleteCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/SessionDeleteCommandParser.java), 
    [SessionEditCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/SessionEditCommandParser.java) and
    [SessionListCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/SessionListCommandParser.java) classes.
* [PersonList](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/PersonList.java)
  * Implemented a wrapper class for storing a list of Person objects.
* Integration of [`TableFormatter`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/ui/TableFormatter.java) class to
  [`Profile`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/Profile.java) class
  * Improves the readability when printing list of sessions.
* Added custom 'Back' buttons for User Guide and Developer guide to aid with navigation and readability.


#### Testing
* Added command `Junit` test for 
  [SessionCreateCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/command/SessionCreateCommandTest.java), 
[SessionDeleteCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/command/SessionDeleteCommandTest.java) and 
[SessionEditCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/command/SessionEditCommandTest.java).
  * Ensure each Junit Test achieved 100% class, method and line coverage.
* Added command parser `Junit` test for
  [SessionCreateCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/parser/commandparser/SessionCreateCommandParserTest.java),
  [SessionDeleteCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/parser/commandparser/SessionDeleteCommandParserTest.java) and
  [SessionEditCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/parser/commandparser/SessionEditCommandParserTest.java).
  * Ensure each Junit Test achieved 100% class, method and line coverage.
* Added data `Junit` test for 
  [Profile](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/data/Profile.java)
  * Ensure each Junit Test achieved at least 90% class, method and line coverage.
### Documentation Contributions
Documentation Contribution: [RepoSense Link](https://nus-cs2113-ay2122s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=froststein&tabRepo=AY2122S2-CS2113T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs&authorshipIsBinaryFileTypeChecked=false)

#### User Guide
* Added documentation for [`Creating a session`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create),
  [`Deleting a session`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#deleting-a-session-session-delete) and
  [`Editing a session`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#editing-a-session-session-edit) sections of the User Guide.
* Managed all command screenshots for the user guide.

#### Developer Guide
* Added the introduction and a how to use section.
* Added the application's architecture diagram and component interaction diagram to show how each internal component of the application works together.
* Explained how the `Storage` Component works in the application [#403](https://github.com/AY2122S2-CS2113T-T10-1/tp/pull/403/files).
* Added the sequence diagrams and workflow for [`Add a session`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#add-a-session),
  [`Remove a session`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#remove-a-session) and
  [`Edit a session`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#edit-a-session) sections of the Developer Guide.
* Added user stories section.
* Added Manual Testing Section
  * Session: `Creating a session`, `Deleting a Session` and `Editing a Session`.
  * Group: `Creating a group`, `Deleting a group`, `Editing a group` and `Viewing a group`.
  * Storage

#### Project Management
* Setup Organization & Team Repository.
* Managed `Issue Tracker` to ensure no task is left out & all pending task are tracked.
* Managed Milestones (`v1.0`, `v2.0`, `v2.1`)
* Managed `v2.0` release.
* Listed out objectives to be met for each team meeting.
* Ensured the team's progress was on schedule.

#### Miscellaneous Contributions
* Set up a Telegram channel for sending pull request messages.
    * This is to ensure all PRs are reviewed in a timely fashion.
    * Also ensures that important PRs are reviewed by at least 2 parties before merging.
* Drafted the script and slides for demo video v1.0 and v2.1.
* Bugs found for other team during PED [Bugs found](https://github.com/froststein/ped/issues)
