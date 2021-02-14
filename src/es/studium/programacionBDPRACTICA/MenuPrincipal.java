package es.studium.programacionBDPRACTICA;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
public  class MenuPrincipal extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	MenuBar mnbMenu = new MenuBar();
	Menu mn1 = new Menu("Bomberos");
	Menu mn2 = new Menu("Vehículos");
	Menu mn3 = new Menu("Escuadrillas");
	MenuItem mn11 = new MenuItem("Alta");
	MenuItem mn12 = new MenuItem("Baja");
	MenuItem mn13 = new MenuItem("Modificar");
	MenuItem mn14 = new MenuItem("Consultar");
	MenuItem mn21 = new MenuItem("Alta");
	MenuItem mn22 = new MenuItem("Baja");
	MenuItem mn23 = new MenuItem("Modificar");
	MenuItem mn24 = new MenuItem("Consultar");
	MenuItem mn31 = new MenuItem("Alta");
	MenuItem mn32 = new MenuItem("Baja");
	MenuItem mn33 = new MenuItem("Modificar");
	MenuItem mn34 = new MenuItem("Consultar");
	Button btnAyuda= new Button("Ayuda");
	public MenuPrincipal()
	{
		setLayout(new FlowLayout());
		setTitle("Bomberos");
		setMenuBar(mnbMenu);
		setSize(250,180);
		addWindowListener(this);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		mn1.add(mn11);
		mn1.add(mn12);
		mn1.add(mn13);
		mn1.add(mn14);
		mn2.add(mn21);
		mn2.add(mn22);
		mn2.add(mn23);
		mn2.add(mn24);	
		mn3.add(mn31);
		mn3.add(mn32);
		mn3.add(mn33);
		mn3.add(mn34);
		mnbMenu.add(mn1);
		mnbMenu.add(mn2);
		mnbMenu.add(mn3);
		add(btnAyuda);
		btnAyuda.addActionListener(this);
		mn11.addActionListener(this);
		mn12.addActionListener(this);
		mn13.addActionListener(this);
		mn14.addActionListener(this);
		mn21.addActionListener(this);
		mn22.addActionListener(this);
		mn23.addActionListener(this);
		mn24.addActionListener(this);
		mn31.addActionListener(this);
		mn32.addActionListener(this);
		mn33.addActionListener(this);
		mn34.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object objetoPulsado=ae.getSource();
		if(objetoPulsado.equals(mn11))
		{new AltaBombero();}
		if(objetoPulsado.equals(mn12))
		{new BajaBombero();}
		if(objetoPulsado.equals(mn13))
		{new ModificacionBombero();}
		if(objetoPulsado.equals(mn14))
		{	ConsultaBomberos frame = new ConsultaBomberos();
		frame.setVisible(true);}
		if(objetoPulsado.equals(mn21))
		{new AltaVehiculo();}
		if(objetoPulsado.equals(mn22))
		{new BajaVehiculo();}
		if(objetoPulsado.equals(mn23))
		{new ModificacionVehiculo();}
		if(objetoPulsado.equals(mn24))
		{	ConsultaVehiculos frame = new ConsultaVehiculos();
		frame.setVisible(true);}
		if(objetoPulsado.equals(mn31))
		{new AltaEscuadrilla();}
		if(objetoPulsado.equals(mn32))
		{new BajaEscuadrilla();}
		if(objetoPulsado.equals(mn33))
		{new ModificacionEscuadrilla();}
		if(objetoPulsado.equals(mn34))
		{	ConsultaEscuadrillas frame = new ConsultaEscuadrillas();
		frame.setVisible(true);}
		if(objetoPulsado.equals(btnAyuda))
		{new Ayuda();}
	}
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{ 
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
}