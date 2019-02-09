package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.services.TestActivityManager;
import test.services.TestUserManager;
import test.utils.TestSecurity;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestSecurity.class, TestUserManager.class, TestActivityManager.class})
public class TestAll {
}
