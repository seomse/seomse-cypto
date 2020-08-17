/*
 * Copyright (C) 2020 Seomse Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seomse.cypto;

import java.security.NoSuchAlgorithmException;

/**
 *
 * hash로 값을 생성 하는 방식을 혼동을 주기 위해 문자열로 생성 하는 부분에서
 * 다르게 변환 하여 생성
 * @author macle
 */
public class HashConfusionString {
	
	/***
	 * hash값을 문자열로 변환한 결과얻기 ( 관련패키지 고유결과)
	 * @param hash "MD5","SHA1","SHA-256","SHA-384","SHA-512"
	 * @param value 문자열
	 * @return  change hash
	 */
	public static String get(String hash, String value) throws NoSuchAlgorithmException{
		return  get(java.security.MessageDigest.getInstance(hash), value);
	}
	
	
	
	/**
	 * hash값을 문자열로 변환한 결과얻기  ( 관련패키지 고유결과)
	 * @param messageDigest 해쉬알고리즘 종류 
	 * @param value 문자열
	 * @return change hash
	 */
	public static String get(java.security.MessageDigest messageDigest, String value){
        String eip;
        byte[] bip;
        bip = messageDigest.digest(value.getBytes());
        int length =  bip.length;
        char addc;
        if (value.length() > 0){
        	addc = value.charAt(0);
        }else{
        	addc = ' ';
        }
        StringBuilder temp = new StringBuilder();
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < length; i++) {
            eip = "" + Integer.toHexString((int) bip[i] & 0x000000ff);
            if (eip.length() < 2)
                eip = "0" + eip;
            temp.append(addc).append(eip);
        }
        temp.append(value);
        StringBuilder resultBuilder = new StringBuilder();
        bip=messageDigest.digest(temp.toString().getBytes());
        for (int i = 0; i <length; i++) {
        	int resultchar = ((int) bip[i] & 0x000000ff);
           	resultchar=resultchar%127+33;
           	if(resultchar>126){
        	   if(resultchar <= 135){
        		   resultchar= resultchar - 19;
        	   }else if(resultchar <= 148){
        		   resultchar= resultchar - 36;
        	   }else{
        		   resultchar= resultchar- 86;
        	   }
           }
           if(resultchar == 92){
        	   resultchar -= i ;
           }
           	
           resultBuilder.append((char)resultchar);

        }
        return resultBuilder.toString();
	}
}
