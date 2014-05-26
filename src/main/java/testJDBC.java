import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class testJDBC {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws SQLException {
		try {
			Class.forName("com.mysql.JDBC.driver");
		} catch (Exception e) {
		}

		BoneCP connectionPool;
		Connection con;

		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		config.setUsername("root");
		config.setPassword("Fractal!");
		config.setPartitionCount(1);
		config.setMinConnectionsPerPartition(2);
		config.setMaxConnectionsPerPartition(5);
		ArrayList<Object[]> obj1 = new ArrayList<Object[]>();
		connectionPool = new BoneCP(config);
		con = connectionPool.getConnection();
		ResultSetHandler rsh = new ResultSetHandler() {
			ArrayList<Object[]> obj = new ArrayList<Object[]>();

			@Override
			public Object handle(ResultSet rs) throws SQLException {

				while (rs.next()) {
					System.out.println(rs.getString(2) + " " + rs.getString(3));
					Object temp[] = new Object[3];
					temp[0] = rs.getString(1);
					temp[1] = rs.getString(2);
					temp[2] = rs.getString(3);
					obj.add(temp);
				}

				return obj;
			}
		};
		if (con != null) {
			QueryRunner run = new QueryRunner();
			obj1 = (ArrayList<Object[]>) run.query(con, "SELECT * from user",
					rsh);
			for (Object[] i : obj1) {
				for (Object q : i) {
					System.out.print(q);
				}
				System.out.println();
			}
			System.out.println("Hi");
		}
	}
}
