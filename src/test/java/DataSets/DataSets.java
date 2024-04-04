package DataSets;

import org.testng.annotations.DataProvider;

public class DataSets {
	/**
	 * 
	 * Definition: 
	 * 
	 */
	@DataProvider (name = "dataprovidersource")
    public Object[][] DataProviderSource(){
		return new Object[][] {
			{"Name","Email","Phone","Address"},
			{"John","John@email.com","999-888-22-33","123 Address St."}
		};
    }
}
