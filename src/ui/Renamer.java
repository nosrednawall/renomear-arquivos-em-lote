package ui;

import com.jgoodies.looks.HeaderStyle;
import com.jgoodies.looks.Options;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renamer {

    private JFrame frmRenamer;
    private JTextField folderPath;
    private JTextField pettern;
    private JFormattedTextField extensions;
    private JSpinner start;
    private JSpinner increment;

    /**
     * Launch the application.
     * 
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
	setLookAndFeel();
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    Renamer window = new Renamer();
		    window.frmRenamer.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Set Look and Feel.
     */
    private static void setLookAndFeel() {
	System.setProperty("awt.useSystemAAFontSettings", "on");
	System.setProperty("swing.aatext", "true");
	Options.setPopupDropShadowEnabled(true);
	try {
	    UIManager.setLookAndFeel(UIManager
		    .getCrossPlatformLookAndFeelClassName());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Create the application.
     */
    public Renamer() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frmRenamer = new JFrame();
	frmRenamer.setResizable(false);
	frmRenamer.setTitle("Renamer 0.1.4");
	frmRenamer.setBounds(100, 100, 325, 418);
	frmRenamer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frmRenamer.getContentPane().setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(null);
	panel.setBounds(5, 5, 326, 354);
	frmRenamer.getContentPane().add(panel);
	panel.setLayout(null);

	JLabel lblFolder = new JLabel("Folder:");
	lblFolder.setBounds(12, 12, 61, 19);
	panel.add(lblFolder);

	folderPath = new JTextField();
	folderPath.setBounds(12, 36, 178, 29);
	panel.add(folderPath);
	folderPath.setColumns(10);

	JButton btnNewButton = new JButton("Choose");
	btnNewButton.setBounds(194, 36, 106, 29);
	panel.add(btnNewButton);
	btnNewButton.setIcon(new ImageIcon(Renamer.class
		.getResource("/icons/folder_explore.png")));

	JLabel lblExtension = new JLabel("Extensions:");
	lblExtension.setBounds(12, 72, 86, 19);
	panel.add(lblExtension);

	JLabel lblForAll = new JLabel("* For all extensions");
	lblForAll.setBounds(12, 128, 159, 19);
	panel.add(lblForAll);

	extensions = new JFormattedTextField();
	extensions.setBounds(12, 97, 288, 29);
	panel.add(extensions);
	extensions.setText("*");

	JLabel lblNewLabel = new JLabel("Pattern:");
	lblNewLabel.setBounds(12, 159, 61, 19);
	panel.add(lblNewLabel);

	pettern = new JTextField();
	pettern.setBounds(12, 183, 288, 29);
	panel.add(pettern);
	pettern.setText("file");
	pettern.setColumns(10);

	JLabel lblStart = new JLabel("Start:");
	lblStart.setBounds(12, 230, 61, 19);
	panel.add(lblStart);

	start = new JSpinner();
	start.setBounds(12, 255, 140, 30);
	panel.add(start);
	start.setModel(new SpinnerNumberModel(0, 0, null, 1));

	JLabel lblIncrement = new JLabel("Increment:");
	lblIncrement.setBounds(159, 230, 86, 19);
	panel.add(lblIncrement);

	increment = new JSpinner();
	increment.setBounds(160, 255, 140, 30);
	panel.add(increment);
	increment.setModel(new SpinnerNumberModel(1, 1, null, 1));

	JButton btnRename = new JButton("Rename");
	btnRename.setBounds(180, 307, 120, 29);
	panel.add(btnRename);
	btnRename.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		if (getFolderPath().getText() == null
			|| getFolderPath().getText().equals("")) {
		    JOptionPane.showMessageDialog(frmRenamer,
			    "You must select a directory first.", "Error",
			    JOptionPane.ERROR_MESSAGE);
		} else {
		    File dir = new File(getFolderPath().getText());
		    List<File> files = new ArrayList<File>();

		    if (!(getExtensions().getText() == null)
			    && !(getExtensions().getText().equals(""))
			    && !(getExtensions().getText().equals("*"))) {
			for (File file : dir.listFiles()) {
			    for (String pattern : getExtensions().getText()
				    .split(",")) {
				if (file.getName().endsWith("." + pattern)) {
				    files.add(file);
				}
			    }
			}
		    } else {
			Collections.addAll(files, dir.listFiles());
		    }

		    int start = (Integer) getStart().getValue();
		    int increment = (Integer) getIncrement().getValue();

		    for (File file : files) {
			int dotPos = file.getName().lastIndexOf(".");
			String extension = file.getName().substring(dotPos);
			String newFilename;
			String path = getFolderPath().getText();

			do {
			    newFilename = getPettern().getText()
				    + String.valueOf(start) + extension;
			    start += increment;
			} while (new File(path, newFilename).exists());

			System.out.println("Renaming: " + file.getName()
				+ " to: " + getPettern().getText()
				+ String.valueOf(start) + extension);
			file.renameTo(new File(path, newFilename));

		    }
		    JOptionPane.showMessageDialog(frmRenamer, "Finished",
			    "Finished", JOptionPane.INFORMATION_MESSAGE);

		}
	    }
	});
	btnRename.setIcon(new ImageIcon(Renamer.class
		.getResource("/icons/tick.png")));

	JMenuBar menuBar = new JMenuBar();
	frmRenamer.setJMenuBar(menuBar);
	menuBar.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);

	JMenu mnAbout = new JMenu("About");
	mnAbout.setMnemonic('A');
	menuBar.add(mnAbout);

	JMenuItem mntmAuthor = new JMenuItem("Author");
	mntmAuthor.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frmRenamer,
			"Claudemiro Feitosa<dimiro1@gmail.com>", "About",
			JOptionPane.INFORMATION_MESSAGE);
	    }
	});
	mntmAuthor.setIcon(new ImageIcon(Renamer.class
		.getResource("/icons/user.png")));
	mnAbout.add(mntmAuthor);
	btnNewButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int ret = chooser.showOpenDialog(frmRenamer);
		if (ret == JFileChooser.APPROVE_OPTION) {
		    getFolderPath().setText(
			    chooser.getSelectedFile().getAbsolutePath());
		}

	    }
	});
	final JMenu mnLook = new JMenu("Look");
	mnLook.setMnemonic('L');
	menuBar.add(mnLook);

	JRadioButton mntmNimbus = new JRadioButton("Nimbus", true);
	JRadioButton mntmMetal = new JRadioButton("Metal");
	JRadioButton mntmOS = new JRadioButton("System");
	JRadioButton mntmPlastic = new JRadioButton("Plastic");
	JRadioButton mntmPlastic3D = new JRadioButton("Plastic 3D");
	JRadioButton mntmPlasticXP = new JRadioButton("Plastic XP");
	JRadioButton mntmInfo = new JRadioButton("Info");
	JRadioButton mntmMotif = new JRadioButton("Motif");

	ButtonGroup lookAndFeelGroup = new ButtonGroup();
	lookAndFeelGroup.add(mntmNimbus);
	lookAndFeelGroup.add(mntmMetal);
	lookAndFeelGroup.add(mntmOS);
	lookAndFeelGroup.add(mntmPlastic);
	lookAndFeelGroup.add(mntmPlastic3D);
	lookAndFeelGroup.add(mntmPlasticXP);
	lookAndFeelGroup.add(mntmInfo);
	lookAndFeelGroup.add(mntmMotif);

	mnLook.add(mntmNimbus);
	mnLook.add(mntmMetal);
	mnLook.add(mntmOS);
	mnLook.add(mntmPlastic);
	mnLook.add(mntmPlastic3D);
	mnLook.add(mntmPlasticXP);
	mnLook.add(mntmInfo);
	mnLook.add(mntmMotif);

	mntmNimbus.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent event) {
		try {
		    UIManager
			    .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		    SwingUtilities.updateComponentTreeUI(frmRenamer);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		mnLook.setSelected(true);
	    }
	});

	mntmMetal.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent event) {
		try {
		    UIManager
			    .setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		    SwingUtilities.updateComponentTreeUI(frmRenamer);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		mnLook.setSelected(true);
	    }
	});

	mntmOS.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent event) {
		try {
		    UIManager.setLookAndFeel(UIManager
			    .getSystemLookAndFeelClassName());
		    SwingUtilities.updateComponentTreeUI(frmRenamer);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		mnLook.setSelected(true);
	    }
	});

	mntmPlastic.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent event) {
		try {
		    UIManager
			    .setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		    SwingUtilities.updateComponentTreeUI(frmRenamer);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		mnLook.setSelected(true);
	    }
	});

	mntmPlastic3D.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent event) {
		try {
		    UIManager
			    .setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		    SwingUtilities.updateComponentTreeUI(frmRenamer);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		mnLook.setSelected(true);
	    }
	});

	mntmPlasticXP.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent event) {
		try {
		    UIManager
			    .setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		    SwingUtilities.updateComponentTreeUI(frmRenamer);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		mnLook.setSelected(true);
	    }
	});

	mntmInfo.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent event) {
		try {
		    UIManager
			    .setLookAndFeel("net.infonode.gui.laf.InfoNodeLookAndFeel");
		    SwingUtilities.updateComponentTreeUI(frmRenamer);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		mnLook.setSelected(true);
	    }
	});

	mntmMotif.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent event) {
		try {
		    UIManager
			    .setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		    SwingUtilities.updateComponentTreeUI(frmRenamer);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		mnLook.setSelected(true);
	    }
	});

    }

    protected JTextField getFolderPath() {
	return folderPath;
    }

    protected JFormattedTextField getExtensions() {
	return extensions;
    }

    protected JTextField getPettern() {
	return pettern;
    }

    protected JSpinner getStart() {
	return start;
    }

    protected JSpinner getIncrement() {
	return increment;
    }
}
