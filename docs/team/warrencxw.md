# Warren Chong - Project Portfolio Page

## Project - SplitLah
<hr>
SplitLah is a Command Line (CLI) program that is written in Java and meant to run on `Java 11`. SplitLah is an 
application for keeping track of expenses during group outing sessions, specifically for those 
who are budget conscious. It then proceeds to split the expenses according to the group members' individual costs for 
the activities they participated in, for a particular session.

## Summary of Contributions
<hr>

### Code Contributions
Code Contribution: [RepoSense Link](https://nus-cs2113-ay2122s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&since=2022-02-18&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=warrencxw&tabRepo=AY2122S2-CS2113T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

#### New Features
* [Parsing API](https://github.com/AY2122S2-CS2113T-T10-1/tp/tree/master/src/main/java/seedu/splitlah/parser)
   * What it does: Provides a full set of methods to parse different arguments of a user input
     and perform preliminary validity checking of the user input for extraneous arguments and invalid inputs.
   * Justification: Allows other developers to develop functional code without having to worry about input validity.
     Centralisation of all parsing logic also helps to make debugging of any incorrect parsing logic easier.
* [Session](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/Session.java)
   * Represents a group outing that involves a list of participants and contains one or more activities.
     Also provides relevant methods for handling and processing its member attributes.
* [Session Summary](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionSummaryCommand.java)
   * What it does: Creates a transaction summary that helps users calculate how much each person in a session
     must pay and to whom they should pay for all debts to be resolved.
   * Justification: One of the key features of `SplitLah`. Serves to simplify the payment process when users split
     bills during group outings as it minimises the number of transactions required to be made by consolidating all
     debts to be paid.
   * Highlights: This was slightly challenging as there were floating point errors to take into account for when
     splitting costs between multiple people. Additionally, a new class [`PersonCostPair`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/util/PersonCostPair.java)
     had to be made to assist the sorting of `Person` objects by their overall cost in the session.
* [Help command](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/HelpCommand.java)
   * What it does: Provides users a reference of all available commands while they are using `SplitLah`.
   * Justification: Gives users a quick way to find out the syntax of all available commands while using `SplitLah` 
     without having to constantly refer back to the user guide.
* [PersonCostPair](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/util/PersonCostPair.java)
   * What it does: It is a utility class that supports `SessionSummaryCommand` in minimising transactions required to be
     made by encapsulates a `Person` object and their overall cost in the session as a debt owed by or owed to an individual.
     It also keeps track of whether a debt has been fully paid or not, assisting in the algorithm used in `SessionSummaryCommand`.
   * Justification: Enables `SessionSummaryCommand` to sort all debts in the session without losing the ability to
     refer to the `Person` object that bears the debt.

#### Enhancements to existing features
 * [CommandParser interface](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/commandparser/CommandParser.java)
   * What it does: Isolates `Parser` component related dependencies from `Command` classes by shifting all input parsing
     functionality to the `CommandParser` itself, allowing the `Command` class to focus on carrying out the command
     as specified by the user.
   * Justification: To encourage other developers to separate the dependency of the `Parser` component from the `Command`
     subclasses that they are working on. This improves the overall quality of the code of the project as testing can be
     much more modular.
 * [ParserUtils](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/ParserUtils.java)
   * What it does: Handles all argument parsing delegated to the `Parser` component, allowing `Parser` class to
     have the sole purpose of returning an `XYZCommand` object corresponding to the given user input.
   * Justification: To improve the code quality by reducing unnecessary dependencies and following the separation of
     concerns principle.
 * [ParserErrors](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/ParserErrors.java)
   * What it does: Handles the generation of `Parser` component related error messages.
 * Integration of [`TableFormatter`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/ui/TableFormatter.java) class to 
   [`Session`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/data/Session.java) class
   * What it does: Improve the readability of details of a `Session` object when printed.
 * Add support and future-proofing to parsing GST and Service charge percentages in [`ParserUtils`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/ParserUtils.java) class
   * Allow the use of real numbers up to two decimal points to support potential changes and variability in charges.
   * Previously, only integers were allowed which may not support changes in percentages less than 1%.

#### Testing
 * Added comprehensive JUnit tests for
   [`Parser`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/parser/ParserTest.java),
   [`ParserUtils`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/parser/ParserUtilsTest.java) and 
   [`Session`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/data/SessionTest.java) classes
   * Ensure that key components in `SplitLah` necessary for the operation of other components developed by other
     developers in the team are bug free and work as they are intended to.
   * Ensure that each of these classes have at least 90% class, method and line coverage.
 * Added JUnit tests for
   [`SessionSummaryCommandParser`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/parser/commandparser/SessionSummaryCommandParserTest.java) and 
   [`HelpCommandParser`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/test/java/seedu/splitlah/parser/commandparser/HelpCommandParserTest.java) classes
   * Performed integration testing of the `Parser` component to ensure that objects of the correct subclass of
     `Command` class is produced.
 * Added [text-based testing](https://github.com/AY2122S2-CS2113T-T10-1/tp/tree/master/text-ui-test)
   from the command line for [`SessionSummaryCommand`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionSummaryCommand.java) class
   * Since JUnit tests cannot test for output printed onto standard output, text-based testing has to be done for `SessionSummaryCommand`.
   * Ensures that calculation logic works as intended.

### Documentation Contributions
Documentation Contribution: [RepoSense Link](https://nus-cs2113-ay2122s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&since=2022-02-18&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=warrencxw&tabRepo=AY2122S2-CS2113T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs&authorshipIsBinaryFileTypeChecked=false)

#### User Guide
 * Added documentation for 
   [`Settling all transactions for a session`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#settling-all-transactions-for-a-session-session-summary) and
   [`Listing all available commands`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#listing-all-available-commands-help)
 * Added questions under [Frequently Asked Questions (FAQ)](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#faq) section
 * Maintained the [`Command Summary`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#command-summary) section of the User Guide

#### Developer Guide
 * Added description for [`Profile`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#profile-component) component
 * Added section for [`Parser`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#parser-component) component and 
   [`Command`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#command-component) component
   * Created class diagrams for the components
   * Added description and general workflow for the components
 * Added section under [`Implementation`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#implementation) for 
   [`Parsing of Commands`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#parsing-of-commands) and 
   [`Settle a session`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#settle-a-session)
   * Created sequence diagrams for both sections
   * Added explanation for the sequence diagrams
 * Added various sections under [Instructions for Manual Testing](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#appendix-instructions-for-manual-testing)
   * [Launch and Shutdown](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#launch-and-shutdown)
   * [Session:](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#session-testing) 
     [`Settling a Session`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#settling-a-session),
     [`Viewing a Session`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#viewing-a-session) and
     [`Listing all Sessions`](https://ay2122s2-cs2113t-t10-1.github.io/tp/DeveloperGuide.html#listing-all-sessions)

### Miscellaneous Contributions
 * Spearheaded replacement of `XYZCommand#prepare` method with a [`XYZCommandParser`](https://github.com/AY2122S2-CS2113T-T10-1/tp/tree/master/src/main/java/seedu/splitlah/parser/commandparser) class
   * Previously, `XYZCommand#prepare` (e.g. [`SessionCreateCommand#prepare`](https://github.com/AY2122S2-CS2113T-T10-1/tp/commit/5b05ce84aa13841db2383fad527184aca772fb33))
     was used instead to process the user input and create a `XYZCommand` object.
   * Encouraged other developers to follow separation of concerns principle to improve code quality by replacing
     `XYZCommand#prepare` with `XYZCommandParser` class.
 * Reviewed and reported [bugs found](https://github.com/warrencxw/ped/issues) in another group's project ([_Spendvalope_](https://ay2122s2-cs2113-f12-1.github.io/tp/))
   as part of the Practical Exam Dry-Run
 * Headed the fixing of bugs found by other teams during the PE dry run
    * Set up a collaborative document for the sorting of bugs, allocation of duties and tracking of progress
 * Assisted in the drafting of script and slides for demo video v1.0 and v2.1
 * Looked through and [reviewed](https://nus-cs2113-ay2122s2.github.io/dashboards/contents/tp-comments.html#1-chong-xu-wei-warrencxw-145-comments)
   many of my team members' pull requests in detail
   * [PR #213](https://github.com/AY2122S2-CS2113T-T10-1/tp/pull/213) - Clean up User Guide
   * [PR #320](https://github.com/AY2122S2-CS2113T-T10-1/tp/pull/320) - Add TableFormatter class
   * [PR #391](https://github.com/AY2122S2-CS2113T-T10-1/tp/pull/391) - Update user guide

#### Project Management
* Worked on pre-planning and ideation of project
  * Drafted sketches of class diagrams and visualisation of goals
