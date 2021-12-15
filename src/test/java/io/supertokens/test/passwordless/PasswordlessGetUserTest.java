/*
 *    Copyright (c) 2021, VRAI Labs and/or its affiliates. All rights reserved.
 *
 *    This software is licensed under the Apache License, Version 2.0 (the
 *    "License") as published by the Apache Software Foundation.
 *
 *    You may not use this file except in compliance with the License. You may
 *    obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 */

package io.supertokens.test.passwordless;

import io.supertokens.passwordless.Passwordless;
import io.supertokens.pluginInterface.passwordless.PasswordlessStorage;
import io.supertokens.pluginInterface.passwordless.UserInfo;
import io.supertokens.storageLayer.StorageLayer;
import io.supertokens.test.TestingProcessManager;
import io.supertokens.test.Utils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static io.supertokens.test.passwordless.PasswordlessUtility.*;
import static org.junit.Assert.*;

/**
 * This UT encompasses
 */

public class PasswordlessGetUserTest {

    @Rule
    public TestRule watchman = Utils.getOnFailure();

    @AfterClass
    public static void afterTesting() {
        Utils.afterTesting();
    }

    @Before
    public void beforeEach() {
        Utils.reset();
    }

    /**
     * getUserById
     */

    /**
     * with email set
     *
     * @throws Exception
     */
    @Test
    public void getUserByIdWithEmail() throws Exception {
        TestingProcessManager.TestingProcess process = startApplicationWithDefaultArgs();

        PasswordlessStorage storage = StorageLayer.getPasswordlessStorage(process.getProcess());
        UserInfo user = null;

        Passwordless.ConsumeCodeResponse consumeCodeResponse = createUserWith(process, EMAIL, null);

        user = storage.getUserById(consumeCodeResponse.user.id);
        assertNotNull(user);
        assertEquals(user.email, EMAIL);

    }

    /**
     * with phone number set
     *
     * @throws Exception
     */
    @Test
    public void getUserByIdWithPhoneNumber() throws Exception {
        TestingProcessManager.TestingProcess process = startApplicationWithDefaultArgs();

        PasswordlessStorage storage = StorageLayer.getPasswordlessStorage(process.getProcess());
        UserInfo user = null;

        Passwordless.ConsumeCodeResponse consumeCodeResponse = createUserWith(process, null, PHONE_NUMBER);

        user = storage.getUserById(consumeCodeResponse.user.id);
        assertNotNull(user);
        assertEquals(user.phoneNumber, PHONE_NUMBER);
    }

    /**
     * with both email and phoneNumber set
     *
     * @throws Exception
     */
    @Test
    public void getUserByIdWithEmailAndPhoneNumber() throws Exception {
        TestingProcessManager.TestingProcess process = startApplicationWithDefaultArgs();

        PasswordlessStorage storage = StorageLayer.getPasswordlessStorage(process.getProcess());
        UserInfo user = null;

        Passwordless.ConsumeCodeResponse consumeCodeResponse = createUserWith(process, EMAIL, PHONE_NUMBER);

        user = storage.getUserById(consumeCodeResponse.user.id);
        assertNotNull(user);
        assertEquals(user.email, EMAIL);
        assertEquals(user.phoneNumber, PHONE_NUMBER);
    }

    /**
     * returns null if it doesn't exist
     *
     * @throws Exception
     */
    @Test
    public void getUserByInvalidId() throws Exception {
        TestingProcessManager.TestingProcess process = startApplicationWithDefaultArgs();

        PasswordlessStorage storage = StorageLayer.getPasswordlessStorage(process.getProcess());
        UserInfo user = null;

        Passwordless.ConsumeCodeResponse consumeCodeResponse = createUserWith(process, EMAIL, null);

        user = storage.getUserById(consumeCodeResponse.user.id + "1");
        assertNull(user);
    }

    /**
     * getUserByEmail
     *
     * @throws Exception
     */
    @Test
    public void getUserByEmail() throws Exception {
        TestingProcessManager.TestingProcess process = startApplicationWithDefaultArgs();

        PasswordlessStorage storage = StorageLayer.getPasswordlessStorage(process.getProcess());
        UserInfo user = null;

        createUserWith(process, EMAIL, null);

        user = storage.getUserByEmail(EMAIL);
        assertNotNull(user);
        assertEquals(user.email, EMAIL);

    }

    /**
     * getUserByEmail
     *
     * @throws Exception
     */
    @Test
    public void getUserByInvalidEmail() throws Exception {
        TestingProcessManager.TestingProcess process = startApplicationWithDefaultArgs();

        PasswordlessStorage storage = StorageLayer.getPasswordlessStorage(process.getProcess());
        UserInfo user = null;

        createUserWith(process, EMAIL, null);

        user = storage.getUserByEmail(EMAIL + "A");
        assertNull(user);

    }

    /**
     * getUserByPhoneNumber
     *
     * @throws Exception
     */
    @Test
    public void getUserByPhoneNumber() throws Exception {
        TestingProcessManager.TestingProcess process = startApplicationWithDefaultArgs();

        PasswordlessStorage storage = StorageLayer.getPasswordlessStorage(process.getProcess());
        UserInfo user = null;

        createUserWith(process, null, PHONE_NUMBER);

        user = storage.getUserByPhoneNumber(PHONE_NUMBER);
        assertNotNull(user);
        assertEquals(user.phoneNumber, PHONE_NUMBER);
    }

    /**
     * getUserByPhoneNumber
     *
     * @throws Exception
     */
    @Test
    public void getUserByInvalidPhoneNumber() throws Exception {
        TestingProcessManager.TestingProcess process = startApplicationWithDefaultArgs();

        PasswordlessStorage storage = StorageLayer.getPasswordlessStorage(process.getProcess());
        UserInfo user = null;

        createUserWith(process, null, PHONE_NUMBER);

        user = storage.getUserByPhoneNumber(PHONE_NUMBER + "1");
        assertNull(user);
    }

}
