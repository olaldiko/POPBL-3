package Admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class MiDialogo extends JDialog implements ActionListener{
	JPanel panel, panelUser;
	JTextField fUser;
	JPasswordField fPass;
	JLabel tUser, tPass;
	JButton bComp, bCancel;
	final String user = "admin";
	boolean respuesta;
	
	public MiDialogo (JFrame ventana,String titulo, boolean modo) {
		super(ventana, titulo, modo);
		this.setSize(240,100);
		this.setLocation (100,100);
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}

	public Container crearPanelDialogo(){
		panel = new JPanel();
		panel.add(crearPanelUserPass(), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}
	
	public Container crearPanelUserPass(){
		panelUser = new JPanel(new GridLayout(2, 2, 10, 0));
		panelUser.add(crearPanelTexto(tUser, "Usuario:"));
		panelUser.add(crearTextField());
		panelUser.add(crearPanelTexto(tPass, "Contraseña:"));
		panelUser.add(crearPassField());
		return panelUser;
	}
	
	private Component crearPanelBotones() {
		JPanel panel = new JPanel(new GridLayout(1,2,10,0));
		panel.add(crearBoton(bComp, "Comprobar"));
		panel.add(crearBoton(bCancel, "Cancelar"));
		return panel;
	}
	
	private Component crearBoton(JButton button, String texto) {
		button = new JButton (texto);
		button.setActionCommand(texto);
		button.addActionListener(this);
		return button;
	}
	
	public Component crearTextField(){
		fUser = new JTextField();
		return fUser;
	}
	
	public Component crearPassField(){
		fPass = new JPasswordField();
		return fPass;
	}
	
	private Component crearPanelTexto(JLabel label, String textoAñadir) {
		label = new JLabel(textoAñadir);
		return label;		
	}
	
	private static boolean isPasswordCorrect(char[] input) {
	    boolean isCorrect = true;
	    char[] correctPassword = { '1', '2', '3', '4'};
	    if (input.length != correctPassword.length) {
	        isCorrect = false;
	    } else {
	        isCorrect = Arrays.equals (input, correctPassword);
	    }
	    Arrays.fill(correctPassword,'0');
	    return isCorrect;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Comprobar")) {
			respuesta = true;
			if (fUser.getText().equals(user)){
				char[] input = fPass.getPassword();
		        if (!isPasswordCorrect(input)) {
		        	JOptionPane.showMessageDialog(this, "Contraseña no valida.", "Error", JOptionPane.ERROR_MESSAGE);
		        	Arrays.fill(input, '0');
			        fPass.selectAll();
		        } else {
		        	Arrays.fill(input, '0');
			        fPass.selectAll();
			        this.dispose();
		        }
			} else JOptionPane.showMessageDialog(this, "Usuario no valido.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			respuesta = false;
			this.dispose();
		}		
	}
}