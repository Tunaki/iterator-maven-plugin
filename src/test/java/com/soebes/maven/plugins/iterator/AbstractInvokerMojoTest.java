package com.soebes.maven.plugins.iterator;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import org.apache.maven.shared.invoker.InvocationRequest;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Karl-Heinz Marbaise <a href="mailto:khmarbaise@apache.org">khmarbaise@apache.org</a>
 */
public class AbstractInvokerMojoTest
{

    private AbstractInvokerMojo mock;

    @BeforeMethod
    public void beforeMethod()
    {
        mock = Mockito.mock( AbstractInvokerMojo.class, Mockito.CALLS_REAL_METHODS );
        mock.setIteratorName( "item" );
        mock.setBeginToken( "@" );
        mock.setEndToken( "@" );
    }

    @Test
    public void shouldReplaceInBaseDirectory()
    {
        mock.setBaseDirectory( new File( "first-@item@" ) );

        InvocationRequest createAndConfigureAnInvocationRequest = mock.createAndConfigureAnInvocationRequest( "one" );

        assertThat( createAndConfigureAnInvocationRequest.getBaseDirectory() )
            .isEqualTo( new File( "first-one" ) );
    }

    @Test
    public void shouldReplaceInGoal()
    {
        mock.setGoals( Collections.singletonList( "java:@item@-environment" ) );

        InvocationRequest createAndConfigureAnInvocationRequest = mock.createAndConfigureAnInvocationRequest( "one" );

        assertThat( createAndConfigureAnInvocationRequest.getGoals() )
            .hasSize( 1 )
            .containsExactly( "java:one-environment" );
    }

    @Test
    public void shouldReplaceInMultipleGoals()
    {
        mock.setGoals( Arrays.asList( "java:@item@-environment", "@item@", "selection-@item@-choice" ) );

        InvocationRequest createAndConfigureAnInvocationRequest = mock.createAndConfigureAnInvocationRequest( "one" );

        assertThat( createAndConfigureAnInvocationRequest.getGoals() )
            .hasSize( 3 )
            .containsExactly( "java:one-environment", "one","selection-one-choice" );
    }

    @Test
    public void shouldReplaceInProfile()
    {
        mock.setProfiles( Collections.singletonList( "profile-@item@" ) );

        InvocationRequest createAndConfigureAnInvocationRequest = mock.createAndConfigureAnInvocationRequest( "two" );

        assertThat( createAndConfigureAnInvocationRequest.getProfiles() )
            .hasSize( 1 )
            .containsExactly( "profile-two" );
    }

    @Test
    public void shouldReplaceInMultipleProfiles()
    {
        mock.setProfiles( Arrays.asList( "profile-@item@", "profile-second-@item@", "@item@-profile" ) );

        InvocationRequest createAndConfigureAnInvocationRequest = mock.createAndConfigureAnInvocationRequest( "two" );

        assertThat( createAndConfigureAnInvocationRequest.getProfiles() )
            .hasSize( 3 )
            .containsExactly( "profile-two", "profile-second-two", "two-profile"  );
    }

    @Test
    public void shouldReplaceInProject()
    {
        mock.setProjects( Collections.singletonList( "project-@item@-a" ) );

        InvocationRequest createAndConfigureAnInvocationRequest = mock.createAndConfigureAnInvocationRequest( "three" );

        assertThat( createAndConfigureAnInvocationRequest.getProjects() )
            .hasSize( 1 )
            .containsExactly( "project-three-a" );
    }

    @Test
    public void shouldReplaceInMultipleProjects()
    {
        mock.setProjects( Arrays.asList( "project-@item@-a", "@item@project", "@item@" ) );

        InvocationRequest createAndConfigureAnInvocationRequest = mock.createAndConfigureAnInvocationRequest( "three" );

        assertThat( createAndConfigureAnInvocationRequest.getProjects() )
            .hasSize( 3 )
            .containsExactly( "project-three-a", "threeproject", "three" );
    }

}
