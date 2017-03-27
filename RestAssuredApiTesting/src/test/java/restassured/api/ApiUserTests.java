package restassured.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.restassured.path.json.JsonPath;

import model.Post;
import tools.EmailValidator;
import tools.FieldValidator;


import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class ApiUserTests {
	int randomUserId;
	FieldValidator fieldValidator;

	@Before
	public void generateRandomId() {
		Random rand = new Random();
		randomUserId = rand.nextInt(10) + 1;
	}

	@Test
	public void getRandomUserAndVerifyItsEmail() {

		String responseBody = get("http://jsonplaceholder.typicode.com/users/" + randomUserId).then().body("id", is(randomUserId)).extract()
				.asString();

		JsonPath jsonPath = new JsonPath(responseBody);
		String address = jsonPath.getString("address");
		System.out.println(address);

		String email = jsonPath.getString("email");
		EmailValidator emailValidator = new EmailValidator();
		boolean valid = emailValidator.validate(email);
		assertTrue(valid);
	}

	@Test
	public void testValidityOfIdTitleAndBody() {
		boolean valid = true;
		List<Post> posts = new ArrayList<>();		

		String jsonString;
		jsonString = get("http://jsonplaceholder.typicode.com/posts").then().extract().asString();
		JsonParser parser = new JsonParser();
		JsonElement jsnobject = parser.parse(jsonString);
		JsonArray jsonArray = jsnobject.getAsJsonArray();

		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement rec = jsonArray.get(i);
			int userId = rec.getAsJsonObject().get("userId").getAsInt();
			if (userId == randomUserId) {
				int id = rec.getAsJsonObject().get("id").getAsInt();
				String title = rec.getAsJsonObject().get("title").getAsString();
				String body = rec.getAsJsonObject().get("body").getAsString();
				posts.add(new Post(userId, id, title, body));
			}
		}
		for (Post post : posts) {
			if (!(FieldValidator.isValid(posts, post.getId()) && FieldValidator.isTitleValid(post.getTitle())
					&& FieldValidator.isBodyValid(post.getBody()))) {
				valid = false;
			}
		}
		assertTrue(valid);	
	}
	
	@Test
	public void doAPostWithSameUserId() {
		String jsonString;
		jsonString = get("http://jsonplaceholder.typicode.com/posts").then().extract().asString();
		given().param(Integer.toString(randomUserId), Integer.toString(jsonString.length()+1),"Brand new title","This book is fantastic").when()
		.post("/posts").then().body("id", equalTo(jsonString.length()+1)).body("userId",equalTo(randomUserId)).body("title",equalTo("Brand new title"))
		.body("body", equalTo("This book is fantastic"));
	}
}
