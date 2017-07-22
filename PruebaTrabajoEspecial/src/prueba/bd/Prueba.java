package prueba.bd;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Prueba {

	private Connection conn;

	public Prueba() {
		conn = SQLiteConnection.getConnection();
	}
	public void listarUsuarios(){
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT	u.usuario AS NombreDeUsuario, u.contrasenia AS Contraseña"
				+ " FROM	usuarioinfo u ";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println("Usuario\tContrasenia");
			while (rs.next()) {
				String user = rs.getString(1);
				String pass = rs.getString(2);
				

				System.out.println(user+" "+pass);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	public boolean existeUsuario(String user, Integer pass) {
		PreparedStatement pstmt = null;
		ResultSet rs=null;

	try {
		
		pstmt = conn
				.prepareStatement("SELECT * FROM usuarioInfo WHERE usuario=? AND contrasenia=?");
		pstmt.setString(1, user);
		pstmt.setInt(2, pass);
		rs=pstmt.executeQuery();
		int count=0;
		while(rs.next()){
			count++;
		}
			
			if (count==1) {
				return true;
			}
			
	} catch (SQLException sqle) {
		sqle.printStackTrace();
	} finally {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		return false;


		 
	}
	public void agregarUsuario(String user, int pass) {
		PreparedStatement pstmt = null;

		try {
			
			pstmt = conn
					.prepareStatement("Insert into usuarioinfo values(?, ?);");
			pstmt.setString(1, user);
			pstmt.setInt(2, pass);
			pstmt.execute();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void actualizaEmpleadoDpto(String documento, String tipoDocumento,
			int nroDepto) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn
					.prepareStatement("Update Empleado set nroDepto = ? where documento = ? and tipoDocumento = ?;");
			pstmt.setInt(1, nroDepto);
			pstmt.setString(2, documento);
			pstmt.setString(3, tipoDocumento);
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void eliminaEmpleado(String documento, String tipoDocumento) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn
					.prepareStatement("Delete from Empleado where documento = ? and tipoDocumento = ?;");
			pstmt.setString(1, documento);
			pstmt.setString(2, tipoDocumento);
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void desconectar() {
		SQLiteConnection.close();
	}

	public static void main(String[] args) {

		Prueba prueba = new Prueba();
		//prueba.listarUsuarios();
		//prueba.agregarUsuario("carlitos", "alfajor");
		prueba.listarUsuarios();
		
		/*
		prueba.listarEmpleados();
		System.out.println("Agrego nuevo empleado...");
		prueba.agregarEmpleado("28976455", "DNI", "Saviola", "Juan", "Analista",
				"2016-01-01", 1);
		prueba.listarEmpleados();
		System.out.println("Actualizo departamento de empleado...");
		prueba.actualizaEmpleadoDpto("21235489", "DNI", 5);
		prueba.listarEmpleados();
		System.out.println("Elimino empleado...");
		prueba.eliminaEmpleado("28976455", "DNI");
		prueba.listarEmpleados();*/
		prueba.desconectar();
	}
}
