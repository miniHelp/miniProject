package Util;

import java.util.HashMap;
import java.util.Map;

public class PaymentChineseName {
    static Map<Integer,String> map = new HashMap<>();
    static {
        map.put(1,"支付宝扫码");
        map.put(2,"支付宝WAP");
        map.put(3,"微信掃碼");
        map.put(4,"微信WAP");
        map.put(5,"網銀");
        map.put(6,"財付通掃碼");
        map.put(7,"財付通WAP");
        map.put(8,"QQ钱包掃碼");
        map.put(9,"QQ钱包WAP");
        map.put(10,"京东钱包掃碼");
        map.put(11,"京东钱包WAP");
        map.put(12,"百度钱包掃碼");
        map.put(13,"百度钱包WAP");
        map.put(14,"網銀快捷");
        map.put(15,"五碼合一");
        map.put(16,"銀聯掃碼|云闪付");
        map.put(21,"点卡");
        map.put(22,"微信反掃");
        map.put(23,"支付寶反掃");
        map.put(24,"QQ反掃");
        map.put(25,"支付宝转账银行卡扫码");
        map.put(27,"支付寶固碼");
        map.put(28,"微信固碼");
        map.put(29,"QQ固碼");
        map.put(41,"支付宝转账银行卡WAP");
    }

    public String getPaymentName(int paymentMethodId){
        return map.get(paymentMethodId);
    }


}
