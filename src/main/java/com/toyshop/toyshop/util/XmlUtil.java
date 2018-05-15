package com.toyshop.toyshop.util;


import com.thoughtworks.xstream.XStream;
import com.toyshop.toyshop.model.Role;
import com.toyshop.toyshop.model.User;

import java.util.HashSet;
import java.util.Set;

public class XmlUtil {

    public static String xmlString(Object obj) {
        XStream xStream = new XStream();
        return xStream.toXML(obj);
    }

}
