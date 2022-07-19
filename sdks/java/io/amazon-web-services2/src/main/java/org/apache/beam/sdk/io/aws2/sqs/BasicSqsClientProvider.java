/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.sdk.io.aws2.sqs;

import static org.apache.beam.sdk.io.aws2.common.ClientBuilderFactory.defaultFactory;
import static org.apache.beam.vendor.guava.v26_0_jre.com.google.common.base.Preconditions.checkArgument;

import java.net.URI;
import org.apache.beam.sdk.io.aws2.common.ClientConfiguration;
import org.apache.beam.sdk.io.aws2.options.AwsOptions;
import org.checkerframework.checker.nullness.qual.Nullable;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

/**
 * Basic implementation of {@link SqsClientProvider} used by default in {@link SqsIO}.
 *
 * @deprecated Configure a custom {@link org.apache.beam.sdk.io.aws2.common.ClientBuilderFactory}
 *     using {@link AwsOptions#getClientBuilderFactory()} instead.
 */
@Deprecated
class BasicSqsClientProvider implements SqsClientProvider {
  private final ClientConfiguration config;

  BasicSqsClientProvider(
      AwsCredentialsProvider credentialsProvider, String region, @Nullable URI endpoint) {
    checkArgument(credentialsProvider != null, "awsCredentialsProvider can not be null");
    checkArgument(region != null, "region can not be null");
    config = ClientConfiguration.create(credentialsProvider, Region.of(region), endpoint);
  }

  @Override
  public SqsClient getSqsClient() {
    return defaultFactory().create(SqsClient.builder(), config, null).build();
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BasicSqsClientProvider that = (BasicSqsClientProvider) o;
    return config.equals(that.config);
  }

  @Override
  public int hashCode() {
    return config.hashCode();
  }
}
