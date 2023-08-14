package dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import exceptions.DAOException;
import utils.JDBCUtils;

@SuppressWarnings("unchecked")
public class BaseDAO<T> {
    private Class<T> clazz = null;
    private QueryRunner queryRunner = new QueryRunner();

    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;

        Type[] typeArguments = paramType.getActualTypeArguments();

        clazz = (Class<T>) typeArguments[0];
    }

    public T queryOne(String sql, Object... args) {
        try {
            Connection conn = JDBCUtils.getConn();

            BeanHandler<T> handler = new BeanHandler<>(clazz);

            T item = queryRunner.query(conn, sql, handler, args);

            return item;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO exception in queryOne method");
        }
    }

    public List<T> queryMany(String sql, Object... args) {
        try {
            Connection conn = JDBCUtils.getConn();

            BeanListHandler<T> handler = new BeanListHandler<>(clazz);

            List<T> list = queryRunner.query(conn, sql, handler, args);

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO exception in queryMany method");
        }
    }

    public int execute(String sql, Object... args) {
        try {
            Connection conn = JDBCUtils.getConn();
            return queryRunner.execute(conn, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO exception in execute method");
        }
    }

    public <E> E queryOther(String sql, Object... args) {
        try {
            Connection conn = JDBCUtils.getConn();

            ScalarHandler<E> handler = new ScalarHandler<>();
            E data = queryRunner.query(conn, sql, handler, args);

            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO exception in queryOther method");
        }
    }
}
