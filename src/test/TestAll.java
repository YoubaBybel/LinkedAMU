package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.services.TestActivityManager;
import test.services.TestUserManager;
import test.utils.TestSecurity;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestActivityManager.class, TestUserManager.class, TestSecurity.class})
public class TestAll {
}
