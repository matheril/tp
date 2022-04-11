---
title: User Guide
---

<p align="center"><img alt="logo" src="https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/pngLogo.png"></p>

# SplitLah
SplitLah is a desktop application with a Command Line Interface (CLI) for **splitting bills** amongst you and your friends after a group outing.
Each group outing can consist of several activities with **different people paying the bill** for each activity.
SplitLah does the work of **calculating how much each participant owes** and who they must pay at the end of the outing.


SplitLah also **consolidates all debts intelligently** so that they can be paid off with the fewest number of transactions 
possible, helping you **reduce time spent on making unnecessary transactions**. 
SplitLah remembers your information by saving your outings and activities so that you can **look up past activities** anytime you want.
If you can type fast, SplitLah can manage your outings faster than applications using a Graphical User Interface (GUI).

### Who SplitLah is for:
- People who go on group outings often and need to split bills.
- People who go on group outings where different people pay for different activities, making bill-splitting harder.
- People who dislike manually calculating how much everyone owes across several activities.
- People who want to manage outings with multiple social circles in one application.

<hr>

## Contents
* [How to use this user guide](#how-to-use-this-user-guide)
* [Quick Notes](#quick-notes)
* [Warnings](#warnings)
* [Quick Start](#quick-start)
* [How SplitLah works](#how-splitlah-works)
* [Features](#features)
  * [Session Management](#session-management)
  * [Activity Management](#activity-management)
  * [Transaction Management](#transaction-management)
  * [Group Management](#group-management)
  * [Miscellaneous](#miscellaneous)
    * [Listing all available commands: `help`](#listing-all-available-commands-help)
    * [Exiting the application: `exit`](#exiting-the-application-exit)
* [FAQ](#faq)
* [Command Summary](#command-summary)

<hr>

## How to use this user guide
- Each feature that SplitLah supports is known as a command.
- These commands are divided into five sections: [Session Management](#session-management), [Activity Management](#activity-management), 
[Transaction Management](#transaction-management), [Group Management](#group-management) and [Miscellaneous](#miscellaneous).
- Each command has its own section in this guide explaining them in greater detail.
- The format for each command is enclosed within a `code block`, so that they can be copied directly into SplitLah if needed.
  - Example: `help`
- In each command: 
  - The points indicated under 💡 are **important notes** to be mindful of when using the command.
  - The points indicated under ⚠️ are **warnings** that you should pay attention to when using the command.
- The button at the end of each section or subsection provides a hyperlink back to the table of contents that includes it. 

## Quick Notes
- Allowed characters for values:
  - Names of `Sessions`,`Activities` and `Groups` can have all printable [ASCII](https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html) characters.
  - Names of participants can only contain: `A-Z`, `a-z`
  - Decimals (up to 2 decimal places): `3.5`, `3.95`
  - Unique identifiers must be positive integers less than or equal to `2147483647`: `1`, `3`
  - Dates (DD-MM-YYYY or today): `02-04-2022`, `today`
- A forward slash `/` indicates a delimiter and is used to separate commands into parts.
  Each command's documentation specifies the required delimiters and their purpose.
  - Example: `/n`, `/sid`
  - Therefore, the forward slash `/` should **only** be used to indicate the delimiters,
    and **never** in the arguments.
- Parameters enclosed in `[ ]` must be supplied by the user.
  - Example: `[SESSION_ID]`
- Parameters with an ellipsis `...` indicate that the user can supply multiple values.
  - Example: `[COST1 COST2 ...]`
- Parameters enclosed within `[<` and `>]` indicates that the arguments are optional.
  - Example: `[</gst [GST_PERCENTAGE]>] [</sc [SERVICE_CHARGE]>]`
- Parameters enclosed within `{` and `}` indicates that at least one of the delimiters
  and their respective arguments have to be supplied.
  - Example: `{/pl [NAME1 NAME2 ...] /gid [GROUD_ID]}`

## Warnings
- If the save file cannot be created, **nothing will be saved** during the runtime of SplitLah.
- The save file is **not meant to be read or edited**.
  - If the save file is edited, the application will not be able to load the corrupted save file.
  - A corrupted save file will then be replaced with a new save file upon running SplitLah,
    **all previously saved information will be lost**.

## Quick Start

1. Ensure that you have Java 11 or above installed.
2. Download the latest .jar version of SplitLah from [here](https://github.com/AY2122S2-CS2113T-T10-1/tp/releases)
3. Copy the file to the folder you wish to use as a home folder for SplitLah.
4. Open a terminal and set the working directory to the home folder.
5. Start SplitLah by executing `java -jar SplitLah.jar` in the terminal.
6. Type in a command and press Enter to execute it.
7. Refer to [Features](#features) for a more in-depth explanation of all commands available.

## How SplitLah works
* An activity represents a single group activity, paid for by one person. It stores a list of its participants,
  the payer and how much each participant owes.
  * Example: `Lunch at a restaurant`
  * Consisting of the following participants:
    * `Alice`, `Bob`, `Charlie`
  * Paid for by:
    * `Alice`
* A session represents a group outing that that spans a period of time and stores activities,
  as well as a list of participants. Each activity can have a different payer.
  * Example: `Bob's birthday`
  * Consisting of the following participants:
    * `Alice`, `Bob`, `Charlie`
  * Consisting of the following activities:
    * `Breakfast at McDonald's`, `Lunch at a restaurant`, `Movie at a theatre`
    * Each paid for by a different person.
* At the end of a session, SplitLah calculates how much each person owes and who they need to pay. This information is
  displayed in an easy-to-read summary.

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>

<hr>

## Features 

## _Session Management_
> A session represents a group outing that involves a list of participants and 
> spans an arbitrary period of time containing activities.

### Overview

* [Creating a session: `session /create`](#creating-a-session-session-create)
* [Deleting a session: `session /delete`](#deleting-a-session-session-delete)
* [Editing a session: `session /edit`](#editing-a-session-session-edit)
* [Viewing a session: `session /view`](#viewing-a-session--session-view)
* [Listing all sessions: `session /list`](#listing-all-sessions-session-list)

<br>

<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>

<hr>

### Creating a session: `session /create`
Creates a session so that you can manage your group outings. <br>

> Format: `session /create /n [SESSION_NAME] /d [SESSION_DATE] {/pl [NAME1 NAME2 ...] /gid [GROUD_ID]}`
>* `[SESSION_NAME]` refers to the name of the session.
>   * The session name is **case-insensitive**.
>* `[SESSION_DATE]` refers to the date of the session.
>   * The format of the date must be in `DD-MM-YYYY` or `today`.
>* `[NAME1 NAME2 ...]` refers to a list of participants in the session.
>   * Each individual name is **case-insensitive**.
>* `[GROUP_ID]` refers to the unique identifier of a group.
>   * The unique identifier for a group can be retrieved with the [`group /list`](#listing-all-groups-group-list) command.
 
<br>

>  **💡 Notes:**
>* The `[SESSION_NAME]` should be unique across all existing sessions.
>* Each name in `[NAME1 NAME2 ...]` for a particular session should be unique.
>* The names in `[NAME1 NAME2 ...]` must only be a single word without whitespaces.
>   * Example: `Alice Tan` is not allowed.
> 
> **⚠️ Warning:**
>* If you include a name of an individual in `[NAME1 NAME2 ...]` who already exists in the group specified by
   `[GROUP_ID]`, only one instance of this individual is stored in the session.
>   * Example: Where the group specified by `/gid` consists of _Alice_ and _Bob_ and the arguments of `/pl` 
      includes _Alice_, only two names, _Alice_ and _Bob_, would be saved.

<br>

**Example 1** 
* Adds a new session named _Class Outing_ involving _Alice_ and _Bob_ on _15-03-2022_.<br><br>
  `session /create /n Class Outing /d 15-03-2022 /pl Alice Bob` <br><br>
  ![Session create command Screenshot 1](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/SessionCreateCommand[1].png)

**Example 2** 
* A [group has been created](#creating-a-group-group-create) with group named _Uni Friends_ with _Sally, Emily, David, Uriel, Natalie, Daniel, Nathan, Ethan_ and _Sam_. <br>
  Adds a new session named _Class Gathering_ consisting of a group named _Uni Friends_ and _Alice_, on _16-04-2022_.<br><br>
  `session /create /n Class Gathering /d 16-04-2022 /gid 1 /pl Alice` <br><br>
  ![Session create command Screenshot 2](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/SessionCreateCommand[2].png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#session-management">Back to Session Management</a>
</div>

<hr>

### Deleting a session: `session /delete`
Deletes an existing session so that you can remove sessions that you no longer need.<br>

>Format: `session /delete /sid [SESSION_ID]`
>* `[SESSION_ID]` refers to the unique identifier of the session.
>   * The unique identifier for a session can be retrieved with the [`session /list`](#listing-all-sessions-session-list) command.

<br>

> **💡 Note:**
>* A session with a unique identifier of `[SESSION_ID]` has to exist before it can be removed.
> 
> **⚠️ Warning:**
>* This action is irreversible. The session is deleted immediately after entering this command.
>* Additionally, all activities in the deleted session are deleted immediately as well.

<br>

**Example** 
* Removes an existing session with a unique identifier of _2_.<br><br>
  `session /delete /sid 2` <br><br>
  ![Session delete command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/SessionDeleteCommand.png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#session-management">Back to Session Management</a>
</div>

<hr>

### Editing a session: `session /edit`
Edits an existing session so that you can change the details of a session.<br>

You only have to supply delimiters for the details you wish to edit. However, the `/sid`
delimiter is compulsory to identify the session you wish to edit.

>Format: `session /edit /sid [SESSION_ID] {/n [SESSION_NAME] /d [SESSION_DATE] /pl [NAME1 NAME2...]}`
>* `[SESSION_ID]` refers to the unique identifier of the session.
>   * The unique identifier for a session can be retrieved with the [`session /list`](#listing-all-sessions-session-list) command.
>* `[SESSION_NAME]` refers to the name of the session.
>   * The session name is **case-insensitive**.
>* `[SESSION_DATE]` refers to the date of the session.
>   * The format of the date must be in `DD-MM-YYYY` or `today`.
>* `[NAME1 NAME2 ...]` refers to a list of participants in the session.
>   * Each individual name is **case-insensitive**.

<br>

> **💡 Notes:**
>* A session with a unique identifier of `[SESSION_ID]` has to exist before it can be edited.
> The `[SESSION_NAME]` should be unique across all existing sessions.
>* Each name in `[NAME1 NAME2 ...]` for a particular session should be unique.
>* The names in `[NAME1 NAME2 ...]` must only be a single word without whitespaces.
>   * Example: `Alice Tan` is not allowed.
>* There are 3 editable fields: _session name_, _session date_ and the _list of participants_ in the session.
>   * At least 1 field has to be edited for the command to run.
>   * More than 1 field can be edited in a single run of the command.
>* When editing the _list of participants_, existing participants, including those part of the group declared when
>  creating the session, must be included.
>   * Activities may have been created using participants in the list, hence the _list of participants_ can only expand
>     and not shrink. This is to prevent introducing any inconsistencies to activities in the session.
>   * Example 1: If the session with session unique identifier of _1_ has been created with _Alice_ and _Bob_ 
>     and you wish to edit it to include _Charlie_, a valid edit command would be:<br>
>     `session /edit /sid 1 /pl Alice Bob Charlie`.
>   * Example 2: If the session with session unique identifier of _1_ has been created with
>     _Alice_ and a group consisting of _Bob_ and _Charlie_, and you wish to edit it to include _Mike_,
>     a valid edit command would be: <br>
>     `session /edit /sid 1 /pl Alice Bob Charlie Mike`.
> 
> **⚠️ Warning:**
>* This action is irreversible. The session is edited immediately after entering this command.

<br>

**Examples of usage:**
* A [session has been created](#creating-a-session-session-create) with a unique identifier of _1_,
  named _Class Outing_ with _Alice_ and _Bob_ involved on _15-03-2022_.

**Example 1** 
* Edits the name of the session to _Class gathering_ and the date to _16-03-2022_. <br><br>
  `session /edit /sid 1 /n Class gathering /d 16-03-2022` <br><br>
  ![Session Edit command Screenshot 1](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/SessionEditCommand[1].png)

**Example 2** 
* Edits the session to include _Charlie_. <br><br>
  `session /edit /sid 1 /pl Alice Bob Charlie` <br><br>
  ![Session Edit command Screenshot 2](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/SessionEditCommand[2].png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#session-management">Back to Session Management</a>
</div>

<hr>

### Viewing a session : `session /view`
Displays the details of an existing session so that you can review it.<br>

>Format: `session /view /sid [SESSION_ID]`
><br>
>* `[SESSION_ID]` refers to the unique identifier of the session.
>    * The unique identifier for a session can be retrieved with the [`session /list`](#listing-all-sessions-session-list) command.

<br>

> **💡 Note:**
>* The session with a unique identifier of `[SESSION_ID]` has to exist before the session can be viewed.

<br>

**Example**:
* Views an existing session with a unique identifier of _1_.<br><br>
  `session /view /sid 1` <br><br>
  ![Session View command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/SessionViewCommand.png)
  <br>     
  <br>

<div class="button-box">
  <a class="back-button" href="#session-management">Back to Session Management</a>
</div>

<hr>

### Listing all sessions: `session /list`
Displays all existing sessions so that you can have an overview of previously created sessions. 
However, deleted sessions are not listed.

> Format: `session /list`

<br>

**Example**
* Lists all existing sessions. <br><br>
  `session /list`<br><br>
  ![Session list command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/SessionListCommand.png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#session-management">Back to Session Management</a>
</div>

<hr>

## _Activity Management_
> An activity represents a single group activity and stores its name, costs and the name of the payer.

### Overview
 * [Creating an activity: `activity /create`](#creating-an-activity-activity-create)
 * [Deleting an activity: `activity /delete`](#deleting-an-activity-activity-delete)
 * [Editing an activity: `activity /edit`](#editing-an-activity-activity-edit)
 * [Viewing an activity: `activity /view`](#viewing-an-activity-activity-view)
 * [Listing all activities in a session: `activity /list`](#listing-all-activities-in-a-session-activity-list)

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>

<hr>

### Creating an activity: `activity /create`
Creates an activity with the details that you specify and then assigns it to a session. <br>

There are 2 ways that you can specify the costs of an activity:
1. Record the total cost to be split amongst everyone involved.
2. Record each person's individual cost.

> Format 1: `activity /create /sid [SESSION_ID] /n [ACTIVITY_NAME] /p [PAYER] /i [NAME1 NAME2 ...]
/co [TOTAL_COST] [</gst [GST_PERCENTAGE]>] [</sc [SERVICE_CHARGE]>]`
> 
> Format 2: `activity /create /sid [SESSION_ID] /n [ACTIVITY_NAME] /p [PAYER] /i [NAME1 NAME2 ...]
/cl [COST1 COST2 ...] [</gst [GST_PERCENTAGE]>] [</sc [SERVICE_CHARGE]>]`
>
>* `[SESSION_ID]` refers to the unique identifier of the session.
>    * The unique identifier for a session can be retrieved with the [`session /list`](#listing-all-sessions-session-list) command.
>* `[ACTIVITY_NAME]` refers to the name of the activity.
>    * The activity name is **case-insensitive**.
>* `[PAYER]` refers to the person who paid for the activity.
>    * The person's name is **case-insensitive**.
>* `[NAME1 NAME2 ...]` refers to a list of participants in the activity.
>    * Each individual name is **case-insensitive**.
>* `[TOTAL_COST]` refers to the total cost of the activity.
>* `[COST1 COST2 ...]` refers to a list of costs respective to each person involved in the activity.
>    * Example: `/i Alice Bob /cl 10 20` means that _Alice's_ portion cost _$10_ while _Bob's_ portion cost _$20_.
>* `[GST_PERCENTAGE]` refers to the additional percentage GST that may be charged during the activity.
>* `[SERVICE_CHARGE]` refers to the additional percentage service charge that may be charged during the activity.

<br>

> **💡 Notes:**
>* A session with a unique identifier of `[SESSION_ID]` has to exist before an activity can be created and assigned to 
>  it.
>* Each name in `[NAME1 NAME2 ...]` for the activity should be unique.
>* The names in `[PAYER]` and `[NAME1 NAME2 ...]` must also be associated with the session referenced by
>  `[SESSION_ID]`.
>* The names in `[PAYER]` and `[NAME1 NAME2 ...]` must only be a single word without whitespaces.
>   * Example: `Alice Tan` is not allowed.
>* The values in `[TOTAL_COST]` and `[COST1 COST2 ...]` are decimal values with a maximum of 12 digits before
>  and 2 digits after the decimal point, if any.
>* The values in `[GST_PERCENTAGE]` and `[SERVICE_CHARGE]` are decimal values that range from 0 to 100,
>  with a maximum of 3 digits before and 2 after the decimal point, if any.
>* If the payer is also involved in the activity, the payer's name has to be included in the list of participants
>   * Example: _Alice_ paid for a movie which she watched with _Bob_ in a session with a session unique identifier of _1_,
>    costing a total of _$20_. The correct command format is: <br>
>    `activity /create /sid 1 /n movie /p Alice /i Alice Bob /co 20`
>* All values displayed are rounded off to 2 decimal places. This may result in slight inaccuracies.

<br>

**Examples of usage:**
* A [session has been created](#creating-a-session-session-create) with a unique identifier of _1_,
  named _Class Outing_ with _Alice_ and _Bob_ involved on _15-03-2022_.

**Example 1**
* Adds a new activity to a session with a session unique identifier of _1_ named _Class Lunch_. Alice paid a total of _$10_
  for both Bob and herself which is split equally amongst them.<br><br>
  `activity /create /sid 1 /n Class Lunch /p Alice /i Alice Bob /co 10` <br><br>
  ![Activity create command [1] Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/ActivityCreateCommand[1].png)

**Example 2**
* Adds a new activity to a session with a session unique identifier of _1_ named _High Tea_. Alice paid for both
  Bob and herself. Alice's meal cost _$20.50_ while Bob's meal cost _$13.50_.<br><br>
  `activity /create /sid 1 /n High Tea /p Alice /i Alice Bob /cl 20.50 13.50` <br><br>
  ![Activity create command [2] Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/ActivityCreateCommand[2].png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#activity-management">Back to Activity Management</a>
</div>

<hr>

### Deleting an activity: `activity /delete`
Deletes an existing activity from a particular session so that you can remove activities that you no longer need.<br>

>Format: `activity /delete /sid [SESSION_ID] /aid [ACTIVITY_ID]`
>
>* `[SESSION_ID]` refers to the unique identifier of the session.
>    * The unique identifier for a session can be retrieved with the [`session /list`](#listing-all-sessions-session-list) command.
>* `[ACTIVITY_ID]` refers to the unique identifier of the activity.
>    * The unique identifier for an activity can be retrieved with the [`activity /list`](#listing-all-activities-in-a-session-activity-list) command.

<br>

> **💡 Notes:**
>- A session with a unique identifier of `[SESSION_ID]` has to exist before an activity in that session can be removed.
>- An activity with a unique identifier of `[ACTIVITY_ID]` has to exist before it can be removed.
>
> **⚠️ Warning:**
>- This action is irreversible. The activity is deleted immediately after entering this command.

<br>

**Example**:
- Removes an existing activity with a unique identifier of _2_ from a session with a unique identifier of _1_. <br><br>
  `activity /delete /sid 1 /aid 2` <br><br>
  ![Activity delete command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/ActivityDeleteCommand.png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#activity-management">Back to Activity Management</a>
</div>

<hr>

### Editing an activity: `activity /edit`
Edits an existing activity so that you can change the details of an activity.<br>

You only have to supply delimiters for the details you wish to edit. However, the `/sid` and `/aid` 
delimiters are compulsory to identify the activity you wish to edit.
> Format 1: `activity /edit /sid [SESSION_ID] /aid [ACTIVITY_ID] {/n [ACTIVITY_NAME] /p [PAYER]
  /i [NAME1 NAME2...] /co [TOTAL_COST] /gst [GST_PERCENTAGE] /sc [SERVICE_CHARGE]}`
> 
> Format 2: `activity /edit /sid [SESSION_ID] /aid [ACTIVITY_ID] {/n [ACTIVITY_NAME] /p [PAYER]
  /i [NAME1 NAME2...] /cl [COST1 COST2...] /gst [GST_PERCENTAGE] /sc [SERVICE_CHARGE]}`
> 
>* `[SESSION_ID]` refers to the unique identifier of the session.
>    * The unique identifier for a session can be retrieved with the [`session /list`](#listing-all-sessions-session-list) command.
>* `[ACTIVITY_ID]` refers to the unique identifier of the activity.
>    * The unique identifier for an activity can be retrieved with the [`activity /list`](#listing-all-activities-in-a-session-activity-list) command.
>* `[ACTIVITY_NAME]` refers to the name of the activity.
>     * The activity name is **case-insensitive**.
>     * If one is not provided, the original activity name is left unchanged.
>* `[PAYER]` refers to the person who paid for the activity.
>    * The person's name is **case-insensitive**.
>    * If one is not provided, the original payer is left unchanged.
>* `[NAME1 NAME2 ...]` refers to a list of participants in the activity.
>* Each individual name is **case-insensitive**.
>    * If a list is not provided, the original participants are left unchanged.
>    * You **must** supply a costlist or overall cost if you wish to edit the list of participants.
>* `[TOTAL_COST]` refers to the overall cost of the activity.
>    * If one is not provided, the original overall cost is left unchanged.
>      You cannot supply both a total cost and a cost list.
>    * You can use this even if the activity was originally created using a cost list. The new overall cost will be evenly 
distributed amongst all participants.
>* `[COST1 COST2 ...]` refers to a list of costs respective to each person involved in the activity.
>    * If a list is not provided, the original overall cost list is left unchanged. 
>      You cannot supply both a total cost and a cost list.
>    * You can use this even if the activity was originally created using an overall cost. The new cost list will be assigned 
>to the existing participants in the order displayed when viewing the activity.
>    * You are encouraged to additionally provide a participant list with `/i` to confirm the distribution of costs.
>* `[GST_PERCENTAGE]` refers to the additional GST that may be charged during the activity.
>    * If one is not provided, the original GST is left unchanged and will be applied to any changes to the overall cost or
>cost list.
>    * Changing the GST percentage will automatically recalculate all costs associated with this activity to reflect the new
>GST value.
>    * To remove the GST entirely, you must explicitly specify `/gst 0`.
>* `[SERVICE_CHARGE]` refers to the additional service charge that may be charged during the activity.
>    * If one is not provided, the original service charge is left unchanged and will be applied to any changes to the
>overall cost or cost list.
>    * Changing the service charge will automatically recalculate all costs associated with this activity to reflect the new
>service charge.
>    * To remove the service charge entirely, you must explicitly specify `/sc 0`.

<br>

> **💡 Notes:**
>- An activity with a unique identifier of `[ACTIVITY_ID]` has to exist before it can be edited.
>- A session with a unique identifier of `[SESSION_ID]` has to exist before activities in it can be edited.
>- Each name in `[NAME1 NAME2 ...]` for the activity should be unique.
>- The names in `[PERSON_PAID]` and `[NAME1 NAME2 ...]` must also be associated with the session referenced by
   `[SESSION_ID]`.
>* The names in `[PAYER]` and `[NAME1 NAME2 ...]` must only be a single word without whitespaces.
>   - Example: `Alice Tan` is not allowed.
>* The values in `[TOTAL_COST]` and `[COST1 COST2 ...]` are decimal values with a maximum of 12 digits before
   and 2 digits after the decimal point, if any.
>* The values in `[GST_PERCENTAGE]` and `[SERVICE_CHARGE]` are decimal values that can only range from 0 to 100,
   with a maximum of 3 digits before and 2 after the decimal point, if any.
>* All values displayed are rounded off to 2 decimal places. This may result in slight inaccuracies.
>
> **⚠️ Warning:**
>* This action is irreversible. The activity is edited immediately after entering this command.

<br>

**Example**
* Edits the name, payer, overall cost, GST percentage and service charge of an [activity that has been created](#creating-an-activity-activity-create)
  with an activity unique identifier of _1_ in a session with a session unique identifier of _1_, leaving the list of
  participants unchanged. The activity is currently named _Class Lunch_ and involves _Alice_ and _Bob_.<br><br>
  `activity /edit /sid 1 /aid 1 /n Dinner /p Bob /i Alice Bob /co 30 /gst 7 /sc 10`<br><br>
  ![Activity edit command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/ActivityEditCommand.png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#activity-management">Back to Activity Management</a>
</div>

<hr>

### Viewing an activity: `activity /view`
Displays the details about an existing activity so that you can review it.<br>

> Format: `activity /view /sid [SESSION_ID] /aid [ACTIVITY_ID]`
>
>* `[SESSION_ID]` refers to the unique identifier of the session.
>    * The unique identifier for a session can be retrieved with the [`session /list`](#listing-all-sessions-session-list) command.
>* `[ACTIVITY_ID]` refers to the unique identifier of the activity. 
>    * The unique identifier for an activity can be retrieved with the [`activity /list`](#listing-all-activities-in-a-session-activity-list) command.

<br>

> **💡 Notes:**
>- A session with a unique identifier of `[SESSION_ID]` has to exist before an activity in that session can be viewed.
>- An activity with a unique identifier of `[ACTIVITY_ID]` has to exist before it can be viewed.
>- All values displayed are rounded off to 2 decimal places. This may result in slight inaccuracies.

<br>

**Example**
- Views an existing activity with a unique identifier of _1_ in a session with a session unique identifier of _1_.<br><br>
  `activity /view /sid 1 /aid 1` <br><br>
  ![Activity view command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/ActivityViewCommand.png)
  <br>     
  <br>

<div class="button-box">
  <a class="back-button" href="#activity-management">Back to Activity Management</a>
</div>

<hr>

### Listing all activities in a session: `activity /list`
Displays all existing activities so that you can have an overview of previously created activities.
However, deleted activities are not listed.

>Format: `activity /list /sid [SESSION_ID]`
>
>* `[SESSION_ID]` refers to the unique identifier of the session.
>    * The unique identifier for a session can be retrieved with the [`session /list`](#listing-all-sessions-session-list) command.

<br>

> **💡 Note:**
>- A session with a unique identifier of `[SESSION_ID]` has to exist before its activities can be listed.

<br>

**Example**:
- Lists all activities in a session with a unique identifier of _1_.<br><br>
  `activity /list /sid 1` <br><br>
  ![Activity list command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/ActivityListCommand.png)
  <br>     
  <br>

<div class="button-box">
  <a class="back-button" href="#activity-management">Back to Activity Management</a>
</div>

<hr>

## _Transaction Management_

### Settling all transactions for a session: `session /summary`

Displays a transaction summary for a session. The transaction summary helps you calculate how much each person in the session
must pay and to whom they should pay for all debts to be resolved.<br>

> Format: `session /summary /sid [SESSION_ID]`
> * `[SESSION_ID]` refers to the unique identifier of the session.
>    * The unique identifier for a session can be retrieved with the [`session /list`](#listing-all-sessions-session-list) command.

<br>

> **💡 Notes:**
>* A session with a unique identifier of `[SESSION_ID]` has to exist before its summary can be generated.
>* All values displayed are rounded off to 2 decimal places. This may result in slight inaccuracies.
>* SplitLah simplifies transactions to minimise the number of transactions you have to make.
>   * Example: In the same session, _Alice_ paid for _Bob_'s lunch which costs _$10_ and _Bob_ paid for _Charlie_'s
>     movie ticket which costs _$10_. 
>   * Instead of having _Bob_ pay _Alice_ _$10_ and _Charlie_ pay _Bob_ _$10_, SplitLah simplifies this
>     so _Charlie_ just has to pay _$10_ directly to _Alice_, settling all debts in a single transaction.

<br>

**Example**
* Displays a session summary that summarises the [session that has been created](#creating-a-session-session-create)
  with session unique identifier of _1_ to help _Alice_ and _Bob_ calculate what transactions they have to make to
  resolve all their debts to each other.<br><br>
  `session /summary /sid 1`<br><br>
  ![Session summary command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/SessionSummaryCommand.png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>

<hr>

## _Group Management_
> A group represents one or more individuals. The sole purpose of a group is to quickly identify a group of individuals
> without having to manually enter their details one by one when creating a session.

### Overview
* [Creating a group: `group /create`](#creating-a-group-group-create)
* [Deleting a group: `group /delete`](#deleting-a-group-group-delete)
* [Editing a group: `group /edit`](#editing-a-group-group-edit)
* [Viewing a group: `group /view`](#viewing-a-group-group-view)
* [Listing all groups: `group /list`](#listing-all-groups-group-list)

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>

<hr>

### Creating a group: `group /create`
Creates a new group consisting of a group of individuals. You can use this group whenever you are creating a new session
that involves the same group of individuals. This saves you time when entering the list of participants for the session.

> Format : `group /create /n [GROUP_NAME] /pl [NAME1 NAME2 ...]`
>
>* `[GROUP_NAME]` refers to the name of the group.
>    * The group name is **case-insensitive**.
>* `[NAME1 NAME2 ...]` refers to a list of individuals in the group.
>    * Each individual name is **case-insensitive**.

<br>

> **💡 Notes:**
>* The `[GROUP_NAME]` should be unique across all existing groups.
>* Each name in `[NAME1 NAME2 ...]` for a particular group should be unique.
>* The names in `[NAME1 NAME2 ...]` must only be a single word without whitespaces.
>   * Example: `Alice Tan` is not allowed.

<br>

**Example**:
- Adds a new group named _Uni Friends_, consisting of _Sally, Emily, David, Uriel, Natalie, Daniel, Nathan, Ethan_ and _Sam_.<br><br>
  `group /create /n Uni Friends /pl Sally Emily David Uriel Natalie Daniel Nathan Ethan Sam` <br><br>
  ![Group create command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/GroupCreateCommand.png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#group-management">Back to Group Management</a>
</div>

<hr>

### Deleting a group: `group /delete`
Deletes an existing group so that you can remove groups that you no longer need.

> Format: `group /delete /gid [GROUP_ID]`
>
>* `[GROUP_ID]` refers to the unique identifier of the group.
>    * The unique identifier for a group can be retrieved with the [`group /list`](#listing-all-groups-group-list) command.

<br>

> **💡 Note:**
>- A group with a unique identifier of `[GROUP_ID]` has to exist before it can be removed.
>
> **⚠️ Warning:**
> - This action is irreversible. The group is deleted immediately after entering this command.

<br>

**Example**:
- Removes an existing group with a unique identifier of _1_.<br><br>
  `group /delete /gid 1` <br><br>
  ![Group delete command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/GroupDeleteCommand.png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#group-management">Back to Group Management</a>
</div>

<hr>

### Editing a group: `group /edit`
Edits an existing group so that you can change the details of a group.<br>

You only have to supply delimiters for the details you wish to edit. However, the `/gid`
delimiter is compulsory to identify the group you wish to edit.

>Format: `group /edit /gid [GROUP_ID] {/n [GROUP_NAME] /pl [NAME1 NAME2...]}`
>* `[GROUP_ID]` refers to the unique identifier of the group.
>   * The unique identifier for a group can be retrieved with the [`group /list`](#listing-all-groups-group-list) command.
>* `[GROUP_NAME]` refers to the name of the group.
>   * The group name is **case-insensitive**.
>* `[NAME1 NAME2 ...]` refers to a list of individuals in the group.
>   * Each individual name is **case-insensitive**.

<br>

> **💡 Notes:**
>- A group with a unique identifier of `[GROUP_ID]` has to exist before it can be edited.
>- The `[GROUP_NAME]` should be unique across all existing groups.
>- Each name in `[NAME1 NAME2 ...]` for a particular group should be unique.
>- There are 2 editable fields: _group name_ and the _list of individuals_ in the group.
>   - At least 1 field has to be edited for the command to run.
>   - More than 1 field can be edited in a single run of the command.
>- Editing groups **does not** affect sessions.
>   - If a session has been created with the group that you are editing,
>     editing the group **does not modify** the _list of participants_ in the session.
>
> **⚠️ Warning:**
> - This action is irreversible. The group is edited immediately after entering this command.

<br>

**Examples of usage:**
* A [group has been created](#creating-a-group-group-create) with a unique identifier of _2_,
  named _School Friends_ consisting of _Alice_, _Bob_, _Charlie_ and _David_.

**Example 1**
- Edits the group name to _Project Teammates_. <br><br>
  `group /edit /gid 2 /n Project Teammates` <br><br>
  ![Group Edit command Screenshot 1](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/GroupEditCommand[1].png)

**Example 2**
- Edits the group to exclude _David_. <br><br>
  `group /edit /gid 2 /pl Alice Bob Charlie` <br><br>
  ![Group Edit command Screenshot 2](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/GroupEditCommand[2].png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#group-management">Back to Group Management</a>
</div>

<hr>

### Viewing a group: `group /view`
Displays the details about an existing group so that you can review it.<br>

>Format: `group /view /gid [GROUP_ID]`
>
>* `[GROUP_ID]` refers to the unique identifier of the group.
>    * The unique identifier for a group can be retrieved with the [`group /list`](#listing-all-groups-group-list) command.

<br>

> **💡 Note:**
>- The group with a unique identifier of `[GROUP_ID]` has to exist before the group can be viewed.

<br>

**Example**:
- Views an existing group with a unique identifier of _2_.<br><br>
  `group /view /gid 2` <br><br>
  ![Group view command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/GroupViewCommand.png)
  <br>     
  <br>

<div class="button-box">
  <a class="back-button" href="#group-management">Back to Group Management</a>
</div>

<hr>

### Listing all groups: `group /list`
Displays all existing groups so that you can have an overview of previously created groups.
However, deleted groups are not listed.

>Format: `group /list`

<br>

**Example**:
- Lists all existing groups.<br><br>
  `group /list`<br><br>
  ![Group list command Screenshot](https://raw.githubusercontent.com/AY2122s2-cs2113t-t10-1/tp/master/docs/images/userguide/GroupListCommand.png)
  <br>
  <br>

<div class="button-box">
  <a class="back-button" href="#group-management">Back to Group Management</a>
</div>

<hr>

## _Miscellaneous_

### Listing all available commands: `help`
Displays all available SplitLah commands and their syntax to help you use SplitLah.<br>
You can also refer to the [Command Summary](#command-summary) for a summary of all available commands in SplitLah.

> Format: `help`

<br>

### Exiting the application: `exit`
Quits the application.<br>

> Format: `exit`

<br>

<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>

<hr>
 
## FAQ

**Q**: Is data saved to the disk upon exit?

**A**: SplitLah 2.0 creates a save file in a folder named `data` that is created in the same directory as
`SplitLah.jar`. Changes are saved whenever a `create`, `delete` or `edit` command is run.

**Q**: How do session, activity and group unique identifiers work?

**A**: Unique identifiers uniquely identify each session, activity and group separately. Once a unique identifier
has been allocated, it will not be changed nor reused.

**Q**: Is there any way I can mark a transaction that is shown using the `session /summary` command as paid?

**A**: As of now, this functionality is not supported by SplitLah. All functionalities that SplitLah supports are documented in this user guide.
You can find a summary of all available commands in the [Command Summary](#command-summary) below.
Stay tuned to future updates from us!
<br>

<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>

<hr>

## Command Summary

| Action                                           | Format                                                                                                                                                                                                                                                                              |
|--------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Create a new session                             | Format: `session /create /n [SESSION_NAME] /d [SESSION_DATE] {/pl [NAME1 NAME2 ...] /gid [GROUP_ID]}`<br><br> Example: `session /create /n Outing /d 15-03-2022 /pl Alice Bob Charlie`                                                                                              |
| Delete an existing session                       | Format: `session /delete /sid [SESSION_ID]`<br><br>Example: `session /delete /sid 1`                                                                                                                                                                                                |
| Edit an existing session                         | Format: `session /edit /sid [SESSION_ID] {/n [SESSION_NAME] /d [SESSION_DATE] /pl [NAME1 NAME2...]}`<br><br> Example: `session /edit /sid 1 /n Class gathering /d 16-03-2022 /pl Alice Bob Charlie`                                                                                 |
| View an existing session                         | Format: `session /view /sid [SESSION_ID]`<br><br>Example: `session /view /sid 1`                                                                                                                                                                                                    |
| List all sessions                                | Format: `session /list`                                                                                                                                                                                                                                                             |
| Create activity (split costs evenly)             | Format: `activity /create /sid [SESSION_ID] /n [ACTIVITY_NAME] /p [PAYER] /i [NAME1 NAME2...] /co [TOTAL_COST] [</gst [GST_PERCENTAGE]>] [</sc [SERVICE_CHARGE]>]`<br><br>Example: `activity /create /sid 1 /n Lunch /p Alice /i Alice Bob Charlie /co 7.5`                         |
| Create activity (split costs manually)           | Format: `activity /create /sid [SESSION_ID] /n [ACTIVITY_NAME] /p [PAYER] /i [NAME1 NAME2...] /cl [COST1 COST2...] [</gst [GST_PERCENTAGE]>] [</sc [SERVICE_CHARGE]>]`<br><br>Example: `activity /create /sid 1 /n Lunch /p Alice /i Alice Bob Charlie /cl 1 1 5.5`                 |
| Delete an existing activity                      | Format: `activity /delete /sid [SESSION_ID] /aid [ACTIVITY_ID]`<br><br>Example: `activity /delete /sid 2 /aid 1`                                                                                                                                                                    |
| Edit an existing activity (split costs evenly)   | Format: `activity /edit /sid [SESSION_ID] /aid [ACTIVITY_ID] {/n [ACTIVITY_NAME] /p [PAYER] /i [NAME1 NAME2...] /co [TOTAL_COST] /gst [GST_PERCENTAGE] /sc [SERVICE_CHARGE]}`<br><br>Example: `activity /edit /sid 1 /aid 1 /n Lunch /p Alice /i Alice Bob Charlie /co 7.5`         |
| Edit an existing activity (split costs manually) | Format: `activity /edit /sid [SESSION_ID] /aid [ACTIVITY_ID] {/n [ACTIVITY_NAME] /p [PAYER] /i [NAME1 NAME2...] /cl [COST1 COST2...] /gst [GST_PERCENTAGE] /sc [SERVICE_CHARGE]}`<br><br>Example: `activity /edit /sid 1 /aid 1 /n Lunch /p Alice /i Alice Bob Charlie /cl 1 1 5.5` |
| View an existing activity                        | Format: `activity /view /sid [SESSION_ID] /aid [ACTIVITY_ID]` <br><br>Example: `activity /view /sid 1 /aid 1`                                                                                                                                                                       |
| List all activities                              | Format: `activity /list /sid [SESSION_ID]` <br><br>Example: `activity /list /sid 1`                                                                                                                                                                                                 |
| Show session summary                             | Format: `session /summary /sid [SESSION_ID]`<br><br>Example: `session /summary /sid 1`                                                                                                                                                                                              |
| Create a new group                               | Format: `group /create /n [GROUP_NAME] /pl [NAME1 NAME2 ...]`<br><br>Example: `group /create /n SplitLah /pl Roy Ivan Warren Saurav Tianle`                                                                                                                                         |
| Delete an existing group                         | Format: `group /delete /gid [GROUP_ID]`<br><br>Example: `group /delete /gid 1`                                                                                                                                                                                                      |
| Edit an existing group                           | Format: `group /edit /gid [GROUP_ID] {/n [GROUP_NAME] /pl [NAME1 NAME2...]}`<br><br>Example: `group /edit /gid 1 /n Class gathering`                                                                                                                                                |
| View an existing group                           | Format: `group /view /gid [GROUP_ID]`<br><br>Example: `group /view /gid 1`                                                                                                                                                                                                          |
| List all groups                                  | Format: `group /list`                                                                                                                                                                                                                                                               |
| List all available commands                      | Format: `help`                                                                                                                                                                                                                                                                      |
| Exit                                             | Format: `exit`                                                                                                                                                                                                                                                                      |

<br>
<div class="button-box">
  <a class="back-button" href="#contents">Back to Contents</a>
</div>
