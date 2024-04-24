package keenan.james.nathan.muddemo;

import static org.mockito.ArgumentMatchers.anyList;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import keenan.james.nathan.muddemo.game.GameMap;

@SpringBootTest
class MudDemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void fileCheck()
	{
		GameMap map = new GameMap("overworld");
	}
	
	@Test
	void emptyCheck()
	{
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("Not Empty");
		testList.add("");
		testList.add("");
		testList.add("2");
		testList.add("asdf");
		testList.add("");
		testList.add("Not Empty");
		
		testList.removeIf((e) -> e.equals("")); // remove all empty elements, works!
		
		System.out.println(testList.toString());
	}
}
