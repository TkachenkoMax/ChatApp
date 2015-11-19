import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MainForm implements Observer{
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
    private Caller caller;
    public static MainForm window;

    public MainForm()  {  // TODO
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

        mainPanel.add(loginLabel, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(loginTextField, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteLoginLabel, new GridBagConstraints(2, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteLoginTextField, new GridBagConstraints(3, 0, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(connectButton, new GridBagConstraints(5, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(applyButton, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteAddressLabel, new GridBagConstraints(2, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteAddressTextField, new GridBagConstraints(3, 1, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(disconnectButton, new GridBagConstraints(5, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(messagesScrollPane, new GridBagConstraints(0, 2, GridBagConstraints.REMAINDER, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(yourMessageScrollPane, new GridBagConstraints(0, 3, 5, 1, 1, 0.025, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(sendButton, new GridBagConstraints(5, 3, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        mainFrame.add(mainPanel);

        connectButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectButton.setEnabled(false);
                disconnectButton.setEnabled(true);
                sendButton.setEnabled(true);
                yourMessageField.setEditable(true);
            }
        });

        disconnectButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectButton.setEnabled(true);
                disconnectButton.setEnabled(false);
                sendButton.setEnabled(false);
                yourMessageField.setEditable(false);
                try {
                    connection.disconnect();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yourMessageField.getText().equals("")||remoteAddressTextField.getText().equals("")||remoteLoginTextField.getText().equals("")){
                    sendButton.setSelected(false);
                }
                else {
                    try {
                        connection.sendMessage(yourMessageField.getText());
                        messagesArea.setText(yourMessageField.getText());
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
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
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
                if (callListenerThread==null){
                    callListenerThread=new CallListenerThread(new CallListener(loginTextField.getText()));
                }
                else {
                    callListenerThread.getCallListener().setLocalNick(loginTextField.getText());
                }
                applyButton.setSelected(false);
            }

        });

        connectButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (/*remoteLoginTextField.getText().equals("") && */remoteAddressTextField.getText().equals("")){
                    JOptionPane.showMessageDialog(mainFrame,"Insufficient data. Please, enter a remote login and a remote adress!");
                }
                else {
                    caller=new Caller(loginTextField.getText(),remoteAddressTextField.getText());
                    Thread t=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            connectButton.setSelected(false);
                            disconnectButton.setSelected(true);
                            try {
                                connection = caller.call();
                                if (caller.getStatus()== Caller.CallStatus.valueOf("OK")){
                                    remoteLoginTextField.setText(caller.getRemoteNick());
                                }
                                else {
                                    if (caller.getStatus()== Caller.CallStatus.valueOf("BUSY")){
                                        JOptionPane.showMessageDialog(mainFrame,"User "+caller.getRemoteNick()+" is busy now.");
                                        connection=null;
                                    }
                                    else {
                                        if (caller.getStatus()== Caller.CallStatus.valueOf("REJECT")){
                                            JOptionPane.showMessageDialog(mainFrame,"User "+caller.getRemoteNick()+" has rejected your call.");
                                            connection=null;
                                        }
                                    }
                                }
                            } catch (IOException e1) {
                                JOptionPane.showMessageDialog(mainFrame,"Eror 404! Couldn't connect!");
                                connection=null;
                            }
                        }
                    });
                    t.start();
                }
            }
        });

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

    @Override
    public void update(Observable o, Object arg) {
        if (arg == CallListener.class){
            CallListener callListener= (CallListener) arg;
            callListenerThread.suspend();
            callListenerThread.resume();
            System.out.println("1");
        }
        else {
            if (arg == Connection.class){
                connection = (Connection) arg;
                System.out.println("2");
            }
            else {
                Command command=(Command) arg;
                if (command.getCommandType()== Command.CommandType.valueOf("MESSAGE")){
                    System.out.println("3");
                    messagesArea.setText("Back: "+command.toString());
                    //messagesArea.insert("Back: "+command.toString(),messagesArea.getLineCount()+1);
                }
            }
        }
    }
}