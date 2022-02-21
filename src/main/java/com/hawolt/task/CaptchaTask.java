package com.hawolt.task;

import com.hawolt.Logger;
import com.hawolt.Method;
import com.hawolt.Request;
import com.hawolt.Response;
import com.hawolt.error.AntiCaptchaException;
import com.hawolt.error.ErrorType;
import org.json.JSONObject;

import java.io.IOException;

public class CaptchaTask {

    private static final String BASE_URL = "https://api.anti-captcha.com/getTaskResult";

    private final long CREATED_AT = System.currentTimeMillis();
    private final String clientKey;
    private final long id;

    public CaptchaTask(String clientKey, long id) {
        this.clientKey = clientKey;
        this.id = id;
    }

    public CaptchaResult complete() {
        CaptchaResult result = null;
        do {
            if (System.currentTimeMillis() - CREATED_AT >= 240000) return CaptchaResult.FAILURE;
            try {
                Thread.sleep(10000);
                result = get();
            } catch (AntiCaptchaException e) {
                Logger.error(e);
                return CaptchaResult.FAILURE;
            } catch (IOException | InterruptedException e) {
                Logger.error(e);
            }
        } while (result == null);
        return result;
    }

    private CaptchaResult get() throws IOException, AntiCaptchaException {
        Request request = new Request(BASE_URL, Method.POST, true);
        JSONObject payload = new JSONObject();
        payload.put("clientKey", clientKey);
        payload.put("taskId", id);
        request.write(payload);
        Response response = new Response(request);
        JSONObject tmp = new JSONObject(response.getBodyAsString());
        ErrorType type = ErrorType.find(tmp);
        if (type == ErrorType.NO_ERROR) {
            if (tmp.getString("status").equals("processing")) return null;
            else return new CaptchaResult(tmp);
        } else throw new AntiCaptchaException(type);
    }
}
