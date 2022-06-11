package software.robsoncassiano.learn;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GsonExamples {
    public static final String API_KEY = "eb860b2c8563448b80af73b4ae2d2522";
    public static final String TRANSCRIPT_URL = "https://api.assemblyai.com/v2/transcript";

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        final Transcript transcript = new Transcript();
        transcript.setAudio_url("https://speech-to-text-demo.ng.bluemix.net/audio/en-US_Broadband_sample2.wav");
        final String json = new Gson().toJson(transcript);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(TRANSCRIPT_URL))
                .header("Authorization", API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        final HttpResponse<String> response = HttpClient.newHttpClient().send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        final Transcript fromJson = new Gson().fromJson(response.body(), Transcript.class);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(TRANSCRIPT_URL + "/" + fromJson.getId()))
                .header("Authorization", API_KEY)
                .GET()
                .build();

        Transcript statusBody;
        do {
            final HttpResponse<String> getResponse = HttpClient.newHttpClient().send(getRequest, HttpResponse.BodyHandlers.ofString());
            statusBody = new Gson().fromJson(getResponse.body(), Transcript.class);

            System.out.println(statusBody.getStatus());
        } while (!"completed".equals(statusBody.getStatus()) && !"error".equals(statusBody.getStatus()));
        System.out.println(statusBody.getText());
    }
}

class Transcript {
    private String id;
    public String status;
    public String text;
    private String audio_url;

    public String getAudio_url() {
        return audio_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
