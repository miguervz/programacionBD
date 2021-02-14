package es.studium.programacionBDPRACTICA;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
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
public class ModificacionEscuadrilla extends Frame implements WindowListener, ActionListener, ItemListener
{
	private static final long serialVersionUID = 1L;
	
	TextField numeroEscuadrilla = new TextField(20);
	TextField numeroComponente = new TextField(20);
	Label lblnSquad = new Label("Número de escuadrilla");
	Label lblnComp = new Label("nº de componentes de la escuadrilla");

	Choice lista = new Choice();
	
	Button modificar = new Button("Modificar");
	Button btnVolver = new Button("Volver");
	
	Dialog dConfirmacion= new Dialog(this, "Confirmar", true);
	Label lblConfirmacion = new Label ("¿Seguro que quiere llevar a cabo esta operación?");
	Button btnVolverConfirmacion = new Button("Volver");
	Button btnOkConfirmacion = new Button("Ok");
	
	Dialog dError = new Dialog(this, "Error", true);
	Label lblError = new Label ("Revisa los campos.Usa una escuadrilla existente.");
	Button btnOkError = new Button("Ok");
	
	Dialog dExito = new Dialog(this, "Operación Inserción", true);
	Label eExito = new Label ("Operación realizada correctamente!");
	Button btnOkExito = new Button("Ok");
	
	
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
	String login = "root";
	String password = "Studium2019;";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	String id;
	public ModificacionEscuadrilla()
	{
	setLayout(new FlowLayout());
		setSize(252,400);
		setTitle("Modificar");
	setResizable(false);
	add(lista);
	setLocationRelativeTo(null);
	
	modificar.addActionListener(this);
	btnVolver.addActionListener(this);
	addWindowListener(this);

	
	dConfirmacion.setLayout(new FlowLayout());
	dConfirmacion.setSize(290,150);
	dConfirmacion.setResizable(false);
	dConfirmacion.addWindowListener(this);
	dConfirmacion.setLocationRelativeTo(null);
	dConfirmacion.add(lblConfirmacion);
	dConfirmacion.add(btnOkConfirmacion);
	btnOkConfirmacion.addActionListener(this);
	dConfirmacion.add(btnVolverConfirmacion);
	btnVolverConfirmacion.addActionListener(this);


	dError.setLayout(new FlowLayout());
	dError.setSize(290,150);
	dError.addWindowListener(this);
	dError.setLocationRelativeTo(null);
	dError.add(lblError);
	dError.add(btnOkError);
	btnOkError.addActionListener(this);
	
	dExito.setLayout(new FlowLayout());
	dExito.setSize(250,130);
	dExito.addWindowListener(this);
	dExito.setLocationRelativeTo(null);
	dExito.add(eExito);
	dExito.add(btnOkExito);
	btnOkExito.addActionListener(this);
	lista.addItemListener(this);
	
	add(lblnSquad);
	add(numeroEscuadrilla);
	add(lblnComp);
	add(numeroComponente);
	add(modificar);
	add(btnVolver);
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
			lista.add(rs.getInt("idEscuadrilla")+" Escuadrilla número "+rs.getString("numeroEscuadrilla"));
		}
	}
	catch(SQLException e)
	{
		System.out.println("Error en la sentencia SQL");
	}
	try
	{
		statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		rs=statement.executeQuery("SELECT* FROM escuadrillas");
		if(rs.next())
		{
			numeroEscuadrilla.setText(rs.getString("numeroEscuadrilla"));
			numeroComponente.setText(rs.getString("numeroComponentesEscuadrilla"));	
		}}
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
		{	try
		{
			statement.close();
			connection.close();
		}
		catch(SQLException e)
		{
			System.out.println("Error al cerrar "+e.toString());
		}dispose();
		new ModificacionEscuadrilla();

		}
		if(dConfirmacion.isActive())
		{
			dispose();
			new ModificacionEscuadrilla();
		}
		if(dError.isActive())
		{
			dispose();
			new ModificacionEscuadrilla();
		}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
	public void itemStateChanged(ItemEvent ie) {
		String nombreItem = new String(lista.getSelectedItem());
		int posicion = nombreItem.indexOf(" ");
		id = nombreItem.substring(0, posicion);
		try
		{
			statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=statement.executeQuery("SELECT* FROM escuadrillas WHERE idEscuadrilla="+id);
			if(rs.next())
			{
				numeroEscuadrilla.setText(rs.getString("numeroEscuadrilla"));
				numeroComponente.setText(rs.getString("numeroComponentesEscuadrilla"));	
			}}
		catch(SQLException e)
		{
			System.out.println("Error en la sentenciaj SQL");
		}	
	}
	public void actionPerformed(ActionEvent actionEvent)
	{
		if(modificar.equals(actionEvent.getSource()))
		{dConfirmacion.setVisible(true);
		}
		if(btnVolver.equals(actionEvent.getSource())) {
			this.setVisible(false);
		}
		if(btnOkConfirmacion.equals(actionEvent.getSource())) {
			String nombre1 = new String(lista.getSelectedItem());
			int posicion = nombre1.indexOf(" ");
			id = nombre1.substring(0, posicion);
			try
			{
				statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate("UPDATE escuadrillas SET numeroEscuadrilla='"+numeroEscuadrilla.getText()+"',numeroComponentesEscuadrilla='"+numeroComponente.getText()+"' where idEscuadrilla="+id);
				try {
					Metodos.InfoLog("Modificación: UPDATE escuadrillas SET numeroEscuadrilla='"+numeroEscuadrilla.getText()+"',numeroComponentesEscuadrilla='"+numeroComponente.getText()+"' where idEscuadrilla="+id);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				if(rs.next())
				{
					numeroEscuadrilla.setText(rs.getString("numeroEscuadrilla"));
					numeroComponente.setText(rs.getString("numeroComponentesEscuadrilla"));	
				}}
			catch(SQLException e)
			{try {
				Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dError.setVisible(true);
			}
			

			dExito.setVisible(true);

		}
		
		if(btnVolverConfirmacion.equals(actionEvent.getSource())) {
			dConfirmacion.setVisible(false);
		}
		if(btnOkError.equals(actionEvent.getSource()))
		{	this.setVisible(false);
		new ModificacionEscuadrilla();
		}
		if(btnOkExito.equals(actionEvent.getSource()))
		{	this.setVisible(false);
		new ModificacionEscuadrilla();
		}
	}
}