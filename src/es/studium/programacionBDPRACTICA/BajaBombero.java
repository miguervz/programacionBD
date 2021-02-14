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
public class BajaBombero extends Frame implements WindowListener, ActionListener, ItemListener
{
	private static final long serialVersionUID = 1L;
	Choice lista = new Choice();
	ResultSet rs = null;
	Button ok = new Button("Ok");
	Button baja = new Button("Baja");
	Button aceptar = new Button("Volver");
	Dialog dExito = new Dialog(this, "Operación Baja", true);
	Dialog dConfirmacion = new Dialog(this, "Confirmación Baja", true);
	Button si = new Button("Sí");
	Button volver = new Button("Volver");
	Label lblExito = new Label ("Operación realizada correctamente!");
	Label lblConfirmacion = new Label ("¿Está seguro de que quiere realizar esa operación?");
	Label lblConfirmacion2 = new Label ("Si borra un bombero, también eliminará el vehículo asignado a él de la BD.");

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
	String login = "root";
	String password = "Studium2019;";
	Connection connection = null;
	Statement statement = null;
	String id=new String();
	public BajaBombero()
	{
		setTitle("Baja");
		setLayout(new FlowLayout());
		setSize(230,360);
		setResizable(false);
		add(lista);
		add(baja);
		add(aceptar);
		setLocationRelativeTo(null);
		ok.addActionListener(this);
		baja.addActionListener(this);
		aceptar.addActionListener(this);
		si.addActionListener(this);
		volver.addActionListener(this);
		addWindowListener(this);
		dExito.setLayout(new FlowLayout());
		dExito.add(lblExito);
		dExito.setSize(320,150);
		dExito.addWindowListener(this);
		dExito.setLocationRelativeTo(null);
		dExito.add(ok);
		dConfirmacion.setLayout(new FlowLayout());
		dConfirmacion.add(lblConfirmacion);
		dConfirmacion.add(lblConfirmacion2);
		dConfirmacion.setSize(480,150);
		dConfirmacion.addWindowListener(this);
		dConfirmacion.setLocationRelativeTo(null);
		dConfirmacion.add(si);
		dConfirmacion.add(volver);
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
			rs=statement.executeQuery("SELECT* FROM bomberos");
			while(rs.next())
			{
				lista.add(rs.getInt("idBombero")+" "+rs.getString("nombreBombero")+" "+rs.getString("apellidosBombero")+"\n");
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
		if(dConfirmacion.isActive()) {
			dConfirmacion.setVisible(false);
		}
		if(dExito.isActive()) {
			dExito.setVisible(false);
		}}
		
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
	public void itemStateChanged(ItemEvent ie) {
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
		if(ok.equals(actionEvent.getSource())) {
			dExito.setVisible(false);
		}
		if(volver.equals(actionEvent.getSource())) {
			dConfirmacion.setVisible(false);
		}
		if(si.equals(actionEvent.getSource())) {
			dConfirmacion.setVisible(false);
			String nombre = new String(lista.getSelectedItem());
			int posicion = nombre.indexOf(" ");
			id = nombre.substring(0, posicion);
			try
			{
				
				statement.executeUpdate("DELETE FROM bomberos WHERE idBombero="+id);
				try {
					Metodos.InfoLog("Baja: DELETE FROM bomberos WHERE idBombero="+id);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dExito.setVisible(true);
			}
			catch(SQLException se)
			{try {
				Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				System.out.println("Error en la sentencia SQL"+se.toString());
			}
			this.setVisible(false);
			new BajaBombero();
		}
	}
}