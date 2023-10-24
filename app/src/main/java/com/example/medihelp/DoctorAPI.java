//package com.example.medihelp;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import io.github.cdimascio.dotenv.Dotenv;
//
//public class DoctorApi {
//    private static final String OPENAI_ENDPOINT = "https://api.openai.com/v1/engines/davinci/completions";
//    private static final Dotenv dotenv = Dotenv.load();
//    private static final String OPENAI_KEY = dotenv.get("OPENAI_KEY");
//
//    public static void main(String[] args) {
//        Javalin app = Javalin.create().start(7000);
//
//        app.post("/generate-text", DoctorApi::handleGenerateTextRequest);
//    }
//
//    private static void handleGenerateTextRequest(Context ctx) {
//        try {
//            Map<String, String> requestMap = ctx.bodyAsClass(Map.class);
//            String prompt = requestMap.get("You are an AI assistant that is an expert in medical health and is part of a hospital system called medicare AI\n" +
//                    "You know about symptoms and signs of various types of illnesses.\n" +
//                    "Recommend the mecical symptom queries with which specialist they should consult\n" +
//                    "If you are asked a question that is not related to medical health respond with \"Im sorry but your question is beyond my functionalities\".\n" +
//                    "Just give one word answer which is the specialist to be consulted as output. Nothing more\n" +
//                    "Do not use external URLs or blogs to refer\n" +
//                    "Format any lists on individual lines with a dash and a space in front of each line.");
//
//            String responseText = fetchChatGptResponse(prompt);
//            ctx.json(Map.of("response", responseText));
//
//        } catch (Exception e) {
//            logger.error("Error processing request", e);
//            ctx.status(500).result("Internal server error");
//        }
//    }
//
//    private static String fetchChatGptResponse(String prompt) throws Exception {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(new URI(OPENAI_ENDPOINT))
//                .header("Authorization", "Bearer " + API_KEY)
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"prompt\":\"%s\", \"max_tokens\":150}", prompt)))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // TODO: Parse the response and extract the completion text.
//        // This is a simple example, you'll want a more robust JSON parsing method.
//        return response.body();  // Modify this to extract the exact text from the JSON response.
//    }
//}
//
