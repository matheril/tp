# Saurav Venu Matheril - Project Portfolio Page

## Project - SplitLah
<hr>
SplitLah is a Command Line (CLI) program that is written in Java and meant to run on `Java 11` . SplitLah is an 
application for keeping track of expenses during group outing sessions, specifically for those 
who are budget conscious. It then proceeds to split the expenses according to the group members' individual costs for 
the activities they participated in, for a particular session.

### Code Contributions
Code Contribution: [RepoSense Link](https://nus-cs2113-ay2122s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=matheril&tabRepo=AY2122S2-CS2113T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

#### New Features
* [Activity Cost](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/ActivityCost.java)
    * What it does: Encapsulates the cost paid and owed for a specific activity by a person.
    * Justification: Allows costs to be tracked per person across multiple activities.
* [Activity Edit](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityEditCommand.java)
    * What it does: Edits an activity. Users can specify any number of delimiters while using this command to indicate the details they wish to edit.
      Details not specified are left unchanged from the original activity. If the user input does not result in any real changes to the activity (e.g. changing
      the activity name to the same name as before), this command informs the user that nothing has been changed. 
    * Justification: Saves users the effort of having to delete and recreate an activity with incorrect or outdated details. 
      Editing an activity preserves the unique activity identifier whereas deleting and recreating the activity does not. This makes the
      overall experience of editing activites more user friendly and intuitive.
      Informing the user when no changes are made helps prevent user error, as editing an activity without changing anything is 
      very likely to be user error.
    * Highlights: This was challenging as activity details are spread out across different components. This made fetching existing details from
      the original activity (in case the user does not wish to edit them) difficult.
      `Activity` originally did not keep track of GST and service charge values after creation, so changes had to be made to it to store that information.
      As activity costs are recalculated and stored with GST and service charge immediately upon creation of an activity, this command
      has to reverse these calculations to find the original costs of the activity, in case the user wishes to modify 
      the GST/service charge values.
      Recreating the activity was also difficult, as many methods in other classes rely on `activityId` to do things such as removing
      `ActivityCost` objects from `Person` objects. Recreating the activity with the same `activityId` would cause bugs with these
      methods, and so a temporary activityId must be used when recreating the activity and changed later after the original has been removed.
      Since all the activity detail delimiters are entirely optional when editing an activity, there are several permutations of delimiters
      that the user can supply. Each permutation must be handled differently, and some must be rejected outright to prevent bugs. 
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
* Message
    * Set up this class so other developers can store their hardcoded strings in it, such as error and logging messages.
* InvalidDataException
    * Set up this class to represent an exception thrown when data-related errors occur.
* InvalidFormatException
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
* Added documentation for `ActivityEditCommand` and `SessionViewCommand`.
* Added documentation for [`Editing an activity`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/UserGuide.md#editing-an-activity-activity-edit)
  and [`Viewing a session`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/UserGuide.md#viewing-a-session--session-view) 
* Wrote sections aimed at introducing users to the program, such as the introduction (SplitLah), the target audience
  (Who SplitLah is for) and the basic workflow of the program ([How SplitLah works](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/UserGuide.md#how-splitlah-works)).
* Standardised language, grammar and tense across the guide.
 
#### Developer Guide
* Added documentation for [`TextUI`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/DeveloperGuide.md#textui-component),
  [`View a session`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/DeveloperGuide.md#view-a-session)
  and [`ActivityEditCommand`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/docs/DeveloperGuide.md#edit-an-activity).

### Miscellaneous Contributions
* Recorded and narrated a demo video with slides and annotations including footage of the program in action for use
  in the product demo presentation.
