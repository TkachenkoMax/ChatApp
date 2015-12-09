import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class MainForm extends JFrame {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel loginLabel;
    private JTextField loginTextField;
    private JLabel remoteLoginLabel;
    private JTextField remoteLoginTextField;
    private JButton connectButton;
    private JButton applyButton;
    private JLabel remoteAddressLabel;
    private JTextField remoteAddressTextField;
    private JButton disconnectButton;
    private JTextArea messagesArea;
    private JTextField yourMessageField;
    private JButton sendButton;
    public JScrollPane messagesScrollPane;
    public JScrollPane yourMessageScrollPane;
    private Connection connection;
    private CallListenerThread callListenerThread;
    private CommandListenerThread commandListenerThread;
    private Caller caller;
    public static MainForm window;
    private ServerConnection server;
    private UserListView userListView;
    private UserListModel userListModel;
    private String localNick = "unnamed";

    public MainForm() {  // TODO
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeigth = screenSize.height;

        mainFrame.setBounds(screenWidth / 4, screenHeigth / 4, screenWidth / 2, screenHeigth / 2);
        mainFrame.setTitle("ChatApp");
        Image img = kit.getImage("1.jpg");
        mainFrame.setIconImage(img);

        mainPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);

        mainPanel.setBackground(new Color(0x3D3E3A));

        loginLabel = new JLabel("Enter your login:");
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        loginTextField = new JTextField();

        remoteLoginLabel = new JLabel("Enter remote login:");
        remoteLoginLabel.setForeground(Color.WHITE);
        remoteLoginLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        remoteLoginTextField = new JTextField();
        remoteLoginTextField.setEditable(false);

        connectButton = new JButton("Connect");
        connectButton.setBackground(new Color(0x33AD54));
        connectButton.setForeground(Color.WHITE);
        connectButton.setBorder(new LineBorder(new Color(0x318040), 1));

        applyButton = new JButton("Apply");
        applyButton.setForeground(Color.WHITE);
        applyButton.setBorder(new LineBorder(new Color(0x8F5527), 1));
        applyButton.setBackground(new Color(0xCB7832));

        remoteAddressLabel = new JLabel("Enter remote address:");
        remoteAddressLabel.setForeground(Color.WHITE);
        remoteAddressLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        remoteAddressTextField = new JTextField();
        remoteAddressTextField.setText("files.litvinov.in.ua");
        disconnectButton = new JButton("Disconnect");
        disconnectButton.setBackground(new Color(0xDD5140));
        disconnectButton.setForeground(Color.WHITE);
        disconnectButton.setBorder(new LineBorder(new Color(0x943D30), 1));
        disconnectButton.setEnabled(false);

        messagesArea = new JTextArea();
        messagesArea.setEditable(false);
        messagesArea.setLineWrap(true);
        messagesArea.setBackground(new Color(0x2B2B2B));
        messagesArea.setForeground(new Color(0xC97832));
        messagesArea.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 2));

        yourMessageField = new JTextField();
        yourMessageField.setBackground(new Color(0x2B2B2B));
        yourMessageField.setForeground(new Color(0xCA7832));
        Insets insets = yourMessageField.getInsets();
        yourMessageField.setBorder(BorderFactory.createEmptyBorder(2, 5, insets.bottom, 2));
        yourMessageField.setEditable(false);

        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(0x2A2D2F));
        sendButton.setForeground(Color.WHITE);
        sendButton.setBorder(new LineBorder(Color.WHITE, 1));
        sendButton.setEnabled(false);

        messagesScrollPane = new JScrollPane(messagesArea);
        messagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        yourMessageScrollPane = new JScrollPane(yourMessageField);
        yourMessageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        userListModel = new UserListModel();
        userListView = new UserListView(userListModel);

        this.server = new ServerConnection("jdbc:mysql://files.litvinov.in.ua/chatapp_server");
        String[] nicks;
        nicks = this.server.getAllNicks();
        int n = nicks.length;
        int n2 = 0;
        while (n2 < n) {
            String val = nicks[n2];
            this.userListModel.add(val, this.server.getIpForNick(val));
            ++n2;
        }

        mainPanel.add(loginLabel, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(loginTextField, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteLoginLabel, new GridBagConstraints(2, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteLoginTextField, new GridBagConstraints(3, 0, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(connectButton, new GridBagConstraints(5, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(applyButton, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(userListView, new GridBagConstraints(0, 2, 1, 1, 0.25, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteAddressLabel, new GridBagConstraints(2, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteAddressTextField, new GridBagConstraints(3, 1, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(disconnectButton, new GridBagConstraints(5, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(messagesScrollPane, new GridBagConstraints(0, 2, GridBagConstraints.REMAINDER, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(yourMessageScrollPane, new GridBagConstraints(0, 3, 5, 1, 1, 0.025, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(sendButton, new GridBagConstraints(5, 3, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        mainFrame.add(mainPanel);
        final MainForm self = this;

        disconnectButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Yes, really!", "No, it's mistake!"};
                int choise = JOptionPane.showOptionDialog(mainFrame,
                        "You are connected to " + remoteLoginTextField.getText() + ". You really want to disconnect the connection?",
                        "Disconnect...", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choise == JOptionPane.YES_OPTION) {
                    waitMode();
                    try {
                        connection.disconnect();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    commandListenerThread.stop();
                    remoteLoginTextField.setText("");
                    remoteAddressTextField.setText("");
                }
            }
        });
        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yourMessageField.getText().equals("") || remoteAddressTextField.getText().equals("") || remoteLoginTextField.getText().equals("")) {
                    sendButton.setSelected(false);
                } else {
                    try {
                        connection.sendMessage(yourMessageField.getText());
                        Date d = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                        messagesArea.append(localNick + ": " + yourMessageField.getText() + "             " + dateFormat.format(d) + '\n');
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                yourMessageField.setText("");
            }
        });
        yourMessageField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendButton.doClick();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        applyButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!loginTextField.getText().equals("")) {
                    localNick = loginTextField.getText();
                    callListenerThread.setLocalNick(localNick);
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(mainFrame, "Your log in as " + localNick + ". Welcome!");
                    }
                });
            }
        });

        connectButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remoteAddressTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(mainFrame, "Insufficient data. Please, enter a remote login and a remote adress!");
                } else {
                    caller = new Caller(localNick, remoteAddressTextField.getText());
                    try {
                        connection = caller.call();
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(mainFrame, "Error! There is not internet connection.");
                    }
                    remoteLoginTextField.setText(caller.getRemoteNick());
                    if (connection == null) {
                        switch (caller.getStatus()) {
                            case NOT_ACCESSIBLE: {
                                JOptionPane.showMessageDialog(mainFrame, "User/line is not accessible!");
                                break;
                            }
                            case NO_SERVICE: {
                                JOptionPane.showMessageDialog(mainFrame, "User has not served!");
                                break;
                            }
                            case BUSY: {
                                JOptionPane.showMessageDialog(mainFrame, "User/line is busy!");
                                break;
                            }
                            case REJECTED: {
                                JOptionPane.showMessageDialog(mainFrame, "User has rejected call!");
                            }
                        }
                    } else if (caller.getStatus() == Caller.CallStatus.valueOf("OK")) {
                        dialogMode();
                        JOptionPane.showMessageDialog(mainFrame, "User " + caller.getRemoteNick() + " successfully accepted!");
                        commandListenerThread = new CommandListenerThread(connection);
                        commandListenerThread.addObserver(new Observer() {
                            @Override
                            public void update(Observable o, Object arg) {
                                if (commandListenerThread.getLastCommand() != null && !commandListenerThread.isDisconnected()) {
                                    if (commandListenerThread.getLastCommand() instanceof NickCommand) {
                                        remoteLoginTextField.setText(((NickCommand) commandListenerThread.getLastCommand()).getNick());
                                        try {
                                            Object[] options = {"Yes!", "No, I'm reject"};
                                            int choise = JOptionPane.showOptionDialog(mainFrame,
                                                    "User " + remoteLoginTextField.getText() + " wants to chat. Do you you want to accept this call?",
                                                    "Incoming connection...", JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                            if (choise == JOptionPane.YES_OPTION) {
                                                callListenerThread.getLastConnection().accept();
                                                dialogMode();
                                            } else {
                                                if (choise == JOptionPane.NO_OPTION) {
                                                    callListenerThread.getLastConnection().reject();
                                                }
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else if (commandListenerThread.getLastCommand() instanceof MessageCommand) {
                                        SwingUtilities.invokeLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                Date d = new Date();
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                                                messagesArea.append(remoteLoginTextField.getText() + ": " + ((MessageCommand) commandListenerThread.getLastCommand()).toString() + "             " + dateFormat.format(d) + '\n');
                                                //messagesArea.append(((MessageCommand) commandListenerThread.getLastCommand()).toString() + "\n");
                                            }
                                        });
                                    } else if (commandListenerThread.getLastCommand().getCommandType() != null) {
                                        switch (commandListenerThread.getLastCommand().getCommandType()) {
                                            case ACCEPT: {
                                                callListenerThread.setBusy(true);
                                                dialogMode();
                                                break;
                                            }
                                            case REJECT: {
                                                callListenerThread.setBusy(false);
                                                commandListenerThread.stop();
                                                connection = null;
                                                SwingUtilities.invokeLater(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        JOptionPane.showMessageDialog(mainFrame, "User " + caller.getRemoteNick() + " has rejected your call.");
                                                    }
                                                });
                                                waitMode();
                                                break;
                                            }
                                            case DISCONNECT: {
                                                SwingUtilities.invokeLater(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        callListenerThread.setBusy(false);
                                                        commandListenerThread.stop();
                                                        connection = null;
                                                        JOptionPane.showMessageDialog(mainFrame, "User " + caller.getRemoteNick() + " has disconnected.");
                                                    }
                                                });
                                                waitMode();
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    if (commandListenerThread.getLastCommand() instanceof MessageCommand && !commandListenerThread.isDisconnected()) {
                                        SwingUtilities.invokeLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                Date d = new Date();
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                                                messagesArea.append(remoteLoginTextField.getText() + ": " + ((MessageCommand) commandListenerThread.getLastCommand()).toString() + "             " + dateFormat.format(d) + '\n');
                                                //messagesArea.append(((MessageCommand) commandListenerThread.getLastCommand()).toString() + "\n");
                                            }
                                        });
                                    } else {
                                        waitMode();
                                        commandListenerThread.stop();
                                        connection = null;
                                        callListenerThread.setBusy(false);
                                    }
                                }
                            }
                        });
                        remoteLoginTextField.setText(caller.getRemoteNick());

                    }
                }

            }
        });

        CallListener callListener = new CallListener(localNick);
        this.callListenerThread = new CallListenerThread(callListener);
        this.callListenerThread.addObserver((Observer) new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (arg instanceof Connection)
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            connection = callListenerThread.getLastConnection();
                            callListenerThread.setBusy(true);
                            commandListenerThread = new CommandListenerThread(connection);
                            commandListenerThread.addObserver(new Observer() {
                                @Override
                                public void update(Observable o, Object arg) {
                                    if (commandListenerThread.getLastCommand() != null && !commandListenerThread.isDisconnected() && connection.isOpen()) {
                                        if (commandListenerThread.getLastCommand() instanceof NickCommand) {
                                            remoteLoginTextField.setText(((NickCommand) commandListenerThread.getLastCommand()).getNick());
                                            try {
                                                Object[] options = {"Yes!", "No, I'm reject"};
                                                int choise = JOptionPane.showOptionDialog(mainFrame,
                                                        "User " + remoteLoginTextField.getText() + " wants to chat. Do you you want to accept this call?",
                                                        "Incoming connection...", JOptionPane.YES_NO_OPTION,
                                                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                                if (choise == JOptionPane.YES_OPTION) {
                                                    callListenerThread.getLastConnection().accept();
                                                    dialogMode();
                                                } else {
                                                    if (choise == JOptionPane.NO_OPTION) {
                                                        callListenerThread.getLastConnection().reject();
                                                    }
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } else if (commandListenerThread.getLastCommand() instanceof MessageCommand) {
                                            SwingUtilities.invokeLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Date d = new Date();
                                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                                                    messagesArea.append(remoteLoginTextField.getText() + ": " + ((MessageCommand) commandListenerThread.getLastCommand()).toString() + "             " + dateFormat.format(d) + '\n');
                                                    //messagesArea.append(((MessageCommand) commandListenerThread.getLastCommand()).toString() + "\n");
                                                }
                                            });
                                        } else if (commandListenerThread.getLastCommand().getCommandType() != null) {
                                            switch (commandListenerThread.getLastCommand().getCommandType()) {
                                                case ACCEPT: {
                                                    callListenerThread.setBusy(true);
                                                    dialogMode();
                                                    break;
                                                }
                                                case REJECT: {
                                                    callListenerThread.setBusy(false);
                                                    connection = null;
                                                    SwingUtilities.invokeLater(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            JOptionPane.showMessageDialog(mainFrame, "User " + caller.getRemoteNick() + " has rejected your call.");
                                                        }
                                                    });
                                                    waitMode();
                                                    break;
                                                }
                                                case DISCONNECT: {
                                                    connection = null;
                                                    callListenerThread.setBusy(false);
                                                    commandListenerThread.stop();
                                                    SwingUtilities.invokeLater(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            JOptionPane.showMessageDialog(mainFrame, "User " + caller.getRemoteNick() + " has disconnected.");
                                                        }
                                                    });
                                                    waitMode();
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        if (commandListenerThread.getLastCommand() instanceof MessageCommand && !commandListenerThread.isDisconnected()) {
                                            SwingUtilities.invokeLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Date d = new Date();
                                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                                                    messagesArea.append(remoteLoginTextField.getText() + ": " + ((MessageCommand) commandListenerThread.getLastCommand()).toString() + "             " + dateFormat.format(d) + '\n');
                                                    //messagesArea.append(((MessageCommand) commandListenerThread.getLastCommand()).toString() + "\n");
                                                }
                                            });
                                        } else {
                                            commandListenerThread.stop();
                                            connection = null;
                                            commandListenerThread = null;
                                            callListenerThread.setBusy(false);
                                        }
                                    }
                                }
                            });
                        }
                    });
            }

        });
        this.callListenerThread.start();
    }

    public void dialogMode() {
        connectButton.setEnabled(false);
        disconnectButton.setEnabled(true);
        sendButton.setEnabled(true);
        yourMessageField.setEditable(true);
        loginTextField.setEditable(false);
        applyButton.setEnabled(false);
    }

    public void waitMode() {
        connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
        sendButton.setEnabled(false);
        yourMessageField.setEditable(false);
        loginTextField.setEditable(true);
        applyButton.setEnabled(true);
        messagesArea.setText("");
    }

    public static void main(String[] args) throws IOException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new MainForm();
                    window.mainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}