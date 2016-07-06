package org.zunpeng.core.utils;

import java.util.Random;

/**
 * Created by dapeng on 7/4/16.
 */
public class SlugGenerateUtils {

	private final static char[] BASE_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z' };

	private static final Random RANDOM = new Random();

	private static final int RANDOM_CHAR_SIZE = BASE_CHARS.length;

	public static String generate(int length){
		StringBuffer stringBuffer = new StringBuffer();
		for(int i = 0; i < length; i ++){
			stringBuffer.append(BASE_CHARS[RANDOM.nextInt(RANDOM_CHAR_SIZE)]);
		}
		return stringBuffer.toString();
	}

}
