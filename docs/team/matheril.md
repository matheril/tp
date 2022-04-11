---
title: Saurav Venu Matheril - Project Portfolio Page
---
### Summary of Contributions
### Code Contributions
Code Contribution: [RepoSense Link](https://nus-cs2113-ay2122s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=matheril&tabRepo=AY2122S2-CS2113T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

#### New Features
* [Activity Cost](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/ActivityCost.java)
    * What it does: Encapsulates the cost paid and owed for a specific activity by a person.
    * Justification: Allows costs to be tracked per person across multiple activities.
* [Activity Edit](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityEditCommand.java)
    * What it does: Edits an activity. Users can specify any number of delimiters while using this command to indicate the details they wish to edit.
      Details not specified are left unchanged from the original activity. If the user input does not change the activity
      a message is printed indicating that nothing is changed.
    * Justification: Saves users the effort of having to delete and recreate an activity with edited details.
      Editing an activity preserves the unique activity identifier whereas deleting and recreating the activity does not. This makes the
      overall experience of editing activites more user friendly and intuitive.
      Informing the user when no changes are made helps prevent user error, as editing an activity without changing anything is 
      very likely to be user error.
* [Session View](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionViewCommand.java)
    * What it does: Views a session. Displays session details and a list of activities involved.
    * Justification: Users need a way to get a quick bird's-eye view of a session and the activities involved without 
      overloading the user with detailed information on every activity.
* [Text UI](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/ui/TextUI.java)
    * What it does: Acts as a user interface and has methods that print and read from the user interface.
    * Justification: Abstracts the interface away from the different components in SplitLah, allowing developers to code
      each component independently of the interface. Changes can be made to the interface without affecting other components.
* [Person](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/Person.java)
    * What it does: Represents a person. It encapsulates its `Name` and a list of `ActivityCost`s associated with it.
    * Justification: People need to be tracked individually and separately from activities to keep track of their costs owed
      and paid. 
* [Name](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/Name.java)
    * What it does: Represents a name. It sanitises the name used to instantiate it by stripping whitespace from it, and
      checks if the name is valid (contains only alphabetical characters).
    * Justification: Can be used by other developers to validate names in a manner that is standardized across all components
      that also make use of the `Name` class.
     
#### Enhanced Features
* Separated dependency of parser class from command class by creating Command Parser classes for certain features.
    * These include the [ActivityEditCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/ActivityEditCommandParser.java),
      [SessionViewCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/SessionViewCommandParser.java) classes.
* [TableFormatter](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/ui/TableFormatter.java)
    * A `TableFormatter` object creates a table that can be modified with new data at any time. When printed, it
      produces a neatly formatted and decorated table that automatically aligns its borders to the data stored within.
      Other classes can create `TableFormatter` objects, edit them and pass them to `TextUI` to be printed to the user 
      interface.
    * This allows all developers to print neatly to the interface in a standardized format.
    * Makes use of the helper class [TableFormatterRow](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/ui/TableFormatterRow.java) that represents
      a single row of the `TableFormatter` object.
* [Message](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/ui/Message.java)
    * Set up this class so other developers can store their hardcoded strings in it, such as error and logging messages.
* [InvalidDataException](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/exceptions/InvalidDataException.java)
    * Set up this class to represent an exception thrown when data-related errors occur.
* [InvalidFormatException](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/exceptions/InvalidFormatException.java)
    * Set up this class to represent an exception thrown when format-related errors occur.

#### Testing
* Added JUnit tests for [ActivityEditCommand](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityEditCommandTest.java),
  [ActivityEditCommandParser](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/ActivityEditCommandParserTest.java),
  [Name](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/NameTest.java),
  [Person](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/PersonTest.java),
  and [ActivityCost](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/ActivityCostTest.java).
* As `TextUI` mostly consists of methods that print to the interface instead of returning a value, we decided not to add JUnit tests for this 
  class.
 
### Documentation Contributions
Documentation contribution: [RepoSense Link](https://nus-cs2113-ay2122s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=matheril&tabRepo=AY2122S2-CS2113T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs&authorshipIsBinaryFileTypeChecked=false)

#### User Guide
* Added documentation for [`Editing an activity`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/UserGuide.md#editing-an-activity-activity-edit)
  and [`Viewing a session`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/UserGuide.md#viewing-a-session--session-view) 
* Wrote sections aimed at introducing users to the program, such as the introduction (SplitLah), the target audience
  (Who SplitLah is for) and the basic workflow of the program ([How SplitLah works](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/UserGuide.md#how-splitlah-works)).
* Standardised language, grammar and tense across the guide.
 
#### Developer Guide
* Added documentation and sequence diagrams for [`TextUI`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/DeveloperGuide.md#textui-component),
  [`View a session`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/DeveloperGuide.md#view-a-session)
  and [`ActivityEditCommand`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/DeveloperGuide.md#edit-an-activity).
* Fixed typos in the [SessionSummaryCommand sequence diagram](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/images/developerguide/SessionSummaryCommand.drawio.png) and
  the [GroupEditCommand sequence diagram](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/images/developerguide/GroupEditCommand.drawio.png).

### Miscellaneous Contributions
* Recorded and narrated a demo video with slides and annotations including footage of the program in action for use
  in the product demo presentation.
