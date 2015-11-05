import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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

    public MainForm() {
        initialize();
    }

    public void initialize() {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeigth = screenSize.height;

        mainFrame.setBounds(screenWidth / 4, screenHeigth / 4, screenWidth / 2, screenHeigth / 2);
        mainFrame.setTitle("ChatApp");

        mainPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);

        loginLabel = new JLabel("Enter your login:");
        loginLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        loginTextField = new JTextField();
        remoteLoginLabel = new JLabel("Enter remote login:");
        remoteLoginLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        remoteLoginTextField = new JTextField();
        connectButton = new JButton("Connect");
        applyButton = new JButton("Apply");
        remoteAddressLabel = new JLabel("Enter remote address:");
        remoteAddressLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        remoteAddressTextField = new JTextField();
        disconnectButton = new JButton("Disconnect");
        messagesArea = new JTextArea();
        messagesArea.setBorder(new LineBorder(Color.BLACK, 2));
        messagesArea.setEditable(false);
        messagesArea.setLineWrap(true);
        yourMessageField = new JTextField();
        sendButton = new JButton("Send");

        mainPanel.add(loginLabel, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(loginTextField, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteLoginLabel, new GridBagConstraints(2, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteLoginTextField, new GridBagConstraints(3, 0, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(connectButton, new GridBagConstraints(5, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(applyButton, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteAddressLabel, new GridBagConstraints(2, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(remoteAddressTextField, new GridBagConstraints(3, 1, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(disconnectButton, new GridBagConstraints(5, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(messagesArea, new GridBagConstraints(0, 2, GridBagConstraints.REMAINDER, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(yourMessageField, new GridBagConstraints(0, 3, 5, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(sendButton, new GridBagConstraints(5, 3, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        mainFrame.add(mainPanel);
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