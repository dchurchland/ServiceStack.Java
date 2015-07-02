//  Copyright (c) 2015 ServiceStack LLC. All rights reserved.

package net.servicestack.client.tests;

import android.app.Application;
import android.test.ApplicationTestCase;

import net.servicestack.client.ConnectionFilter;
import net.servicestack.client.JsonServiceClient;

import net.servicestack.client.tests.dto.*;

import java.net.HttpURLConnection;

public class JsonServiceClientTests extends ApplicationTestCase<Application> {

    public JsonServiceClientTests() {
        super(Application.class);
    }
    //10.0.2.2 = loopback
    //http://developer.android.com/tools/devices/emulator.html
    JsonServiceClient client = new JsonServiceClient("http://servicestackunittests.azurewebsites.net");

    public void test_can_GET_HelloAll(){
        Hello request = new Hello()
            .setName("World");

        HelloResponse response = client.get(request);

        assertEquals("Hello, World!", response.getResult());
    }

    public void test_can_use_request_filter() {
        final Boolean[] passTest = {false};
        JsonServiceClient localTestClient = new JsonServiceClient("http://servicestackunittests.azurewebsites.net/");

        localTestClient.RequestFilter = new ConnectionFilter() {
            @Override
            public void exec(HttpURLConnection conn) {
                passTest[0] = true;
            }
        };

        Hello request = new Hello()
                .setName("World");

        HelloResponse response = localTestClient.get(request);

        assertEquals("Hello, World!", response.getResult());
        assertTrue(passTest[0]);
    }

}
