package com.okcoin.rest.stock.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.okcoin.rest.HttpUtilManager;
import com.okcoin.rest.MD5Util;
import com.okcoin.rest.stock.IStockRestApi;


public class StockRestApi implements IStockRestApi{

	// 密钥
	private String secret_key;
	// 唯一用户ID
	private String partner;
	
	private String url_prex;
	
	public StockRestApi(String url_prex,String partner,String secret_key){
		this.partner = partner;
		this.secret_key = secret_key;
		this.url_prex = url_prex;
	}
	
	public StockRestApi(String url_prex){
		this.url_prex = url_prex;
	}
	
	/**
	 * 现货行情URL
	 */
	private final String TICKER_URL = "/api/v1/ticker.do?";
	
	/**
	 * 现货市场深度URL
	 */
	private final String DEPTH_URL = "/api/v1/depth.do?";
	
	/**
	 * 现货历史交易信息URL
	 */
	private final String TRADES_URL = "/api/v1/trades.do?";
	
	/**
	 * 现货获取用户信息URL
	 */
	private final String USERINFO_URL = "/api/v1/userinfo.do?";
	
	/**
	 * 现货 下单交易URL
	 */
	private final String TRADE_URL = "/api/v1/trade.do?";
	
	/**
	 * 现货 批量下单URL
	 */
	private final String BATCH_TRADE_URL = "/api/v1/batch_trade.do";
	
	/**
	 * 现货 撤销订单URL
	 */
	private final String CANCEL_ORDER_URL = "/api/v1/cancel_order.do";
	
	/**
	 * 现货 获取用户订单URL
	 */
	private final String ORDER_INFO_URL = "/api/v1/order_info.do";
	
	/**
	 * 现货 批量获取用户订单URL
	 */
	private final String ORDERS_INFO_URL = "/api/v1/orders_info.do";
	
	/**
	 * 现货 获取历史订单信息，只返回最近七天的信息URL
	 */
	private final String ORDER_HISTORY_URL = "/api/v1/order_history.do";

	@Override
	public String ticker(String symbol) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		if (symbol != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "symbol=" + symbol;
		}
		String result = httpUtil.requestHttpGet(url_prex, TICKER_URL, param);
	    return result;
	}

	@Override
	public String depth(String symbol) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		if (symbol != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "symbol=" + symbol;
		}
		String result = httpUtil.requestHttpGet(url_prex, this.DEPTH_URL, param);
	    return result;
	}

	@Override
	public String trades(String symbol, String since) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		if (symbol != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "symbol=" + symbol;
		}
		if (since != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "since=" + since;
		}
		String result = httpUtil.requestHttpGet(url_prex, this.TRADES_URL, param);
	    return result;
	}

	@Override
	public String userinfo() throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		String sign = MD5Util.buildMysignV1(params, this.secret_key);
		params.put("sign", sign);
		
		// 发送post请求
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.USERINFO_URL,
				params);

		return result;
	}

	@Override
	public String trade(String symbol, String type,
			String price, String amount) throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		if(symbol!=null){
			params.put("symbol", symbol);
		}
		if(type!=null){
			params.put("type", type);
		}
		if(price!=null){
			params.put("price", price);
		}
		if(amount!=null){
			params.put("amount", amount);
		}
		String sign = MD5Util.buildMysignV1(params, this.secret_key);
		params.put("sign", sign);
		
		// 发送post请求
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.TRADE_URL,
				params);

		return result;
	}

	@Override
	public String batch_trade( String symbol, String type,
			String orders_data) throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		if(symbol!=null){
			params.put("symbol", symbol);
		}
		if(type!=null){
			params.put("type", type);
		}
		if(orders_data!=null){
			params.put("orders_data", orders_data);
		}
		String sign = MD5Util.buildMysignV1(params, this.secret_key);
		params.put("sign", sign);
		
		// 发送post请求
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.BATCH_TRADE_URL,
				params);

		return result;
	}

	@Override
	public String cancel_order(String symbol, String order_id) throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		if(symbol!=null){
			params.put("symbol", symbol);
		}
		if(order_id!=null){
			params.put("order_id", order_id);
		}

		String sign = MD5Util.buildMysignV1(params, this.secret_key);
		params.put("sign", sign);
		
		// 发送post请求
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.CANCEL_ORDER_URL,
				params);

		return result;
	}

	@Override
	public String order_info(String symbol, String order_id) throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		if(symbol!=null){
			params.put("symbol", symbol);
		}
		if(order_id!=null){
			params.put("order_id", order_id);
		}

		String sign = MD5Util.buildMysignV1(params, this.secret_key);
		params.put("sign", sign);
		
		// 发送post请求
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.ORDER_INFO_URL,
				params);

		return result;
	}

	@Override
	public String orders_info(String type, String symbol,
			String order_id) throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		if(type!=null){
			params.put("type", type);
		}
		if(symbol!=null){
			params.put("symbol", symbol);
		}
		if(order_id!=null){
			params.put("order_id", order_id);
		}

		String sign = MD5Util.buildMysignV1(params, this.secret_key);
		params.put("sign", sign);
		
		// 发送post请求
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.ORDERS_INFO_URL,
				params);

		return result;
	}

	@Override
	public String order_history(String symbol, String status,
			String current_page, String page_length) throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		if(symbol!=null){
			params.put("symbol", symbol);
		}
		if(status!=null){
			params.put("status", status);
		}
		if(current_page!=null){
			params.put("current_page", current_page);
		}
		if(page_length!=null){
			params.put("page_length", page_length);
		}

		String sign = MD5Util.buildMysignV1(params, this.secret_key);
		params.put("sign", sign);
		
		// 发送post请求
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.ORDER_HISTORY_URL,
				params);

		return result;
	}

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getUrl_prex() {
		return url_prex;
	}

	public void setUrl_prex(String url_prex) {
		this.url_prex = url_prex;
	}

}
