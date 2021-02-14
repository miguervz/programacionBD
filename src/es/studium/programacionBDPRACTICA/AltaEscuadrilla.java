package es.studium.programacionBDPRACTICA;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class AltaEscuadrilla extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	TextField numero = new TextField(20);
	TextField nComp = new TextField(20);
	Label lblnumero = new Label("Número de Escuadrilla");
	Label lblnComp = new Label("nº de componentes Escuadrilla");
	Button ok2 = new Button("Ok");
	Button ok = new Button("Ok");
	Button volver = new Button("Volver");
	Button insertar = new Button("Insertar");
	Button borrar = new Button("Borrar");
	Dialog d = new Dialog(this, "Operación Inserción", true);
	Dialog d2 = new Dialog(this, "Error", true);
	Label e = new Label ("Operación realizada correctamente!");
	Label e2 = new Label ("Revisa que has introducido los datos correctamente.");
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
	String login = "root";
	String password = "Studium2019;";
	Connection connection = null;
	Statement statement = null;
	public AltaEscuadrilla()
	{
		setLayout(new FlowLayout());
		setSize(230,260);
		setResizable(false);
		add(lblnumero);
		add(numero);
		add(lblnComp);
		add(nComp);
		setTitle("Alta");
		ok.addActionListener(this);
		ok2.addActionListener(this);
		add(insertar);
		add(borrar);
		add(volver);
		volver.addActionListener(this);
		setLocationRelativeTo(null);
		addWindowListener(this);
		insertar.addActionListener(this);
		borrar.addActionListener(this);
		d2.setLayout(new FlowLayout());
		d2.add(e2);
		d2.setSize(320,150);
		d2.setLocationRelativeTo(null);
		d2.addWindowListener(this);
		d.setLayout(new FlowLayout());
		d.add(e);
		d.setSize(250,150);
		d.setLocationRelativeTo(null);
		d.addWindowListener(this);
		d.add(ok);
		d2.add(ok2);
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
			statement =
					connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		setVisible(true);
	}

	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {
	}
	public void windowClosing(WindowEvent windowEvent)
	{if(this.isActive()) 
	{
		this.setVisible(false);
	}
	if(d.isActive())
	{
		d.setVisible(false);
	}
	if(d2.isActive())
	{
		d2.setVisible(false);
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
	public void actionPerformed(ActionEvent actionEvent)
	{
		if(insertar.equals(actionEvent.getSource()))
		{
			try
			{
			
				statement.executeUpdate("INSERT INTO escuadrillas VALUES (null,'"+numero.getText()+"','"+nComp.getText()+"')");
				
				try {
					Metodos.InfoLog("Alta: INSERT INTO escuadrillas VALUES (null,'"+numero.getText()+"','"+nComp.getText()+"')");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				nComp.setText("");
				numero.setText("");
				d.setVisible(true);
				d.setLocationRelativeTo(null);
			}
			catch(SQLException se)
			{
				try {
					Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				d2.setVisible(true);
			}}
		if(ok.equals(actionEvent.getSource()))
		{
			d.setVisible(false);
		}
		if(ok2.equals(actionEvent.getSource()))
		{
			d2.setVisible(false);
		}
		if(volver.equals(actionEvent.getSource()))
		{
			this.setVisible(false);
		}
		else
		{
			numero.getText();
			numero.setText("");
			nComp.getText();
			nComp.setText("");
		}
	}
}