package com.hawolt;

import com.hawolt.error.AntiCaptchaException;
import com.hawolt.error.ErrorType;
import com.hawolt.task.TaskBuilder;
import com.hawolt.task.TaskType;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AntiCaptcha {

    private final String clientKey;
    public static SynchronizedDouble balance;

    public AntiCaptcha(String clientKey) throws AntiCaptchaException, IOException {
        this.clientKey = clientKey;
        balance = new SynchronizedDouble(getBalance());
    }

    public double getCurrentBalance() {
        return balance.get();
    }

    private double getBalance() throws AntiCaptchaException, IOException {
        Request request = new Request(" https://api.anti-captcha.com/getBalance", Method.POST, true);
        JSONObject payload = new JSONObject();
        payload.put("clientKey", clientKey);
        request.write(payload);
        Response response = new Response(request);
        JSONObject tmp = new JSONObject(response.getBodyAsString());
        ErrorType type = ErrorType.find(tmp);
        if (type == ErrorType.NO_ERROR) return tmp.getDouble("balance");
        else throw new AntiCaptchaException(type);
    }

    public TaskBuilder buildTask(TaskType taskType, String target, String siteKey) {
        return new TaskBuilder(clientKey, taskType, target, siteKey);
    }
}
