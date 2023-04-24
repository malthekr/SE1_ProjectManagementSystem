package softwarehuset.management.acceptance_tests;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;


@RunWith(Cucumber.class)
@CucumberOptions(
	features = "features", 
	plugin = { "summary", "html:target/cucumber/wikipedia.html"}, 
	monochrome=true, 
	snippets = SnippetType.CAMELCASE, 
	glue = {"softwarehuset.management.acceptance_tests"}
)
public class AcceptanceTest {

}

