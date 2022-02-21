package com.hawolt.task;

import com.hawolt.Method;
import com.hawolt.Request;
import com.hawolt.Response;
import com.hawolt.error.AntiCaptchaException;
import com.hawolt.error.ErrorType;
import org.json.JSONObject;

import java.io.IOException;

public class TaskBuilder {

    private static final String BASE_URL = "https://api.anti-captcha.com/createTask";

    private final JSONObject task = new JSONObject();
    private final String clientKey;

    public TaskBuilder(String clientKey, TaskType taskType, String target, String siteKey) {
        this.task.put("type", taskType.name());
        this.task.put("websiteKey", siteKey);
        this.task.put("websiteURL", target);
        this.clientKey = clientKey;
    }

    public CaptchaTask create() throws AntiCaptchaException, IOException {
        Request request = new Request(BASE_URL, Method.POST, true);
        JSONObject payload = new JSONObject();
        payload.put("clientKey", clientKey);
        payload.put("task", task);
        request.write(payload);
        Response response = new Response(request);
        JSONObject tmp = new JSONObject(response.getBodyAsString());
        ErrorType type = ErrorType.find(tmp);
        if (type == ErrorType.NO_ERROR) return new CaptchaTask(clientKey, tmp.getLong("taskId"));
        else throw new AntiCaptchaException(type);
    }
}
