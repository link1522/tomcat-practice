package trans;

import java.sql.Connection;
import java.sql.SQLException;

import utils.JDBCUtils;

public class TransactionManager {
    public static void beginTrans() throws SQLException {
        JDBCUtils.getConn().setAutoCommit(false);
    }

    public static void commit() throws SQLException {
        Connection conn = JDBCUtils.getConn();
        conn.commit();
        JDBCUtils.closeConn();
    }

    public static void rollback() throws SQLException {
        Connection conn = JDBCUtils.getConn();
        conn.rollback();
        JDBCUtils.closeConn();
    }
}
