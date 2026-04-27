import java.time.LocalDate;
import java.util.Scanner;
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static HabitTracker tracker = new HabitTracker();

    public static void main(String[] args) {

        while (true) {
            printMenu();

            int command = Integer.parseInt(scanner.nextLine());

            if (command == 1) {
                addDayReport();
            } else if (command == 2) {
                showDayReport();
            } else if (command == 3) {
                tracker.printWeekReport();
            } else if (command == 4) {
                tracker.printMonthReport();
            } else if (command == 5) {
                tracker.printRealAIAnalysis(); // твой простой AI
            } else if (command == 6) {
                System.out.println("Пока! Завтра 5/5 делаем 💪🔥");
                break;// 🔥 настоящий AI
            }
             else {
                System.out.println("Нет такой команды");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== HABIT TRACKER ===");
        System.out.println("1. Добавить дневной отчёт");
        System.out.println("2. Посмотреть отчёт за день");
        System.out.println("3. Недельный отчёт");
        System.out.println("4. Месячный отчёт");
        System.out.println("5. AI анализ (реальный)");
        System.out.println("6. Выход");
        System.out.print("Выбери: ");
    }

    private static void addDayReport() {
        LocalDate date = LocalDate.now();

        System.out.println("Отчёт за сегодня: " + date);

        boolean wakeUp = ask("Встал до 5?");
        boolean javaStudy = ask("Занимался Java 1 час?");
        boolean english = ask("Английский 30 мин?");
        boolean usefulTask = ask("Сделал полезное дело?");
        boolean sport = ask("Был спорт?");

        DayReport report = new DayReport(
                date,
                wakeUp,
                javaStudy,
                english,
                usefulTask,
                sport
        );

        tracker.addOrUpdateReport(report);

        System.out.println("\nОтчёт сохранён!");
        report.printReport();
    }

    static void showDayReport() {
        System.out.print("Введи дату (YYYY-MM-DD): ");
        String input = scanner.nextLine();

        LocalDate date = LocalDate.parse(input);

        tracker.printDayReport(date);
    }

    private static boolean ask(String question) {
        System.out.print(question + " (y/n): ");
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("y");
    }
}
