package cn.itrip;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.cloopen.rest.sdk.CCPRestSmsSDK.*;
import java.util.HashMap;
import java.util.Set;

public class SMS_Sent {

    public static void SentSms(String phone,String code) {
        HashMap<String, Object> result = null;
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init("app.cloopen.com", "8883");
        // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
        restAPI.setAccount("8a216da8685986c2016863c56831044b", "ea14047bbf2c446486d6d6c831aba8dd");
        // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
        restAPI.setAppId("8a216da8685986c2016863c568890451");
        // 请使用管理控制台中已创建应用的APPID。
        result = restAPI.sendTemplateSMS(phone,"1" ,new String[]{code});
        System.out.println("SDKTestGetSubAccounts result=" + result);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
    }
}
