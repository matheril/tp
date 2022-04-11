---
title: Tan Ivan - Project Portfolio Page
---

## Summary of Contributions

### Code Contributions
Code contribution: [RepoSense Link](https://nus-cs2113-ay2122s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=ivanaitzliddat&tabRepo=AY2122S2-CS2113T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

#### New Features
* [Activity](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/Activity.java)
  * This feature was implemented so that details about an activity can be stored in an object.
* [Activity Create](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityCreateCommand.java)
  * What it does: Allows users to create activities in the application.
  * Justification: This feature allows users to record the details of each activity within a particular outing and keep track of the expenditure.
* [Activity Delete](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityDeleteCommand.java)
  * What it does: Allows users to delete activities in the application.
  * Justification: This feature allows users to remove unnecessary activities that were previously created.
* [Group View](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/GroupViewCommand.java)
  * What it does: Allows users to edit previously created sessions.
  * Justification: This features allows users to see the full details of a particular group, including the names of the people in the group.
* [Group List](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/GroupListCommand.java)
  * What it does: Allows users to edit previously created sessions.
  * Justification: This features allows users to see a list of all the groups that were created.

#### Enhanced Features
* Separated dependency of parser class from command class by creating Command Parser classes for certain features.
  * These include [ActivityCreateCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/ActivityCreateCommandParser.java),
    [ActivityDeleteCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/ActivityDeleteCommandParser.java) and
    [GroupViewCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/GroupViewCommandParser.java) classes.

#### Testing
* Added JUnit tests for [Activity](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/data/ActivityTest.java),
  [ActivityCreateCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/command/ActivityCreateCommandTest.java) and
  [ActivityDeleteCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/command/ActivityDeleteCommandTest.java) classes.
* Added JUnit tests for [ActivityCreateCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/parser/commandparser/ActivityCreateCommandParserTest.java),
  [ActivityDeleteCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/parser/commandparser/ActivityDeleteCommandParserTest.java) and
  [GroupViewCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/parser/commandparser/GroupViewCommandParserTest.java) classes.

### Documentation Contributions
Documentation Contribution: [RepoSense Link](https://nus-cs2113-ay2122s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=ivanaitzliddat&tabRepo=AY2122S2-CS2113T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs&authorshipIsBinaryFileTypeChecked=false)

#### User Guide
* Added documentation for [`Creating an activity`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-an-activity-activity-create),
  [`Deleting an activity`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#deleting-an-activity-activity-delete),
  [`Viewing a group`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#viewing-a-group-group-view) and
  [`Listing all groups`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#listing-all-groups-group-list) sections of the User Guide.
* Made the first round of edits to the User Guide to ensure that feedbacks from the PE Dry Run were well acknowledged. [#391](https://github.com/AY2122S2-CS2113T-T10-1/tp/pull/391/files)

#### Developer Guide
* Added the sequence diagrams and workflow for [`Add an activity`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#add-an-activity),
  [`Remove an activity`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#remove-an-activity),
  [`View a group`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#view-a-group) and
  [`List all groups`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#list-groups) sections of the Developer Guide.
* Assisted in ensuring that diagrams were correct and followed the logic of the application.
* Made a round of edits to the Developer Guide to complete certain areas that are not yet completed. [#485](https://github.com/AY2122S2-CS2113T-T10-1/tp/pull/485/files)

### Miscellaneous Contributions
* Updated Issues to ensure that the team is on track.
* Assisted in fixing bugs after the PE dry run.
* Ensured that as much as possible, overall coding standardisation .

#### Project Management
* Summarised the overall timeline for the module to ensure that deadlines are known beforehand.
* Do meeting minutes when necessary in order to ensure that important details are not missed out.
