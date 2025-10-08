package co.spribe.qaa.testdata.dataproviders;

import org.testng.annotations.DataProvider;

public final class PlayerData {
    private PlayerData() {}
    @DataProvider(name = "roles", parallel = true)
    public static Object[][] roles() {
        return new Object[][]{
                {"admin", true},
                {"user", true},
                {"supervisor", false},
                {"manager", false}
        };
    }

    @DataProvider(name = "ages", parallel = true)
    public static Object[][] ages() {
        return new Object[][]{
                {17, true},
                {59, true},
                {35, true},
                {16, false},
                {60, false},
                {-5, false},
                {0, false},
        };
    }

    @DataProvider(name = "genders", parallel = true)
    public static Object[][] genders() {
        return new Object[][]{
                {"male",   true},
                {"female", true},
                {"unknown", false}
        };
    }
}
