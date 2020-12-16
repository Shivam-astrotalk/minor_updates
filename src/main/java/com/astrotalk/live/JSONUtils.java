package com.astrotalk.live;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public class JSONUtils {
    public static GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
    public static Gson gson = gsonBuilder.create();

    public static String getSuccessJsonString(String key, String value) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (value == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, value);
        return obj.toString();
    }

    public static String getSuccessJsonObject(String key, Object value) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (value == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, value);
        return obj.toString();
    }

    public static String getSuccessJsonDouble(String key, Double value) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");

        if (value == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, value);
        return obj.toString();
    }

    public static String getSuccessJson(Object o) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj.toString();
    }

    public static String getSuccessJson(Object o, String key, Object value) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put(key, value);
        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj.toString();
    }

    public static String getSuccessJson_KeyValue(Object o, String key, Object value) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");

        if (value == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, value);

        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj.toString();
    }

    public static String getSuccessJson_KeyValue2(Object o, String key, Object value, String key1, Object value1)
            throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");

        if (value == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, value);

        if (value1 == null)
            obj.put(key1, JSONObject.NULL);
        else
            obj.put(key1, value1);

        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj.toString();
    }

    public static String getSuccessJson_KeyValue(Object o, String key, String value) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");

        if (value == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, value);

        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj.toString();
    }

    public static String getSuccessJson_2KeyValueObj(Object o, String key, String value, String key2, Object value2) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");

        if (value == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, value);

        if (value2 == null)
            obj.put(key2, JSONObject.NULL);
        else
            obj.put(key2, value);

        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj.toString();
    }

    public static String getSuccessJson_3KeyValueObj(Object o, String key, String value, String key2, Object value2, String key3, Object value3) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");

        if (value == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, value);

        if (value2 == null)
            obj.put(key2, JSONObject.NULL);
        else
            obj.put(key2, value);

        if (value3 == null)
            obj.put(key3, JSONObject.NULL);
        else
            obj.put(key3, value3);

        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj.toString();
    }


    public static String getSuccessJson_KeyValue(String key, Object value) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");

        if (value == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, value);

        return obj.toString();
    }

    public static String getSuccessJson_2KeyValue(String key1, Object value1, String key2, Object value2) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");

        if (value1 == null)
            obj.put(key1, JSONObject.NULL);
        obj.put(key1, value1);

        if (value2 == null)
            obj.put(key2, JSONObject.NULL);
        obj.put(key2, value2);


        return obj.toString();
    }

    public static String getSuccessJson_KeyValue2(Object o, String key1, String value1, String key2, String value2)
            throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");

        if (value1 == null)
            obj.put(key1, JSONObject.NULL);
        else
            obj.put(key1, value1);

        if (value2 == null)
            obj.put(key2, JSONObject.NULL);
        else
            obj.put(key2, value2);

        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj.toString();
    }

    public static String getSuccessJson_KeyValue3(Object o, String key1, Object value1, String key2, Object value2,
                                                  String key3, Object value3) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put(key3, value3);
        obj.put(key2, value2);
        obj.put(key1, value1);

        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj.toString();
    }

    public static String getSuccessJson_KeyValue4(Object o, String key1, Object value1, String key2, Object value2,
                                                  String key3, Object value3, String key4, Object value4) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");

        if (value4 == null)
            obj.put(key4, JSONObject.NULL);
        else
            obj.put(key4, value4);

        obj.put(key3, value3);
        obj.put(key2, value2);
        obj.put(key1, value1);

        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj.toString();
    }

    public static String getSuccessJson_isToTakeVerifiedNo(Object o, boolean isToTakeVerifiedNo,
                                                           boolean isToTakeVerifiedNo_firebase, boolean hasLanguage) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        obj.put("isToTakeVerifiedNo", isToTakeVerifiedNo);
        obj.put("isToTakeVerifiedNo_firebase", isToTakeVerifiedNo_firebase);
        obj.put("hasLanguage", hasLanguage);

        return obj.toString();
    }

    public static String getSuccessJsonWithDndChecker(Object o, boolean hasDnd) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        obj.put("hasDnd", hasDnd);
        return obj.toString();
    }

    public static String getSuccessJsonPayment(Object o, double totalPaymentOfConsultant) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        obj.put("totalPaymentOfConsultant", totalPaymentOfConsultant);
        return obj.toString();
    }

    public static String getSuccessJsonPaymentData(Object o, double totalPaymentOfConsultant, long totalElement,
                                                   int totalPages) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        obj.put("totalPaymentOfConsultant", totalPaymentOfConsultant);
        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);
        return obj.toString();
    }

    public static String getSuccessJsonWithConsultantPart(List set, Integer total, Long totalCount,
                                                          double totalPaymentOfConsultant) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("totalPages", total);
        obj.put("totalCount", totalCount);
        obj.put("totalPaymentOfConsultant", totalPaymentOfConsultant);
        obj.put("data", new org.json.JSONArray(gson.toJson(set)));
        return obj.toString();
    }

    public static String getSuccessJsonStatus(boolean status) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("isAvailable", status);
        return obj.toString();
    }

    public static String getSuccessJsonUser(Object o, String otp, String email) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        obj.put("is_register", true);
        obj.put("otp", otp);
        obj.put("email", email);
        return obj.toString();
    }

    public static String getSuccessJsonUserIOSDemoAccount(Object o, String otp, String email) throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        obj.put("is_register", true);
        obj.put("otp", otp);
        obj.put("email", email);
        return obj.toString();
    }

    public static String getSuccessJson(List list) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        return obj.toString();
    }

    public static String getSuccessJsonByTyping(List list, Map<Object, Object> typing) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        obj.put("typing", typing);
        return obj.toString();
    }


    public static String getSuccessJson_keyValue(List list, String key, Object value) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        obj.put(key, value);

        return obj.toString();
    }

    public static String getSuccessJson_keyValue(List list, String key, String value) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put(key, value);
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        return obj.toString();
    }

    public static String getSuccessJsonWithGooglePlaces(List list, Boolean googlePlaces,Boolean atLocationApi) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("googlePlaces", googlePlaces);
        obj.put("atLocationApi", atLocationApi);
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        return obj.toString();
    }

    public static String getSuccessJsonForPaymentGateway(List list, Long defaultIdIndia, Long defaultIdForein)
            throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        obj.put("defaultPaymentGatewayId", defaultIdIndia);
        obj.put("defaultPaymentGatewayIdForeign", defaultIdForein);

        return obj.toString();
    }

    public static String getSuccessJsonWithSlotFee(List list, double slotFee) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        obj.put("slotFee", slotFee);
        return obj.toString();
    }

    public static String getSuccessJson(List list, double credit, double questionFee, double videoCallFee,
                                        boolean isForceUpdateIos, boolean isForceUpdateAndroid, String iosVersion, String androidVersion,
                                        boolean isSeen) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        obj.put("creditData", credit);
        obj.put("questionFee", questionFee);
        obj.put("videoCallFee", videoCallFee);
        obj.put("isForceUpdateIos", isForceUpdateIos);
        obj.put("isForceUpdateAndroid", isForceUpdateAndroid);
        obj.put("iosVersion", iosVersion);
        obj.put("androidVersion", androidVersion);
        obj.put("seeQuestion", isSeen);
        return obj.toString();
    }

    public static String getSuccessJsonRfereal(List list, String refferedBy) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));

        if (refferedBy == null)
            obj.put("refferedBy", "");
        else
            obj.put("refferedBy", refferedBy);

        return obj.toString();
    }

    public static String getSuccessJsonSet(Set set) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(set)));
        return obj.toString();
    }

    public static String getSuccessJsonNotification(List list, Long no_of_unseen_notification) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("no_of_unseen_notification", no_of_unseen_notification);
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        return obj.toString();
    }

    public static String getSuccessJsonPayment(List list, Double total_payment) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("total_payment", total_payment);
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        return obj.toString();
    }

    public static String getSuccessJsonSpcility(List list, List list1) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("speciality", new org.json.JSONArray(gson.toJson(list1)));
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        return obj.toString();
    }

    public static String getSuccessVerfymobile(Integer otp, String mobileno, String country_code) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("otp", otp);
        obj.put("mobileno", mobileno);
        obj.put("country_code", country_code);
        return obj.toString();
    }

    public static String sendOtp(String otp, String email) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("otp", otp);
        obj.put("email", email);
        obj.put("is_register", false);
        return obj.toString();
    }

    public static String sendOtpIOSDemoAccount(String otp, String email) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("otp", otp);
        obj.put("email", email);
        obj.put("is_register", true);

        return obj.toString();
    }

    public static String getSuccessJsonForPagenation(Set set, Integer total) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("totalPages", total);
        obj.put("data", new org.json.JSONArray(gson.toJson(set)));
        return obj.toString();
    }

    public static String getSuccessJsonForPagenationList(List list, int total) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("totalPages", total);
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        return obj.toString();
    }

    public static String getSuccessJsonForcount(Long count) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("count", count);

        return obj.toString();
    }

    public static String getSuccessJsonForPagenationListByTotal(List set, Integer total, Integer totalCount)
            throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("totalPages", total);
        obj.put("totalCount", totalCount);
        obj.put("data", new org.json.JSONArray(gson.toJson(set)));
        return obj.toString();
    }

    public static String getSuccessJsonForPagenation(Page page) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(page)));
        return obj.toString();
    }

    public static String getSuccessJsonForPagenationWithAmount(List set, Integer total, Long totalCount, double amount,
                                                               Boolean isLocked) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("amount", amount);
        obj.put("totalPages", total);
        obj.put("totalCount", totalCount);
        obj.put("isLocked", isLocked);
        obj.put("data", new org.json.JSONArray(gson.toJson(set)));
        return obj.toString();

    }

    public static String getSuccessJsonForPagenationWithAmount2(List set, Integer total, Long totalCount, double amount,
                                                                Boolean isLocked, JSONObject jsonObject) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("amount", amount);
        obj.put("totalPages", total);
        obj.put("totalCount", totalCount);
        obj.put("isLocked", isLocked);
        obj.put("data", new org.json.JSONArray(gson.toJson(set)));
        obj.put("pgAmount", jsonObject);
        return obj.toString();
    }

    public static String getSuccessJsonAmountIsNewUserVoucherApplied(List set, Integer total, Long totalCount, double amount,
                                                                     Boolean isLocked, Boolean isNewUser, Boolean isVoucherApplied) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("amount", amount);
        obj.put("totalPages", total);
        obj.put("totalCount", totalCount);
        obj.put("isLocked", isLocked);
        obj.put("isNewUser", isNewUser);
        obj.put("isVoucherApplied", isVoucherApplied);
        obj.put("data", new org.json.JSONArray(gson.toJson(set)));
        return obj.toString();
    }

    public static String getSuccessJsonForPagenationWithAmount(List set, Integer total, Long totalCount, double amount)
            throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("amount", amount);
        obj.put("totalPages", total);
        obj.put("totalCount", totalCount);
        obj.put("data", new org.json.JSONArray(gson.toJson(set)));
        return obj.toString();
    }

    public static String getSuccessJsonForPagenation(List set, Integer total, Long totalCount) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("totalPages", total);
        obj.put("totalCount", totalCount);
        obj.put("totalElements", totalCount);
        obj.put("data", new org.json.JSONArray(gson.toJson(set)));
        return obj.toString();
    }

    public static String getSuccessJsonForPagenationObject(Object object, Integer total) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("totalPages", total);
        if (object == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(object)));
        return obj.toString();
    }

    public static JSONObject getSuccessJsonObject(List list) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        return obj;
    }

    public static JSONObject getSuccessJsonObject(Object o) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (o == null)
            obj.put("data", JSONObject.NULL);
        else
            obj.put("data", new JSONObject(gson.toJson(o)));
        return obj;

    }

    public static String getSuccessJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return obj.toString();
    }

    public static String getSuccessJson(String key, Object value) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (key == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, value);
        return obj.toString();
    }

    public static String getSuccessJson(double ltv) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("ltv", ltv);
        return obj.toString();
    }

    public static String getSuccessJson(int value) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("value", value);
        return obj.toString();
    }

    public static String getSuccRessJson_version(long version) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("version", version);
        return obj.toString();
    }

    public static String getSuccessJson_Long(long count) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("count", count);
        return obj.toString();
    }

    public static String getSuccessJson_Id(long id) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("id", id);
        return obj.toString();
    }

    public static JSONObject getSuccessJsonObject() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return obj;
    }

    public static JSONObject getFailJsonObject() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "fail");
        return obj;
    }

    public static String getSuccessJson(String message) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("message", message);
        return obj.toString();
    }

    public static String getSuccessJsonData(String data) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", data);
        return obj.toString();
    }

    public static String getSucce12ssJsonForChat(String consultantName) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("consultantName", consultantName);
        return obj.toString();
    }

    public static String getSuccessJsondata(String data) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", data);
        return obj.toString();
    }

    public static String getSuccessJsonRefrelData(String data, String refferedBy) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", data);
        obj.put("refferedBy", refferedBy);
        return obj.toString();
    }

    public static String getFailJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "fail");
        return obj.toString();
    }

    public static String getFailJsonWithTyping(Map<Object, Object> typing) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "fail");
        obj.put("typing", typing);
        return obj.toString();
    }

    public static String getFailJson(Map map) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "fail");
        obj.put("data", map);
        return obj.toString();
    }

    public static String getFailJsonWithReason(String reason) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "fail");
        obj.put("reason", reason);
        return obj.toString();
    }

    public static String getFailJsonWithReason(String reason, Map map) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "fail");
        obj.put("reason", reason);
        obj.put("data", map);
        return obj.toString();
    }

    public static String getFailJsonWithReasonAndMessage(String reason, String message, String flag)
            throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "fail");
        obj.put("reason", reason);
        obj.put("message", message);
        obj.put("flag", flag);
        return obj.toString();
    }

    public static String getFailJsonWithDoesNotExist() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "fail");
        obj.put("reason", "Entity does not exist");
        return obj.toString();
    }

    public static String getFailDueToNullJson(String message) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "fail");
        obj.put("reason", message);
        return obj.toString();
    }

    public static String getFailDueToAlreadyExist(String message) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "fail");
        obj.put("reason", message);
        return obj.toString();
    }

    public static String getSerchedSuccessJson(List list, Map map) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new org.json.JSONArray(gson.toJson(list)));
        obj.put("total_data", map);
        return obj.toString();
    }

    public static String getSuccessJson(Map map) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", map);
        return obj.toString();
    }
//    public static String getSuccessJsonMap(Map map) throws JSONException {
//        JSONObject obj = new JSONObject();
//        obj.put("status", "success");
//        obj.put("data", new org.json.JSONObject(map));
//        return obj.toString();
//    }

//	public static String getSuccessJsonV2(Map map) throws JSONException {
//		JSONObject obj = new JSONObject();
//		obj.put("status", "success");
//		obj.put("data", new JSONObject(map));
//		return obj.toString();
//	}

    public static JSONObject getSuccessJsonArray(JSONArray list) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", new JSONArray(gson.toJson(list)));
        return obj;
    }

    public static JSONObject getSuccessJsonNum(Map map) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("data", map);
        return obj;
    }

    public static String getSuccessJsonDataList(List list, long totalElement, int totalPages) throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("content", new org.json.JSONArray(list));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        return obj.toString();
    }

    public static String getSuccessJsonDataList(List list, long totalElement, int totalPages, String bgImageId)
            throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("content", new org.json.JSONArray(list));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        if (obj == null)
            obj.put("bgImageId", JSONObject.NULL);

        obj.put("bgImageId", bgImageId);

        return obj.toString();
    }

    public static String getSuccessJsonDataListData(List list, long totalElement, int totalPages) throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("data", new org.json.JSONArray(list));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        return obj.toString();
    }

    public static String getSuccessJsonData(List list, long totalElement, int totalPages) throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("content", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        return obj.toString();
    }

    public static String getSuccessJsonData_KeyValue(List list, long totalElement, int totalPages, String key,
                                                     String value) throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");
        obj.put(key, value);

        obj.put("content", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        return obj.toString();
    }

    public static String getSuccessJsonData_Object(List list, long totalElement, int totalPages, String key, Object o)
            throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        if (o == null)
            obj.put(key, JSONObject.NULL);
        else
            obj.put(key, new JSONObject(gson.toJson(o)));

        obj.put("content", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        return obj.toString();
    }

    public static String getSuccessJsonData_2KeyValue(List list, long totalElement, int totalPages, String key1,
                                                      String value1, String key2, String value2) throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");
        obj.put(key2, value2);
        obj.put(key1, value1);

        obj.put("content", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        return obj.toString();
    }

    public static String getSuccessJsonData_merge(List list, long totalElement, int totalPages,
                                                  Map<String, Object> mobj) throws JSONException {

        Map<String, Object> obj = new HashMap<>();
        obj.putAll(mobj);

        obj.put("status", "success");

        obj.put("content", new org.json.JSONArray(gson.toJson(list)));
        obj.put("totalPages", totalPages);
        obj.put("totalElements", totalElement);

        return new JSONObject(obj).toString();
    }

    public static String getSuccessJsonData_merge(Map<String, Object> mobj) throws JSONException {
        Map<String, Object> obj = new HashMap<>();
        obj.putAll(mobj);
        obj.put("status", "success");
        return new JSONObject(obj).toString();
    }

    public static String getSuccessJsonData_mergeJson(JSONObject jobj) throws JSONException {
        jobj.put("status", "success");
        return jobj.toString();
    }

    public static String getFailJson_merge(Map<String, Object> mobj) throws JSONException {
        Map<String, Object> obj = new HashMap<>();
        obj.putAll(mobj);
        obj.put("status", "fail");
        return new JSONObject(obj).toString();
    }

    public static String getSuccessJsonData_3KeyValue(List list, long totalElement, int totalPages, String key1,
                                                      String value1, String key2, String value2, String key3, String value3) throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");
        obj.put(key3, value3);
        obj.put(key2, value2);
        obj.put(key1, value1);

        obj.put("content", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        return obj.toString();
    }

    public static String getSuccessJsonDataHasNext(List list, long totalElement, int totalPages, boolean hasNext)
            throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("content", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);
        obj.put("hasNext", hasNext);

        return obj.toString();
    }

    public static String getSuccessJsonDataPagination(List list, long totalElement, int totalPages)
            throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("data", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        return obj.toString();
    }

    public static String getSuccessJsonDataPaginationWithStatus(List list, long totalElement, int totalPages, String chatStatus)
            throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("data", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);
        obj.put("chatStatus", chatStatus);

        return obj.toString();
    }

    public static String getSuccessJsonDataPagination_keyValue(List list, long totalElement, int totalPages, String key,
                                                               Object value) throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("data", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        obj.put("key", value);

        return obj.toString();
    }

    public static String getSuccessJsonDataPaginationWithNames(List list, long totalElement, int totalPages,
                                                               String userName, String consultantName) throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("data", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);
        obj.put("userName", userName);
        obj.put("consultantName", consultantName);

        return obj.toString();
    }

    public static String getSuccessJsonDataPaginationHasNext(List list, long totalElement, int totalPages)
            throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("data", new org.json.JSONArray(gson.toJson(list)));

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        return obj.toString();
    }


    public static String getSuccessJsonForChat(String profile_pic, String name) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        obj.put("profilePic", profile_pic);
        obj.put("consultantName", name);
        return obj.toString();
    }




    public static String getSuccessJsonDataCustom(Object list, long totalElement, int totalPages) throws
            JSONException {

        JSONObject obj = new JSONObject();

        obj.put("status", "success");

        obj.put("content", list);

        obj.put("totalPages", totalPages);

        obj.put("totalElements", totalElement);

        return obj.toString();
    }

    public static String getSuccessJsonBoost(String message, String text) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        if (message != null)
            obj.put("message", message);
        obj.put("text", text);
        return obj.toString();
    }

}
