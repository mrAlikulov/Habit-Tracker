import java.time.LocalDate;
import java.time.LocalDateTime;

public class DayReport {
    private LocalDate date;
    private boolean wakeUpBeforeFive;
    private boolean javaStudy;
    private boolean englishStudy;
    private boolean usefulTask;
    private boolean sport;

    public DayReport(LocalDate date, boolean wakeUpBeforeFive, boolean javaStudy,
                     boolean englishStudy, boolean usefulTask, boolean sport) {
        this.date = date;
        this.wakeUpBeforeFive = wakeUpBeforeFive;
        this.javaStudy = javaStudy;
        this.englishStudy = englishStudy;
        this.usefulTask = usefulTask;
        this.sport = sport;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public boolean isWakeUpBeforeFive() {
        return wakeUpBeforeFive;
    }

    public boolean isJavaStudy() {
        return javaStudy;
    }

    public boolean isEnglishStudy() {
        return englishStudy;
    }

    public boolean isUsefulTask() {
        return usefulTask;
    }

    public boolean isSport() {
        return sport;
    }
    public int getScore(){
        int score = 0;
        if(wakeUpBeforeFive) score++;
        if(javaStudy) score++;
        if(englishStudy) score++;
        if(usefulTask) score++;
        if(sport) score++;
        return score;
    }
    public String getStatusMessage() {
        int score = getScore();

        if (score == 5) {
            return "Отлично! День закрыт на максимум 🔥";
        } else if (score == 4) {
            return "Хорошо! Ты почти справился 💪";
        } else if (score == 3) {
            return "Нормально, но можно лучше 🙂";
        } else if (score >= 1) {
            return "Слабый день, завтра надо собраться ⚠️";
        } else {
            return "День провален, но главное — не бросать ❌";
        }
    }
    public String toCsv() {
        return date + "," +
                wakeUpBeforeFive + "," +
                javaStudy + "," +
                englishStudy + "," +
                usefulTask + "," +
                sport;
    }

    public static DayReport fromCsv(String line) {
        String[] parts = line.split(",");

        LocalDate date = LocalDate.parse(parts[0]);
        boolean wakeUp = Boolean.parseBoolean(parts[1]);
        boolean javaStudy = Boolean.parseBoolean(parts[2]);
        boolean english = Boolean.parseBoolean(parts[3]);
        boolean usefulTask = Boolean.parseBoolean(parts[4]);
        boolean sport = Boolean.parseBoolean(parts[5]);

        return new DayReport(date, wakeUp, javaStudy, english, usefulTask, sport);
    }

    public void printReport() {
        System.out.println("Дата: " + date);
        System.out.println("Подъём до 5: " + yesNo(wakeUpBeforeFive));
        System.out.println("Java: " + yesNo(javaStudy));
        System.out.println("Английский: " + yesNo(englishStudy));
        System.out.println("Полезное дело: " + yesNo(usefulTask));
        System.out.println("Спорт: " + yesNo(sport));
        System.out.println("Итог: " + getScore() + "/5");
        System.out.println(getStatusMessage());
    }

    private String yesNo(boolean value) {
        return value ? "✅" : "❌";
    }
}
