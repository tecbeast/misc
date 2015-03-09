package de.seipler.ausgaben.datenversorgung.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungAnbieter;
import de.seipler.ausgaben.datenversorgung.xml.XmlEntityCache;
import de.seipler.ausgaben.entity.xml.XmlAnbieter;

/**
 * 
 * @author Georg Seipler
 */
public class DatenversorgungSqlAnbieter extends DatenversorgungSql implements IDatenversorgungAnbieter {
    
    public static final String QUERY_ALLE_ANBIETER = "SELECT * FROM anbieter;";
    public static final String QUERY_INSERT_ANBIETER = "INSERT INTO anbieter (id, name, beschreibung) VALUES (?, ?, ?);";
    public static final String QUERY_UPDATE_ANBIETER = "UPDATE anbieter SET name = ?, beschreibung = ? WHERE id = ?;";
    public static final String QUERY_MAXIMALE_ID = "SELECT MAX(id) FROM anbieter;";
    
    private XmlEntityCache fCache;
    private SqlIdGenerator fIdGenerator;
    
    protected DatenversorgungSqlAnbieter(IDbConfiguration pDbConfiguration) {
        super(pDbConfiguration);
    }
    
    public XmlAnbieter liesAnbieterMitId(int pId) throws DatenversorgungException {
        return (XmlAnbieter) fCache.get(pId);
    }
    
    public XmlAnbieter erzeugeAnbieter() {
        XmlAnbieter anbieter = new XmlAnbieter();
        anbieter.setId(fIdGenerator.getId());
        anbieter.setNew(true);
        fCache.put(anbieter);
        return anbieter;
    }
    
    public void init() throws DatenversorgungException {
        erneuereCache();
        fIdGenerator = new SqlIdGenerator(getDbConfiguration(), QUERY_MAXIMALE_ID);
        fIdGenerator.init();
    }
    
    public void release() throws DatenversorgungException {
        sync();
        fCache = null;
        fIdGenerator = null;
    }
    
    public void sync() throws DatenversorgungException {
        if (fCache.isDirty()) {
            try {
                Connection connection = leaseConnection();
                PreparedStatement insertQuery = connection.prepareStatement(QUERY_INSERT_ANBIETER);
                PreparedStatement updateQuery = connection.prepareStatement(QUERY_UPDATE_ANBIETER);
                Iterator idIterator = fCache.idIterator();
                while (idIterator.hasNext()) {
                    int id = ((Integer) idIterator.next()).intValue();
                    XmlAnbieter anbieter = (XmlAnbieter) fCache.get(id);
                    if (anbieter.isNew()) {
                        insertQuery.setInt(1, anbieter.getId());
                        insertQuery.setString(2, anbieter.getName());
                        insertQuery.setString(3, anbieter.getBeschreibung());
                        insertQuery.executeUpdate();
                        anbieter.setNew(false);
                    }
                    if (anbieter.isUpdated()) {
                        updateQuery.setInt(3, anbieter.getId());
                        updateQuery.setString(1, anbieter.getName());
                        updateQuery.setString(2, anbieter.getBeschreibung());
                        updateQuery.executeUpdate();
                        anbieter.setUpdated(false);
                    }
                }
                returnLeasedConnection(connection);                
            } catch (SQLException se) {
                throw new DatenversorgungException(se);
            }
        }
    }
    
    private void erneuereCache() {
        fCache = new XmlEntityCache();
        try {
            Connection connection = leaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_ALLE_ANBIETER);
            while (resultSet.next()) {
                XmlAnbieter anbieter = liesAnbieter(resultSet);
                fCache.put(anbieter);
            }
            returnLeasedConnection(connection);
        } catch (SQLException se) {
            throw new DatenversorgungException(se);
        }
    }
    
    private XmlAnbieter liesAnbieter(ResultSet pResultSet) throws SQLException {
        // --- id --- name --- beschreibung ---
        XmlAnbieter anbieter = new XmlAnbieter();
        anbieter.setId(pResultSet.getInt(1));
        anbieter.setName(pResultSet.getString(2));
        anbieter.setBeschreibung(pResultSet.getString(3));
        return anbieter;
    }

}
