package driverFactory;

import org.testng.annotations.Test;

public class AppTests {
	@Test
	public void kicktest() throws Throwable
	{
		DriverScript ds = new DriverScript();
		ds.starttest();
	}

}
