package testPackage;

import model.SendMail;

public class SendMailTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SendMail sendM = new SendMail("tbagis@hotmail.com", "Hello");
		System.out.println(sendM.send());

	}

}
