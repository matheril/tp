# Ren Tianle - Project Portfolio Page

## Project - SplitLah
<hr>
SplitLah is a Command Line (CLI) program that is written in Java and meant to run on `Java 11` . SplitLah is an 
application for keeping track of expenses during group outing sessions, specifically for those 
who are budget conscious. It then proceeds to split the expenses according to the group members' individual costs for 
the activities they participated in, for a particular session.


## Summary of Contributions
<hr>

### Code Contributions
Code Contribution: [RepoSense Link](https://nus-cs2113-ay2122s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&since=2022-02-18&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=lelerer&tabRepo=AY2122S2-CS2113T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

#### New Features
* [Group Create](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/GroupCreateCommand.java)
    * What it does: Allows users to create groups in the application.
    * Justification: This feature allows users to record the details of a group.
* [Group Delete](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/GroupDeleteCommand.java)
    * What it does: Allows users to delete groups in the application.
    * Justification: This feature allows users to remove unnecessary groups that were previously created.
* [Group Edit](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/GroupEditCommand.java)
    * What it does: Allows users to edit previously created groups.
    * Justification: This features allows users to amend mistakes previously made when creating a group. 
    * Highlights: This was challenging as the edits made had to be crossed check with existing groups within the application.
* [Activity List](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityListCommand.java)
    * What it does: Allows users to view all previously created activities within a session.
    * Justification: This features allows users to see a list of all the existing activities that are stored in a session.
* [Activity View](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityViewCommand.java)
    * What it does: Allows users to view all previously created activities within a session.
    * Justification: This features allows users to see the full details of a particular activity.
* [Group](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/Group.java)
    * Represents a group that involves a list of participants.

#### Enhancements to existing features:
* Separated dependency of parser class from command class by creating Command Parser classes for certain features.
  * These include [GroupCreateCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/GroupCreateCommandParser.java),
    [GroupDeleteCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/GroupDeleteCommandParser.java),
    [GroupEditCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/GroupEditCommandParser.java),
    [ActivityViewCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/ActivityViewCommandParser.java) and 
    [ActivityListCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/ActivityListCommandParser.java) classes.

#### Testing
* Added command `Junit` test for
  [GroupCreateCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/command/GroupCreateCommandTest.java),
  [GroupDeleteCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/command/GroupDeleteCommandTest.java),
  [GroupEditCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/command/GroupEditCommandTest.java),
  [ActivityViewCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/command/ActivityViewCommandTest.java) and
  [ActivityListCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/command/ActivityListCommandTest.java).

### Documentation Contributions
Documentation Contribution:

#### User Guide
* Added documentation for [`Creating a group`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-group-group-create),
  [`Deleting a group`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#deleting-a-group-group-delete),
  [`Editing a group`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#editing-a-group-group-edit),
  [`Viewing an activity`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#viewing-an-activity-activity-view) and
  [`Listing an activity`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#listing-all-activities-in-a-session-activity-list) sections of the User Guide.

#### Developer Guide
* Added the explanation for [`Add a group`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#add-a-group),
  [`Remove a group`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#remove-a-group) and
  [`Edit a group`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#edit-a-gorup) sections of the Developer Guide.