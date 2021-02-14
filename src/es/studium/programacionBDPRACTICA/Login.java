package es.studium.programacionBDPRACTICA;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Login extends WindowAdapter implements ActionListener, KeyListener
{
	//Declaramos las variables
	public static String usuarioLog="";
	Frame login = new Frame("Login");
	Frame menuPrincipal = new Frame("Menú Principal");
	Dialog errorLogin = new Dialog(login,"ERROR", true);
	Label lblUsuario = new Label("Usuario:");
	Label lblClave = new Label("Clave:");
	TextField txtUsuario = new TextField(20); 
	TextField txtClave = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	
	
	Button btnLimpiar = new Button("Limpiar");
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
	String usuario = "root";
	String clave = "Studium2019;";
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	Login()
	{
		//Les damos valores
		login.setLayout(new FlowLayout());
		login.setSize(270, 150);
		login.setResizable(false);
		login.setLocationRelativeTo(null);
		login.addWindowListener(this);
		btnAceptar.addActionListener(this);
		txtClave.addKeyListener(this);
		txtUsuario.addKeyListener(this);
		btnLimpiar.addActionListener(this);
		login.add(lblUsuario);
		login.add(txtUsuario);
		login.add(lblClave);
		txtClave.setEchoChar('*');
		login.add(txtClave);
		login.add(btnAceptar);
		login.add(btnLimpiar);
		login.setVisible(true);
	}
	//Aqui ejecutamos
	public static void main(String[] args)
	{
		new Login();
	}
	//Le damos valores a los botones
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnLimpiar)) 
		{
			txtUsuario.selectAll();
			txtUsuario.setText("");
			txtClave.selectAll();
			txtClave.setText("");
			txtUsuario.requestFocus();
		}
		else if(evento.getSource().equals(btnAceptar)) 
		{  	usuarioLog=txtUsuario.getText();
			String cadenaEncriptada = getSHA256(txtClave.getText());
			sentencia = "SELECT * FROM usuarios WHERE ";
			sentencia += "nombreUsuario = '"+txtUsuario.getText()+"'";
			sentencia += " AND claveUsuario = '"+cadenaEncriptada+"'";
			try
			{
				Class.forName(driver);
				connection = DriverManager.getConnection(url, usuario, clave);
				statement = connection.createStatement();
				rs = statement.executeQuery(sentencia);
				if(rs.next())
				{usuarioLog=txtUsuario.getText();
					if(rs.getString("tipoUsuario").equals("Admin")) {
						
						try {
							Metodos.InfoLog("Nuevo acceso");
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
						
						new MenuPrincipal();
					}
					else {
						try {
							Metodos.InfoLog("Nuevo acceso");
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
						new MenuPrincipal2();}
					login.setVisible(false);
				}
				else
				{
					try {
						Metodos.InfoLog("ERROR- Acceso denegado.");
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
					errorLogin.setLayout(new FlowLayout());
					errorLogin.setSize(160, 90);
					errorLogin.setResizable(false);
					errorLogin.addWindowListener(this);
					errorLogin.add(new Label("Credenciales Incorrectas"));
					Button btnVolver = new Button("Volver");
					btnVolver.addActionListener(this);
					errorLogin.add(btnVolver);
					errorLogin.setLocationRelativeTo(null);
					errorLogin.setVisible(true);
				}
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}
			finally
			{
				try
				{
					if(connection!=null)
					{
						connection.close();
					}
				}
				catch (SQLException e)
				{
					System.out.println("Error 3-"+e.getMessage());
				}
			}}
		else
		{
			errorLogin.setVisible(false);
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			usuarioLog=txtUsuario.getText();
			String cadenaEncriptada = getSHA256(txtClave.getText());
			sentencia = "SELECT * FROM usuarios WHERE ";
			sentencia += "nombreUsuario = '"+txtUsuario.getText()+"'";
			sentencia += " AND claveUsuario = '"+cadenaEncriptada+"'";
			try
			{
				Class.forName(driver);
				connection = DriverManager.getConnection(url, usuario, clave);
				statement = connection.createStatement();
				rs = statement.executeQuery(sentencia);
				if(rs.next())
				{usuarioLog=txtUsuario.getText();
					if(rs.getString("tipoUsuario").equals("Admin")) {
						try {
							Metodos.InfoLog("Nuevo acceso");
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
						new MenuPrincipal();
					}
					else {
						try {
							Metodos.InfoLog("Nuevo acceso");
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
						new MenuPrincipal2();}
					login.setVisible(false);
				}
				else
				{
					try {
						Metodos.InfoLog("Error- Acceso denegado.");
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
					errorLogin.setLayout(new FlowLayout());
					errorLogin.setSize(160, 90);
					errorLogin.setResizable(false);
					errorLogin.addWindowListener(this);
					errorLogin.add(new Label("Credenciales Incorrectas"));
					Button btnVolver = new Button("Volver");
					btnVolver.addActionListener(this);
					errorLogin.add(btnVolver);
					errorLogin.setLocationRelativeTo(null);
					errorLogin.setVisible(true);
				}
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}
			finally
			{
				try
				{
					if(connection!=null)
					{
						connection.close();
					}
				}
				catch (SQLException u)
				{
					System.out.println("Error 3-"+u.getMessage());
				}
			}
		}
		else
		{
			errorLogin.setVisible(false);
		}
	}
	public void keyTyped(KeyEvent e) {
	}

	public static String getSHA256(String data)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(data.getBytes());
			byte byteData[] = md.digest();
			for (int i = 0; i < byteData.length; i++) 
			{
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100,
						16).substring(1));
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}
	public void windowClosing(WindowEvent arg0)
	{
		if(errorLogin.isActive())
		{
			errorLogin.setVisible(false);
		}
		else
		{
			System.exit(0);
		}
	}
}