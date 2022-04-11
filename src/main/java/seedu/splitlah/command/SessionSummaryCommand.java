package seedu.splitlah.command;

import seedu.splitlah.data.Manager;
import seedu.splitlah.data.Person;
import seedu.splitlah.data.Profile;
import seedu.splitlah.data.Session;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TextUI;
import seedu.splitlah.util.PersonCostPair;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Represents a command that produces a summary of expenditure for a specified Session object.
 *
 * @author Warren
 */
public class SessionSummaryCommand extends Command {

    private final int sessionId;

    // MISC CONSTANTS
    private static final String SUMMARY_HEADER_PREPEND = "Summary (Session Id #";
    private static final String SUMMARY_HEADER_POSTPEND = ") --";
    private static final String PREPEND_SESSION_NAME = "\nName: ";
    private static final String PREPEND_SESSION_DATE = "\nDate: ";
    private static final String PREPEND_PAYMENTS = "\nTransactions to be made:";
    private static final String PREPEND_TRANSACTION = "\n - ";
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final int ZERO_INDEXING_OFFSET = 1;
    private static final double SMALL_DIFFERENCE_LIMIT = 0.0001;

    /**
     * Initializes a SessionSummaryCommand object.
     *
     * @param sessionId An integer that uniquely identifies a session.
     */
    public SessionSummaryCommand(int sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Checks whether the difference between two double values is negligible to minimise floating point errors.
     * 
     * @param cost1 A double that represents a monetary value to be compared.
     * @param cost2 Another double that represents a monetary value to be compared.
     * @return true if the difference is negligible,
     *         false otherwise.
     */
    private static boolean isDifferenceSmall(double cost1, double cost2) {
        return Math.abs(cost1 - cost2) <= SMALL_DIFFERENCE_LIMIT;
    }

    /**
     * Checks whether a double value is negligible in monetary value.
     * 
     * @param cost A double that represents a monetary value to be checked for.
     * @return true if the value is negligible,
     *         false otherwise.
     */
    private static boolean isValueSmall(double cost) {
        return Math.abs(cost) <= SMALL_DIFFERENCE_LIMIT;
    }

    /**
     * Groups each person with the cost borne by them and consolidates them into an ArrayList object of
     * PersonCostPair objects.
     * 
     * @param personList An ArrayList object of Person objects representing a list of participants in a Session object.
     * @return An ArrayList object of PersonCostPair objects each corresponding to a Person object in the
     *         ArrayList object provided in the parameter.
     */
    private static ArrayList<PersonCostPair> getPersonCostPairList(ArrayList<Person> personList) {
        ArrayList<PersonCostPair> personCostPairList = new ArrayList<>();
        for (Person person : personList) {
            PersonCostPair newPair = new PersonCostPair(person);
            if (!isValueSmall(newPair.getCost())) {
                personCostPairList.add(newPair);
            }
        }
        return personCostPairList;
    }

    /**
     * Checks if an ArrayList object of PersonCostPair objects has a negligible total sum, representing that there
     * is a matching owing sum of money for every owed sum of money in the Session object.
     * 
     * @param personCostPairList An ArrayList object of PersonCostPair objects that each represents the total cost borne
     *                           by a Person object.
     * @return true if the provided ArrayList object of PersonCostPair objects has a negligible total sum,
     *         false otherwise.
     */
    private static boolean isPersonCostPairListValid(ArrayList<PersonCostPair> personCostPairList) {
        double total = 0;
        for (PersonCostPair personCostPair : personCostPairList) {
            total += personCostPair.getCost();
        }
        return isValueSmall(total);
    }

    /**
     * Returns a String object representing a transaction that should happen between two Person objects as described by
     * the PersonCostPair objects provided as parameters, in order settle the debt/owed amount of at least one person
     * out of the two persons described by the parameters.
     * 
     * @param payer    A PersonCostPair object that describes the total cost borne by a Person object representing a
     *                 person that has to pay.
     * @param receiver A PersonCostPair object that describes the total cost borne by a Person object representing a
     *                 person that has to be paid.
     * @return A String object representing a transaction between two Person objects if there are debts to settle,
     *         an empty String object if both parties have a negligible debt/to-be-paid amount.
     */
    private String processTransaction(PersonCostPair payer, PersonCostPair receiver) {
        double payerCost = Math.abs(payer.getCost());
        double receiverAmount = Math.abs(receiver.getCost());
        
        // Both parties have negligible debt/cost
        if (isValueSmall(payerCost) && isValueSmall(receiverAmount)) {
            payer.setProcessed(true);
            receiver.setProcessed(true);
            return "";
        }
        
        // Equal costs
        if (isDifferenceSmall(payerCost, receiverAmount)) {
            payer.setProcessed(true);
            receiver.setProcessed(true);
            return payer.getPerson().getName() + " has to pay " + receiver.getPerson().getName()
                    + " $" + String.format("%.2f", payerCost);
        }
        
        // Payer has less debt than receiver has to collect
        if (payerCost < receiverAmount) {
            payer.setProcessed(true);
            receiver.setCost(receiverAmount - payerCost);
            return payer.getPerson().getName() + " has to pay " + receiver.getPerson().getName()
                    + " $" + String.format("%.2f", payerCost);
        }
        
        // Payer has more debt than receiver has to collect
        receiver.setProcessed(true);
        payer.setCost(receiverAmount - payerCost);
        return payer.getPerson().getName() + " has to pay " + receiver.getPerson().getName()
                + " $" + String.format("%.2f", receiverAmount);
    }

    /**
     * Processes the ArrayList object of PersonCostPair objects and returns a String object that represents a
     * summary of all transactions that has to be made in order for the entire session to reach a settlement
     * where no debts are owed or uncollected.
     * 
     * @param personCostPairList An ArrayList object of PersonCostPair objects that each represents the total cost borne
     *                           by a Person object.
     * @param session            A Session object of which the transactions are processed and summarised for.
     * @return A String object representing the summary of all transactions to be made for the session.
     */
    private String processAllTransactions(ArrayList<PersonCostPair> personCostPairList, Session session) {
        String dateString = session.getDateCreated().format(dateFormat);
        StringBuilder sb = new StringBuilder(SUMMARY_HEADER_PREPEND);
        sb.append(sessionId).append(SUMMARY_HEADER_POSTPEND);
        sb.append(PREPEND_SESSION_NAME).append(session.getSessionName());
        sb.append(PREPEND_SESSION_DATE).append(dateString);
        sb.append(PREPEND_PAYMENTS);
        personCostPairList.sort(PersonCostPair::compareTo);
        
        int payerIndex = 0;
        int receiverIndex = personCostPairList.size() - ZERO_INDEXING_OFFSET;
        assert isPersonCostPairListValid(personCostPairList) : 
                Message.ASSERT_SESSIONSUMMARY_INVALID_PERSONCOSTPAIR_LIST;

        boolean hasInserted = false;
        while (payerIndex < receiverIndex) {
            PersonCostPair payer = personCostPairList.get(payerIndex);
            PersonCostPair receiver = personCostPairList.get(receiverIndex);
            assert payer.getCost() < receiver.getCost() :
                    Message.ASSERT_SESSIONSUMMARY_PAYER_EXPECTS_FROM_RECEIVER;
            String output = processTransaction(payer, receiver);
            
            if (!output.isEmpty()) {
                sb.append(PREPEND_TRANSACTION).append(output);
                hasInserted = true;
            }
            if (payer.isProcessed()) {
                payerIndex += 1;
            }
            if (receiver.isProcessed()) {
                receiverIndex -= 1;
            }
        }

        if (!hasInserted) {
            sb.append(PREPEND_TRANSACTION).append(Message.MESSAGE_SESSIONSUMMARY_NO_PAYMENTS_REQUIRED);
        }
        return sb.toString();
    }

    /**
     * Runs the command with the session identifier as provided by the user input and prints a
     * summary of expenditure for the session specified by the session identifier.
     *
     * @param manager A Manager object that manages the TextUI, Profile and Storage object.
     */
    @Override
    public void run(Manager manager) {
        Profile profile = manager.getProfile();
        TextUI ui = manager.getUi();
        Session session;
        try {
            session = profile.getSession(sessionId);
        } catch (InvalidDataException exception) {
            ui.printlnMessage(exception.getMessage());
            Manager.getLogger().log(Level.FINEST, Message.LOGGER_SESSIONSUMMARY_SESSION_ID_NOT_FOUND + sessionId);
            return;
        }

        ArrayList<Person> personList = session.getPersonArrayList();
        ArrayList<PersonCostPair> personCostPairList = getPersonCostPairList(personList);
        String output = processAllTransactions(personCostPairList, session);
        ui.printlnMessageWithDivider(output);
        Manager.getLogger().log(Level.FINEST, Message.LOGGER_SESSIONSUMMARY_SESSION_SUMMARY_PRINTED + sessionId);
    }
}
