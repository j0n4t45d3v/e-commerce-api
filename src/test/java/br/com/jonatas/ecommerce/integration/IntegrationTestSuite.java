package br.com.jonatas.ecommerce.integration;


import br.com.jonatas.ecommerce.integration.user.UserControllerV1IT;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("All Integration Test Suite")
@SelectClasses({UserControllerV1IT.class})
public class IntegrationTestSuite {
}
