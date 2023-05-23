package com.qa.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class NextBirthdayAPITest {

	@DataProvider(name = "testData")
	public Object[][] testData() {
		return new Object[][] { { "1990-10-30", "hour", "3840 hours left" }, { "1990-10-30", "day", "160 days left" },
				{ "1990-10-30", "week", "22 weeks left" }, { "1990-10-30", "month", "5 months left" },
				{ "2023-12-31", "day", "222 days left" }
				// Future birth date within the same year
		};
	}

	@Test(dataProvider = "testData")
	public void testNextBirthday(String dateOfBirth, String unit, String expectedMessage) {
		String apiUrl = "https://lx8ssktxx9.execute-api.eu-west-1.amazonaws.com/Prod/next-birthday";
		String url = String.format("%s?dateofbirth=%s&unit=%s", apiUrl, dateOfBirth, unit);

		// System.out.println("Api is for check = " + url);

		Response response = RestAssured.get(url);
		int statusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();

		if (statusCode == 200) {
			Assert.assertTrue(responseBody.contains(expectedMessage), "API response message mismatch");
		}

		System.out.println("=========================================================================");

	}
}