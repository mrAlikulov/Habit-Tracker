import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HabitTracker {
    private List<DayReport> reports = new ArrayList<>();
    private final String fileName = "reports.csv";

    public HabitTracker() {
        loadFromFile();
    }

    public void addOrUpdateReport(DayReport newReport) {
        for (int i = 0; i < reports.size(); i++) {
            if (reports.get(i).getDate().equals(newReport.getDate())) {
                reports.set(i, newReport);
                saveToFile();
                System.out.println("Отчёт обновлён!");
                return;
            }
        }

        reports.add(newReport);
        saveToFile();
        System.out.println("Отчёт добавлен!");
    }

    public void printDayReport(LocalDate date) {
        DayReport report = findByDate(date);

        if (report == null) {
            System.out.println("Нет отчёта за этот день.");
        } else {
            report.printReport();
        }
    }

    public void printWeekReport() {
        LocalDate today = LocalDate.now();
        printPeriodReport(today.minusDays(6), today, "Недельный отчёт");
    }
    public String getWeekReport() {
        StringBuilder result = new StringBuilder();

        result.append("=== Недельный отчёт ===\n");

        for (DayReport report : reports) {
            result.append(report.getDate()).append(" -> ")
                    .append(report.getScore()).append("/5\n");
        }

        return result.toString();
    }
    public String getRealAIAnalysis() {
        RealAIAnalyzer analyzer = new RealAIAnalyzer();
        return analyzer.analyze(reports);
    }
    public DayReport getReportByDate(LocalDate date) {
        for (DayReport report : reports) {
            if (report.getDate().equals(date)) {
                return report;
            }
        }

        return null;
    }

    public void printMonthReport() {
        LocalDate today = LocalDate.now();
        printPeriodReport(today.minusDays(29), today, "Месячный отчёт");
    }
    public String getMonthReport() {
        StringBuilder result = new StringBuilder();

        result.append("=== Месячный отчёт ===\n");

        for (DayReport report : reports) {
            result.append(report.getDate()).append(" -> ")
                    .append(report.getScore()).append("/5\n");
        }

        return result.toString();
    }
    public void printRealAIAnalysis() {
        RealAIAnalyzer analyzer = new RealAIAnalyzer();
        analyzer.analyze(reports);
    }

    private void printPeriodReport(LocalDate start, LocalDate end, String title) {
        List<DayReport> periodReports = new ArrayList<>();

        for (DayReport r : reports) {
            if (!r.getDate().isBefore(start) && !r.getDate().isAfter(end)) {
                periodReports.add(r);
            }
        }

        System.out.println("=== " + title + " ===");

        if (periodReports.isEmpty()) {
            System.out.println("Нет данных.");
            return;
        }

        int total = 0;

        for (DayReport r : periodReports) {
            total += r.getScore();
        }

        int max = periodReports.size() * 5;
        double percent = total * 100.0 / max;

        System.out.println("Дней: " + periodReports.size());
        System.out.println("Баллы: " + total + "/" + max);
        System.out.println("Процент: " + String.format("%.1f", percent) + "%");

        // лучший день
        DayReport best = periodReports.stream()
                .max(Comparator.comparingInt(DayReport::getScore))
                .get();

        // худший день
        DayReport worst = periodReports.stream()
                .min(Comparator.comparingInt(DayReport::getScore))
                .get();

        System.out.println("Лучший день: " + best.getDate() + " (" + best.getScore() + "/5)");
        System.out.println("Худший день: " + worst.getDate() + " (" + worst.getScore() + "/5)");

        System.out.println("Серия (streak ≥3): " + calculateStreak());

        printLevel(percent);
    }

    private int calculateStreak() {
        reports.sort(Comparator.comparing(DayReport::getDate));

        int streak = 0;
        int maxStreak = 0;

        for (DayReport r : reports) {
            if (r.getScore() >= 3) {
                streak++;
                if (streak > maxStreak) {
                    maxStreak = streak;
                }
            } else {
                streak = 0;
            }
        }

        return maxStreak;
    }

    private void printLevel(double percent) {
        if (percent >= 90) {
            System.out.println("Уровень: Легенда 🔥");
        } else if (percent >= 75) {
            System.out.println("Уровень: Очень хорошо 💪");
        } else if (percent >= 60) {
            System.out.println("Уровень: Нормально 🙂");
        } else {
            System.out.println("Уровень: Нужно собраться ⚠️");
        }
    }

    private DayReport findByDate(LocalDate date) {
        for (DayReport r : reports) {
            if (r.getDate().equals(date)) {
                return r;
            }
        }
        return null;
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (DayReport r : reports) {
                writer.println(r.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Ошибка сохранения.");
        }
    }

    private void loadFromFile() {
        File file = new File(fileName);

        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                reports.add(DayReport.fromCsv(line));
            }
        } catch (IOException e) {
            System.out.println("Ошибка загрузки.");
        }
    }
}
