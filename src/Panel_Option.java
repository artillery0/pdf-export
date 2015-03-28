import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Panel_Option extends JPanel
{
    private static int numberofthreads = 1;
    private static boolean addAppendices = false;
    private static boolean multiThreadingEnabled = false;

    private String[] threadOptions =
    {
            "1", "2", "3", "4", "5"
    };

    private JCheckBox appendixBtn;
    private JCheckBox multiThreadBtn;
    private JComboBox<String> threadComboBox;


    public Panel_Option()
    {
        setLayout(new GridLayout(0, 3));

        appendixBtn = new JCheckBox("Add Appendices");
        multiThreadBtn = new JCheckBox("Multi-threading");
        multiThreadBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                multiThreadingEnabled = !multiThreadingEnabled;
                threadComboBox.setEnabled(multiThreadingEnabled);
                if (!threadComboBox.isEnabled())
                {
                    numberofthreads = 1;
                    System.out.println(numberofthreads);
                    threadComboBox.setSelectedIndex(0);
                }
            }
        });

        add(appendixBtn);
        add(multiThreadBtn);
        setupComboBox();

        JPanel subPanel = new JPanel(new GridLayout(2, 0));
        subPanel.add(new JLabel("Number of threads: "));
        subPanel.add(threadComboBox);

        add(subPanel);

        // add(threadComboBox);

    }

    private void setupComboBox()
    {
        threadComboBox = new JComboBox<String>(threadOptions);
        threadComboBox.setEnabled(false);
        threadComboBox.setSelectedIndex(0);
        threadComboBox.setEditable(false);
        threadComboBox.setOpaque(true);
        threadComboBox.setToolTipText("This is for thread selection");

        threadComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
                switch (comboBox.getSelectedIndex())
                {
                    case 0:
                        numberofthreads = 1;
                        System.out.println(numberofthreads);
                        break;
                    case 1:
                        numberofthreads = 2;
                        System.out.println(numberofthreads);
                        break;
                    case 2:
                        numberofthreads = 3;
                        System.out.println(numberofthreads);
                        break;
                    case 3:
                        numberofthreads = 4;
                        System.out.println(numberofthreads);
                        break;
                    case 4:
                        numberofthreads = 5;
                        System.out.println(numberofthreads);
                        break;
                    default:
                        numberofthreads = 1;
                        System.out.println(numberofthreads);
                        break;
                }
            }
        });
    }

}
