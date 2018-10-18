
package dataaccess;
import java.sql.*;

/**
 *
 * @author Paul Brown
 */
public class DBUtil {
    
    public static void closePreparedStatement(Statement ps) {
        
        try {
            if(ps != null) {
                ps.close();
            }
        }catch (SQLException e) {
            System.out.println(e);
        }
    }
}
