package net.continuumsecurity;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.junit.Test;

import java.util.Date;

/**
 * Created by stephen on 06/07/2014.
 */

public class JSoupXMLDoSTest {

    @Test
    public void testExpandingEntities() {
        String attack = "<!DOCTYPE evildoc [\n" +
                "<!ENTITY x0 hello XDoS\">\n" +
                "<!ENTITY xevilparam &x99;&x99;\"> ]>\n" +
                "<foobar>&xevilparam;</foobar>";

        Date now = new Date();
        String output = Jsoup.clean(attack, Whitelist.basic());
        System.out.println(output);
        Date after = new Date();
        assert (after.getTime() - now.getTime()) < 1000;
    }
}
