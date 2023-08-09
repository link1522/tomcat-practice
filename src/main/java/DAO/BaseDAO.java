package DAO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

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
        Connection conn = null;

        try {
            conn = JDBCUtils.getConnection();

            BeanHandler<T> handler = new BeanHandler<>(clazz);

            T item = queryRunner.query(conn, sql, handler, args);

            return item;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, null, null);
        }

        return null;
    }

    public List<T> queryMany(String sql, Object... args) {
        Connection conn = null;

        try {
            conn = JDBCUtils.getConnection();

            BeanListHandler<T> handler = new BeanListHandler<>(clazz);

            List<T> list = queryRunner.query(conn, sql, handler, args);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, null, null);
        }

        return null;
    }

    public int execute(String sql, Object... args) {
        Connection conn = null;

        try {
            conn = JDBCUtils.getConnection();

            return queryRunner.execute(conn, sql, args);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, null, null);
        }

        return 0;
    }

    public <E> E queryOther(String sql, Object... args) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            ScalarHandler<E> handler = new ScalarHandler<>();
            E data = queryRunner.query(conn, sql, handler, args);

            return data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, null, null);
        }

        return null;
    }
}
