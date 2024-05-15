import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PgConnection implements DbConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/Task3_1_real";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "081273";

    private static PgConnection instance;

    private Connection connection;

    static {
        instance = new PgConnection();
    }

    public static PgConnection getInstance() {
        return instance;
    }

    private PgConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            var DDL = "CREATE TABLE IF NOT EXISTS CACHED (id SERIAL PRIMARY KEY, name TEXT, VAL TEXT)";
            var statement = connection.createStatement();
            statement.execute(DDL);


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(String key) {
        var sqlInsert = "select c.val from cached c where c.name = ?";
        try {
            var statement = connection.prepareStatement(sqlInsert);
            statement.setString(1, key);
            var res = statement.executeQuery();
            if (res.next()) {
                return res.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void put(String key, String value) {
        var sqlInsert = "Insert into cached (name,val) values (? , ?)";
        try {
            var statement = connection.prepareStatement(sqlInsert);
            statement.setString(1, key);
            statement.setString(2, value);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
