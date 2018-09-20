package eu.benayoun.badass.utility.model;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

import eu.benayoun.badass.utility.ui.BadassLog;

/**
 * Created by PierreB on 05/06/2017.
 */

public class XmlParser
{
	static public XmlPullParser getXmlParser(String inputString)
	{
		XmlPullParserFactory factory = null;
		XmlPullParser xmlPullParser = null;

		try
		{
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(inputString));
		} catch (XmlPullParserException e)
		{
			BadassLog.error("XmlPullParserException: " + e.toString());
		}
		return xmlPullParser;
	}
}
