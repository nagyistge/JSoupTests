package net.continuumsecurity;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by stephen on 06/07/2014.
 */

public class JSoupXMLDoSTest {

    @Test
    public void testExpandingEntities() {
        ArrayList<String> attacks = new ArrayList<>();
        attacks.add("<!DOCTYPE evildoc [\n" +
                "<!ENTITY x0 hello XDoS\">\n" +
                "<!ENTITY xevilparam &x99;&x99;\"> ]>\n" +
                "<foobar>&xevilparam;</foobar>");

        attacks.add("<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE lolz [\n" +
                "  <!ENTITY lol \"lol\">\n" +
                "  <!ENTITY lol2 \"&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;\">\n" +
                "  <!ENTITY lol3 \"&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;\">\n" +
                "  <!ENTITY lol4 \"&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;\">\n" +
                "  <!ENTITY lol5 \"&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;\">\n" +
                "  <!ENTITY lol6 \"&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;\">\n" +
                "  <!ENTITY lol7 \"&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;\">\n" +
                "  <!ENTITY lol8 \"&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;\">\n" +
                "  <!ENTITY lol9 \"&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;\">\n" +
                "]>\n" +
                "<lolz>&lol9;</lolz>");

        StringBuffer tooManyOpenTags = new StringBuffer();
        for (int i=0;i < 20000; i++) {
            tooManyOpenTags.append("<h1>boo");
        }
        attacks.add(tooManyOpenTags.toString());

        for (String attack : attacks) {
            Date now = new Date();
            String output = Jsoup.clean(attack, Whitelist.basic());
            Date after = new Date();
            System.out.println((after.getTime() - now.getTime()) / 1000);
            assert (after.getTime() - now.getTime()) < 1000;
        }
    }


}
