package com.wixpress.uri;

import java.io.IOException;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: uri
 * Date: 5/25/14
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static final String MACHINE_NAME = "localhost";

    public static void  main(String... args) throws SQLException, IOException {
        new PageMigrator(args.length != 1 ? Main.MACHINE_NAME : args[0]).migrate();
    }

}
