package me.chanjar.weixin.cp.api;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.school.WxCpCustomizeHealthInfo;
import me.chanjar.weixin.cp.bean.school.WxCpPaymentResult;
import me.chanjar.weixin.cp.bean.school.WxCpResultList;
import me.chanjar.weixin.cp.bean.school.WxCpTrade;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.demo.WxCpDemoInMemoryConfigStorage;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 企业微信家校应用 复学码相关接口.
 * https://developer.work.weixin.qq.com/document/path/93744
 *
 * @author <a href="https://github.com/0katekate0">Wang_Wong</a>
 * @date: 2022/5/31 9:10
 */
@Slf4j
public class WxCpSchoolTest {

  private static WxCpConfigStorage wxCpConfigStorage;
  private static WxCpService cpService;

  @Test
  public void test() throws WxErrorException {

    /**
     * 注意：
     * 权限说明：仅复学码应用可以调用
     */
    InputStream inputStream = ClassLoader.getSystemResourceAsStream("test-config.xml");
    WxCpDemoInMemoryConfigStorage config = WxCpDemoInMemoryConfigStorage.fromXml(inputStream);

    wxCpConfigStorage = config;
    cpService = new WxCpServiceImpl();
    cpService.setWxCpConfigStorage(config);
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    /**
     * 获取学生付款结果
     * https://developer.work.weixin.qq.com/document/path/94553
     */
    String paymentResultStr = "{\"errcode\":0,\"errmsg\":\"ok\",\"project_name\":\"学费\",\"amount\":998,\"payment_result\":[{\"student_userid\":\"xxxx\",\"trade_state\":1,\"trade_no\":\"xxxxx\",\"payer_parent_userid\":\"zhangshan\"}]}";
    WxCpPaymentResult cpPaymentResult = WxCpPaymentResult.fromJson(paymentResultStr);
    log.info("cpPaymentResult:{}", cpPaymentResult.toJson());

    WxCpPaymentResult paymentResult = cpService.getSchoolService().getPaymentResult("");
    log.info("paymentResult:{}", paymentResult.toJson());

    /**
     * 获取订单详情
     * https://developer.work.weixin.qq.com/document/path/94554
     */
    String tradeStr = "{\n" +
      "\t\"errcode\":0,\n" +
      "\t\"errmsg\":\"ok\",\n" +
      "\t\"transaction_id\":\"xxxxx\",    \t     \n" +
      "\t\"pay_time\":12345\n" +
      "}";
    WxCpTrade wxCpTrade = WxCpTrade.fromJson(tradeStr);
    log.info("wxCpTrade:{}", wxCpTrade.toJson());

    WxCpTrade trade = cpService.getSchoolService().getTrade("", "");
    log.info("trade:{}", trade.toJson());


    /**
     * 获取老师健康信息
     * https://developer.work.weixin.qq.com/document/path/93744
     */
    WxCpCustomizeHealthInfo teacherCustomizeHealthInfo = cpService.getSchoolService().getTeacherCustomizeHealthInfo(date, null, null);
    log.info("teacherCustomizeHealthInfo为：{}", teacherCustomizeHealthInfo.toJson());

    /**
     * 获取学生健康信息
     * https://developer.work.weixin.qq.com/document/path/93745
     */
    WxCpCustomizeHealthInfo studentCustomizeHealthInfo = cpService.getSchoolService().getStudentCustomizeHealthInfo(date, null, null);
    log.info("studentCustomizeHealthInfo为：{}", studentCustomizeHealthInfo.toJson());

    /**
     * 获取师生健康码
     * https://developer.work.weixin.qq.com/document/path/93746
     */
    ArrayList<String> userIds = Lists.newArrayList();
    userIds.add("Wangkai");
    WxCpResultList healthQrCode = cpService.getSchoolService().getHealthQrCode(userIds, 1);
    log.info("healthQrCode为：{}", healthQrCode.toJson());

  }

}
