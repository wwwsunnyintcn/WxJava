package me.chanjar.weixin.cp.api.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpSchoolService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.school.WxCpCustomizeHealthInfo;
import me.chanjar.weixin.cp.bean.school.WxCpPaymentResult;
import me.chanjar.weixin.cp.bean.school.WxCpResultList;
import me.chanjar.weixin.cp.bean.school.WxCpTrade;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static me.chanjar.weixin.cp.constant.WxCpApiPathConsts.School.*;

/**
 * 企业微信家校应用 复学码相关接口实现类.
 * https://developer.work.weixin.qq.com/document/path/93744
 *
 * @author <a href="https://github.com/0katekate0">Wang_Wong</a>
 * @date: 2022/6/1 14:05
 */
@Slf4j
@RequiredArgsConstructor
public class WxCpSchoolServiceImpl implements WxCpSchoolService {

  private final WxCpService cpService;

  @Override
  public WxCpCustomizeHealthInfo getTeacherCustomizeHealthInfo(@NotNull String date, String nextKey, Integer limit) throws WxErrorException {
    String apiUrl = this.cpService.getWxCpConfigStorage().getApiUrl(GET_TEACHER_CUSTOMIZE_HEALTH_INFO);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("date", date);
    jsonObject.addProperty("limit", Optional.ofNullable(limit).orElse(100));
    if (nextKey != null) {
      jsonObject.addProperty("next_key", nextKey);
    }
    String responseContent = this.cpService.post(apiUrl, jsonObject.toString());
    return WxCpCustomizeHealthInfo.fromJson(responseContent);
  }

  @Override
  public WxCpCustomizeHealthInfo getStudentCustomizeHealthInfo(@NotNull String date, String nextKey, Integer limit) throws WxErrorException {
    String apiUrl = this.cpService.getWxCpConfigStorage().getApiUrl(GET_STUDENT_CUSTOMIZE_HEALTH_INFO);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("date", date);
    jsonObject.addProperty("limit", Optional.ofNullable(limit).orElse(100));
    if (nextKey != null) {
      jsonObject.addProperty("next_key", nextKey);
    }
    String responseContent = this.cpService.post(apiUrl, jsonObject.toString());
    return WxCpCustomizeHealthInfo.fromJson(responseContent);
  }

  @Override
  public WxCpResultList getHealthQrCode(@NotNull List<String> userIds, @NotNull Integer type) throws WxErrorException {
    String apiUrl = this.cpService.getWxCpConfigStorage().getApiUrl(GET_HEALTH_QRCODE);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("type", type);
    jsonObject.addProperty("userids", userIds.toString());
    String responseContent = this.cpService.post(apiUrl, jsonObject.toString());
    return WxCpResultList.fromJson(responseContent);
  }

  @Override
  public WxCpPaymentResult getPaymentResult(@NotNull String paymentId) throws WxErrorException {
    String apiUrl = this.cpService.getWxCpConfigStorage().getApiUrl(GET_PAYMENT_RESULT);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("payment_id", paymentId);
    String responseContent = this.cpService.post(apiUrl, jsonObject.toString());
    return WxCpPaymentResult.fromJson(responseContent);
  }

  @Override
  public WxCpTrade getTrade(@NotNull String paymentId, @NotNull String tradeNo) throws WxErrorException {
    String apiUrl = this.cpService.getWxCpConfigStorage().getApiUrl(GET_TRADE);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("payment_id", paymentId);
    jsonObject.addProperty("trade_no", tradeNo);
    String responseContent = this.cpService.post(apiUrl, jsonObject.toString());
    return WxCpTrade.fromJson(responseContent);
  }

}
