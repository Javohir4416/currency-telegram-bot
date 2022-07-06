package javokhir.dev.currencytelegrambot.service;

import javokhir.dev.currencytelegrambot.entity.SmsForToken;
import javokhir.dev.currencytelegrambot.payload.SMS.SMSDto;
import javokhir.dev.currencytelegrambot.repo.SmsForApiRepo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsForApiRepo smsForApiRepo;

    public SmsForToken getSmsApi(){
        Long maxId = smsForApiRepo.maxId();
        return smsForApiRepo.findById(maxId).get();

    }

    public SmsForToken  addSmsApi(SmsForToken smsForToken) {
            String token = getToken(smsForToken);
            smsForToken.setToken(token);
            smsForApiRepo.save(smsForToken);
            return smsForToken;
    }

    public String getToken(SmsForToken smsForToken) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("email",smsForToken.getEmail())
                .addFormDataPart("password",smsForToken.getPassword())
                .build();
        Request request = new Request.Builder()
                .url(smsForToken.getUrl())
                .method("POST", body)
                .build();
        try {

            Response response = client.newCall(request).execute();
            String str=response.body().string();
            Gson gson=new Gson();
            SMSDto sms=gson.fromJson(str, SMSDto.class);
            return sms.getData().getToken();
        }catch (Exception e){
            return null;
        }

    }


    public void sendMessageCode(String phoneNumber, String code){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("mobile_phone",phoneNumber.substring(1))
                .addFormDataPart("message","Tasdiqlash kodi: "+code)
                .addFormDataPart("from","4546")
                .addFormDataPart("callback_url","http://0000.uz/test.php")
                .addFormDataPart("user_sms_id","sms1")
                .build();
        Request request = new Request.Builder()
                .url("https://notify.eskiz.uz/api/message/sms/send")
                .addHeader("Authorization", "Bearer "+getSmsApi().getToken())
                .method("POST", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body1 = response.body();

        }catch (Exception ignored){
        }
    }



}
