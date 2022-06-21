package com.yangke.base.util;

import java.util.UUID;


/**
 *
 * @author ke.yang1
 * @date 2022-5-12 下午16:07:19
 */

public class UUIDUtil {


	public static String generateUUID(){
		String uuid=UUID.randomUUID().toString();
		uuid=uuid.replaceAll("-", "");
		return uuid;
	}

}

