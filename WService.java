package com.example.ifcalogin;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


public class WService implements IWService {

	// WebService地址
	 private static final String NameSpace = "http://tempuri.org/";
	 //114.129.36.138     192.168.1.18
	 //private static final String URL = "http://192.168.1.18/IFCAMobile/LoginServer.asmx";
	 
	 //login-登录验证
	 private static final String SOAP_ACTION_login = "http://tempuri.org/Login";
	 private static final String MethodName_login = "Login";
	 //获取预警明细
	 private static final String SOAP_ACTION_AlertDetail = "http://tempuri.org/GetAlertDetail";
	 private static final String MethodName_AlertDetail = "GetAlertDetail";
	 //获取预警类型列表(未实现)
	 private static final String SOAP_ACTION_AlertTypes = "http://tempuri.org/GetAlertTypes";
	 private static final String MethodName_AlertTypes = "GetAlertTypes";
	 //根据类型获取预警列表信息
	 private static final String SOAP_ACTION_AlertInfosByType = "http://tempuri.org/GetAlertInfos";
	 private static final String MethodName_AlertInfosByType = "GetAlertInfos";
	 //根据类型获取预警列表信息
	 private static final String SOAP_ACTION_UpdateAlertMsgOperationByID = "http://tempuri.org/UpdateOperationStatus";
	 private static final String MethodName_UpdateAlertMsgOperationByID = "UpdateOperationStatus";
	 private String username=null;
	 private String password=null;
	 private String serverpath1=null;
	 public WService()
	 {
	 }
	 
	 public WService(String serverpath)
	 {
		 this.serverpath1=serverpath;
	 }
	 
	 public WService(String userid,String pwd,String serverpath  )
	 {
		 this.username=userid;
		 this.password=pwd;
		 this.serverpath1=serverpath;
	 }
	@Override 
	//登录
	public SoapObjectResultBase LoginManager() {
		// TODO Auto-generated method stub
		SoapObject soapObject = new SoapObject(NameSpace, MethodName_login);
        soapObject.addProperty("loginName", this.username);
        soapObject.addProperty("pwd", this.password);
		return LoadResult(soapObject,SOAP_ACTION_login);
	}
	@Override
	//获取预警明细
	public SoapObjectResultBase GetAlertDetail(String recordID) {
		// TODO Auto-generated method stub
				SoapObject soapObject = new SoapObject(NameSpace, MethodName_AlertDetail);
		        soapObject.addProperty("RecordID", recordID);
				return LoadResult(soapObject,SOAP_ACTION_AlertDetail);
	}
	@Override
	//获取预警类型列表 
	public SoapObjectResultBase GetAlertTypes(int userID,int page,int pageSize,int alertType) {
		// TODO Auto-generated method stub
				SoapObject soapObject = new SoapObject(NameSpace, MethodName_AlertTypes);
		        soapObject.addProperty("userID", userID);
		        soapObject.addProperty("alertType", userID);
		        soapObject.addProperty("page", page);
		        soapObject.addProperty("pageSize", pageSize);
		        soapObject.addProperty("Language", "zh-CN");
		        //soapObject.addProperty("Language","en-US");
				return LoadResult(soapObject,SOAP_ACTION_AlertTypes);
	}
	@Override
	//根据类型获取预警列表信息
	public SoapObjectResultBase GetAlertInfosByType(int useID,String WarningCode,int Page,int PageSize,int AlertType) {
		// TODO Auto-generated method stub
				SoapObject soapObject = new SoapObject(NameSpace, MethodName_AlertInfosByType);
		        soapObject.addProperty("userID", useID);
		        soapObject.addProperty("WarningCode", WarningCode);
		        soapObject.addProperty("page", Page);
		        soapObject.addProperty("pageSize", PageSize);
		        soapObject.addProperty("alertType", AlertType);
				return LoadResult(soapObject,SOAP_ACTION_AlertInfosByType);
	}
	@Override
	//更新新的通知为已通知
	public SoapObjectResultBase UpdateAlertMsgOperationByID(String recordID) {
		// TODO Auto-generated method stub
		SoapObject soapObject = new SoapObject(NameSpace, MethodName_UpdateAlertMsgOperationByID);
        soapObject.addProperty("RecordID", recordID);
		return LoadResult(soapObject,SOAP_ACTION_UpdateAlertMsgOperationByID);
	}
	@Override
	public SoapObjectResultBase LoadResult(SoapObject soapObject,String soap_action) {
		// TODO Auto-generated method stub
		SoapObjectResultBase resultbase=new SoapObjectResultBase(); 
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // 版本
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        String errmsg =null;
        
        String URLServer ="http://" + serverpath1 + "/IFCAMobile/LoginServer.asmx";
        HttpTransportSE trans = new HttpTransportSE(URLServer);
        trans.debug = true; // 使用调试功能
        try {
            trans.call(soap_action, envelope);
            System.out.println("Call Successful!");
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
            errmsg = e.getMessage();
        } catch (XmlPullParserException e) {
            System.out.println("XmlPullParserException");
            e.printStackTrace();
            errmsg = e.getMessage();
        }
        
        SoapObject result = (SoapObject) envelope.bodyIn;
        resultbase.soapObject=result;
        resultbase.ErrMsg=errmsg;
        return resultbase;
	}
}
