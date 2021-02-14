package es.studium.programacionBDPRACTICA;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class BajaEscuadrilla extends Frame implements WindowListener, ActionListener, ItemListener
{
	private static final long serialVersionUID = 1L;
	Choice lista = new Choice();
	ResultSet rs = null;
	Button baja = new Button("Baja");
	Button okExito = new Button("Ok");
	Button okConfirmacion = new Button("Ok");
	Button volverConfirmacion = new Button("Volver");
	Button aceptar = new Button("Volver");
	Dialog dExito = new Dialog(this, "Operación Inserción", true);
	Label lblExito = new Label ("Operación realizada correctamente!");
	Dialog dConfirmacion = new Dialog(this, "Operación Inserción", true);
	Label lblConfirmacion = new Label ("¿Seguro que quiere eliminar esta escuadrilla?");
	Label lblConfirmacion2 = new Label ("Si borra una escuadrilla, también eliminará a sus pertenecientes de la base de datos");
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
	String login = "root";
	String password = "Studium2019;";
	Connection connection = null;
	Statement statement = null;
	String id=new String();
	public BajaEscuadrilla()
	{
		setLayout(new FlowLayout());
		setSize(300,160);
		setResizable(false);
		addWindowListener(this);
		setTitle("Baja Escuadrilla");
		add(lista);
		add(baja);
		add(aceptar);
		setLocationRelativeTo(null);
		okConfirmacion.addActionListener(this);
		volverConfirmacion.addActionListener(this);
		okExito.addActionListener(this);
		baja.addActionListener(this);
		aceptar.addActionListener(this);
		
		dExito.setLayout(new FlowLayout());
		dExito.add(lblExito);
		dExito.setSize(250,150);
		dExito.addWindowListener(this);
		dExito.setLocationRelativeTo(null);
		dExito.add(okExito);
		
		dConfirmacion.setLayout(new FlowLayout());
		dConfirmacion.add(lblConfirmacion);
		dConfirmacion.add(lblConfirmacion2);
		dConfirmacion.add(okConfirmacion);
		dConfirmacion.add(volverConfirmacion);
		dConfirmacion.setSize(480,150);
		dConfirmacion.addWindowListener(this);
		dConfirmacion.setLocationRelativeTo(null);
		lista.addItemListener(this);
		try
		{
			Class.forName(driver);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Se ha producido un error al cargar el Driver");
		}
		try
		{
			connection = DriverManager.getConnection(url, login, password);
		}
		catch(SQLException e)
		{
			System.out.println("Se produjo un error al conectar a la Base de Datos");
		}
		try
		{
			statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=statement.executeQuery("SELECT* FROM escuadrillas");
			while(rs.next())
			{
				lista.add(rs.getInt("idEscuadrilla")+" Escuadrilla "+rs.getString("numeroEscuadrilla"));
			}
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		setVisible(true);
	}

	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent windowEvent)
	{
		if(this.isActive()) {
			this.setVisible(false);
		}
		if(dExito.isActive())
		{
			dExito.setVisible(false);
		}
		if(dConfirmacion.isActive())
		{
			dConfirmacion.setVisible(false);
		}
		else
		{
			try
			{
				statement.close();
				connection.close();
			}
			catch(SQLException e)
			{
				System.out.println("Error al cerrar "+e.toString());
			}
		}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
	public void itemStateChanged(ItemEvent ie) {
		String nombre = new String(lista.getSelectedItem());
		int posicion = nombre.indexOf(" ");
		id = nombre.substring(0, posicion);	
	}
	public void actionPerformed(ActionEvent actionEvent)
	{
		if(baja.equals(actionEvent.getSource()))
		{
			dConfirmacion.setVisible(true);
		}
		if(aceptar.equals(actionEvent.getSource())) {
			this.setVisible(false);
		}
		if(volverConfirmacion.equals(actionEvent.getSource())) {
			dConfirmacion.setVisible(false);
		}
		if(okExito.equals(actionEvent.getSource())) {
			if(dExito.isActive())
			{
				dExito.setVisible(false);
			}
		}
		if(okConfirmacion.equals(actionEvent.getSource())) {
			try
			{
				String nombre = new String(lista.getSelectedItem());
				int posicion = nombre.indexOf(" ");
				id = nombre.substring(0, posicion);	
				statement.executeUpdate("DELETE FROM escuadrillas WHERE idEscuadrilla="+id);
				try {
					Metodos.InfoLog("Baja: DELETE FROM escuadrillas WHERE idEscuadrilla="+id);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dExito.setVisible(true);
			}
			catch(SQLException se)
			{
				try {
					Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dConfirmacion.setVisible(true);
			}
			this.setVisible(false);
			new BajaEscuadrilla();	
		}
	}
}