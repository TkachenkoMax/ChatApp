import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class UserListView extends JPanel {
    private UserListModel model;
    private JTable table = new JTable();
    private JButton deleteButton = new JButton("Delete");
    private JButton clearButton = new JButton("Clear");
    private JLabel contactsLabel = new JLabel("Contacts");


    public UserListView() {
        this(null);
    }

    public UserListView(UserListModel model) {
        if (model != null) {
        }
        this.setModel(model);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        this.setBackground(new Color(0x3D3E3A));
        this.setBorder(new LineBorder(Color.WHITE, 1));

        JScrollPane scroll = new JScrollPane(this.table);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contactsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contactsLabel.setForeground(Color.WHITE);

        deleteButton.setBackground(new Color(0x2A2D2F));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorder(new LineBorder(Color.WHITE, 1));

        clearButton.setBackground(new Color(0x2A2D2F));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBorder(new LineBorder(Color.WHITE, 1));

        //gridx,gridy,gridwidth,gridheight,weightx,weighty,anchor,fill,insets,ipadx,ipady
        this.add(contactsLabel, new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(scroll, new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(deleteButton, new GridBagConstraints(0, 2, 1, 1, 1, 0.025, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(clearButton, new GridBagConstraints(1, 2, 1, 1, 1, 0.025, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        this.deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                int pos = UserListView.this.table.getSelectedRow();
                if (pos >= 0) {
                    UserListView.this.model.delete(UserListView.this.model.getNickAt(pos));
                }
            }
        });

        this.clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserListView.this.model.clear();
            }
        });
    }

    public void setModel(UserListModel model) {
        if (model == null) {
            this.table.setModel(new DefaultTableModel());
            return;
        }
        this.model = model;
        this.table.setModel(new ModelForTable(model));
    }

    public void addSelectionListener(ListSelectionListener listener) {
        ListSelectionModel model = this.table.getSelectionModel();
        model.addListSelectionListener(listener);
    }

    public int getSelectedRow() {
        return this.table.getSelectedRow();
    }

    private class ModelForTable extends AbstractTableModel implements Observer {
        private UserListModel ul_model;

        @Override
        public int getColumnCount() {
            return 2;
        }

        public Class getColumnClass(int c) {
            return String.class;
        }

        @Override
        public String getColumnName(int col) {
            if (col == 0) {
                return "Nick";
            }
            return "IP";
        }

        public ModelForTable(UserListModel m) {
            assert (m != null);
            this.ul_model = m;
            this.ul_model.addObserver(this);
        }

        @Override
        public int getRowCount() {
            return this.ul_model.getSize();
        }

        @Override
        public Object getValueAt(int row, int col) {
            String[] nicks = this.ul_model.getNicks();
            if (col == 0) {
                return nicks[row];
            }
            if (col == 1) {
                return this.ul_model.getIpFor(nicks[row]);
            }
            assert (false);
            return null;
        }

        @Override
        public void update(Observable arg0, Object arg1) {
            this.fireTableDataChanged();
        }
    }
}