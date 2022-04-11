package seedu.splitlah.ui;

import seedu.splitlah.parser.commandparser.ActivityCreateCommandParser;
import seedu.splitlah.parser.commandparser.ActivityDeleteCommandParser;
import seedu.splitlah.parser.commandparser.ActivityEditCommandParser;
import seedu.splitlah.parser.commandparser.ActivityListCommandParser;
import seedu.splitlah.parser.commandparser.ActivityViewCommandParser;
import seedu.splitlah.parser.commandparser.ExitCommandParser;
import seedu.splitlah.parser.commandparser.GroupCreateCommandParser;
import seedu.splitlah.parser.commandparser.GroupDeleteCommandParser;
import seedu.splitlah.parser.commandparser.GroupEditCommandParser;
import seedu.splitlah.parser.commandparser.GroupListCommandParser;
import seedu.splitlah.parser.commandparser.GroupViewCommandParser;
import seedu.splitlah.parser.commandparser.HelpCommandParser;
import seedu.splitlah.parser.commandparser.SessionCreateCommandParser;
import seedu.splitlah.parser.commandparser.SessionDeleteCommandParser;
import seedu.splitlah.parser.commandparser.SessionEditCommandParser;
import seedu.splitlah.parser.commandparser.SessionListCommandParser;
import seedu.splitlah.parser.commandparser.SessionSummaryCommandParser;
import seedu.splitlah.parser.commandparser.SessionViewCommandParser;

/**
 * Container of hardcoded messages.
 * Naming convention: [TYPE]_[CLASS]_[MESSAGE].
 *
 * @author Saurav
 */
public abstract class Message {

    // SplitLah
    public static final String LOGGER_SPLITLAH_APPLICATION_EXIT =
            "The application is terminated";

    // TextUI
    public static final String ASCII_TEXTUI_LOGO =
            " $$$$$$\\            $$\\ $$\\   $$\\     $$\\           $$\\\n"
            + "$$  $$\\           $$ |\\|  $$ |    $$ |          $$ |\n"
            + "$$ /  \\__| $$$$$$\\  $$ |$$\\ $$$$$$\\   $$ | $$$$$$\\  $$$$$$$\\\n"
            + "\\$$$$$$\\  $$  $$\\ $$ |$$ |\\_$$  _|  $$ | \\____$$\\ $$  __$$\\\n"
            + " \\____$$\\ $$ /  $$ |$$ |$$ |  $$ |    $$ | $$$$$$$ |$$ |  $$ |\n"
            + "$$\\   $$ |$$ |  $$ |$$ |$$ |  $$ |$$\\ $$ |$$  __$$ |$$ |  $$ |\n"
            + "\\$$$$$$  |$$$$$$$  |$$ |$$ |  \\$$$$  |$$ |\\$$$$$$$ |$$ |  $$ |\n"
            + " \\______/ $$  ____/ \\|\\__|   \\____/ \\__| \\_______|\\__|  \\__|\n"
            + "          $$ |\n"
            + "          $$ |\n"
            + "          \\__|";
    public static final String MESSAGE_TEXTUI_HELP_MENU =
            "HELP MENU --\n"
                    + "1.  Create a new session\n"
                    + "        " + SessionCreateCommandParser.COMMAND_FORMAT + "\n"
                    + "2.  Delete an existing session\n"
                    + "        " + SessionDeleteCommandParser.COMMAND_FORMAT + "\n"
                    + "3.  Edit an existing session\n"
                    + "        " + SessionEditCommandParser.COMMAND_FORMAT + "\n"
                    + "4.  View an existing session's details\n"
                    + "        " + SessionViewCommandParser.COMMAND_FORMAT + "\n"
                    + "5.  List all existing sessions\n"
                    + "        " + SessionListCommandParser.COMMAND_FORMAT + "\n"
                    + "6.  Create a new activity\n"
                    + "        Syntax: " + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n"
                    + "                " + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND + "\n"
                    + "7.  Delete an existing activity\n"
                    + "        " + ActivityDeleteCommandParser.COMMAND_FORMAT + "\n"
                    + "8.  Edit an existing activity\n"
                    + "        Syntax: " + ActivityEditCommandParser.COMMAND_FORMAT_FIRST + "\n"
                    + "                " + ActivityEditCommandParser.COMMAND_FORMAT_SECOND + "\n"
                    + "9.  View an existing activity's details\n"
                    + "        " + ActivityViewCommandParser.COMMAND_FORMAT + "\n"
                    + "10. List all existing activities\n"
                    + "        " + ActivityListCommandParser.COMMAND_FORMAT + "\n"
                    + "11. Show the summary of an existing session\n"
                    + "        " + SessionSummaryCommandParser.COMMAND_FORMAT + "\n"
                    + "12. Create a new group\n"
                    + "        " + GroupCreateCommandParser.COMMAND_FORMAT + "\n"
                    + "13. Delete an existing group\n"
                    + "        " + GroupDeleteCommandParser.COMMAND_FORMAT + "\n"
                    + "14. Edit an existing group\n"
                    + "         " + GroupEditCommandParser.COMMAND_FORMAT + "\n"
                    + "15. View an existing group's details\n"
                    + "        " + GroupViewCommandParser.COMMAND_FORMAT + "\n"
                    + "16. List all existing groups\n"
                    + "        " + GroupListCommandParser.COMMAND_FORMAT + "\n"
                    + "17. Show this help menu\n"
                    + "        " + HelpCommandParser.COMMAND_FORMAT + "\n"
                    + "18. Exit the program\n"
                    + "        " + ExitCommandParser.COMMAND_FORMAT;
    public static final String PROMPT_TEXTUI_AWAITING_INPUT =
            "> ";

    // Parser
    public static final String ERROR_PARSER_DELIMITER_NOT_FOUND =
            "Please include the following delimiter in your input: ";
    public static final String ERROR_PARSER_MISSING_ARGUMENT =
            "Please include an argument after the following delimiter: ";
    public static final String ERROR_PARSER_NON_ASCII_ARGUMENT =
            "Only ASCII inputs are accepted by SplitLah.";
    public static final String ERROR_PARSER_MORE_THAN_ONE_PAYER =
            "The activity should only have a single payer. Please rectify and try again.";
    public static final String ERROR_PARSER_NON_INTEGER_ARGUMENT =
            "Please enter a valid integer after the following delimiter: ";
    public static final String ERROR_PARSER_ID_VALUE_NOT_POSITIVE =
            "Please enter a positive value for identifier numbers.";
    public static final String ERROR_PARSER_INVALID_DATE_FORMAT =
            "Please enter the date in the form DD-MM-YYYY or enter 'today' for today's date.";
    public static final String ERROR_PARSER_NON_MONETARY_VALUE_ARGUMENT =
            "Please enter a valid monetary value(s) after the following delimiter: ";
    public static final String ERROR_PARSER_COST_NOT_POSITIVE =
            "Please enter a positive value for monetary value(s).";
    public static final String ERROR_PARSER_COST_NOT_TWO_DP =
            "Please enter a value up to 2 decimal places for monetary value(s).";
    public static final String ERROR_PARSER_COST_MORE_THAN_TWELVE_DIGITS_BEFORE_DP =
            "Please enter a value up to 12 digits in dollars for monetary value(s).";
    public static final String ERROR_PARSER_NON_PERCENTAGE_ARGUMENT =
            "Please enter a valid percentage value after the delimiter: ";
    public static final String ERROR_PARSER_PERCENTAGE_NEGATIVE =
            "Please enter a non-negative percentage value.";
    public static final String ERROR_PARSER_PERCENTAGE_NOT_TWO_DP =
            "Please enter a value up to 2 decimal places for percentage values.";
    public static final String ERROR_PARSER_PERCENTAGE_MORE_THAN_THREE_DIGITS_BEFORE_DP =
            "Please enter a value up to 3 digits before the decimal point for percentage values.";
    public static final String ERROR_PARSER_INVALID_GST_SURCHARGE =
            "Please enter a valid GST surcharge in % in the range [0, 100] after the delimiter: ";
    public static final String ERROR_PARSER_INVALID_SERVICE_CHARGE =
            "Please enter a valid service charge in % in the range [0, 100] after the delimiter: ";
    public static final String ERROR_PARSER_EMPTY_COMMAND =
            "Please enter a command.";
    public static final String ERROR_PARSER_INVALID_COMMAND =
            "Please enter a valid command.";
    public static final String ERROR_PARSER_ADDITIONAL_INVALID_TOKEN =
            "The command contains an argument that does not have a delimiter. Please rectify and try again.";
    public static final String ERROR_PARSER_INVALID_DELIMITERS =
            "The command contains one or more invalid delimiters. Please rectify and try again.";
    public static final String ERROR_PARSER_DUPLICATE_DELIMITERS =
            "The command contains one or more duplicate delimiters. Please rectify and try again.";
    public static final String ASSERT_PARSER_COMMAND_TYPE_NULL =
            "The command type cannot be null.";
    public static final String ASSERT_PARSER_COMMAND_ARGUMENTS_NULL =
            "The command arguments cannot be null.";
    public static final String ASSERT_PARSER_TOKEN_INPUT_NULL =
            "The token input cannot be null.";
    public static final String ASSERT_PARSER_DELIMITER_NULL =
            "The delimiter cannot be null.";
    public static final String ASSERT_PARSER_PARAMETERS_NULL =
            "The input parameters cannot be null.";
    public static final String ASSERT_PARSER_PLACES_NEGATIVE =
            "A number cannot have less than 0 digits before the integer position.";
    public static final String ASSERT_PARSER_PERCENTAGE_NEGATIVE =
            "A parsed percentage value cannot be negative.";
    public static final String LOGGER_PARSER_COMMAND_TYPE =
            "Parsed command type: ";
    public static final String LOGGER_PARSER_REMAINING_ARGS =
            "Parsed remaining arguments: ";

    // ActivityCost
    public static final String LOGGER_ACTIVITYCOST_CONSTRUCT_WITH_DEFAULT_PARAMS =
            "Constructing ActivityCost with default parameters";
    public static final String LOGGER_ACTIVITYCOST_CONSTRUCT_WITH_ACTIVITYID =
            "Constructing ActivityCost with activityId";
    public static final String LOGGER_ACTIVITYCOST_CONSTRUCT_WITH_ALL_PARAMS =
            "Constructing ActivityCost with activityId, costPaid and costOwed";

    // Person
    public static final String ERROR_PERSON_NO_ACTIVITIES =
            "This person is not participating in any activities.";
    public static final String ERROR_PERSON_ACTIVITY_NOT_FOUND =
            "This person is not participating in the activity with Id: ";

    // PersonList
    public static final String ERROR_PERSONLIST_DUPLICATE_NAME_IN_SESSION =
            "There are duplicate names in the person list for the session you are trying to create. "
                    + "Please rectify and try again.";
    public static final String ERROR_PERSONLIST_CONTAINS_INVALID_NAME =
            "Names in the person list should only contain alphabets. Please rectify and try again.";
    public static final String ERROR_PERSONLIST_DUPLICATE_NAME_IN_GROUP =
            "There are duplicate names in the person list for the group you are trying to create. "
                    + "Please rectify and try again.";
    public static final String ERROR_PERSONLIST_DUPLICATE_NAME_IN_ACTIVITY =
            "There are duplicate names in the persons involved for the activity you are trying to create. "
                    + "Please rectify and try again.";
    public static final String ASSERT_PERSONLIST_NAME_DUPLICATE_EXISTS_BUT_NOT_DETECTED =
            "Name duplicates exist but not detected.";
    public static final String LOGGER_PERSONLIST_NAME_DUPLICATE_EXISTS_IN_CREATESESSION =
            "A Session object failed to be added into the list of sessions because there are duplicate names in"
                    + "the person list.";
    public static final String LOGGER_PERSONLIST_INVALID_NAME_EXISTS_IN_CREATESESSION =
            "A Session object failed to be added into the list of sessions because there are invalid names in"
                    + "the person list.";
    public static final String LOGGER_PERSONLIST_NAME_DUPLICATE_EXISTS_IN_CREATEGROUP =
            "A Group object failed to be added into the list of groups because there are duplicate names in"
                    + "the person list.";
    public static final String LOGGER_PERSONLIST_NAME_DUPLICATE_EXISTS_IN_CREATEACTIVITY =
            "An Activity object failed to be added into the list of activities because there are duplicate names in"
                    + "the involved list.";
    public static final String LOGGER_PERSONLIST_NAME_DUPLICATE_EXISTS_IN_EDITGROUP =
            "A Group object failed to be added into the list of groups because there are duplicate names in"
                    + "the person list.";

    // Session
    public static final String ERROR_SESSION_EMPTY_ACTIVITY_LIST =
            "The list of activities in the session is currently empty.";
    public static final String ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST =
            "The activity that you have specified was not found in this session.";
    public static final String ERROR_SESSION_PERSON_NOT_IN_LIST =
            "The person that you have specified was not found in this session.";
    public static final String ASSERT_SESSION_PERSON_LIST_EMPTY =
            "Session object cannot be constructed with a null or empty person list.";
    public static final String ASSERT_SESSION_ACTIVITY_NULL =
            "The activity to be added to a Session object cannot be null.";
    public static final String ASSERT_SESSION_NAME_NULL =
            "The String object to be used to search for Person objects by name cannot be null.";
    public static final String ASSERT_SESSION_NAME_LIST_EMPTY =
            "The String array object to be used to search for Person objects by name cannot be null or empty.";
    public static final String ASSERT_SESSION_COMPARED_SESSION_NULL =
            "The Session object provided in the parameter of the comparison method cannot be null.";
    public static final String LOGGER_SESSION_ACTIVITY_REMOVAL =
            "An activity with the following id has been requested to be deleted: ";
    public static final String LOGGER_SESSION_ACTIVITYCOST_REMOVAL =
            "An activity cost from the following person has been requested to be deleted: ";

    // Profile
    public static final String ERROR_PROFILE_DUPLICATE_SESSION =
            "There is another session with the same name. Please rename and try again.";
    public static final String ERROR_PROFILE_DUPLICATE_GROUP =
            "There is another group with the same name. Please rename and try again.";
    public static final String ERROR_PROFILE_SESSION_LIST_EMPTY =
            "There are currently no sessions stored.";
    public static final String ERROR_PROFILE_SESSION_NOT_IN_LIST =
            "The session that you have specified was not found.";
    public static final String ERROR_PROFILE_GROUP_LIST_EMPTY =
            "There are currently no groups stored.";
    public static final String ERROR_PROFILE_GROUP_NOT_IN_LIST =
            "The group that you have specified was not found.";
    public static final String LOGGER_PROFILE_GROUP_NOT_IN_LIST =
            "The Group object was not found in the list";

    // Storage
    public static final String ERROR_STORAGE_FILE_NOT_FOUND =
            "The data file was not found or has been corrupted. No saves were loaded into SplitLah.";
    public static final String ERROR_STORAGE_PATH_LOCATION_CREATION_FAILED =
            "A new data file could not be created. No data would be saved while using SplitLah.";
    public static final String ERROR_STORAGE_DATA_NOT_SAVED =
            "An error occurred while saving. No data was saved.";
    public static final String ERROR_STORAGE_CLASS_EXCEPTION_ISSUE =
            "It seems that something went wrong internally. No saves were loaded into SplitLah.";
    public static final String LOGGER_STORAGE_CLASS_NOT_FOUND =
            "An internal error of ClassNotFoundException has occurred.";
    public static final String LOGGER_STORAGE_FILE_ERROR =
            "There was an error in retrieving data from save file.";

    // Activity
    public static final String ERROR_ACTIVITY_INACCURATE_INVOLVED_LIST =
            "The list of involved persons list is inaccurate "
                    + "as at least one person did not participate in the activity.";
    public static final String ASSERT_ACTIVITY_EMPTY_INVOLVED_PERSON_LIST =
            "The list of persons in the activity is currently empty.";

    // Invalid Command
    public static final String ASSERT_INVALIDCOMMAND_MANAGER_DOES_NOT_EXIST =
            "Manager does not exist.";

    // Session Create Command
    public static final String ERROR_SESSIONCREATE_MISSING_PERSONLIST_AND_GROUP_DELIMITERS =
            "The person list or group identifier delimiters are missing.";
    public static final String ASSERT_SESSIONCREATE_SESSION_NAME_NULL =
            "Session name is empty but was not handled in command parser.";
    public static final String ASSERT_SESSIONCREATE_SESSION_DATE_NULL =
            "Session data is empty but was not handled in command parser.";
    public static final String ASSERT_SESSIONCREATE_MISSING_PERSONLIST_AND_GROUP_DELIMITERS =
            "The person list or group identifier delimiters are missing but was not handled in command parser.";
    public static final String LOGGER_SESSIONCREATE_SESSION_ADDED =
            "A session was added into the list of sessions with Id: ";
    public static final String LOGGER_SESSIONCREATE_DUPLICATE_NAMES_IN_SESSION_LIST =
            "A Session object failed to be added into the list of sessions because there are duplicate names in"
                    + "the session list.";

    // Session Delete Command
    public static final String ASSERT_SESSIONDELETE_SESSION_ID_NOT_INITIALIZED =
            "Session Id not initialized.";
    public static final String LOGGER_SESSIONDELETE_SESSION_REMOVED =
            "A session was removed from the list of sessions with Id: ";
    public static final String LOGGER_SESSIONDELETE_SESSION_REMOVED_FAILED =
            "A session was not removed from the list of sessions with Id: ";

    // Session Edit Command
    public static final String ERROR_SESSIONEDIT_NO_EDIT_DELIMITERS_FOUND =
            "No delimiters were found. Please rectify and try again.";
    public static final String ERROR_SESSIONEDIT_INVALID_PERSONLIST =
            "The specified person list does not contain all existing persons that was previously "
                   + "created in the session. Please rectify and try again.";
    public static final String LOGGER_SESSIONEDIT_SESSION_EDITED =
            "A session was edited from the list of session with Id: ";
    public static final String ASSERT_SESSIONEDIT_SESSION_ID_INVALID =
            "Session ID is less than or equals to zero.";

    // Session Summary Command
    public static final String MESSAGE_SESSIONSUMMARY_NO_PAYMENTS_REQUIRED =
            "There are no payments to be made.";
    public static final String ASSERT_SESSIONSUMMARY_INVALID_PERSONCOSTPAIR_LIST =
            "The generated personCostPairList is invalid with a non-zero total sum of debt.";
    public static final String ASSERT_SESSIONSUMMARY_PAYER_EXPECTS_FROM_RECEIVER =
            "Payer has a greater total cost than receiver.";
    public static final String LOGGER_SESSIONSUMMARY_SESSION_ID_NOT_FOUND =
            "A session summary was not produced as a Session object with the following Id was not found: ";
    public static final String LOGGER_SESSIONSUMMARY_SESSION_SUMMARY_PRINTED =
            "A session summary has been successful produced for the Session object with the following Id: ";

    // Session List Command
    public static final String LOGGER_SESSIONLIST_SESSIONS_LISTED =
            "All the sessions have been listed.";

    // Session View Command
    public static final String LOGGER_SESSIONVIEW_SESSION_VIEW_FAILED =
            "A Session object was unable to be viewed from the list of sessions with Id: ";
    public static final String LOGGER_SESSIONVIEW_SESSION_VIEWED =
            "A session with the following Id was viewed: ";

    // Activity Create Command
    public static final String ERROR_ACTIVITYCREATE_INVOLVED_AND_COST_DIFFERENT_LENGTH =
            "There is a mismatch between persons involved and the costs for each person. Please rectify and try again.";
    public static final String ERROR_ACTIVITYCREATE_HAS_BOTH_COST_AND_COST_LIST =
            "Please only include either a total cost or a list of costs.";
    public static final String ERROR_ACTIVITYCREATE_MISSING_COST_AND_COST_LIST =
            "Please include either a cost or a list of costs.";
    public static final String ASSERT_ACTIVITYCREATE_SESSION_ID_LESS_THAN_ONE =
            "Session ID is less than or equals to zero.";
    public static final String ASSERT_ACTIVITYCREATE_ACTIVITY_NAME_MISSING =
            "Activity name is missing.";
    public static final String ASSERT_ACTIVITYCREATE_TOTAL_COST_LESS_THAN_ONE =
            "Total cost is less than or equals to zero.";
    public static final String ASSERT_ACTIVITYCREATE_PAYER_NAME_MISSING =
            "Payer's name is missing.";
    public static final String ASSERT_ACTIVITYCREATE_INVOLVED_LIST_ARRAY_NULL =
            "Involved list array does not contain the list of names of the persons involved in the activity.";
    public static final String ASSERT_ACTIVITYCREATE_COST_LIST_ARRAY_NULL =
            "Cost list array does not contain the respective costs of the persons involved in the activity.";
    public static final String LOGGER_ACTIVITYCREATE_ACTIVITY_ADDED =
            "An Activity object was succesfully added into the list of activities with Id: ";
    public static final String LOGGER_ACTIVITYCREATE_FAILED_ADDING_ACTIVITY =
            "An Activity object failed to be added into the list of activities due to the following error: ";


    // Activity Edit Command
    public static final String LOGGER_ACTIVITYEDIT_FAILED_EDITING_ACTIVITY =
            "An Activity object failed to be edited.";
    public static final String ASSERT_ACTIVITYEDIT_COMMAND_ARGS_NULL =
            "The command arguments cannot be null.";
    public static final String ASSERT_ACTIVITYEDIT_ACTIVITYID_MISSING =
            "Activity Id missing.";
    public static final String ASSERT_ACTIVITYEDIT_SESSIONID_LESS_THAN_ONE =
            "Session ID is less than or equals to zero.";
    public static final String ERROR_ACTIVITYEDIT_DUPLICATE_NAME =
            "There are duplicate names in the persons involved for the activity you are trying to edit. "
                    + "Please rectify and try again.";
    public static final String ASSERT_ACTIVITYEDIT_COST_LIST_ARRAY_NULL =
            "Cost list array does not contain the respective costs of the persons involved in the activity.";
    public static final String LOGGER_ACTIVITYEDIT_ACTIVITY_EDITED =
            "An Activity object was succesfully edited in the list of activities with Id: ";
    public static final String ERROR_ACTIVITYEDIT_INVOLVED_AND_COST_DIFFERENT_LENGTH =
            "There is a mismatch between persons involved and the costs for each person. Please rectify and try again.";
    public static final String ERROR_ACTIVITYEDIT_COSTLIST_AND_COSTOVERALL_SUPPLIED =
            "Either a cost list or overall cost may be supplied, but not both.";
    public static final String ERROR_ACTIVITYEDIT_COST_NOT_PROVIDED =
            "Please provide an overall cost or costlist if you wish to edit the participants or payer.";
    public static final String ERROR_ACTIVITYEDIT_NO_CHANGE_TO_ACTIVITY =
            "No changes were made to the activity.";

    // Activity Delete Command
    public static final String ASSERT_ACTIVITYDELETE_SESSION_IS_NULL =
            "Session is still not initialized.";
    public static final String ASSERT_ACTIVITYDELETE_SESSION_ID_NOT_INITIALIZED =
            "Session Id not initialized.";
    public static final String ASSERT_ACTIVITYDELETE_ACTIVITY_ID_NOT_INITIALIZED =
            "Activity Id not initialized.";
    public static final String ASSERT_ACTIVITYDELETE_ACTIVITY_NOT_DELETED =
            "The activity was not deleted.";
    public static final String LOGGER_ACTIVITYDELETE_ACTIVITY_REMOVED =
            "An Activity object was successfully deleted from the list of activities with Id: ";
    public static final String LOGGER_ACTIVITYDELETE_ACTIVITY_REMOVE_FAILED =
            "An activity was not removed from the list of activities in a session with Id: ";

    // Activity List Command
    public static final String ASSERT_ACTIVITYLIST_SESSION_ID_LESS_THAN_ONE =
            "Session unique identifier is less than one";
    public static final String LOGGER_ACTIVITYLIST_SESSION_ID_NOT_FOUND =
            "Session was not found with unique identifier of:";
    public static final String LOGGER_ACTIVITYLIST_ACTIVITIES_LISTED =
            "All the activities have been listed with specified session Id: ";

    // Activity View Command
    public static final String LOGGER_ACTIVITYVIEW_ACTIVITY_VIEWED =
            "An activity object was successfully viewed from activity Id: ";
    public static final String LOGGER_ACTIVITYVIEW_ACTIVITY_NOT_VIEWED =
            "An activity object was unable to be viewed from activity Id: ";
    public static final String ASSERT_ACTIVITYVIEW_ACTIVITY_ID_LESS_THAN_ONE =
            "Activity unique identifier is less than one";
    public static final String ASSERT_ACTIVITYVIEW_SESSION_ID_LESS_THAN_ONE =
            "Session unique identifier is less than one";

    // Group
    public static final String ERROR_GROUP_EMPTY_PERSON_LIST =
            "There are currently no person in this group.";
    public static final String ERROR_GROUP_PERSON_NOT_IN_LIST =
            "This person is not in the group.";
    public static final String ASSERT_GROUP_PERSON_LIST_EMPTY =
            "Group object cannot be constructed with a null or empty person list.";

    // Group Create Command
    public static final String ASSERT_GROUPCREATE_GROUP_NAME_NULL =
            "Group name is empty but was not handled in command parser.";
    public static final String ASSERT_GROUPCREATE_PERSONLIST_NULL =
            "The person list is empty but was not handled in command parser.";
    public static final String LOGGER_GROUPCREATE_GROUP_ADDED =
            "A group was added into the list of groups with Id: ";
    public static final String LOGGER_GROUPCREATE_DUPLICATE_NAMES_IN_GROUP_LIST =
            "A Group object failed to be added into the list of groups because there are duplicate names in"
                    + "the group list.";
    public static final String LOGGER_PERSONLIST_INVALID_NAME_EXISTS_IN_CREATEGROUP =
            "A group object failed to be added into the list of groups because there are invalid names in"
                    + "the person list.";

    // Group Delete Command
    public static final String ASSERT_GROUPDELETE_GROUP_ID_NOT_INITIALIZED =
            "Group Id not initialized.";
    public static final String LOGGER_GROUPDELETE_GROUP_REMOVED =
            "A group was removed from the list of groups with Id: ";
    public static final String LOGGER_GROUPDELETE_GROUP_REMOVED_FAILED =
            "A group was not removed from the list of groups with Id: ";


    // Group View Command
    public static final String ASSERT_GROUPVIEW_INCORRECT_GROUP =
            "The group returned is incorrect.";
    public static final String ASSERT_GROUPVIEW_GROUP_ID_LESS_THAN_ONE =
            "Group unique identifier is less than one";
    public static final String LOGGER_GROUPVIEW_GROUP_VIEWED =
            "A Group object was successfully viewed from the list of groups with Id: ";
    public static final String LOGGER_GROUPVIEW_GROUP_NOT_VIEWED =
            "A Group object was unable to be viewed from the list of groups with Id: ";

    // Group View Command Parser
    public static final String ASSERT_GROUPVIEWPARSER_GROUP_ID_NOT_INITIALIZED =
            "Group Id is not initialized.";

    // Group List Command
    public static final String LOGGER_GROUPLIST_GROUPS_LISTED =
            "All the groups have been listed.";

    // Group Edit Command
    public static final String ASSERT_GROUPEDIT_GROUP_ID_INVALID =
            "Group ID is less than or equals to zero.";
    public static final String ERROR_GROUPEDIT_SAME_PERSON_LIST =
            "The new person list provided is the same as the original person list"
                + ", please rectify and try again.";
    public static final String ERROR_GROUPEDIT_GROUP_NAME_DUPLICATE =
             "There is already an existing group with the same name"
                 + ", please rectify and try again.";
    public static final String ERROR_GROUPEDIT_GROUP_NAME_NOT_NEW =
             "The group name provided is the same as the original one,"
                 + " please rectify and try again.";
    public static final String ERROR_GROUPEDIT_NO_CHANGE =
             "No changes made.";
    public static final String ERROR_GROUPEDIT_DUPLICATE_NAME_IN_GROUP =
             "There are duplicate names in the person list for the group you are trying to edit. "
                 + "Please rectify and try again.";

    // Group Edit Command Parser
    public static final String ERROR_GROUPEDIT_NO_EDIT_DELIMITERS_FOUND =
            "No delimiters were found. Please rectify and try again.";
}
