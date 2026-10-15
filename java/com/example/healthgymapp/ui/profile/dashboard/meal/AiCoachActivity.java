package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthgymapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AiCoachActivity extends AppCompatActivity {

    private EditText edtUserMessage;
    private Button btnSend;
    private TextView tvChatResponse;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Mã API Key chuẩn của bạn (đã được kích hoạt thành công trên Google Cloud)
    private static final String API_KEY = "AQ.Ab8RN6IEnA37qNMvQIYyhpWtick2FcT3TdfgD-U8Jho8g9XAJQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_coach);

        edtUserMessage = findViewById(R.id.edtUserMessage);
        btnSend = findViewById(R.id.btnSend);
        tvChatResponse = findViewById(R.id.tvChatResponse);

        btnSend.setOnClickListener(v -> {
            String prompt = edtUserMessage.getText().toString().trim();
            if (!prompt.isEmpty()) {
                tvChatResponse.append("\n\n🧑 Bạn: " + prompt);
                edtUserMessage.setText("");
                tvChatResponse.append("\n\n🤖 AI Coach đang trả lời...");
                callGeminiRestApi(prompt);
            }
        });
    }

    private void callGeminiRestApi(String userPrompt) {
        executor.execute(() -> {
            try {
                // Endpoint URL chuẩn xác dùng gemini-1.5-flash
                String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;
                URL url = new URL(apiUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                // Tạo JSON Body theo đúng cấu trúc chuẩn của Gemini REST API
                JSONObject textObj = new JSONObject();
                textObj.put("text", userPrompt);

                JSONArray partsArray = new JSONArray();
                partsArray.put(textObj);

                JSONObject contentObj = new JSONObject();
                contentObj.put("parts", partsArray);

                JSONArray contentsArray = new JSONArray();
                contentsArray.put(contentObj);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("contents", contentsArray);

                // Gửi request
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = in.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    // Parse kết quả trả về từ Gemini API
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray candidates = jsonResponse.getJSONArray("candidates");
                    JSONObject firstCandidate = candidates.getJSONObject(0);
                    JSONObject content = firstCandidate.getJSONObject("content");
                    JSONArray parts = content.getJSONArray("parts");
                    String reply = parts.getJSONObject(0).getString("text");

                    runOnUiThread(() -> tvChatResponse.append("\n\n🤖 AI Coach: " + reply));
                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        errorResponse.append(line.trim());
                    }
                    String err = errorResponse.toString();
                    runOnUiThread(() -> Toast.makeText(AiCoachActivity.this, "Lỗi HTTP " + responseCode + ": " + err, Toast.LENGTH_LONG).show());
                }

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(AiCoachActivity.this, "Lỗi kết nối: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }
}