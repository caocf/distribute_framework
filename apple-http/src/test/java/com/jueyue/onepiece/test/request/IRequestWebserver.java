package com.jueyue.onepiece.test.request;

import cn.afterturn.http.annotation.IRequest;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.annotation.IRequestParam;
import cn.afterturn.http.entity.enums.RequestTypeEnum;

/**
 * WebServer 接口测试
 * @author JueYue
 * @date 2014年12月21日 上午11:15:28
 */
@IRequest
public interface IRequestWebserver {

    static final String GLOBAL_WEATHER  = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.webserviceX.NET\">"
                                          + "<soapenv:Header/>"
                                          + "<soapenv:Body>"
                                          + "<web:GetWeather>"
                                          + "<web:CityName>%s</web:CityName>"
                                          + "<web:CountryName>%s</web:CountryName>"
                                          + "</web:GetWeather>"
                                          + "</soapenv:Body>"
                                          + "</soapenv:Envelope>";
    static final String soapRequestData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                          + "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
                                          + "<soap12:Body>"
                                          + " <qqCheckOnline xmlns=\"http://WebXml.com.cn/\">"
                                          + "    <qqCode>%s</qqCode>" + "   </qqCheckOnline>"
                                          + "  </soap12:Body>" + "</soap12:Envelope>";

    @IRequestMethod(type = RequestTypeEnum.WEBSERVICE, webserver = GLOBAL_WEATHER, url = "http://www.webservicex.net/globalweather.asmx?wsdl")
    String testGet(@IRequestParam(value = "cityName", order = 1) String cityName,
                   @IRequestParam(value = "countryName", order = 2) String countryName);

    @IRequestMethod(type = RequestTypeEnum.WEBSERVICE, webserver = soapRequestData, url = "http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx?wsdl")
    String testQQ(@IRequestParam(value = "qqCode", order = 1) String qqCode);

}
