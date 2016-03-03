package GUI;

import Controller.ControllerListener;
import DateBase.GameData;
import DateBase.PlayerProfile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * @author kory
 */
public class GUILobby extends JFrame {

    private ControllerListener dl = null;

    private final JMenuBar menuBar;
    private final TablePanel table;

    private PlayerProfile playerProfile;

    public GUILobby() {

        super("Lobby");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        menuBar = setupMenuBar();
        table = new TablePanel();

        if (dl != null) {
            List<GameData> gd = dl.getGameData();
            table.addData(gd);
        }

        this.setJMenuBar(menuBar);

        table.refresh();

        this.add(table);

        this.setVisible(true);

        new Thread(() -> {
            StartDialog log = new StartDialog(this);
            log.setVisible(true);
        }) {
        }.start();

    }

    private JMenuBar setupMenuBar() {

        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");
        bar.add(file);

        JMenuItem copy = new JMenuItem("Copy");
        file.add(copy);

        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem paste = new JMenuItem("Paste");
        file.add(paste);

        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

        file.add(exit);

        exit.addActionListener((ActionEvent e) -> {
            if (JOptionPane.showConfirmDialog(this, "Do you wish to exit?", "Exit?", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });

        copy.addActionListener((ActionEvent e) -> {
            //DO STUFF
        });

        paste.addActionListener((ActionEvent e) -> {
            //DO STUFF
        });

        JMenu options = new JMenu("Options");
        bar.add(options);

        JMenuItem playerItem = new JMenuItem("Profile");
        options.add(playerItem);

        playerItem.addActionListener((ActionEvent e) -> {
            JDialog log = new JDialog(GUILobby.this, true);
            log.setSize(300, 150);
            log.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            
            Icon i = new ImageIcon(playerProfile.getPicLocation());
            System.out.println(playerProfile.getPicLocation());
            if (i.getIconHeight() == -1) {//couldn't find icon
                System.out.println("HERE");
                i = new ImageIcon("resources\\playerLogos\\Kory\\logo.jpg");
            }
            
            JLabel label = new JLabel();
            label.setIcon(i);
            log.add(label);
            log.setVisible(true);
        });

        return bar;

    }

    public void addDataBaseListener(ControllerListener dl) {
        this.dl = dl;
    }

    public void updateTable() {
        if (dl != null) {
            List<GameData> gd = dl.getGameData();
            table.addData(gd);
        }
        table.refresh();
    }

    class StartDialog extends JDialog {

        public final static int START_WIDTH = 300;
        public final static int START_HEIGHT = 150;

        private final JButton login;
        private final JButton createNewUser;

        public StartDialog(JFrame main) {

            super(main, true);

            this.setSize(START_WIDTH, START_HEIGHT);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            login = new JButton("Login");
            createNewUser = new JButton("Create New User");

            this.setLayout(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();
            cs.fill = GridBagConstraints.NONE;

            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 1;
            cs.weightx = 1;
            cs.weighty = 1;
            cs.insets = new Insets(5, 0, 0, 5);
            this.add(login);

            cs.gridx = 1;
            cs.gridy = 0;
            this.add(createNewUser);

            login.addActionListener((ActionEvent e) -> {
                Login lg = new Login(main);
                this.dispose();
                lg.setVisible(true);
            });

            createNewUser.addActionListener((ActionEvent e) -> {
                CreateUser cu = new CreateUser(main);
                this.dispose();
                cu.setVisible(true);
            });

            Dimension dim = main.getSize();

            int x = (dim.width / 2) - (this.getWidth() / 2);
            int y = (dim.height / 2) - (this.getHeight() / 2);
            this.setBounds(x, y, this.getWidth(), this.getHeight());

        }

    }

    class CreateUser extends JDialog {

        public final static int USER_WIDTH = 300;
        public final static int USER_HEIGHT = 150;

        private final JLabel nameLabel;
        private final JTextField name;

        private final JLabel passwordLabel;
        private final JPasswordField password;

        private final JLabel passwordLabelReenter;
        private final JPasswordField passwordReenter;

        private final JButton cancel;
        private final JButton ok;

        public CreateUser(JFrame main) {

            super(main, true);

            this.setSize(USER_WIDTH, USER_HEIGHT);

            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            nameLabel = new JLabel("Username: ");
            name = new JTextField(20);

            passwordLabel = new JLabel("Password: ");
            password = new JPasswordField(20);

            passwordLabelReenter = new JLabel("Confirm Password: ");
            passwordReenter = new JPasswordField(20);

            cancel = new JButton("Cancel");
            ok = new JButton("OK");

            JPanel p = new JPanel(new GridBagLayout());
            p.setSize(this.getSize());

            this.setLayout(new FlowLayout());
            this.setResizable(false);

            GridBagConstraints cs = new GridBagConstraints();

            cs.fill = GridBagConstraints.NONE;

            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 1;
            cs.weightx = 1;
            cs.weighty = 1;
            cs.insets = new Insets(5, 0, 0, 5);
            cs.anchor = GridBagConstraints.EAST;
            p.add(nameLabel, cs);

            cs.gridx = 1;
            cs.gridy = 0;
            p.add(name, cs);

            cs.gridx = 0;
            cs.gridy = 1;
            p.add(passwordLabel, cs);

            cs.gridx = 1;
            cs.gridy = 1;
            p.add(password, cs);

            cs.gridx = 0;
            cs.gridy = 2;
            p.add(passwordLabelReenter, cs);

            cs.gridx = 1;
            cs.gridy = 2;
            p.add(passwordReenter, cs);

            cs.gridx = 0;
            cs.gridy = 3;
            p.add(cancel, cs);

            cs.gridx = 1;
            cs.gridy = 3;
            p.add(ok, cs);

            this.add(p);
            this.pack();

            JPanel error = new JPanel();
            JLabel errorMessage = new JLabel("Player already in Database");
            //errorMessage.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/res/table.png"));
            error.add(errorMessage);

            ok.addActionListener((ActionEvent e) -> {
                String pass1 = String.valueOf(password.getPassword());
                String pass2 = String.valueOf(passwordReenter.getPassword());
                System.out.println(pass1 + " " + pass2);

                if (pass1.equals("")) {
                    password.setBorder(BorderFactory.createLineBorder(Color.RED));
                    System.out.println("Pass1 is null");
                }
                if (pass2.equals("")) {
                    passwordReenter.setBorder(BorderFactory.createLineBorder(Color.RED));
                    System.out.println("Pass2 is null");
                }

                if (pass1.equals(pass2) && pass1.length() > 0) {
                    System.out.println("Pass equal");
                    boolean added = dl.playerToAdd(name.getText(), pass1);
                    if (added) {
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(main, error, "Error", JOptionPane.CANCEL_OPTION);
                    }
                }
            });

            cancel.addActionListener((ActionEvent e) -> {
                this.dispose();
            });

            Dimension dim = main.getSize();

            int x = (dim.width / 2) - (this.getWidth() / 2);
            int y = (dim.height / 2) - (this.getHeight() / 2);
            this.setBounds(x, y, this.getWidth(), this.getHeight());

        }

    }

    class Login extends JDialog {

        public final static int DIALOG_WIDTH = 300;
        public final static int DIALOG_HEIGHT = 150;

        private final JTextField username;
        private final JLabel nameLabel;

        private final JPasswordField password;
        private final JLabel passwordLabel;

        private final JButton cancel;
        private final JButton ok;

        public Login(JFrame main) {

            super(main, true);
            this.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);

            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            username = new JTextField(20);
            nameLabel = new JLabel("Username: ");

            password = new JPasswordField(20);
            passwordLabel = new JLabel("Password: ");

            cancel = new JButton("Cancel");
            ok = new JButton("OK");

            JPanel p = new JPanel(new GridBagLayout());
            p.setSize(this.getSize());

            this.setLayout(new FlowLayout());
            this.setResizable(false);

            GridBagConstraints cs = new GridBagConstraints();

            cs.fill = GridBagConstraints.NONE;

            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 1;
            cs.weightx = 1;
            cs.weighty = 1;
            cs.insets = new Insets(5, 0, 0, 5);
            p.add(nameLabel, cs);

            cs.gridx = 1;
            cs.gridy = 0;
            cs.gridwidth = 1;
            p.add(username, cs);

            cs.gridx = 0;
            cs.gridy = 1;
            p.add(passwordLabel, cs);

            cs.gridx = 1;
            cs.gridy = 1;
            p.add(password, cs);

            cs.gridx = 0;
            cs.gridy = 2;
            p.add(cancel, cs);

            cs.gridx = 1;
            cs.gridy = 2;
            cs.anchor = GridBagConstraints.FIRST_LINE_END;
            p.add(ok, cs);

            this.add(p);
            this.pack();

            Dimension d = main.getSize();

            int x = (d.width / 2) - (this.getWidth() / 2);
            int y = (d.height / 2) - (this.getHeight() / 2);
            this.setBounds(x, y, this.getWidth(), this.getHeight());

            ok.addActionListener((ActionEvent e) -> {
                boolean match = check(username.getText(), String.valueOf(password.getPassword()));
                System.out.println("Match found: " + match);

                if (match) {
                    playerProfile = dl.getPlayerProfile(username.getText());
                    System.out.println(playerProfile.toString());
                    this.dispose();
                }

            });

        }

        /*MOVE TO CONTROLLER*/
        private boolean check(String n, String pass) {

            return dl.validateLogin(n, pass);
        }

    }

    public static void main(String[] args) {
        GUILobby g = new GUILobby();
    }

}
