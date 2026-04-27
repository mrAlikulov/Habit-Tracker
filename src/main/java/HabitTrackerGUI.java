import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class HabitTrackerGUI extends JFrame{
    private HabitTracker tracker = new HabitTracker();

    private JCheckBox wakeUpBox;
    private JCheckBox javaBox;
    private JCheckBox englishBox;
    private JCheckBox usefulTaskBox;
    private JCheckBox sportBox;
    private JTextField dateField;
    private JTextArea outputArea;

    public HabitTrackerGUI() {
        setTitle("Habit Tracker AI");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }
    private void showDayReport() {
        try {
            LocalDate date = LocalDate.parse(dateField.getText());

            DayReport report = tracker.getReportByDate(date);

            if (report == null) {
                outputArea.setText("Нет отчёта за дату: " + date);
                return;
            }

            outputArea.setText("Дата: " + report.getDate() + "\n");
            outputArea.append("Подъём до 5: " + (report.isWakeUpBeforeFive() ? "✅" : "❌") + "\n");
            outputArea.append("Java: " + (report.isJavaStudy() ? "✅" : "❌") + "\n");
            outputArea.append("Английский: " + (report.isEnglishStudy() ? "✅" : "❌") + "\n");
            outputArea.append("Полезное дело: " + (report.isUsefulTask() ? "✅" : "❌") + "\n");
            outputArea.append("Спорт: " + (report.isSport() ? "✅" : "❌") + "\n");
            outputArea.append("Итог: " + report.getScore() + "/5\n");
            outputArea.append(report.getStatusMessage());

        } catch (Exception e) {
            outputArea.setText("Ошибка: введи дату в формате YYYY-MM-DD");
        }
    }

    private void createUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel habitsPanel = new JPanel();
        habitsPanel.setLayout(new GridLayout(5, 1));

        wakeUpBox = new JCheckBox("Подъём до 5");
        javaBox = new JCheckBox("Java 1 час");
        englishBox = new JCheckBox("Английский 30 минут");
        usefulTaskBox = new JCheckBox("Полезное дело");
        sportBox = new JCheckBox("Спорт");
        dateField = new JTextField(LocalDate.now().toString(), 10);


        habitsPanel.add(wakeUpBox);
        habitsPanel.add(javaBox);
        habitsPanel.add(englishBox);
        habitsPanel.add(usefulTaskBox);
        habitsPanel.add(sportBox);

        JPanel buttonsPanel = new JPanel();

        JButton saveButton = new JButton("Сохранить день");
        JButton weekButton = new JButton("Недельный отчёт");
        JButton monthButton = new JButton("Месячный отчёт");
        JButton aiButton = new JButton("AI анализ");
        JButton dayButton = new JButton("Отчёт за день");
        dayButton.addActionListener(e -> showDayReport());
        JButton exitButton = new JButton("Выход");

        buttonsPanel.add(new JLabel("Дата:"));
        buttonsPanel.add(dateField);
        buttonsPanel.add(dayButton);
        buttonsPanel.add(exitButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(weekButton);
        buttonsPanel.add(monthButton);
        buttonsPanel.add(aiButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        mainPanel.add(habitsPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        saveButton.addActionListener(e -> saveDayReport());
        weekButton.addActionListener(e -> showWeekReport());
        monthButton.addActionListener(e -> showMonthReport());
        aiButton.addActionListener(e -> showAIAnalysis());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void saveDayReport() {
        DayReport report = new DayReport(
                LocalDate.now(),
                wakeUpBox.isSelected(),
                javaBox.isSelected(),
                englishBox.isSelected(),
                usefulTaskBox.isSelected(),
                sportBox.isSelected()
        );

        tracker.addOrUpdateReport(report);

        outputArea.setText("Отчёт сохранён!\n\n");
        outputArea.append("Дата: " + report.getDate() + "\n");
        outputArea.append("Итог: " + report.getScore() + "/5\n");
        outputArea.append(report.getStatusMessage());
    }

    private void showWeekReport() {
        String report = tracker.getWeekReport();
        outputArea.setText(report);
    }


    private void showMonthReport() {
        String report = tracker.getMonthReport();
        outputArea.setText(report);
    }

    private void showAIAnalysis() {
        outputArea.setText("AI думает...\n");

        try {
            String result = tracker.getRealAIAnalysis();
            outputArea.setText(result);
        } catch (Exception e) {
            outputArea.setText("Ошибка AI анализа:\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HabitTrackerGUI gui = new HabitTrackerGUI();
            gui.setVisible(true);
        });
    }
}
