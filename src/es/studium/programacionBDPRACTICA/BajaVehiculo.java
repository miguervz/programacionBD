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
public class BajaVehiculo extends Frame implements WindowListener, ActionListener, ItemListener
{
	private static final long serialVersionUID = 1L;
	Choice lista = new Choice();
	ResultSet rs = null;
	Button ok=new Button("Ok");
	Button baja = new Button("Baja");
	Button aceptar = new Button("Aceptar");
	Dialog d1 = new Dialog(this, "Confirmación Baja", true);
	Button si = new Button("Sí");
	Button volver = new Button("Volver");
	Label e1 = new Label ("¿Está seguro de que quiere realizar esa operación?");
	Dialog d = new Dialog(this, "Operación Inserción", true);
	Label e = new Label ("Operación realizada correctamente!");
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
	String login = "root";
	String password = "Studium2019;";
	Connection connection = null;
	Statement statement = null;
	String id=new String();
	public BajaVehiculo()
	{
		setTitle("Baja");
		setLayout(new FlowLayout());
		setSize(290,160);
		setResizable(false);
		add(lista);
		add(baja);
		add(aceptar);
		setLocationRelativeTo(null);
		baja.addActionListener(this);
		aceptar.addActionListener(this);
		addWindowListener(this);
		d.setLayout(new FlowLayout());
		d.add(e);
		d.setSize(250,150);
		d.addWindowListener(this);
		d.setLocationRelativeTo(null);
		d1.setLayout(new FlowLayout());
		d1.add(e1);
		d1.setSize(320,150);
		d1.addWindowListener(this);
		d1.setLocationRelativeTo(null);
		d1.add(si);
		d1.add(volver);
		si.addActionListener(this);
		volver.addActionListener(this);
		lista.addItemListener(this);
		d.add(ok);
		ok.addActionListener(this);
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
			rs=statement.executeQuery("SELECT* FROM vehiculos");
			while(rs.next())
			{
				lista.add(rs.getInt("idVehiculo")+" "+rs.getString("tipoVehiculo")+" con matrícula "+rs.getString("matriculaVehiculo")+"\n");
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
		if(d.isActive())
		{
			d.setVisible(false);
		}
		if(d1.isActive())
		{
			d1.setVisible(false);
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
		if(si.equals(actionEvent.getSource()))
		{
			String nombre = new String(lista.getSelectedItem());
			int posicion = nombre.indexOf(" ");
			id = nombre.substring(0, posicion);	
			try
			{
				
				statement.executeUpdate("DELETE FROM vehiculos WHERE idVehiculo="+id);
				try {
					Metodos.InfoLog("Baja: DELETE FROM vehiculos WHERE idVehiculo="+id);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				d.setVisible(true);
				d1.setVisible(false);
			}
			catch(SQLException se)
			{
				try {
					Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Error en la sentencia SQL"+se.toString());
			}
			this.setVisible(false);
			new BajaVehiculo();
		}
		if(aceptar.equals(actionEvent.getSource())) {
			this.setVisible(false);
		}
		if(baja.equals(actionEvent.getSource())) {
			d1.setVisible(true);
		}
		if(ok.equals(actionEvent.getSource()))
		{d.setVisible(false);
		}
		if(volver.equals(actionEvent.getSource()))
		{d1.setVisible(false);
		}
	}
}