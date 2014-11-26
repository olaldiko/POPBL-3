package Admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
import javax.swing.SwingConstants;

public class Identificacion implements ActionListener{
	JDialog dialog;
	JPanel panel, panelCenter, panelUser;
	JTextField fUser;
	JPasswordField fPass;
	JLabel tUser, tPass, tTitulo;
	JButton bComp, bCancel;
	final String user = "admin";
	boolean respuesta = false;
	
	public Identificacion (JFrame ventana,String titulo, boolean modo) {
		dialog = new JDialog(ventana, titulo, modo);
		dialog.setSize(350,150);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation(dim.width/2-dialog.getSize().width/2, dim.height/2-dialog.getSize().height/2);
		dialog.setContentPane(crearPanelDialogo());
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	public Container crearPanelDialogo(){
		panel = new JPanel(new BorderLayout());
		panel.add(crearTextTitulo(), BorderLayout.NORTH);
		panel.add(crearPanelCenter(), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}
	
	public Container crearPanelCenter(){
		panelCenter = new JPanel();
		panelCenter.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		panelCenter.add(crearPanelUserPass(), gbc);
		return panelCenter;
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
		JPanel panel = new JPanel();
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
		label.setFont(new Font("arial", Font.BOLD, 15));
		return label;		
	}
	
	public Component crearTextTitulo(){
		tTitulo = new JLabel("MordorBet");
		tTitulo.setFont(new Font("arial", Font.BOLD, 25));
		tTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		return tTitulo;		
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
			if (fUser.getText().equals(user)){
				char[] input = fPass.getPassword();
		        if (!isPasswordCorrect(input)) {
		        	JOptionPane.showMessageDialog(dialog, "Contraseña no valida.", "Error", JOptionPane.ERROR_MESSAGE);
		        	Arrays.fill(input, '0');
			        fPass.selectAll();
		        } else {
		        	respuesta = true;
		        	Arrays.fill(input, '0');
			        fPass.selectAll();
			        dialog.dispose();
		        }
			} else JOptionPane.showMessageDialog(dialog, "Usuario no valido.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			respuesta = false;
			dialog.dispose();
		}		
	}
}