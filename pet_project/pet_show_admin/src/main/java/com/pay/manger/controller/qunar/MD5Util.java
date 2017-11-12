package com.pay.manger.controller.qunar;

import java.security.MessageDigest;

import org.apache.commons.io.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class MD5Util {
	private static final Logger logger = LoggerFactory.getLogger(MD5Util.class);
	//32位的加密
    public static String MD5(String data) {
        String md5Result = null;
        if(null == data){
            return md5Result;
        }

        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer sb = new StringBuffer("");
            for(int offset = 0; offset < b.length; offset ++){
                i = b[offset];
                if(i< 0){
                    i += 256;
                }
                if(i < 16){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            md5Result = sb.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return md5Result;
    }

    public static void main(String[] args) {
        String plainText = "createTime=1449134890133key=dedbcc2f09592be66c9ef0071188bc83params={\"airCode\":\"ZH\",\"orderType\":\"NQB2C\",\"userDefinedOrderId\":\"1601874450\",\"flightSegments\":[{\"depAirport\":\"CAN\",\"arrAirport\":\"TSN\",\"depCity\":\"广州\",\"arrCity\":\"天津\",\"depDate\":\"2015-12-31\",\"depTime\":\"08:55\",\"arrTime\":\"11:55\",\"flightCode\":\"CA1396\",\"cabin\":\"V1\",\"arrDate\":\"2015-12-31\"}],\"passengers\":[{\"name\":\"刘光宇\",\"cardType\":\"1\",\"cardNum\":\"340602199609091016\",\"birthday\":\"1996-09-09\",\"bxCount\":0}],\"costPrice\":\"665.0000\",\"orderPrice\":\"715.00\",\"bxPrice\":\"0\"}tag=flight.nb2c.order.uploadtoken=efdbf64b504296c5a486ad94a16a7252";
        String a="createTime=1493260440992key=a03f6df6c9f9fd663bbf99c931e2d676params={\"arr\":\"CSX\",\"date\":\"2017-04-27\",\"dpt\":\"SZX\",\"ex_track\":\"youxuan/tehui\"}trg=flight.national.supply.sl.searchflighttoken=7a41fb953c144dc2ffb5f9894178c8ce";
        String result = MD5Util.MD5(a);
        HashFunction function = Hashing.md5();
        System.out.println(result.equals(function.hashString(a, Charsets.UTF_8).toString()));
        System.out.println(MD5Util.MD5("createTime=1450345241710key=865f4f97cb1a91912dc2d1cda4da6eacparams={\"airCode\":\"MU\",\"orderType\":\"0\",\"userDefinedOrderId\":\"MU635859708367805388\",\"costPrice\":\"899.9900\",\"orderPrice\":\"899.9900\",\"bxPrice\":\"0\",\"flightSegments\":[{\"depAirport\":\"KMG\",\"arrAirport\":\"PEK\",\"depCity \":\"昆明\",\"arrCity\":\"北京\",\"depDate\":\"2016-01-09\",\"depTime\":\"11:55\",\"arrTime\":\"15:15\",\"flightCode\":\"MU5711\",\"cabin\":\"V\",\"arrDate\":\"2016-01-09\"}],\"passengers\":[{\"name\":\"卢克寒\",\"cardType\":\"1\",\"cardNum\":\"130705199601163033\",\"birthday\":\"1996-01-16\",\"bxCount\":\"0\"}]}tag=flight.nb2c.order.uploadtoken=21dc7d8f5f4381286eb84b391810728c"));
    }
    
    
    /*private void checkSign(ApiRequest apiRequest) {
        String sign = apiRequest.getSign();
        List<String> list = getSignList(apiRequest);
        String sortedParams = getSortedItem(list);

        String signFromRequest = MD5Util.MD5(sortedParams);
        logger.info("sing str:{},sing result:{}",sortedParams,signFromRequest);
        Checker.checkNotNull(signFromRequest, SystemError.ERROR_SYSPARAM_SIGN_INVALID);
        Checker.checkState(signFromRequest.equalsIgnoreCase(sign), SystemError.ERROR_SYSPARAM_SIGN_CHANGED, apiRequest);
    }

    private List<String> getSignList(ApiRequest apiRequest) {
        List<String> list = new ArrayList<String>();
        list.add("token=" + apiRequest.getToken());
        list.add("tag=" + apiRequest.getTag());
        list.add("createTime=" + apiRequest.getCreateTime());
        list.add("params=" + apiRequest.getOriginParams());
        list.add("key=" + apiRequest.getApp().getKey());
        return list;
    }

    public String getSortedItem(List<String> list) {
        Collections.sort(list);
        StringBuffer sb = new StringBuffer(512);
        for (String param : list) {
            sb.append(param);
        }
        return sb.toString();
    }*/
}
