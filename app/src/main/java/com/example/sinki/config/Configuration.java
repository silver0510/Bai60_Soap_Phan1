package com.example.sinki.config;

/**
 * Created by Sinki on 9/5/2017.
 */

public class Configuration {
    public static String SERVER_URL ="http://www.webservicex.net/ConvertTemperature.asmx";
    public static String NAME_SPACE = "http://www.webserviceX.NET/";
    public static String METHOD_CONVERT ="ConvertTemp";
    public static String PARAM_TEMP = "Temperature";
    public static String PARAM_FROM = "FromUnit";
    public static String PARAM_TO = "ToUnit";
    public static String SOAP_ACTION = NAME_SPACE+METHOD_CONVERT;
}
