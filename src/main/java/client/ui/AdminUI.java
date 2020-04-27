package client.ui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import client.controller.EasyFilmController;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2536273181002934434L;
	private JTextField fileName;
	private JButton addArchive;
	private EasyFilmController controller;
	public AdminUI(EasyFilmController e) {
		this.controller = e;
		// Formato de la ventana
		this.setTitle( "EasyFilmin Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra la ventana y se para la ejecución
		setSize(600,250);
		setLocation(600,175);
		setResizable(false);

		
		getContentPane().setLayout(null);
		
		fileName = new JTextField();
		fileName.setBounds(305, 75, 250, 25);
		getContentPane().add(fileName);
		fileName.setColumns(10);
		
		addArchive = new JButton("Add File");
		addArchive.setFont(new Font("Tahoma", Font.BOLD, 10));
		addArchive.setBounds(50, 75, 150, 30);
		getContentPane().add(addArchive);
		

	}
	//fcCancion = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
/*		bNuevo.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int datoDevuelve = fcCancion.showOpenDialog(Reproductor.this);
			if (datoDevuelve == JFileChooser.APPROVE_OPTION){
				java.io.File ficElegido = fcCancion.getSelectedFile();
				System.out.println(ficElegido.getAbsolutePath());
				biblioteca.canciones.add(Cancion.crearCancionDeFichero(ficElegido));
				biblioteca.cargarCanciones(Reproductor.this);
				liSeleccionar.revalidate();
			} 			
		}
	});
*/
	
/*				bLista.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					fcCancion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fcCancion.setAcceptAllFileFilterUsed(false);						
					int datoDevuelve = fcCancion.showOpenDialog(Reproductor.this);
					if (datoDevuelve == JFileChooser.APPROVE_OPTION){
						final JProgressBar pbllenar = new JProgressBar(0,1000);
						(new Thread(){
							@Override
							public void run(){
								java.io.File ficElegido = fcCancion.getSelectedFile();
								File[] files = new File(ficElegido.getAbsolutePath()).listFiles();
								biblioteca.llenaBiblioteca(files, pbllenar, 0.0, 1.0);
								biblioteca.canciones.sort(null);
								biblioteca.cargarInterpretesDeCanciones();
								biblioteca.interpretes.sort(null);
								biblioteca.cargarCanciones(Reproductor.this);
								liSeleccionar.revalidate();
							}
						}).start();
						Object[] campo = new Object[1];
						campo[0] = pbllenar;
						JOptionPane.showConfirmDialog(Reproductor.this, campo, "Progreso de carga...", JOptionPane.YES_OPTION);
					}
				}
			});
*/
	
	public static void main(String[] args) {
		EasyFilmController e = new EasyFilmController(args[0], args[1]);
		AdminUI va = new AdminUI(e);
		va.setSize(600, 250);
		va.setVisible(true);
	}
}
