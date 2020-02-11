package com.Training.Internationalization;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.*;

public class I18NSample implements Serializable {

   /**
	 * 

	private static final long serialVersionUID = 5163029709880139170L;
	 */
static public void main(String[] args) {

      String language;
      String country;

      if (args.length != 2) {
          language = new String("en");
          country = new String("US");
      } else {
          language = new String(args[0]);
          country = new String(args[1]);
      }

      Locale currentLocale = new Locale(language, country);

      ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle",currentLocale);

      System.out.println(messages.getString("greetings"));
      System.out.println(messages.getString("inquiry"));
      System.out.println(messages.getString("farewell"));
   }
}