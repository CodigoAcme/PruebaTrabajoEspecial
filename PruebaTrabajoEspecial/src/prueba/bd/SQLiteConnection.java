package prueba.bd;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {

	private static Connection conn=null;

	public static Connection getConnection() {

		try {
			if (conn == null) {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager
						.getConnection("jdbc:sqlite:usuario.sqlite");
				/*
				conn = DriverManager
						.getConnection("jdbc:sqlite:/home/ruben/git/PruebaTrabajoEspecial/PruebaTrabajoEspecial/usuario.sqlite");
						*/
				System.out
						.println("** La conexi�n se ha realizado con �xito.**\nInformaci�n de la Metadata");
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println("\tDriver name: " + dm.getDriverName());
				System.out
						.println("\tDriver version: " + dm.getDriverVersion());
				System.out.println("\tProduct name: "
						+ dm.getDatabaseProductName());
				System.out.println("\tProduct version: "
						+ dm.getDatabaseProductVersion());
				System.out.println();
			} else
				System.out.println("La conexi�n ya se encuentra realizada.");
		} catch (ClassNotFoundException cnfe) {
			System.err.println("No se encuentra el Driver.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return conn;
	}

	public static void close() {
		try {
			if (conn != null) {
				conn.close();
				System.out.println("** La Desconexi�n de la BD exitosa. **");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public static void main(String args[]) {
		SQLiteConnection.getConnection();
		SQLiteConnection.close();
	}

}
