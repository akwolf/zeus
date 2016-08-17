package org.zunpeng;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by dapeng on 16/8/14.
 */
public class ShiroTest {

	@Test
	public void demo(){
		System.out.println(BCrypt.hashpw("hello", BCrypt.gensalt()));
	}
	/*
	insert into account_info (username, passwd) values ("13260091590", "$2a$10$JA5HGWu46xOq5uMphenUce1B/DBQIiYstGpU2eKempixHXwtcpH0u");
	 */
}
