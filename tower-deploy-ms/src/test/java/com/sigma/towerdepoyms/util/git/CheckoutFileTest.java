package com.sigma.towerdepoyms.util.git;

import org.junit.Test;

import java.io.File;

/**
 * @author zhenpeng
 */
public class CheckoutFileTest {


    @Test
    public void rollBackPreRevision() throws Exception {

        CheckoutFile checkoutFile= new CheckoutFile();
        checkoutFile.gitCheckout(new File("G:\\git-package\\asfasdfasdfasdf"),"2c9e3ec3");
    }
}