package seedu.splitlah.ui;

import java.util.ArrayList;

/**
 * Represents a Table Formatter that creates a dynamic table that can be updated to add or remove rows,
 * and prints a neatly formatted table.
 *
 * @author Saurav
 */
public class TableFormatter {

    // KEY CONSTANTS
    private static final int MINIMUM_PADDING = 2;

    private TableFormatterRow columnHeaders;
    private ArrayList<TableFormatterRow> rows;
    private String tableName = null;

    public TableFormatter(String... columnHeaders) {
        this.columnHeaders = new TableFormatterRow(columnHeaders);
        this.rows = new ArrayList<>();
    }

    public int getRowLength() {
        return columnHeaders.getRowLength();
    }

    public void addTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Creates a TableFormatterRow object from the supplied String objects and adds the row to the TableFormatter
     * object.
     * If the number of String objects supplied does not match the number of columns, a new TableFormatterRow object
     * is not created or added.
     *
     * @param rowItems One or more String objects that form this TableFormatterRow object.
     * @return true if a new TableFormatterRow object was successfully created and added to the TableFormatter object.
     *         false if the number of String objects supplied did not match the number of columns.
     */
    public boolean addRow(String... rowItems) {
        if (rowItems.length != getRowLength()) {
            return false;
        }
        rows.add(new TableFormatterRow(rowItems));
        return true;
    }

    /**
     * Calculates maximum width of the specified column. The first column is column 0.
     *
     * @param column An integer representing the column number.
     * @return An integer representing the maximum width of the specified column.
     */
    private int calculateColumnWidth(int column) {
        int maxColumnWidth = 0;
        int headerLength = columnHeaders.getItem(column).length();
        for (TableFormatterRow row : rows) {
            int rowLength = row.getItem(column).length();
            maxColumnWidth = Math.max(maxColumnWidth, rowLength);
        }
        return Math.max(maxColumnWidth, headerLength);
    }

    /**
     * Calculates all column widths in the table and returns them in an integer array object.
     *
     * @return An integer array object containing the maximum column widths of each column in order.
     */
    private int[] calculateAllColumnWidths() {
        int[] columnWidths = new int[columnHeaders.getRowLength()];
        for (int column = 0; column < columnHeaders.getRowLength(); ++column) {
            columnWidths[column] = calculateColumnWidth(column);
        }
        return columnWidths;
    }

    /**
     * Pads and formats a row with decorative characters.
     *
     * @param row A TableFormatterRow object to be formatted.
     * @param columnWidths An integer array object containing the maximum column widths of the table.
     * @return A String object containing the formatted and decorated row.
     */
    private String decorateRow(TableFormatterRow row, int[] columnWidths) {
        ArrayList<String> paddedRow = row.padRow(columnWidths);
        StringBuilder decoratedRow = new StringBuilder();
        for (int rowIndex = 0; rowIndex < columnWidths.length; ++rowIndex) {
            decoratedRow.append(paddedRow.get(rowIndex));
            if (rowIndex < columnWidths.length - 1) {
                decoratedRow.append("|");
            }
        }
        return String.valueOf(decoratedRow);
    }

    /**
     * Calculates total table width from a given integer array object containing column widths.
     *
     * @param columnWidths An integer array object representing the widths of each column.
     * @return The total width of the table.
     */
    private int calculateTableWidth(int[] columnWidths) {
        int tableWidth = 0;
        for (int columnWidth : columnWidths) {
            tableWidth += columnWidth;
        }
        return (tableWidth + (MINIMUM_PADDING * columnWidths.length));
    }

    /**
     * Formats all rows in this TableFormatter object, decorates them and appends them to a table String object.
     *
     * @param columnWidths integer array object containing the maximum width of each column.
     * @param formattedTable A StringBuilder object representing the formatted table without the rows.
     */
    private void appendRowsToFormattedTableString(int[] columnWidths, StringBuilder formattedTable) {
        for (TableFormatterRow row : rows) {
            formattedTable.append(decorateRow(row, columnWidths));
            formattedTable.append("\n");
        }
    }

    /**
     * Appends the name of this TableFormatter object to the table StringBuilder object if a name has been set.
     *
     * @param formattedTable A StringBuilder object representing the formatted table.
     */
    private void appendTableNameIfExists(StringBuilder formattedTable) {
        if (tableName != null) {
            formattedTable.append(tableName).append("\n");
        }
    }

    @Override
    public String toString() {
        int[] columnWidths = calculateAllColumnWidths();
        StringBuilder formattedTable = new StringBuilder();
        appendTableNameIfExists(formattedTable);
        formattedTable.append("-".repeat(calculateTableWidth(columnWidths) + MINIMUM_PADDING)).append("\n");
        formattedTable.append(decorateRow(columnHeaders, columnWidths)).append("\n");
        formattedTable.append("-".repeat(calculateTableWidth(columnWidths) + MINIMUM_PADDING)).append("\n");
        appendRowsToFormattedTableString(columnWidths, formattedTable);
        formattedTable.append("=".repeat(calculateTableWidth(columnWidths) + MINIMUM_PADDING));
        return String.valueOf(formattedTable);
    }
}
