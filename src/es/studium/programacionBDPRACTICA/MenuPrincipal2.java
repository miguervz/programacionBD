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
public  class MenuPrincipal2 extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	MenuBar mnbMenu = new MenuBar();
	Menu mn1 = new Menu("Bomberos");
	Menu mn2 = new Menu("Vehículos");
	Menu mn3 = new Menu("Escuadrillas");
	MenuItem mn11 = new MenuItem("Alta");
	MenuItem mn21 = new MenuItem("Alta");
	MenuItem mn31 = new MenuItem("Alta");
	Button btnAyuda= new Button("Ayuda");

	public MenuPrincipal2()
	{
		setLayout(new FlowLayout());
		setTitle("Bomberos");
		setMenuBar(mnbMenu);
		setSize(250,250);
		addWindowListener(this);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		mn1.add(mn11);
		mn2.add(mn21);
		mn3.add(mn31);
		mnbMenu.add(mn1);
		mnbMenu.add(mn2);
		mnbMenu.add(mn3);
		mn11.addActionListener(this);
		mn21.addActionListener(this);
		mn31.addActionListener(this);	
		add(btnAyuda);
		btnAyuda.addActionListener(this);
	}
	public static void main(String[] args) {
		new MenuPrincipal2();
	}
	public void actionPerformed(ActionEvent ae) {
		Object objetoPulsado=ae.getSource();
		if(objetoPulsado.equals(mn11))
		{new AltaBombero();}
		if(objetoPulsado.equals(mn21))
		{new AltaVehiculo();}
		if(objetoPulsado.equals(mn31))
		{new AltaEscuadrilla();}
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