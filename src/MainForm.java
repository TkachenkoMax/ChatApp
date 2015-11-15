import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainForm {
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

    public MainForm() {
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
            }
        });
    }

    public static void main(String[] args) throws IOException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainForm window = new MainForm();
                    window.mainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}