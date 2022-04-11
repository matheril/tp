---
title: Developer Guide
---

<p align="center"><img alt="logo" src="https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/pngLogo.png"></p>

# Developer Guide
## Contents
* [Acknowledgements](#acknowledgements)
* [Introduction](#introduction)
    * [How to use this developer guide](#how-to-use-this-developer-guide)
* [Design](#design)
    * [Architecture](#architecture)
    * [Manager Component](#manager-component)
    * [Profile Component](#profile-component)
    * [TextUI Component](#textui-component)
    * [Storage Component](#storage-component)
    * [Parser Component](#parser-component)
    * [Command Component](#command-component)
* [Implementation](#implementation)
  * [Parsing of Commands](#parsing-of-commands)
  * [Session Commands](#session-commands)
  * [Activity Commands](#activity-commands)
  * [Group Commands](#group-commands)
* [Appendix: Requirements](#appendix-requirements)
  * [Project Scope](#product-scope)
      * [Target user profile](#target-user-profile)
      * [Value proposition](#value-proposition)
  * [User stories](#user-stories)
  * [Non-functional requirements](#non-functional-requirements)
  * [Glossary](#glossary)
* [Appendix: Instructions for Manual Testing](#appendix-instructions-for-manual-testing)

## Acknowledgements
We would like to acknowledge [Address Book (Level-3)](https://github.com/se-edu/addressbook-level3) for providing much
insight as to how we could improve SplitLah and develop SplitLah while abiding by Object-Oriented Programming principles.
We also referenced their [User Guide(UG)](https://se-education.org/addressbook-level3/UserGuide.html)
and [Developer Guide(DG)](https://se-education.org/addressbook-level3/DeveloperGuide.html) to structure SplitLah's documentation.

## Introduction
This guide's purpose is to explain the internal workings of SplitLah, so that future engineers can understand the application's implementation in detail and continue working on the project with ease.

### How to use this developer guide
* SplitLah is divided into **six components**: [Manager Component](#manager-component),
[Profile Component](#profile-component), [TextUI Component](#textui-component),
[Storage Component](#storage-component), [Parser Component](#parser-component) and [Command Component](#command-component)
* Each component has its own section in this guide explaining them in detail.
* This [diagram](#interaction-between-components) shows the inner workings of how each component interacts with each other.
* SplitLah supports a total of **18 commands**. However, the `Help` and `Exit` commands are not explained in detail.
* Please refer to the [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html) to find out how to use each command.
* Please refer to the [implementation section](#implementation) to find out how each command is designed and implemented in SplitLah.
* Each command's section in this guide contains an API reference link encased in `code block` that brings you to the main source code responsible for executing the command.

## Design
### Architecture
![Application Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/ArchitectureDiagram.drawio.png)

The *Architecture Diagram* shown above illustrates the high-level design of the SplitLah application.

**Overview of components**
* [`SplitLah [Main]`](#splitlah-component)
    * On app launch: Creates an instance of a `Manager` and runs the command loop.
* [`Manager`](#manager-component)
    * On creation: Initializes the `Profile`, `TextUI` and `Storage` components.
    * On run: Loads data from `Storage`, receives user input from `TextUI` and uses `Parser` to parse user input into application.
* [`Profile`](#profile-component)
    * Handles cached data within run time of application.
    * Manages and stores the list of `session` and `group` objects for the application
    * Stores the data cached within the application into `Storage` whenever a change is made.
* [`TextUI`](#textui-component)
    * Handles the reading of user input and the printing of the application's output to the user.
* [`Storage`](#storage-component)
    * Handles storage operations of the application.
* [`Parser`](#parser-component)
    * Handles input parsing and determines which command to run.
* [`Command`](#command-component)
    * Defines how a command is executed.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### Interaction between components
![Component Interaction Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/ComponentInteraction.drawio.png)
<br>
The *Component Interaction Diagram* shows the inner workings of how each component in SplitLah interacts.
The diagram depicts a scenario where a user attempts to create a session.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### SplitLah Component
![SplitLah Component Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/SplitLahComponent.drawio.png)
<br>
The `SplitLah` component is the application's main class. Its job is to initialize an instance of `Manager` when the
application starts. After initialization, it proceeds to run a loop which prompts the user for an input.
When it receives an input from the user, it invokes the `Parser` and retrieves a command corresponding to the input for SplitLah
to run. Upon retrieving and running the `Exit` command, the application ends the loop and terminates.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### Manager Component
![Manager Component Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/ManagerComponent.drawio.png)
<br>
The `Manager` class is initialized by the `SplitLah` class (the main class) when the application starts.
It stores the `Profile`, `TextUI` and `Storage` objects. The `Profile` class helps to manage all data accesses 
throughout the lifetime of the application while the `Storage` class helps to save what the `Profile` class has captured. 
As for the `TextUI` class, it serves as an interface to read user inputs and to print application outputs.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### Profile Component
![Profile Component Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/Profile%20Component.drawio.png)
<br>
The `Profile` component is responsible for all data management and accesses within the lifetime of `SplitLah`.

It consists of the `Profile`, `Session`, `Activity`, `PersonList`, `Person`, `ActivityCost`
and `Name` classes.
* The `Profile` class serves as a container and holds a list of all `Session` and `Group` objects created by the user.<br>
  It also keeps track of and issues new _unique identifiers_ for the creation of `Session`, `Activity` and `Group` objects.
* The `Session` class stores a list of `Activity` objects that are created within a `Session` object as well as a
  `PersonList` object that represents a collection of all participants in the session.<br>
  It also holds an _optional_ `Group` object (explained in detail in the following point).
  * A `Session` object is identified by a _session unique identifier_.
* The `Group` class holds a single `PersonList` object and represents a collection of persons.<br>
  A `Group` object serves as a reusable shortcut to represent a group of participants if used 
  in the creation of a `Session` object.
  * A `Group` object is identified by a _group unique identifier_.
* The `Activity` class holds a single `PersonList` object that represents persons involved in the activity as well as
  an independent `Person` object that represents the person who paid for the activity.
  * An `Activity` object is identified by an _activity unique identifier_.
* The `Person` class represents a single individual identified by their name which is stored in a `Name` object.<br>
  Each `Person` object also stores a list of `ActivityCost` objects representing the costs that they bear
  or have paid for in each activity that they participate in.
* The `PersonList` class serves as a container for `Person` objects.

Each of the classes in the `Profile` component also provides utility methods to manage the objects that they hold.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### TextUI Component
![TextUI Component Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/TextUIComponent.drawio.png)
<br>
The `TextUI` class is initialized by the `Manager` class when the application starts.
It stores a `Scanner` and `PrintStream` object supplied upon initialization to read and write to the user interface.
It offers methods to print application output to and read user input from these objects for other classes to use.
As TextUI handles all input and output streams, these streams can be changed without affecting the rest of the program.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### Storage Component
![Storage Component Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/StorageComponent.drawio.png)
<br>
The `Storage` class is initialised by the `Manager` class when the application starts. 
Upon initialization, the `Storage` class checks if a save file already exists. 
A save file records user data related to SplitLah even after the application is closed.
* If a save file is found, the `Storage` class loads data from it into the `Profile` object managed by the `Manager` object.
* Else a new save file is created and an empty `Profile` object is created instead.

When a command updates any attributes of the `Profile` object, the changes are updated to the save file. These commands are:
* `SessionCreateCommand`, `SessionDeleteCommand`, `SessionEditCommand`
* `ActivityCreateCommand`, `ActivityDeleteCommand`, `ActivityEditCommand`
* `GroupCreateCommand`, `GroupDeleteCommand`, `GroupEditCommand`

Once the commands finishes executing, `Manager#saveProfile` is called to update the save file.<br>

For example:
  1. The user enters `session /create /n Class outing /d 31-03-2022 /pl Alice bob`
  2. SplitLah recognizes this as a `SessionCreateCommand` and creates the new session. 
  3. As this updates the list of sessions stored in the `Profile` object,
    `Manager#saveProfile` is called to update the save file with the newly created session.

All classes associated with the `Profile` component implements the `Serializable` class. This allows the `Storage` class 
to write all the data stored in the `Profile` component into the save file without having to pre-process the data.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### Parser Component
![Parser Component Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/ParserComponent.drawio.png)
<br>
The `Parser` component consists of the `Parser` class, `ParserUtils` class, `ParserErrors` class,
as well as the `CommandParser` class and its subclasses.<br>
* The `Parser` class provides methods to parse commands from the user and
  return a `Command` object representing an instruction that the user has for SplitLah.<br>
* The `ParserUtils` class provide supporting methods to parse individual arguments from the user input,
  and `ParserErrors` class provide methods to produce custom error messages for the `Parser` component.<br>
* The subclasses of `CommandParser` serve to parse all arguments of a user input to create an object of a corresponding
  subclass of the `Command` class.

The general workflow of the `Parser` component is as follows:
1. When required to parse for a command, the running `SplitLah` object passes a `String` object containing
   the user input to `Parser` class.
2. `Parser` class instantiates a new `XYZCommandParser` object corresponding to the user input 
   and passes the user input to it.
   (`XYZCommand` is a placeholder for specific subclass of the `Command` class, e.g. `SessionCreateCommand`)
3. The `XYZCommandParser` object then uses parse methods from `ParserUtils` class to extract all the
   arguments from the user input.
   * Each of these parse methods in `ParserUtils` class calls other utility methods within the class 
     to return a parsed value.
4. All relevant arguments that are parsed are used to create a new `XYZCommand `object to return to the `Parser` class.
5. The created `XYZCommand` object is then returned to the `SplitLah` object to run.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### Command Component
![Command Component Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/CommandComponent.drawio.png)
<br>
The `Command` component consists of the Command class and all the subclasses of the Command class
(also referred to as XYZCommand collectively).
* The subclasses include _data related commands_ such as `SessionJKLCommand`, `ActivityJKLCommand` and `GroupJKLCommand`,
  each representing commands that work with `Session`, `Activity` and `Group` classes respectively
  (JKL here refers to either `Create`, `Delete`, `Edit`, `List`, `View` or `Summary`).
* The subclasses also include _utility commands_ such as `HelpCommand`, `ExitCommand` and `InvalidCommand`
  (hereafter collectively referred to as `UtilityCommand`).

The general workflow of the `Command` component is as follows:
1. After a `XYZCommand` object is created by the [`Parser` Component](#parser-component),
   it is passed back to the `SplitLah` object to be run.
   * In the above process, all necessary information for the execution of the command is passed to and
     saved by the `XYZCommand` constructor.
2. Then, `XYZCommand#run` is executed by `SplitLah`. `XYZCommand#run` carries out the task
   that `XYZCommand` is designed to do.
   * In general, for _data related commands_, `SessionJKLCommand`, `ActivityJKLCommand` and `GroupJKLCommand`
     obtains the relevant `Session`, `Activity` and `Group` objects before operating upon them with relevant methods.
   * On the other hand, for _utility commands_, `UtilityCommand` works with and uses methods from the `TextUI` component
     to print messages and carry out their tasks.
   * The inner workings of each of the `XYZCommand` classes can be seen in greater detail under the [Implementation section](#implementation).
3. After `XYZCommand#run` completes, control is returned to `SplitLah` and
   the [lifecycle](#architecture) of an `XYZCommand` object ends.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

## Implementation

### Parsing of Commands
**API reference:** [`Parser.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/Parser.java),
[`ParserUtils.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/parser/ParserUtils.java)

The sequence diagram below models the interactions between various entities within the Parser component and
the Command component when any user input is provided to SplitLah.
<br>
<br>
![Reference Frame Parser Sequence Diagram](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/RefParser.drawio.png)
<br>
<br>
1. When `SplitLah` reads a user input, `SplitLah` calls the `Parser#getCommand` method and passes the
   user input as the argument.
2. `Parser` class decomposes the user input into two separate components, the _command type_
   and the _remaining arguments_.
   With the two methods `Parser#getCommandType` and `Parser#getRemainingArguments`,
   `session /create /n Class Outing /d 15-03-2022 /pl Alice Bob` is parsed separately as
   `session /create` and `/n Class Outing /d 15-03-2022 /pl Alice Bob`.
   * If the _command type_ is invalid, the method `Parser#getCommandType` returns null to `Parser` class.
     As a result, an `InvalidCommand` object is created and returned to `SplitLah`.
   * Else, `Parser` class validates the _command type_ and the _remaining arguments_ with
     `Parser#checkIfCommandIsValid`. If either the _command type_ or the _remaining arguments_ are invalid, an error
     message is returned by the method and an `InvalidCommand` object is returned to `SplitLah`.
3. `Parser` class creates the `XYZCommandParser` object for a `XYZCommand`. For example,
   for a _command type_ of `"session /create"`, a `SessionCreateCommandParser` object is instantiated.
   * If `Parser` class does not recognise the _command type_, an `InvalidCommand` object is created and returned immediately.
   
   <br><br>
   ![Reference Frame Command Parser Sequence Diagram](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/RefCommandParser.drawio.png)
   <br><br>
4. `Parser` class calls the `getCommand` method of `XYZCommandParser`.
5. `XYZCommandParser` parses all _remaining arguments_ using `parseABC` methods from the `ParserUtils` class.
   (`parseABC` is a placeholder for specific methods in `ParserUtils` class, 
   e.g. `ParserUtils#parseName` and `ParserUtils#parseSessionId`)
   * For example, `SessionCreateCommandParser` has to call the `parsePersonList`, `parseGroupId`, `parseName` and
     `parseLocalDate` methods from `ParserUtils` class.
   * If an exception is encountered, `XYZCommandParser` handles the exception accordingly, and if necessary,
     throws an exception back to `Parser` class, resulting in an `InvalidCommand` object being created and returned.
6. `ParseUtils#parseABC` then calls `ParserUtils#getArgumentFromDelimiter` to extract the relevant argument from
   the _remaining arguments_ and returns a corresponding parsed object.
   * For example, `ParserUtils#parseName` returns a `String` object representing a name.
7. After all necessary information is parsed, `XYZCommandParser` instantiates a new `XYZCommand` object with
   all the parsed information.
8. Following that, the `XYZCommand` object is returned from `XYZCommandParser` to `Parser` class,
   and finally back to `SplitLah` to run.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

## Session Commands

### Overview
* [Add a session](#add-a-session)
* [Remove a session](#remove-a-session)
* [Edit a session](#edit-a-session)
* [View a session](#view-a-session)
* [List all sessions](#list-all-sessions)
* [Settle a session](#settle-a-session)

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### Add a session
**API reference:** [`SessionCreateCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionCreateCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `session /create` command.
<br>
<br>
![Create Session Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/SessionCreateCommand.drawio.png)
<br>
<br>
The general workflow of the `session /create` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `SessionCreateCommand` object.
3. `SessionCreateCommand#run` method is then invoked to run the `session /create` command.
4. Once the command runs, `SessionCreateCommand#run` method checks if there is an existing session with the same session name.
   * If an existing session with the specified session name is found, a message indicating that another session with the same name exists is printed using `TextUI#printlnMessage`
     and control is returned to `SplitLah`.
5. The `SessionCreateCommand` class creates a new `Session` object using the session name, session date, and person list.
6. The list of `Session` objects are managed by a `Profile` object, hence `Manager#getProfile` is called to obtain the `Profile` object,
   which is used to call the `Profile#addSession` method in order to store the new `Session` object.
7. After the session is added to the `Profile` object, `Manager#saveProfile` is called to save the changes to the local storage file.
8. The `SessionCreateCommand` class then prints a message indicating that a session has been successfully created with `TextUI#printlnMessage`.

<br>
<div class="button-box">
  <a class="back-button" href="#session-commands">Back to Session Commands</a>
</div>
<hr>

### Remove a session
**API reference:** [`SessionDeleteCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionDeleteCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `session /delete` command.
<br>
<br>
![Delete Session Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/SessionDeleteCommand.drawio.png)
<br>
<br>
The general workflow of the `session /delete` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `SessionDeleteCommand` object.
3. The `SessionDeleteCommand#run` method is then invoked to run the `session /delete` command.
4. The list of sessions are stored in a `Profile` object, hence `Manager#getProfile` is called
   before the list of sessions can be retrieved.
5. Once the `Profile` object is returned, `Profile#getSession` is called to retrieve the `Session` object with the specified 
session unique identifier from the list of sessions.
   * If a `Session` object with the specified session unique identifier cannot be found, it prints the error message and returns control to `SplitLah`. 
   * Else, the `Session` object with the specified session unique identifier is returned.
6. To remove the `Session` object from the list of sessions stored in `Profile` object, the `Profile#removeSession` method is invoked.
7. After the session is removed from the `Profile` object, `Manager#saveProfile` is called to save the changes to the local storage file.
8. The `SessionDeleteCommand` class then prints a message indicating that a session has been successfully deleted.

<br>
<div class="button-box">
  <a class="back-button" href="#session-commands">Back to Session Commands</a>
</div>
<hr>

### Edit a session
**API reference:** [`SessionEditCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionEditCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `session /edit` command.
<br>
<br>
![Edit Session Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/SessionEditCommand.drawio.png)
<br>
<br>
The general workflow of the `session /edit` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `SessionEditCommand` object.
3. The `SessionEditCommand#run` method is then invoked to run the `session /edit` command.
4. The list of sessions are stored in a `Profile` object, hence `Manager#getProfile` is called
   before the list of sessions can be retrieved.
5. Once the `Profile` object is returned, `Profile#getSession` is called to retrieve the `Session` object with the specified
   session unique identifier from the list of sessions.
   * If a `Session` object with the specified session unique identifier cannot be found, it prints the error message and returns control to `SplitLah`.
   * Else, the `Session` object with the specified session unique identifier is returned.
6. The details of how a session is updated are displayed in the reference diagram below.<br><br>
   ![Reference Frame Update Session Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/RefUpdateSession.png)
   <br><br>
7. `SessionEditCommand#run` checks if there is an update for a new list of persons, new session or new session date.
8. If there is an update on the list of persons, `SessionEditCommand#getNewPersonList` is called to return a new list of persons to be stored. The method checks if the newly provided list of persons contains duplicated names.
   * If duplicated names are detected, an exception is thrown, an error message is printed and control is returned to `SplitLah`.
   * Else, it calls `PersonList#isSuperSet` to check if the newly supplied list of persons contains all existing persons in the session.
     * If `PersonList#isSuperSet` returns `false`, an exception is thrown, an error message is printed and control is returned to `SplitLah`.
     * Else, a new list of persons ready to be stored in the session is returned.
9. If there is an update on the session name, `SessionEditCommand#getNewSessionName` is called to return the new session name. The method checks if the specified session name already exists in the list of sessions.
   * If the specified session name exists within the list of sessions, an exception is thrown, an error message is printed and control is returned to `SplitLah`.
   * Else, the specified session name is returned to be used as the updated name for the session
10. If there is an update on the session date, `Session#setDateCreated` is called to set the new session date.
11. After which, the necessary setter methods are called to update the session name and the list of persons for the session that is being edited.
12. After the session is edited, `Manager#saveProfile` is called to save the changes to the local storage file.
13. The `SessionEditCommand` class then prints a message indicating that a session has been successfully edited.

<br>
<div class="button-box">
  <a class="back-button" href="#session-commands">Back to Session Commands</a>
</div>
<hr>

### View a session
**API reference:** [`SessionViewCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionViewCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `session /view` command.
<br>
<br>
![View Session Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/SessionViewCommand.drawio.png)
<br>
<br>
The general workflow of the `session /view` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `SessionViewCommand` object.
3. `SessionViewCommand#run` method is then invoked to run the `session /view` command.
4. The list of sessions are stored in a `Profile` object, hence `Manager#getProfile` is called.
5. Once the `Profile` object is returned, `Profile#getSession` is called to retrieve the `Session` object with the specified
   session unique identifier from the list of sessions.
   * If the session with the requested session unique identifier does not exist, an error message is printed out with 
     `TextUI#printlnMessage`.
   * Else, a `String` object representing the details of the requested session is retrieved using the 
     `Session#toString` method and printed out using `TextUI#printlnMessageWithDivider`.

<br>
<div class="button-box">
  <a class="back-button" href="#session-commands">Back to Session Commands</a>
</div>
<hr>

### List all sessions
**API reference:** [`SessionListCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionListCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `session /list` command.
<br>
<br>
![List Sessions Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/SessionListCommand.drawio.png)
<br>
<br>
The general workflow of the `session /list` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `SessionListCommand` object.
3. `SessionListCommand#run` method is then invoked to run the `session /list` command.
4. The list of sessions are stored in a `Profile` object, hence `Manager#getProfile` is called which returns a `Profile`
   object.
5. Once the `Profile` object is returned, `SessionListCommand` runs the `Profile#getSessionListSummaryString` method.
   * If the session list in the `Profile` object is empty, the `Profile` class returns a `String` object containing an error 
     message.
   * Else, a `String` object representing a table summarising the list of sessions in the 
     profile is returned.
6. Following that, the `String` object retrieved is printed out with `TextUI#printlnMessage`.

<br>
<div class="button-box">
  <a class="back-button" href="#session-commands">Back to Session Commands</a>
</div>
<hr>

### Settle a session
**API reference:** [`SessionSummaryCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/SessionSummaryCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `session /summary` command
<br>
<br>
![Session Summary Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/SessionSummaryCommand.drawio.png)
<br>
<br>
The general workflow of the `session /summary` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `SessionSummaryCommand` object.
3. `SessionSummaryCommand#run` method is then invoked to run the `session /summary` command.
4. The `Profile` object that stores all sessions is obtained with the `Manager#getProfile` method.
5. The `TextUI` object that handles all reading and printing operations with the user interface
   is obtained with the `Manager#getUi` method.
6. Once the `Profile` object is returned, `Profile#getSession` is called to retrieve the `Session` object that we want to settle all transactions for,
   with the specified session unique identifier from the list of sessions.
7. The `Session#getPersonList` method is called to retrieve an `ArrayList<Person>` object containing all persons 
   participating in the session.
8. With the list of participants, an `ArrayList<PersonCostPair>` object is obtained with the
   `SessionSummaryCommand#getPersonCostPairList` method.
   * This method first calculates all costs borne by each person in the list of participants, 
     then creates a `PersonCostPair` object that stores both the `Person` object and the cost borne by that person.
9. With both the `Session` object and the `ArrayList<PersonCostPair>` object, the method
   `SessionSummaryCommand#processAllTransactions` is called.
   * This method sorts all the `PersonCostPair` objects by their cost, then matches each debt to be paid with a debt
     to be collected between all persons. Each of such matches is referred to as a transaction.
     The matching process is repeated until no more transactions can be made,
     i.e. all debts are paid and all debts are collected.
   * For the sake of brevity, the specifics of the method `SessionSumamryCommand#processAllTransactions` is omitted
     from the sequence diagram.
   * If no transactions are required to be made, a message explaining that no transactions are required to be made
     is returned.
   * Else, a `String` object containing information regarding all transactions that have to be made is returned.
10. Finally, with the `TextUI` object, the method `TextUI#printlnMessageWithDivider` is called to print the message
    obtained from the `SessionSummaryCommand#processAllTransactions` method.

<br>
<div class="button-box">
  <a class="back-button" href="#session-commands">Back to Session Commands</a>
</div>
<hr>

## Activity Commands

### Overview
* [Add an activity](#add-an-activity)
* [Remove an activity](#remove-an-activity)
* [Edit an activity](#edit-an-activity)
* [View an activity](#view-an-activity)
* [List all activities](#list-all-activities)

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### Add an activity
**API reference:** [`ActivityCreateCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityCreateCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `activity /create` command.
<br>
<br>
![Create Activity Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/ActivityCreateCommand.drawio.png)
<br>
<br>
The general workflow of the `activity /create` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `ActivityCreateCommand` object.
3. `ActivityCreateCommand#run` method is then invoked to run the `activity /create` command.
4. Once the command runs, `ActivityCreateCommand#run` method checks if there are duplicate names in the involved list. 
   * If there are duplicate names in the involved list, a message indicating that there are duplicates is printed using `TextUI#printlnMessage`
     and control is given back to `SplitLah`.
5. The `ActivityCreateCommand` object updates the cost and cost list by invoking the `ActivityCreateCommand#updateCostAndCostList` method.
6. The`ActivityCreateCommand#run` method invokes the `Manager#getProfile` method to retrieve the `Profile` object which stores the list of sessions.
7. Once the `Profile` object is returned, `Profile#getSession` is called to retrieve the `Session` object that the activity is stored in,
   with the specified session unique identifier from the list of sessions.
   * This process is omitted in the sequence diagram for the sake of brevity.
   * If the session does not exist, a message indicating that there is no such session is printed using `TextUI#printlnMessage` and control is given back to `SplitLah`.
   * Else, the `Session` object that the activity is stored in is returned.
8. Other getter methods are then called to obtain the necessary parameters used to instantiate an Activity object. These getter methods are omitted in the sequence diagram.
9. The `ActivityCreateCommand` object then adds the respective costs to each `Person` object involved in the activity using the `ActivityCreateCommand#addAllActivityCost` method.
10. Using the updated details as parameters, the `ActivityCreateCommand` object instantiates an `Activity` object to represent the new activity.
11. Then, the `Session#addActivity` method is called to add the new `Activity` object into the list of activities.
12. After the activity is added to the `Session` object, `Manager#saveProfile` is called to save the changes to the local storage file.
13. The `Manager` object then runs `Storage#saveProfileToFile` to save the updated profile to the local storage file.
14. The `ActivityCreateCommand` object then prints a message indicating that an activity has been successfully created with `TextUI#printlnMessage`.

<br>
<div class="button-box">
  <a class="back-button" href="#activity-commands">Back to Activity Commands</a>
</div>
<hr>

### Remove an activity
**API reference:** [`ActivityDeleteCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityDeleteCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `activity /delete` command.
<br>
<br>
![Delete Activity Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/ActivityDeleteCommand.drawio.png)
<br>
<br>
The general workflow of the `activity /delete` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `ActivityDeleteCommand` object.
3. `ActivityDeleteCommand#run` method is then invoked to run the `activity /delete` command.
4. When the command runs, `ActivityDeleteCommand#run` method invokes the `Manager#getProfile` method to retrieve the `Profile` object which stores the list of sessions.
5. Once the `Profile` object is returned, `Profile#getSession` is called to retrieve the `Session` object that the activity is stored in,
   with the specified session unique identifier from the list of sessions.
   * If the session does not exist, a message indicating that there is no such session is printed using `TextUI#printlnMessage` and control is given back to `SplitLah`.
   * Else, the `Session` object that the activity is stored in is returned.
6. Upon retrieving the `Session` object, the `Session#removeActivity` method is invoked to remove the `Activity` object from the list of activities stored.
   * If the activity does not exist, a message indicating that there is no such activity is printed using `TextUI#printlnMessage` and control is given back to `SplitLah`.
   * Else, the `Activity` object is removed from the list of activities.
7. After the activity is removed from the `Session` object, `Manager#saveProfile` is called to save the changes to the local storage file.
8. The `Manager` object then runs `Storage#saveProfileToFile` to save the updated profile to the local storage file.
9. The `ActivityDeleteCommand` object then prints a message indicating that an activity has been successfully deleted with `TextUI#printlnMessage`.

<br>
<div class="button-box">
  <a class="back-button" href="#activity-commands">Back to Activity Commands</a>
</div>
<hr>

### Edit an activity
**API reference:** [`ActivityEditCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityEditCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `activity /edit` command.
<br>
<br>
![Edit Activity Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/ActivityEditCommand.drawio.png)
<br>
<br>
`activity /edit` functions by deleting an existing activity and recreating it with the same `activityId`, but with 
edited details.
<br>

The general workflow of the `activity /edit` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input using methods in the `Parser` class to obtain an `ActivityEditCommand` object.
3. `ActivityEditCommand#run` method is then invoked to run the `activity /edit` command.
4. When the command runs, `ActivityEditCommand#run` method invokes the `Manager#getProfile` method to retrieve the `Profile` object which stores the list of sessions.
5. Once the `Profile` object is returned, `Profile#getSession` is called to retrieve the `Session` object that the activity is stored in,
   with the specified session unique identifier from the list of sessions.
   * If the session does not exist, a message indicating that there is no such session is printed using `TextUi#printlnMessage` and control is given back to `SplitLah`.
   * Else, the `Session` object that the activity is stored in is returned.
6. `Session#getActivity` is called on the `activityId` provided by the user to fetch the `Activity` object representing
   the activity to be edited. 
   * If the activity does not exist, a message indicating that there is no such activity is printed using `TextUi#printlnMessage` and control is given back to `SplitLah`.
   * Else, the `Activity` object to be edited is returned and stored temporarily as `oldActivity`.
7. `ActivityEditCommand#retrieveDetailsFromOldActivity` is called on `oldActivity` to retrieve existing activity details
   for delimiters not supplied by the user. This allows `ActivityEditCommand` to reuse existing details for details the user
   does not wish to edit.
8. `ActivityEditCommand#updateCostAndCostList` and `ActivityEditCommand#validateCostListAndInvolvedList` are called to 
   create a list of costs and validate them. These costs are necessary to recreate the activity after deleting it.
9. `ActivityEditCommand#checkIfNoChangesMade` is called to check all details of the edited activity. If none of them are different from
   the old activity, an `InvalidDataException` is thrown and a message is printed using `TextUI#printlnMessage` to inform the 
   user that no changes were made to the activity.
10. `ActivityEditCommand#addAllActivityCost` is called to add the respective costs to each `Person` object involved in the activity.
    * A dummy activity unique identifier that cannot be used by any other activity is when adding these costs.
11. `Session#removeActivity()` method is invoked to remove `oldActivity` from the list of activities stored.
12. A new `Activity` object is created using the new activity details and stored as `newActivity`.
13. `Session#addActivity` is called to add `newActivity` to the session.
14. `ActivityEditCommand#updateDummyActivityIdsInActivityCosts` is called on the session to update all dummy activity unique identifiers added by `ActivityEditCommand#addAllActivityCost` to
    the `activityId` of the old activity.
15. `Manager#saveProfile` is called to save the changes to the local storage file.
16. The `ActivityCreateCommand` object then prints a message indicating that an activity has been successfully edited with `TextUi#printlnMessage`.

<br>
<div class="button-box">
  <a class="back-button" href="#activity-commands">Back to Activity Commands</a>
</div>
<hr>

### View an activity
**API reference:** [`ActivityViewCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityViewCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `activity /view` command.
<br>
<br>
![View Activity Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/ActivityViewCommand.drawio.png)
<br>
<br>
The general workflow of the `activity /view` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `ActivityViewCommand` object.
3. `ActivityViewCommand#run` method is then invoked to run the `activity /view` command.
4. The list of sessions are stored in a `Profile` object, hence `Manager#getProfile` is called.
5. Once the `Profile` object is returned, `Profile#getSession` is called to retrieve the `Session` object that the activity is stored in,
   with the specified session unique identifier from the list of sessions.
   * If the session with the requested session unique identifier does not exist, an error message is printed out with
     `TextUI#printlnMessage` and control is given back to `SplitLah`.
6. After retrieving the `Session` object, `Session#getActivity` is invoked to obtain the `Activity` object with the specified
   activity unique identifier from the list of activities.
   * If the activity with the requested activity unique identifier does not exist, an error message is printed out with
     `TextUI#printlnMessage` and control is given back to `SplitLah`.
   * Else, a `String` object representing the details of the requested activity is retrieved using the
      `Activity#toString` method and printed out using `TextUI#printlnMessageWithDivider`.

<br>
<div class="button-box">
  <a class="back-button" href="#activity-commands">Back to Activity Commands</a>
</div>
<hr>
  
### List all activities
**API reference:** [`ActivityListCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/ActivityListCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `activity /list` command.
<br>
<br>
![List Activity Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/ActivityListCommand.drawio.png)
<br>
<br>
The general workflow of the `activity /list` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `ActivityListCommand` object.
3. `ActivityListCommand#run` method is then invoked to run the `activity /list` command.
4. The list of activities are stored in a `Profile` object, hence `Manager#getProfile` is called.
5. Once the `Profile` object is returned, `Profile#getSession` is called to retrieve the `Session` object that the list of activities is stored in,
   with the specified session unique identifier from the list of sessions.
6. When the session is retrieved, `ActivityListCommand` class runs `Session#getActivityListSummaryString`.
   * For the sake of brevity, the specifics of the method `Session#getActivityListSummaryString` is omitted
     from the sequence diagram.
   * If the activity list in the `Session` object is empty, the `Session` class returns a `String` object containing an error message.
   * Else, a `String` object representing a table summarising the list of activities in the session is returned. 
7. Finally, the method `TextUI#printlnMessageWithDivider` is called to print the message returned.

<br>
<div class="button-box">
  <a class="back-button" href="#activity-commands">Back to Activity Commands</a>
</div>
<hr>

## Group Commands

### Overview
* [Add a group](#add-a-group)
* [Remove a group](#remove-a-group)
* [Edit a group](#edit-a-group)
* [View a group](#view-a-group)
* [List all groups](#list-all-groups)

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### Add a group
**API reference:** [`GroupCreateCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/GroupCreateCommand.java)

The sequence diagram for `GroupCreateCommand` is omitted as it bears many similarities with [`SessionCreateCommand`](#add-a-session).<br>
The interactions of `GroupCreateCommand` with `Profile` and `Storage` classes are identical but the key differences lie in the arguments being parsed:
* `GroupCreateCommand` parses only the **name** and **list of persons**. It then creates a new `Group` object and adds it to the list of groups managed by the `Profile` class.

Please refer to the [sequence diagram](#add-a-session) of `SessionCreateCommand` for reference.

The general workflow of the `group /create` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `GroupCreateCommand` object.
3. `GroupCreateCommand#run` method is then invoked to run the `group /create` command.
4. Once the command runs, `GroupCreateCommand#run` method checks if there is an existing group with the same group name.
   * If an existing group with the specified group name is found, a message indicating that another group with the same name exists is printed using `TextUI#printlnMessage`
     and control is returned to `SplitLah`.
5. The `GroupCreateCommand` class creates a new `Group` object using the group name and person list.
6. The list of `Group` objects are managed by a `Profile` object, hence `Manager#getProfile` is called to obtain the `Profile` object,
   which is used to call the `Profile#addGroup` method in order to store the new `Group` object.
7. After the group is added to the `Profile` object, `Manager#saveProfile` is called to save the changes to the local storage file.
8. The `GroupCreateCommand` class then prints a message indicating that a group has been successfully created with `TextUI#printlnMessage`.

<br>
<div class="button-box">
  <a class="back-button" href="#group-commands">Back to Group Commands</a>
</div>
<hr>

### Remove a group
**API reference:** [`GroupDeleteCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/GroupDeleteCommand.java)

The sequence diagram for `GroupDeleteCommand` is omitted as it bears many similarities with [`SessionDeleteCommand`](#remove-a-session).<br>
The interactions of `GroupDeleteCommand` with `Profile` and `Storage` classes are identical but the key differences lie in the arguments being parsed:
* `GroupViewCommand` parses the **group unique identifier** instead of the **session unique identifier**.
* It then removes the `Group` object from the list of groups managed by the `Profile` class corresponding to the **group unique identifier**.

Please refer to the [sequence diagram](#remove-a-session) of `SessionDeleteCommand` for reference.

The general workflow of the `group /delete` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `GroupDeleteCommand` object.
3. The `GroupDeleteCommand#run` method is then invoked to run the `group /delete` command.
4. The list of groups are stored in a `Profile` object, hence `Manager#getProfile` is called
   before the list of groups can be retrieved.
5. Once the `Profile` object is returned, `Profile#getGroup` is called to retrieve the `Group` object with the specified
   group unique identifier from the list of groups.
   * If a `Group` object with the specified group unique identifier cannot be found, it prints the error message and returns control to `SplitLah`.
   * Else, the `Group` object with the specified group unique identifier is returned.
6. To remove the `Group` object from the list of groups stored in `Profile` object, the `Profile#removeGroup` method is invoked.
7. After the group is removed from the `Profile` object, `Manager#saveProfile` is called to save the changes to the local storage file.
8. The `GroupDeleteCommand` class then prints a message indicating that a group has been successfully deleted.

<br>
<div class="button-box">
  <a class="back-button" href="#group-commands">Back to Group Commands</a>
</div>
<hr>

### Edit a group
**API reference:** [`GroupEditCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/GroupEditCommand.java)

The sequence diagram below models the interactions between various entities in SplitLah
when the user invokes the `group /edit` command.
<br>
<br>
![Edit Group Sequence Diagram Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/GroupEditCommand.drawio.png)
<br>
<br>
The general workflow of the `group /edit` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `GroupEditCommand` object.
3. The `GroupEditCommand#run` method is invoked to run the `group /edit` command.
4. The list of groups are stored in a `Profile` object, hence `Manager#getProfile` is called
   before the list of groups can be retrieved.
5. Once the `Profile` object is returned, `Profile#getGroup` is called to retrieve the `Group` object with the specified
   group unique identifier from the list of groups.
    * If a `Group` object with the specified group unique identifier cannot be found, it prints the error message and returns control to `SplitLah`.
    * Else, the `Group` object with the specified group unique identifier is returned.
6. The details of how a group is updated are displayed in the reference diagram below.<br>
   <br><br>
   ![Reference Frame Update Group Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/developerguide/RefUpdateGroup.drawio.png)
   <br><br>
7. If a new group name is provided, `GroupEditCommand#existingGroupWithTheSameName` method is called to check if the provided group name already exists in the list of groups.
   * If the provided group name exists within the list of groups, the method returns `true`. An error message is then printed and control is returned to `SplitLah`.
   * Else, the method returns `false`.
8. If a new list of persons is provided, `PersonList#hasNameDuplicates` is called to check for duplicate names within the provided list of persons.
   * If duplicated names are detected, the method returns `true`. An error message is printed and control is returned to `SplitLah`.
   * Else, the method returns `false`. A new `PersonList` object to be stored is created to be used as the updated list of persons.
9. Where a valid new group name is provided, `Group#setGroupName` method is called to update the group name.
10. Where a valid new person list is provided, `Group#setPersonList` is called to update the new person list.
11. After the group is edited, `Manager#saveProfile` is called to save the changes to the local storage file.
12. The `GroupEditCommand` class then prints a message indicating that the group has been successfully edited.

<br>
<div class="button-box">
  <a class="back-button" href="#group-commands">Back to Group Commands</a>
</div>
<hr>

### View a group
**API reference:** [`GroupViewCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/GroupViewCommand.java)
The sequence diagram for `GroupViewCommand` is omitted as it bears many similarities with [`SessionViewCommand`](#view-a-session).<br>
The interactions of `GroupViewCommand` with the `Profile` class is identical but the key differences are as follows:
* `GroupViewCommand` parses the **group unique identifier** instead of the **session unique identifier**.
* It then displays the details of the `Group` object from the list of groups managed by the `Profile` class corresponding to the **group unique identifier**.

Please refer to the [sequence diagram](#view-a-session) of `SessionViewCommand` for reference.

The general workflow of the `group /view` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `GroupViewCommand` object.
3. `GroupViewCommand#run` method is then invoked to run the `group /view` command.
4. The list of groups are stored in a `Profile` object, hence `Manager#getProfile` is called before a group within 
   the list of groups can be retrieved.
5. The `GroupViewCommand` object runs the `Profile#getGroup` method to retrieve the group represented by the
   group unique identifier provided.
   * If the group with requested group unique identifier does not exist, an error message is printed out with
     `TextUI#printlnMessage`.
   * Else, the `String` object representing the details of the requested group is retrieved using the `Group#toString`
     method. The `String` object is then printed out with `TextUI#printlnMessageWithDivider`.

<br>
<div class="button-box">
  <a class="back-button" href="#group-commands">Back to Group Commands</a>
</div>
<hr>

### List all groups
**API reference:** [`GroupListCommand.java`](https://github.com/AY2122S2-CS2113T-T10-1/tp/blob/master/src/main/java/seedu/splitlah/command/GroupListCommand.java)
The sequence diagram for `GroupListCommand` is omitted as it bears many similarities with [`SessionListCommand`](#list-all-sessions).<br>
The interactions of `GroupListCommand` with the `Profile` class is identical but the key differences lies in the objects that are used in the `Profile` class:
* `GroupListCommand` calls the method `Profile#getGroupListSummaryString` instead of `Profile#getSessionListSummaryString` which utilises the list of groups instead of the list of sessions.

Please refer to the [sequence diagram](#list-all-sessions) of `SessionListCommand` for reference.

The general workflow of the `group /list` command is as follows:
1. The user input provided is passed to `SplitLah`.
2. `SplitLah` then parses the input by using methods in the `Parser` class to obtain a `GroupListCommand` object.
3. `GroupListCommand#run` method is then invoked to run the `group /list` command.
4. The list of groups are stored in a `Profile` object, hence `Manager#getProfile` is called
   before the list of groups can be retrieved.
5. The `GroupListCommand` object runs the `Profile#getGroupListSummaryString` method to retrieve a `String` object
   representing the summaries of the groups stored.
   * If there are no groups stored in the `Profile` object, a `String` object representing an empty list of groups is
     returned.
   * Else, the `Profile` objects instantiates a new `TableFormatter` object and loops through the list of groups,
     calling `TableFormatter#addRow` for each group to create a table with the summary of each group. A `String` object
     representing the table is then returned.
6. The `String` object retrieved is printed out with `TextUI#printlnMessage`.

<br>
<div class="button-box">
  <a class="back-button" href="#group-commands">Back to Group Commands</a>
</div>
<hr>

## Appendix: Requirements
## Product scope
### Target user profile
* Budget conscious.
* Prefers to use desktop applications over other types of applications.
* Are able to type relatively fast.
* Comfortable using CLI applications.

### Value proposition
SplitLah helps the user keep track of expenditures made during each group outing that they go on and help the user 
calculate what transactions that they should make in order to ensure that everyone pays equally for the activities that 
they engage in during the outings.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

## User Stories

| Version | As a ...                   | I want to ...                        | So that I can ...                                                                                                      |
|---------|----------------------------|--------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| v1.0    | New user                   | view a list of instructions          | refer to them when I forget how to use the application                                                                 |
| v1.0    | Budget conscious user      | create sessions                      | record the details of my outing and keep track of the expenditure of each activity                                     |
| v1.0    | Returning user             | delete sessions                      | remove unnecessary sessions                                                                                            |
| v1.0    | Budget conscious user      | list all existing sessions           | see a list of all the outings that were recorded                                                                       |
| v1.0    | Budget conscious user      | create activities                    | record the details and the expenditure of the activities in a particular outing                                        |
| v1.0    | Returning user             | delete activities                    | remove unnecessary activities                                                                                          |
| v1.0    | Returning user             | view existing activities             | see the full details of a particular activity, including the cost breakdown of the activity                            |
| v1.0    | Budget conscious user      | list all activities in a session     | see a list of all the activities that were recorded during a particular outing                                         |
| v1.0    | Budget conscious user      | settle all transactions of a session | see a summary of all the transactions that are needed to be made amongst those who went for a particular outing        |
| v2.0    | Returning user             | view existing sessions               | see the full details of a particular outing, including a list of activities for the outing                             |
| v2.0    | Careless user              | edit sessions                        | amend a mistake previously made when recording an outing                                                               |
| v2.0    | Careless user              | edit activities                      | amend a mistake previously made when recording an activity                                                             |
| v2.0    | User with a lot of friends | create groups                        | easily record outings using groups instead of listing out all the names of the people who went for the outing manually |
| v2.0    | Returning user             | delete groups                        | remove unnecessary groups                                                                                              |
| v2.0    | Careless user              | edit groups                          | amend a mistake previously made when creating a group                                                                  |
| v2.0    | Returning user             | view existing groups                 | see the full details of a particular group, including the names of the people in the group                             |
| v2.0    | User with a lot of friends | list groups                          | see a list of all the groups that were created                                                                         |

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

## Non-Functional Requirements
1. The application should be able to work on any operating systems with `Java 11` installed.
2. The application should be responsive.
3. The application should be usable by a novice who may not be well versed with a Command Line Interface (CLI).
4. Ths application should be usable by a novice who has never used an application to split bills.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

## Glossary

| Terms    | Definition                                                                                                                                                                                                                                                                                                  |
|----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Activity | An activity represents a single group activity and stores its name, costs and the name of the payer.                                                                                                                                                                                                        |
| API      | An Application Programming Interface (API) specifies the interface through which other programs can interact with a software component.                                                                                                                                                                     |
| Command  | A Command is an object that performs a task that corresponds to the user input.                                                                                                                                                                                                                             |
| Group    | A group represents one or more individuals. The sole purpose of a group is to quickly identify a group of individuals without having to manually enter their details one by one when creating a session.                                                                                                    |
| Manager  | A Manager manages and stores 3 different objects, namely the `Profile`, `TextUI` and `Storage` objects.                                                                                                                                                                                                     |
| Parser   | A Parser is responsible for making sense of the user inputs and processing them as commands for the application to run.                                                                                                                                                                                     |
| Profile  | A Profile is responsible for all data management and accesses within the lifetime of the application. It serves as a container and holds a list of all `Session` and `Group` objects and keeps track of the unique identifiers to be issued upon the creation of `Session`, `Activity` and `Group` objects. |
| Session  | A session represents a group outing that involves a list of participants and spans an arbitrary period of time containing one or more activities.                                                                                                                                                           |
| Storage  | A Storage is in charge of saving and reading to and from the save file respectively.                                                                                                                                                                                                                        |
| TextUI   | A TextUI is an user interface that the user sees on the CLI.                                                                                                                                                                                                                                                |


<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

## Appendix: Instructions for Manual Testing
This section includes instructions to test SplitLah manually.

> **💡 Note:**
> 
> These instructions only provide a starting point for testers to work on; testers are free to do more _exploratory_ testing.

### Overview
* [Launch and Shutdown](#launch-and-shutdown)
* [Session Testing](#session-testing)
* [Activity Testing](#activity-testing)
* [Group Testing](#group-testing)
* [Storage Testing](#storage-testing)

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
<hr>

### Launch and Shutdown

#### Initial Launch
1. Ensure that Java 11 or above is installed.
2. Download the latest .jar version of SplitLah from [here](https://github.com/AY2122S2-CS2113T-T10-1/tp/releases).
3. Copy the file to the folder you wish to use as a home folder for SplitLah.
4. Open a terminal and navigate to SplitLah's home folder.
5. Start SplitLah by executing `java -jar splitlah.jar` in the terminal.
6. Once SplitLah has successfully launched a welcome message should appear.
   When launched for the first time, SplitLah should mention that no save files were found or loaded.

#### Shutting Down
1. When SplitLah is awaiting user input, enter `exit` to terminate the application.
2. SplitLah prints a farewell message before terminating.

<br>
<div class="button-box">
  <a class="back-button" href="#appendix-instructions-for-manual-testing">Back to Appendix: Instructions for Manual Testing</a>
</div>
<hr>

### Session Testing

#### Overview
* [Creating a Session](#creating-a-session)
* [Deleting a Session](#deleting-a-session)
* [Editing a Session](#editing-a-session)
* [Settling a Session](#settling-a-session)
* [Viewing a Session](#viewing-a-session)
* [Listing all Sessions](#listing-all-sessions)

<br>
<div class="button-box">
  <a class="back-button" href="#appendix-instructions-for-manual-testing">Back to Appendix: Instructions for Manual Testing</a>
</div>
<hr>

#### Creating a Session
> For details on the usage of the `session /create` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create).

**Test Cases:**

Test Scenario 1: No sessions are currently stored in the application.
1. Test Command: `session /create /n SessionTest1 /pl Alice Bob /d 10-04-2022` <br>
   Expected: A success message should be printed, indicating the details of the session as provided in the command.
2. Test Command: `session /create /n SessionTest2 /pl Alice Bob /d today`<br>
   Expected: A success message should be printed, indicating the details of the session as provided in the command.
3. Test Command: `session /create /n SessionTest3 /pl Alice Alice /d today` <br>
   Expected: An error message should be printed, indicating that there are duplicates in the list of persons provided.

Test Scenario 2: Only a single session with a session unique identifier of `1`, named `SessionTest1`, exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command.
1. Test Command: `session /create /n SessionTest3 /pl Alice Bob /d today` <br>
   Expected: A success message should be printed, indicating the details of the session as provided in the command.
2. Test Command: `session /create /n SessionTest1 /pl Alice Bob /d 10-04-2022` <br>
   Expected: An error message should be printed, indicating that a session with the same name already exists within the application.

Test Scenario 3: Only a single group with a group unique identifier of `1`, named `GroupTest1` with `Alice`, `Bob` and `Charlie` exists in the application after creating
it with the [`group /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-group-group-create) command.
1. Test Command: `session /create /n SessionTest4 /gid 1 /d today`<br>
   Expected: A success message should be printed, indicating the details of the session as provided in the command. 
             The list of persons in the session should include the persons found in the group provided.
2. Test Command: `session /create /n SessionTest5 /gid 1 /pl David /d today`<br>
   Expected: A success message should be printed, indicating the details of the session as provided in the command.
   The list of persons in the session should include Alice, Bob, Charlie and also David.
3. Test Command: `session /create /n SessionTest6 /gid 2 /d today`<br>
   Expected: An error message should be printed, indicating that the specified group unique identifier cannot be found.

<br>
<div class="button-box">
  <a class="back-button" href="#session-testing">Back to Session Testing</a>
</div>
<hr>

#### Deleting a Session
> For details on the usage of `session /delete` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#deleting-a-session-session-delete).

**Test Cases:**

Test Scenario 1: Only a single session with a session unique identifier of `1` exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command.
1. Test Command: `session /delete /sid 1`<br>
   Expected: A success message should be printed, indicating that the session has been deleted.
2. Test Command: `session /delete /sid 2`<br>
   Expected: An error message should be printed, indicating that the specified session unique identifier cannot be found.
3. Test Command; `session /delete /sid apple`<br>
   Expected: An error message should be printed, indicating that an integer argument should be provided following the `/sid` delimiter.

<br>
<div class="button-box">
  <a class="back-button" href="#session-testing">Back to Session Testing</a>
</div>
<hr>

#### Editing a Session
> For details on the usage of `session /edit` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#editing-a-session-session-edit).

**Test Cases:**

Test Scenario 1: Only a single session with a session unique identifier of `1`, named `SessionTest1` with `Alice` and `Bob` involved on `10-04-2022`, exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command.
1. Test Command: `session /edit /sid 1 /n SessionTest1`<br>
   Expected: A message should be printed, indicating that no edits were made.
2. Test Command: `session /edit /sid 1 /n SessionTest10`<br>
   Expected: A success message should be printed, indicating that the session was edited.
3. Test Command: `session /edit /sid /pl Alice Bob Charlie`<br>
   Expected: A success message should be printed, indicating that the session was edited.
4. Test Command: `session /edit /sid 1 /pl Alice`<br>
   Expected: An error message should be printed, indicating that there are missing persons from the original list of persons in the session.
5. Test Command: `session /edit /sid apple`<br>
   Expected: An error message should be printed, indicating that an integer argument should be provided following the `/sid` delimiter.
6. Test Command: `session /edit /sid 1`<br>
   Expected: An error message should be printed, indicating that no delimiters were found.

<br>
<div class="button-box">
  <a class="back-button" href="#session-testing">Back to Session Testing</a>
</div>
<hr>

#### Settling a Session
> For details on the usage of `session /summary` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#settling-all-transactions-for-a-session-session-summary).

**Test Cases:**

Test Scenario 1: No sessions are currently stored in the application. 
* Test Command: `session /summary /sid 1` <br>
  Expected: An error message should be printed, indicating that no sessions are currently stored.
 
Test Scenario 2: Only a single session with a session unique identifier of `1` exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command.
1. Test Command: `session /summary`<br>
   Expected: An error message should be printed, indicating that the `/sid` delimiter is missing from the input.
2. Test Command: `session /summary /sid`<br>
   Expected: An error message should be printed, indicating that an argument following the `/sid` delimiter is missing from the input.
3. Test Command: `session /summary /sid apple`<br>
   Expected: An error message should be printed, indicating that an integer argument should be provided following the `/sid` delimiter.
4. Test Command: `session /summary /sid 2`<br>
   Expected: An error message should be printed, indicating that a session with the specified session unique identifier was not found.
5. Test Command: `session /summary /sid 1`<br>
   Expected: A summary indicating the transactions that have to be made to settle all debts of the session with a 
   session unique identifier of `1` should be printed.

<br>
<div class="button-box">
  <a class="back-button" href="#session-testing">Back to Session Testing</a>
</div>
<hr>

#### Viewing a Session
> For details on the usage of `session /view` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#viewing-a-session--session-view)

**Test Cases:**

Test Scenario 1: No sessions are currently stored in the application.
* Test Command: `session /view /sid 1` <br>
  Expected: An error message should be printed, indicating that no sessions are currently stored.

Test Scenario 2: Only a single session with a session unique identifier of `1` exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command.
1. Test Command: `session /view`<br>
   Expected: An error message should be printed, indicating that the `/sid` delimiter is missing from the input.
2. Test Command: `session /view /sid`<br>
   Expected: An error message should be printed, indicating that an argument following the `/sid` delimiter is missing from the input.
3. Test Command: `session /view /sid apple`<br>
   Expected: An error message should be printed, indicating that an integer argument should be provided following the `/sid` delimiter.
4. Test Command: `session /view /sid 2`<br>
   Expected: An error message should be printed, indicating that a session with the specified session unique identifier was not found.
5. Test Command: `session /view /sid 1`<br>
   Expected: The full details of the session with a session unique identifier of `1` should be printed.

<br>
<div class="button-box">
  <a class="back-button" href="#session-testing">Back to Session Testing</a>
</div>
<hr>

#### Listing all Sessions
> For details on the usage of `session /list` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#listing-all-sessions-session-list)

**Test Cases:**

Test Scenario 1: No sessions are currently stored in the application.
* Test Command: `session /list` <br>
  Expected: An error message should be printed, indicating that no sessions are currently stored.

Test Scenario 2: At least 1 session exists in the application.
* Test Command: `session /list`<br>
  Expected: A table summarising the details of all existing sessions should be printed.


<br>
<div class="button-box">
  <a class="back-button" href="#session-testing">Back to Session Testing</a>
</div>
<hr>

### Activity Testing

#### Overview
* [Creating an Activity](#creating-an-activity)
* [Deleting an Activity](#deleting-an-activity)
* [Editing an Activity](#editing-an-activity)
* [Viewing an Activity](#viewing-an-activity)
* [Listing all Activities](#listing-all-activities)

<br>
<div class="button-box">
  <a class="back-button" href="#appendix-instructions-for-manual-testing">Back to Appendix: Instructions for Manual Testing</a>
</div>
<hr>

#### Creating an Activity
> For details on the usage of `activity /create` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-an-activity-activity-create).

**Test Cases:**

Test Scenario 1: No sessions are currently stored in the application.
* Test Command: `activity /create /sid 1 /n ActivityTest1 /p Alice /i Alice Bob /co 20` <br>
  Expected: An error message should be printed, indicating that no sessions are currently stored.

Test Scenario 2: Only a single session with a session unique identifier of `1` exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command.
1. Test Command: `activity /create /sid 1 /n ActivityTest1 /p Alice /i Alice Bob /co 20` <br>
   Expected: A success message should be printed, along with the details of the activity as provided in the command. Each person's cost owed is $10.
2. Test Command: `activity /create /sid 1 /n ActivityTest1 /p Alice /i Alice Bob /cl 5 10` <br>
   Expected: A success message should be printed, along with the details of the activity as provided in the command. Alice's cost owed is $5 while Bob's cost owed is $10.
3. Test Command: `activity /create /sid 1 /n ActivityTest1 /p Alice /i Alice Bob /co 20 /gst 7 /sc 10` <br>
   Expected: A success message should be printed, along with the details of the activity as provided in the command. Each person's cost should have been multiplied by 1.177 to take GST and service charge into account.
4. Test Command: `activity /create /sid 1 /n ActivityTest1 /p Alice /i Alice Bob /cl 5 10 /gst 7 /sc 10` <br>
   Expected: A success message should be printed, along with the details of the activity as provided in the command. Each person's cost should have been multiplied by 1.177 to take GST and service charge into account.

Test Scenario 3: Only a single session with a session unique identifier of `1`, consisting of only `Alice` and `Bob`, exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command.
1. Test Command: `activity /create /sid 1 /n ActivityTest1 /p Mallory /i Alice Bob /co 20 /gst 7 /sc 10` <br>
   Expected: An error message should be printed, indicating that a person specified was not found in the particular session.
2. Test Command: `activity /create /sid 1 /n ActivityTest1 /p Alice /i Bob Mallory /cl 5 10 /gst 7 /sc 10` <br>
   Expected: An error message should be printed, indicating that a person specified was not found in the particular session.

<br>
<div class="button-box">
  <a class="back-button" href="#activity-testing">Back to Activity Testing</a>
</div>
<hr>

#### Deleting an Activity
> For details on the usage of `activity /delete` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#deleting-an-activity-activity-delete).

**Test Cases:**

Test Scenario 1: No sessions are currently stored in the application.
* Test Command: `activity /delete /sid 1 /aid 1` <br>
  Expected: An error message should be printed, indicating that no sessions are currently stored.

Test Scenario 2: Only a single session with a session unique identifier of `1` exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command. No activities are currently stored in the session.
* Test Command: `activity /delete /sid 1 /aid 1` <br>
  Expected: An error message should be printed, indicating that the list of activities in the session is currently empty.

Test Scenario 3: Only a single session with a session unique identifier of `1` exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command.
Also, only an activity with an activity unique identifier of `1` within this session exists in the application after creating
it with the [`activity /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-an-activity-activity-create) command.
1. Test Command: `activity /delete /sid 1 /aid 1` <br>
   Expected: A success message should be printed, indicating that the activity was deleted.
2. Test Command: `activity /delete /sid 3 /aid 1` <br>
   Expected: An error message should be printed, indicating that the session specified was not found.
3. Test Command: `activity /delete /sid 1 /aid 4` <br>
   Expected: An error message should be printed, indicating that the activity specified was not found in the particular session.

<br>
<div class="button-box">
  <a class="back-button" href="#activity-testing">Back to Activity Testing</a>
</div>
<hr>

#### Editing an Activity
> For details on the usage of `activity /edit` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#editing-an-activity-activity-edit).

**Test Cases:**

Test Scenario 1: No sessions are currently stored in the application.
* Test Command: `activity /edit /sid 1 /aid 1 /n ActivityTest2` <br>
  Expected: An error message should be printed, indicating that no sessions are currently stored.

Test Scenario 2: Only a single session with a session unique identifier of `1` exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command. No activities are currently stored in the session.
* Test Command: `activity /edit /sid 1 /aid 1 /n ActivityTets2` <br>
  Expected: An error message should be printed, indicating that the list of activities in the session is currently empty.

Test Scenario 3: Only a single session with a session unique identifier of `1`, consisting of only `Alice` and `Bob`, exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command.
Also, only an activity with an activity unique identifier of `1`, named `ActivityTest1` with a total cost of `$20`, paid by `Alice`, involving `Alice` and `Bob` within this session exists in the application after creating
it with the [`activity /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-an-activity-activity-create) command.
1. Test Command: `activity /edit /sid 1 /aid 2 /n ActivityTest2` <br>
   Expected: An error message should be printed, indicating that the activity specified was not found in the particular session.
2. Test Command: `activity /edit /sid 1 /aid 1 /n ActivityTest1`
   Expected: A message should be printed, indicating that no edits were made.
3. Test Command: `activity /edit /sid 1 /aid 1 /n ActivityTest2` <br>
   Expected: A success message should be printed, indicating that the activity was edited.
4. Test Command: `activity /edit /sid 1 /aid 1 /p Bob` <br>
   Expected: A success message should be printed, indicating that the activity was edited.

<br>
<div class="button-box">
  <a class="back-button" href="#activity-testing">Back to Activity Testing</a>
</div>
<hr>

#### Viewing an Activity
> For details on the usage of `activity /view` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#viewing-an-activity-activity-view).

**Test Cases:**

Test Scenario 1: No sessions are currently stored in the application.
* Test Command: `activity /view /sid 1 /aid 1` <br>
  Expected: An error message should be printed, indicating that no sessions are currently stored.

Test Scenario 2: Only a single session with a session unique identifier of `1` exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command. No activities are currently stored in the session.
* Test Command: `activity /view /sid 1` <br>
  Expected: An error message should be printed, indicating that the list of activities in the session is currently empty.

Test Scenario 3: Only a single session with a session unique identifier of `1` exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command.
Also, only an activity with an activity unique identifier of 1 within this session exists in the application after creating it with the [activity /create](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-an-activity-activity-create) command.
1. Test Command: `activity /view`<br>
   Expected: An error message should be printed, indicating that the `/sid` delimiter is missing from the input.
2. Test Command: `activity /view /sid`<br>
   Expected: An error message should be printed, indicating that an argument following the `/sid` delimiter is missing from the input.
3. Test Command: `activity /view /sid apple`<br>
   Expected: An error message should be printed, indicating that an integer argument should be provided following the `/sid` delimiter.
4. Test Command: `activity /view /sid 1`<br>
   Expected: An error message should be printed, indicating that the `/aid` delimiter is missing from the input.
5. Test Command: `activity /view /sid 1 /aid`<br>
   Expected: An error message should be printed, indicating that an argument following the `/aid` delimiter is missing from the input.
6. Test Command: `activity /view /sid 1 /aid apple` <br>
   Expected: An error message should be printed, indicating that an integer argument should be provided following the `/aid` delimiter.
7. Test Command: `activity /view /sid 1 /aid 1`<br>
   Expected: The full details of the activity with an activity unique identifier of `1` within the session with a session unique identifier of `1` should be printed.

<br>
<div class="button-box">
  <a class="back-button" href="#activity-testing">Back to Activity Testing</a>
</div>
<hr>

#### Listing all Activities
> For details on the usage of `activity /list` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#listing-all-activities-in-a-session-activity-list).

**Test Cases:**

Test Scenario 1: No sessions are currently stored in the application.
* Test Command: `activity /list /sid 1` <br>
  Expected: An error message should be printed, indicating that no sessions are currently stored.

Test Scenario 2: Only a single session with a session unique identifier of `1` exists in the application after creating
a single session with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command. No activities are currently stored in the session.
* Test Command: `activity /view /sid 1` <br>
  Expected: An error message should be printed, indicating that the list of activities in the session is currently empty.

Test Scenario 3: Only a single session with a session unique identifier of `1` exists in the application after creating
it with the [`session /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-session-session-create) command. At least 1 activity is stored in the session.
1. Test Command: `activity /list`<br>
   Expected: An error message should be printed, indicating that the `/sid` delimiter is missing from the input.
2. Test Command: `activity /list /sid`<br>
   Expected: An error message should be printed, indicating that an argument following the `/sid` delimiter is missing from the input.
3. Test Command: `activity /list /sid apple`<br>
   Expected: An error message should be printed, indicating that an integer argument should be provided following the `/sid` delimiter.
4. Test Command: `activity /view /sid 1`<br>
   Expected: A table summarising the details of all existing activities in the session with session unique identifier of `1` should be printed.

<br>
<div class="button-box">
  <a class="back-button" href="#activity-testing">Back to Activity Testing</a>
</div>
<hr>

### Group Testing

#### Overview
* [Creating a Group](#creating-a-group)
* [Deleting a Group](#deleting-a-group)
* [Editing a Group](#editing-a-group)
* [Viewing a Group](#viewing-a-group)
* [Listing all Groups](#listing-all-groups)

<br>
<div class="button-box">
  <a class="back-button" href="#appendix-instructions-for-manual-testing">Back to Appendix: Instructions for Manual Testing</a>
</div>
<hr>

#### Creating a Group
> For details on the usage of `group /create` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-group-group-create).

**Test Cases:**

Test Scenario 1: No groups are currently stored in the application.
1. Test Command: `group /create /n GroupTest1 /pl Alice Bob Charlie` <br>
   Expected: A success message should be printed, indicating the details of the group as provided in the command.
2. Test Command: `group /create /n GruopTest2 /pl Alice Bob Charlie David Mike`<br>
   Expected: A success message should be printed, indicating the details of the group as provided in the command.
3. Test Command: `group /create /n GruopTest3 /pl Alice Alice` <br>
   Expected: An error message should be printed, indicating that there are duplicates in the list of persons provided.

Test Scenario 2: There is a group named GroupTest1 currently stored in the application.
1. Test Command: `group /create /n GruopTest3 /pl Alice Bob` <br>
   Expected: A success message should be printed, indicating the details of the group as provided in the command.
2. Test Command: `group /create /n GroupTest1 /pl Alice Bob Charlie` <br>
   Expected: An error message should be printed, indicating that a group with the same name already exists within the application.

<br>
<div class="button-box">
  <a class="back-button" href="#group-testing">Back to Group Testing</a>
</div>
<hr>

#### Deleting a Group
> For details on the usage of `group /delete` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#deleting-a-group-group-delete).

**Test Cases:**

Test Scenario 1: No groups are currently stored in the application.
* Test Command: `group /delete /gid 1`<br> 
  Expected: An error message should be printed indicating that there are currently no groups stored.
 
Test Scenario 2: There is only one group with a group unique identifier of 1 stored in the application.
1. Test Command: `group /delete /gid 1`<br>
   Expected: A success message should be printed, indicating that the group has been deleted.
2. Test Command: `group /delete /gid 2`<br>
   Expected: An error message should be printed, indicating that the specified group unique identifier cannot be found.
3. Test Command; `group /delete /gid apple`<br>
   Expected: An error message should be printed, indicating that an integer argument should be provided following the `/gid` delimiter.

<br>
<div class="button-box">
  <a class="back-button" href="#group-testing">Back to Group Testing</a>
</div>
<hr>

#### Editing a Group
> For details on the usage of `group /edit` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#editing-a-group-group-edit).

**Test Cases:**

Test Scenario 1: No groups are currently stored in the application.
* Test Command: `group /edit /gid 1 /pl Alice Bob Charlie` <br>
  Expected: An error message should be printed indicating that there are currently no groups stored.
 
Test Scenario 2: Only one group has been created with a group unique identifier of 1, named GroupTest1 with Alice, Bob and Charlie.
1. Test Command: `group /edit /gid 1 /n GroupTest1`<br>
   Expected: A message should be printed, indicating that no edits were made.
2. Test Command: `group /edit /gid 1 /n GroupTest10`<br>
   Expected: A success message should be printed, indicating that the group was edited.
3. Test Command: `group /edit /gid 1 /pl Alice Bob Charlie`<br>
   Expected: An error message should be printed, indicating that no changes were made to the group.
4. Test Command: `group /edit /gid 1 /pl Alice Bob Charlie Mike`<br>
   Expected: A success message should be printed, indicating that the group was successfully edited.
5. Test Command: `group /edit /gid apple`<br>
   Expected: An error message should be printed, indicating that an integer argument should be provided following the `/gid` delimiter.
6. Test Command: `group /edit /gid 1`<br>
   Expected: An error message should be printed, indicating that no delimiters were found.
7. Test Command: `group /edit /gid 2 /pl Alice Bob Charlie`<br>
   Expected: An error message should be printed, indicating that the specified group unique identifier cannot be found.

<br>
<div class="button-box">
  <a class="back-button" href="#group-testing">Back to Group Testing</a>
</div>
<hr>

#### Viewing a Group
> For details on the usage of `group /view` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#viewing-a-group-group-view).

**Test Cases:**

Test Scenario 1: No groups are currently stored in the application.
* Test Command: `group /view /gid 1` <br>
  Expected: An error message should be printed indicating that there are currently no groups stored.

Test Scenario 2: Only a single group with a group unique identifier of `1` exists in the application after creating
it with the [`group /create`](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#creating-a-group-group-create) command.
1. Test Command: `group /view`<br>
   Expected: An error message should be printed, indicating that the `/gid` delimiter is missing from the input.
2. Test Command: `group /view /gid`<br>
   Expected: An error message should be printed, indicating that an argument following the `/gid` delimiter is missing from the input.
3. Test Command: `group /view /gid apple`<br>
   Expected: An error message should be printed, indicating that an integer argument should be provided following the `/gid` delimiter.
4. Test Command: `group /view /gid 2`<br>
   Expected: An error message should be printed, indicating that a group with the specified group unique identifier was not found.
5. Test Command: `group /view /gid 1`<br>
   Expected: The full details of the group with a group unique identifier of `1` should be printed.

<br>
<div class="button-box">
  <a class="back-button" href="#group-testing">Back to Group Testing</a>
</div>
<hr>

#### Listing all Groups
> For details on the usage of `group /list` command, please refer to our [User Guide](https://ay2122s2-cs2113t-t10-1.github.io/tp/UserGuide.html#listing-all-groups-group-list).

**Test Cases:**

Test Scenario 1: No groups are currently stored in the application.
* Test Command: `group /list` <br>
  Expected: An error message should be printed, indicating that no groups are stored in SplitLah.

Test Scenario 2: At least 1 group exists in the application.
* Test Command: `group /list`<br>
  Expected: A table summarising the details of all existing groups should be printed.

<br>
<div class="button-box">
  <a class="back-button" href="#group-testing">Back to Group Testing</a>
</div>
<hr>

### Storage Testing

**Test Cases:**

Test Scenario 1: SplitLah.jar is placed in a location where read and write permissions are given.
1. Test: No save file was found. <br>
   Expected: A new save file should be created when the application launches.
2. Test: Save file was corrupted. <br>
   Expected: The application should detect it as a corrupted file and create a new save file.

Test Scenario 2: SplitLah.jar is placed in a location where read and write permissions are not given. 
* Test: No save file was found.<br>
   Expected: An error message should be printed indicating no save file was found or created and that changes made while running the application will not be saved.

<br>
<div class="button-box">
  <a class="back-button" href="#appendix-instructions-for-manual-testing">Back to Appendix: Instructions for Manual Testing</a>
</div>
<hr>
