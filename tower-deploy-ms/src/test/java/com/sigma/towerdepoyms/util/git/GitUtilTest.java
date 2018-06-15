package com.sigma.towerdepoyms.util.git;

import org.junit.Test;

import java.io.File;

/**
 * @author zhenpeng
 */
public class GitUtilTest {


    @Test
    public void rollBackPreRevision() throws Exception {

        GitUtil checkoutFile= new GitUtil();
        checkoutFile.gitCheckout(new File("G:\\git-package\\asfasdfasdfasdf"),"2c9e3ec3");
    }
}