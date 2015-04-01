import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class Panel_Option extends JPanel
{
    private static int numberofthreads = 1;
    private static boolean appendixEnabled = false;
    private static boolean multiThreadingEnabled = false;

    private static String[] threadOptions =
    {
            "1", "2", "3", "4", "5"
    };

    private static JCheckBox appendixBtn;
    private static JCheckBox multiThreadBtn;
    private static JComboBox<String> threadComboBox;
    private static JPanel subPanel;


    public Panel_Option()
    {
        setLayout(new GridLayout(0, 3));
        setupComboBox();

        appendixBtn = new JCheckBox("Add Appendices");
        multiThreadBtn = new JCheckBox("Multi-threading");



        appendixBtn.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent paramItemEvent)
            {
                if (appendixBtn.isSelected())
                {
                    appendixEnabled = true;
                    System.out.println("Appendix selected = " + appendixEnabled);
                }
                else
                {
                    appendixEnabled = false;
                    System.out.println("Appendix selected = " + appendixEnabled);
                }
            }
        });

        multiThreadBtn.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent paramItemEvent)
            {
                if (multiThreadBtn.isSelected())
                {
                    multiThreadingEnabled = true;
                    System.out.println("multithreading enabled = " + multiThreadingEnabled);
                }
                else
                {
                    multiThreadingEnabled = false;
                    System.out.println("multithreading enabled = " + multiThreadingEnabled);
                }

                if (multiThreadingEnabled == true)
                {
                    threadComboBox.setEnabled(true);
                }
                else
                {
                    threadComboBox.setEnabled(false);
                }

                if (!threadComboBox.isEnabled())
                {
                    numberofthreads = 1;
                    System.out.println("number of thread is set back to " + numberofthreads);
                    threadComboBox.setSelectedIndex(0);
                }
            }
        });


        appendixBtn.setSelected(true);
        multiThreadBtn.setSelected(false);



        subPanel = new JPanel(new GridLayout(2, 0));
        subPanel.add(new JLabel("Number of threads: "));
        subPanel.add(threadComboBox);

        add(appendixBtn);
        add(multiThreadBtn);
        add(subPanel);
    }

    public static int getNumberOfThreads()
    {
        return numberofthreads;
    }

    public static boolean getAppendixStatus()
    {
        return appendixEnabled;
    }

    public static boolean getMtStatus()
    {
        return multiThreadingEnabled;
    }

    private void setupComboBox()
    {
        threadComboBox = new JComboBox<String>(threadOptions);
        threadComboBox.setEnabled(false);
        threadComboBox.setSelectedIndex(0);
        threadComboBox.setEditable(false);
        threadComboBox.setOpaque(true);
        threadComboBox.setToolTipText("This combo box is for thread selection");
        ((JLabel) threadComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        
        threadComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Export.setFlag(true);
                JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
                switch (comboBox.getSelectedIndex())
                {
                    case 0:
                        numberofthreads = 1;
                        System.out.println("number of threads = " + numberofthreads);
                        break;
                    case 1:
                        numberofthreads = 2;
                        System.out.println("number of threads = " + numberofthreads);
                        break;
                    case 2:
                        numberofthreads = 3;
                        System.out.println("number of threads = " + numberofthreads);
                        break;
                    case 3:
                        numberofthreads = 4;
                        System.out.println("number of threads = " + numberofthreads);
                        break;
                    case 4:
                        numberofthreads = 5;
                        System.out.println("number of threads = " + numberofthreads);
                        break;
                    default:
                        numberofthreads = 1;
                        System.out.println("number of threads = " + numberofthreads);
                        break;
                }
            }
        });
    }

}
