import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

import java.util.List;

public class RealAIAnalyzer {
    private String extractText(Response response) {
        if (response == null) {
            return "Нет ответа от AI";
        }

        try {
            Object output = response.output();
            return output.toString();
        } catch (Exception e) {
            return "Ошибка при разборе ответа AI";
        }
    }
    public String analyze(List<DayReport> reports) {
        if (reports.isEmpty()) {
            return "Пока нет данных для AI анализа.";
        }

        StringBuilder data = new StringBuilder();

        for (DayReport report : reports) {
            data.append(report.toCsv()).append("\n");
        }

        String prompt = """
                Ты мой личный AI-тренер по дисциплине.
                Говори кратко, по делу, как наставник.

                Формат данных:
                дата, подъём до 5, Java, английский, полезное дело, спорт

                Мои данные:
                """ + data + """

                Сделай:
                1. Общий вывод
                2. Что получается хорошо
                3. Где я чаще всего проседаю
                4. Конкретный план на завтра из 3 шагов
                5. Оценка дисциплины от 1 до 10
                """;

        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        ResponseCreateParams params = ResponseCreateParams.builder()
                .model("gpt-4o-mini")
                .input(prompt)
                .build();

        Response response = client.responses().create(params);

        String aiText = extractText(response);
        return aiText;
    }
}
