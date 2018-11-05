package Util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import javax.sql.rowset.CachedRowSet;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.rowset.CachedRowSetImpl;


public class DBQueryRunner {
	//private static Logger log = Logger.getLogger(DBQueryRunner.class);

	/**
	 * Execute a batch of SQL INSERT, UPDATE, or DELETE queries.
	 *
	 * @param conn
	 *            The Connection to use to run the query. The caller is
	 *            responsible for closing this Connection.
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            An array of query replacement parameters. Each row in this
	 *            array is one set of batch replacement values.
	 * @return The number of rows updated per statement.
	 * @throws java.sql.SQLException
	 *             if a database access error occurs
	 * @since DbUtils 1.1
	 */
	public static int[] batch(Connection conn, String sql, Object[][] params) throws SQLException {

		PreparedStatement stmt = null;
		int[] rows = null;
		try {
			stmt = conn.prepareStatement(sql);
			ParameterMetaData pmd = null;
			if (params.length > 0) {
				pmd = stmt.getParameterMetaData();
			}
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null && params.length > 0) {
					fillStatement(stmt, pmd, params[i]);
				}
				stmt.addBatch();
			}
			long startTime = System.currentTimeMillis();
			rows = stmt.executeBatch();
			printExecutionTime(startTime);

		} catch (SQLException e) {
			rethrow(e, sql, (Object[]) params);
		} finally {
			close(stmt);
		}

		return rows;
	}

	/**
	 * Fill the <code>PreparedStatement</code> replacement parameters with the
	 * given objects.
	 *
	 * @param stmt
	 *            PreparedStatement to fill
	 * @param params
	 *            Query replacement parameters; <code>null</code> is a valid
	 *            value to pass in.
	 * @throws java.sql.SQLException
	 *             if a database access error occurs
	 */
	public static void fillStatement(PreparedStatement stmt, ParameterMetaData pmd, Object... params)
		throws SQLException {
		if (params == null) {
			return;
		}

		if (pmd.getParameterCount() < params.length) {
			throw new SQLException(
				"Too many parameters: expected " + pmd.getParameterCount() + ", was given " + params.length);
		}

		boolean pmdKnownBroken = false;
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				//stmt.setObject(i + 1, params[i]);
				if (params[i] instanceof String) {
					stmt.setString(i + 1, params[i].toString());

				} else if (params[i] instanceof Integer) {
					stmt.setInt(i + 1, (Integer) params[i]);

				} else if (params[i] instanceof Long) {
					stmt.setLong(i + 1, (Long) params[i]);

				} else if (params[i] instanceof Timestamp) {
					stmt.setTimestamp(i + 1, (Timestamp) params[i]);

				} else if (params[i] instanceof Float) {
					stmt.setFloat(i + 1, (Float) params[i]);

				} else if (params[i] instanceof Double) {
					stmt.setDouble(i + 1, (Double) params[i]);

				} else if (params[i] instanceof BigDecimal) {
					stmt.setBigDecimal(i + 1, (BigDecimal) params[i]);

				} else if (params[i] instanceof Blob) {
					stmt.setBlob(i + 1, (Blob) params[i]);

				} else if (params[i] instanceof InputStream) {// 上傳檔案用
					try {
						stmt.setBinaryStream(i + 1, (InputStream) params[i], ((InputStream) params[i]).available());
					} catch (IOException e) {
						e.printStackTrace();
					}// 24648
					// pstmt.setBlob(idx++, (InputStream) o, ((InputStream)
					// o).available());//會丟出AbstractMethodError
				} else {
					System.out.println("無此型別:" + params[i].getClass().getName());

				}
			} else {
				// VARCHAR works with many drivers regardless
				// of the actual column type. Oddly, NULL and
				// OTHER don't work with Oracle's drivers.
				int sqlType = Types.VARCHAR;
				if (!pmdKnownBroken) {
					try {
						sqlType = pmd.getParameterType(i + 1);
					} catch (SQLException e) {
						pmdKnownBroken = true;
					}
				}
				stmt.setNull(i + 1, sqlType);
			}
		}
	}

	public static void fillStatement(PreparedStatement stmt, ParameterMetaData pmd, Collection<Object> params)
		throws SQLException {
		if (params == null) {
			return;
		}
		if (pmd.getParameterCount() < params.size()) {
			throw new SQLException(
				"Too many parameters: expected " + pmd.getParameterCount() + ", was given " + params.size());
		}

		boolean pmdKnownBroken = false;
		int i = 0;
		for (Object obj : params) {
			if (obj != null) {
				//stmt.setObject(i + 1, obj);//當型別是Date時會發生錯誤
				if (obj instanceof String) {
					stmt.setString(i + 1, obj.toString());

				} else if (obj instanceof Integer) {
					stmt.setInt(i + 1, (Integer) obj);

				} else if (obj instanceof Long) {
					stmt.setLong(i + 1, (Long) obj);

				} else if (obj instanceof Timestamp) {
					stmt.setTimestamp(i + 1, (Timestamp) obj);

				} else if (obj instanceof Float) {
					stmt.setFloat(i + 1, (Float) obj);

				} else if (obj instanceof Double) {
					stmt.setDouble(i + 1, (Double) obj);

				} else if (obj instanceof BigDecimal) {
					stmt.setBigDecimal(i + 1, (BigDecimal) obj);

				} else if (obj instanceof Blob) {
					stmt.setBlob(i + 1, (Blob) obj);

				} else if (obj instanceof InputStream) {// 上傳檔案用
					try {
						stmt.setBinaryStream(i + 1, (InputStream) obj, ((InputStream) obj).available());
					} catch (IOException e) {
						e.printStackTrace();
					}// 24648
					// pstmt.setBlob(idx++, (InputStream) o, ((InputStream)
					// o).available());//會丟出AbstractMethodError
				} else {
					System.out.println("無此型別:" + obj.getClass().getName());

				}
			} else {
				// VARCHAR works with many drivers regardless
				// of the actual column type. Oddly, NULL and
				// OTHER don't work with Oracle's drivers.
				int sqlType = Types.VARCHAR;
				if (!pmdKnownBroken) {
					try {
						sqlType = pmd.getParameterType(i + 1);
					} catch (SQLException e) {
						pmdKnownBroken = true;
					}
				}
				stmt.setNull(i + 1, sqlType);
			}
			i++;
		}
	}

	/**
	 * Throws a new exception with a more informative error message.
	 *
	 * @param cause
	 *            The original exception that will be chained to the new
	 *            exception when it's rethrown.
	 *
	 * @param sql
	 *            The query that was executing when the exception happened.
	 *
	 * @param params
	 *            The query replacement parameters; <code>null</code> is a valid
	 *            value to pass in.
	 *
	 * @throws java.sql.SQLException
	 *             if a database access error occurs
	 */
	private static void rethrow(SQLException cause, String sql, Object... params) throws SQLException {

		String causeMessage = cause.getMessage();
		if (causeMessage == null) {
			causeMessage = "";
		}
		StringBuffer msg = new StringBuffer(causeMessage);

		msg.append(" Query: ");
		msg.append(sql);
		msg.append(" Parameters: ");

		if (params == null) {
			msg.append("[]");
		} else {
			msg.append(Arrays.deepToString(params));
		}

		SQLException e = new SQLException(msg.toString(), cause.getSQLState(), cause.getErrorCode());
		e.setNextException(cause);

		throw e;
	}

	/**
	 * Execute an SQL INSERT, UPDATE, or DELETE query without replacement
	 * parameters.
	 *
	 * @param conn
	 *            The connection to use to run the query.
	 * @param sql
	 *            The SQL to execute.
	 * @return The number of rows updated.
	 * @throws java.sql.SQLException
	 *             if a database access error occurs
	 */
	public static int update(Connection conn, String sql) throws SQLException {
		return update(conn, sql, (Object[]) null);
	}

	public static int update(Connection conn, String sql, Collection<Object> params) throws SQLException {
		PreparedStatement stmt = null;
		int rows = 0;

		try {
			stmt = conn.prepareStatement(sql);
			if (params != null && params.size() > 0) {
				fillStatement(stmt, stmt.getParameterMetaData(), params);
			}
			printSql(sql, params);
			long startTime = System.currentTimeMillis();
			rows = stmt.executeUpdate();
			printExecutionTime(startTime);

		} catch (SQLException e) {
			rethrow(e, sql, params);
		} finally {
			close(stmt);
		}

		return rows;
	}

	/**
	 * Execute an SQL INSERT, UPDATE, or DELETE query.
	 *
	 * @param conn
	 *            The connection to use to run the query.
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            The query replacement parameters.
	 * @return The number of rows updated.
	 * @throws java.sql.SQLException
	 *             if a database access error occurs
	 */
	public static int update(Connection conn, String sql, Object... params) throws SQLException {

		PreparedStatement stmt = null;
		int rows = 0;

		try {
			stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				fillStatement(stmt, stmt.getParameterMetaData(), params);
			}
			printSql(sql, params);
			long startTime = System.currentTimeMillis();
			rows = stmt.executeUpdate();
			printExecutionTime(startTime);

		} catch (SQLException e) {
			rethrow(e, sql, params);
		} finally {
			close(stmt);
		}

		return rows;
	}


	/**
	 * Notice : 1.CachedRowSet.execute will Auto commit Connection. 2.Don't use
	 * this method to get DB lock(ex : select for update).
	 *
	 * @param conn
	 * @param sql
	 * @return
	 * @throws java.sql.SQLException
	 */
	public static CachedRowSet query(Connection conn, String sql) throws SQLException {
		return query(conn, sql, (Object[]) null);
	}

	/**
	 * Notice : 1.CachedRowSet.execute will Auto commit Connection. 2.Don't use
	 * this method to get DB lock(ex : select for update).
	 *
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws java.sql.SQLException
	 */
	public static CachedRowSet query(Connection conn, String sql, Collection<Object> params)
		throws SQLException {

		CachedRowSet rowset = new CachedRowSetImpl();
		rowset.setCommand(sql);

		if (params != null) {
			int i = 0;
			for (Object obj : params) {
				if (obj != null) {
					rowset.setObject(i + 1, obj);
				} else {
					// VARCHAR works with many drivers regardless
					// of the actual column type. Oddly, NULL and
					// OTHER don't work with Oracle's drivers.
					rowset.setNull(i + 1, Types.VARCHAR);
				}
				i++;
			}
		}
		printSql(sql, params);
		long startTime = System.currentTimeMillis();
		rowset.execute(conn);
		printExecutionTime(startTime);
		return rowset;
	}

	public static CachedRowSet query(Connection conn, String sql, Object... params) throws SQLException {

		CachedRowSet rowset = new CachedRowSetImpl();
		rowset.setCommand(sql);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null) {
					rowset.setObject(i + 1, params[i]);
				} else {
					// VARCHAR works with many drivers regardless
					// of the actual column type. Oddly, NULL and
					// OTHER don't work with Oracle's drivers.
					rowset.setNull(i + 1, Types.VARCHAR);
				}
			}
		}
		printSql(sql, params);
		long startTime = System.currentTimeMillis();
		rowset.execute(conn);
		printExecutionTime(startTime);
		return rowset;
	}

	/**
	 * 在SQL logger中印出SQL
	 * @param sql
	 * @param objs
	 * @return
	 */
	private static String printSql(String sql, Object... objs) {
		String tmp = sql;
		String value = "";
		String location = "";
		try {
			StackTraceElement[] ste = Thread.currentThread().getStackTrace();
			for(StackTraceElement e : ste){
				//LogUtils.sql.info("[location]=" + e.getClassName()+"."+e.getMethodName()+"():"+e.getLineNumber());
				if(!e.getClassName().startsWith("java.lang") && !e.getClassName().startsWith("com.nv.commons.model.database.DBQueryRunner")){
					location = e.getClassName()+"."+e.getMethodName()+"():"+e.getLineNumber();
					break;
				}
			}

			if (objs != null) {
				for (Object o : objs) {
					if (o instanceof String || o instanceof Timestamp) {
						value = "'" + o + "'";
					} else if (o instanceof InputStream) {
						value = "InputStream";
					} else {
						if (o == null) {
							value = "null";
						} else {
							value = o.toString();
						}

					}
					tmp = tmp.substring(0, tmp.indexOf("?")) + value
							+ tmp.substring(tmp.indexOf("?") + 1, tmp.length());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// log.info("[SQL]="+tmp);
		}

		return tmp;
	}

	/**
	 * 在SQL logger中印出SQL
	 * @param sql
	 * @param objs
	 * @return
	 */
	private static String printSql(String sql, Collection<Object> objs) {
		String tmp = sql;
		String value = "";
		String location = "";
		try {
			StackTraceElement[] ste = Thread.currentThread().getStackTrace();
			for(StackTraceElement e : ste){
				//LogUtils.sql.info("[location]=" + e.getClassName()+"."+e.getMethodName()+"():"+e.getLineNumber());
				if(!e.getClassName().startsWith("java.lang") && !e.getClassName().startsWith("com.nv.commons.model.database.DBQueryRunner")){

					location = e.getClassName()+"."+e.getMethodName()+"():"+e.getLineNumber();
					break;
				}
			}

			if (objs != null) {
				for (Object o : objs) {
					if (o instanceof String || o instanceof Timestamp) {
						value = "'" + o + "'";
					} else if (o instanceof InputStream) {
						value = "InputStream";
					} else {
						if (o == null) {
							value = "null";
						} else {
							value = o.toString();
						}

					}
					tmp = tmp.substring(0, tmp.indexOf("?")) + value
							+ tmp.substring(tmp.indexOf("?") + 1, tmp.length());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// log.info("[SQL]="+tmp);
		}

		return tmp;
	}

	public static final ArrayNode getJsonArray(Connection conn, String sql) throws SQLException {
		return getJsonArray(conn, sql, (Object[]) null);
	}

	public static final ArrayNode getJsonArray(Connection conn, String sql, Object... params)
		throws SQLException {
		ArrayNode jsonArray = JSONUtils.getObjectMapper().createArrayNode();
		ObjectNode jsonNode = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				fillStatement(stmt, stmt.getParameterMetaData(), params);
			}
			printSql(sql, params);
			long startTime = System.currentTimeMillis();
			rs = stmt.executeQuery();
			printExecutionTime(startTime);
			String columnName, columnValue = null;
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				jsonNode = JSONUtils.getObjectMapper().createObjectNode();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					if (rsmd.getColumnType(i + 1) != Types.BLOB) {// 非blob才轉成字串
						columnName = rsmd.getColumnName(i + 1);
						columnValue = rs.getString(columnName);
						jsonNode.put(columnName, columnValue);
					}
				}
				jsonArray.add(jsonNode);
			}
		} catch (SQLException e) {
			rethrow(e, sql, params);
		} finally {
			closeAll(stmt, rs);
		}
		return jsonArray;
	}

	public static final ArrayNode getJsonArray(Connection conn, String sql, Collection<Object> values)
		throws SQLException {
		ArrayNode jsonArray = JSONUtils.getObjectMapper().createArrayNode();
		ObjectNode jsonNode = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			if (values != null && values.size() > 0) {
				fillStatement(stmt, stmt.getParameterMetaData(), values);
			}
			printSql(sql, values);
			long startTime = System.currentTimeMillis();
			rs = stmt.executeQuery();
			printExecutionTime(startTime);
			String columnName, columnValue = null;
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				jsonNode = JSONUtils.getObjectMapper().createObjectNode();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					if (rsmd.getColumnType(i + 1) != Types.BLOB) {// 非blob才轉成字串
						columnName = rsmd.getColumnName(i + 1);
						columnValue = rs.getString(columnName);
						jsonNode.put(columnName, columnValue);
					}
				}
				jsonArray.add(jsonNode);
			}
		} catch (SQLException e) {
			rethrow(e, sql, values);
		} finally {
			closeAll(stmt, rs);
		}
		return jsonArray;
	}

	public static Number getNumber(Connection conn, String sql) throws SQLException {
		return getNumber(conn, sql, (Object[]) null);
	}

	public static Number getNumber(Connection conn, String sql, Object... values) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			if (values != null && values.length > 0) {
				fillStatement(stmt, stmt.getParameterMetaData(), values);
			}
			printSql(sql, values);
			long startTime = System.currentTimeMillis();
			rs = stmt.executeQuery();
			printExecutionTime(startTime);
			if (rs.next()) {
				return (Number) rs.getObject(1);
			}
		} catch (SQLException e) {
			rethrow(e, sql, values);
		} finally {
			close(stmt);
			close(rs);
		}
		return null;
	}

	public static Number getNumber(Connection conn, String sql, Collection<Object> values)
		throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			if (values != null && values.size() > 0) {
				fillStatement(stmt, stmt.getParameterMetaData(), values);
			}
			printSql(sql, values);
			long startTime = System.currentTimeMillis();
			rs = stmt.executeQuery();
			printExecutionTime(startTime);
			if (rs.next()) {
				return (Number) rs.getObject(1);
			}
		} catch (SQLException e) {
			rethrow(e, sql, values);
		} finally {
			close(stmt);
			close(rs);
		}
		return null;
	}

	public static String getString(Connection conn, String sql) throws SQLException {
		return getString(conn, sql, (Object[]) null);
	}

	public static String getString(Connection conn, String sql, Object... values) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			if (values != null && values.length > 0) {
				fillStatement(stmt, stmt.getParameterMetaData(), values);
			}
			printSql(sql, values);
			long startTime = System.currentTimeMillis();
			rs = stmt.executeQuery();
			printExecutionTime(startTime);
			if (rs.next()) {
				return (String) rs.getObject(1);
			}
		} catch (SQLException e) {
			rethrow(e, sql, values);
		} finally {
			close(stmt);
			close(rs);
		}
		return null;
	}

	public static String getString(Connection conn, String sql, Collection<Object> values)
		throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			if (values != null && values.size() > 0) {
				fillStatement(stmt, stmt.getParameterMetaData(), values);
			}
			printSql(sql, values);
			long startTime = System.currentTimeMillis();
			rs = stmt.executeQuery();
			printExecutionTime(startTime);
			if (rs.next()) {
				return (String) rs.getObject(1);
			}
		} catch (SQLException e) {
			rethrow(e, sql, values);
		} finally {
			close(stmt);
			close(rs);
		}
		return null;
	}

	/**
	 * 執行時間>500毫秒才印出執行時間
	 * @param startTime
	 */
	private static void printExecutionTime(long startTime){
		long diff = System.currentTimeMillis()-startTime;
		if(diff > 500){
		}
	}


    /**
     * Close connection
     *
     * @param conn
     */
    public static void close(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            if(!conn.isClosed()){
                conn.clearWarnings();
            }
            //For PostgreSQL
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public static void close(Collection<Connection> conns) {
        for(Connection conn : conns) {
            close(conn);
        }
    }

    public static void close(CallableStatement cstmt) {
        if (cstmt == null) {
            return;
        }
        try {
            cstmt.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    /**
     * Close statement
     *
     * @param stmt
     */
    public static void close(Statement stmt) {
        if (stmt == null) {
            return;
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public static void close(ResultSet rs) {
        if (rs == null) {
            return;
        }
        try {
            rs.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }


    public static void closeAll(Connection conn, Statement stmt, Statement ps) {
        close(ps);
        close(stmt);
        close(conn);
    }

    public static void closeAll(Connection conn, Statement ps) {
        close(ps);
        close(conn);
    }

    public static void closeAll(Connection conn, CallableStatement ps) {
        close(ps);
        close(conn);
    }

    public static void closeAll(Connection conn, CachedRowSet crs) {
        close(crs);
        close(conn);
    }

    public static void closeAll(Connection conn, ResultSet rs) {
        close(rs);
        close(conn);
    }

    public static void closeAll(Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
    }

}
