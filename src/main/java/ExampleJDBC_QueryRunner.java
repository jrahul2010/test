import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ExampleJDBC_QueryRunner {

	public static void main(String[] args) {
		BoneCP connectionPool = null;
		Connection connection = null;

		try {
			// load the database driver (make sure this is in your classpath!)
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			// setup the connection pool
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:mysql://localhost:3306/test"); // jdbc url
																	// specific
																	// to
			// your database, eg
			// jdbc:mysql://127.0.0.1/yourdb
			config.setUsername("root");
			config.setPassword("Fractal!");
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config); // setup the connection pool

			connection = connectionPool.getConnection(); // fetch a connection

			if (connection != null) {
				System.out.println("Connection successful!");

				/*****************************************************************************************************************************/
				try {
				

					QueryRunner run = new QueryRunner();
					Object[][] obj = new Object[1][2];
					obj[0][0] = "qa";
					obj[0][1] = "qa";
					run.batch(connection,
							"INSERT INTO USER(username,password) VALUES(?,?)",
							obj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						DbUtils.close(connection);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}

			connectionPool.shutdown(); // shutdown connection pool.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static String getQuery(String tableName) {
		return "SELECT * FROM `test`" + "." + tableName;
	}

}