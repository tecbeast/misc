package de.seipler.ausgaben.datenversorgung.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Georg Seipler
 */
public class DisplaySqlTableHelper {
    
    private static final String VERTICAL_LINE = "|";
    private static final String HORIZONTAL_LINE = "-";
    private static final String CROSSING_LINES = "+";
    private static final String SPACING = " ";
    
    private static final int TYPE_LEFT_ALIGNED = 1;
    private static final int TYPE_RIGHT_ALIGNED = 2;
    
    public static void displayTable(Connection pConnection, String pQuery) throws SQLException {

        Statement statement = pConnection.createStatement();
        
        int columnCount = 0;
        String[] columnNames = null;
        int[] columnLength = null;
        int[] columnTypes = null;
        ResultSet resultSet = statement.executeQuery(pQuery);
        ResultSetMetaData metaData = resultSet.getMetaData();
        columnCount = metaData.getColumnCount();

        if (columnCount > 0) {
            
            columnNames = new String[columnCount];
            columnLength = new int[columnCount];
            columnTypes = new int[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
                columnLength[i] = columnNames[i].length();
                if (metaData.getColumnType(i + 1) == Types.INTEGER) {
                    columnTypes[i] = TYPE_RIGHT_ALIGNED;
                } else {
                    columnTypes[i] = TYPE_LEFT_ALIGNED;
                }
            }
        
            List rowList = new ArrayList();
            while (resultSet.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < row.length; i++) {
                    row[i] = resultSet.getString(i + 1);
                    if ((row[i] != null) && (row[i].length() > columnLength[i])) {
                        columnLength[i] = row[i].length();
                    }
                }
                rowList.add(row);
            }
            
            for (int i = 0; i < columnNames.length; i++) {
                System.out.print(alignColumn(columnTypes[i], columnLength[i], columnNames[i]));
            }
            System.out.println(VERTICAL_LINE);
            
            for (int i = 0; i < columnCount; i++) {
                System.out.print(CROSSING_LINES);
                for (int j = 0; j < columnLength[i]; j++) {
                    System.out.print(HORIZONTAL_LINE);
                }
            }
            System.out.println(CROSSING_LINES);
            
            Iterator rowIterator = rowList.iterator();
            while (rowIterator.hasNext()) {
                String[] row = (String[]) rowIterator.next();
                for (int i = 0; i < row.length; i++) {
                    System.out.print(alignColumn(columnTypes[i], columnLength[i], row[i]));
                }
                System.out.println(VERTICAL_LINE);
            }
        
        }
        
    }
    
    private static String alignColumn(int pType, int pSize, String pContent) {
        String alignedColumn;
        if (pType == TYPE_RIGHT_ALIGNED) {
            alignedColumn = rightAlignColumn(pSize, pContent);
        } else {
            alignedColumn = leftAlignColumn(pSize, pContent);
        }
        return alignedColumn;
    }
    
    private static String leftAlignColumn(int pSize, String pContent) {
        StringBuffer buffer = new StringBuffer();
        if (pSize > 0) {
            int nrOfSpaces = pSize;
            buffer.append(VERTICAL_LINE);
            if (pContent != null) {
                buffer.append(pContent);
                nrOfSpaces -= pContent.length();
            }
            for (int i = 0; i < nrOfSpaces; i++) {
                buffer.append(SPACING);
            }
        }
        return buffer.toString();
    }

    private static String rightAlignColumn(int pSize, String pContent) {
        StringBuffer buffer = new StringBuffer();
        if (pSize > 0) {
            buffer.append(VERTICAL_LINE);
            int nrOfSpaces = pSize;
            if (pContent != null) {
                nrOfSpaces -= pContent.length();
            }
            for (int i = 0; i < nrOfSpaces; i++) {
                buffer.append(SPACING);
            }
            if (pContent != null) {
                buffer.append(pContent);
            }
        }
        return buffer.toString();
    }
    
}
