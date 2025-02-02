/**
 * Copyright © 2024 Apple Inc. and the Pkl project authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pkl.executor;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.pkl.executor.spi.v1.ExecutorSpiOptions;

/**
 * Options for {@link Executor#evaluatePath}.
 *
 * <p>Note that subclasses of {@code ExecutorOptions} offer additional options.
 */
public class ExecutorOptions {
  protected final List<String> allowedModules;

  protected final List<String> allowedResources;

  protected final Map<String, String> environmentVariables;

  protected final Map<String, String> externalProperties;

  protected final List<Path> modulePath;

  protected final /* @Nullable */ Path rootDir;

  protected final /* @Nullable */ Duration timeout;

  protected final /* @Nullable */ String outputFormat;

  protected final /* @Nullable */ Path moduleCacheDir;

  protected final /* @Nullable */ Path projectDir;

  /** Returns the module cache dir that the CLI uses by default. */
  public static Path defaultModuleCacheDir() {
    return Path.of(System.getProperty("user.home"), ".pkl", "cache");
  }

  /**
   * Constructs an options object.
   *
   * @param allowedModules API equivalent of the {@code --allowed-modules} CLI option
   * @param allowedResources API equivalent of the {@code --allowed-resources} CLI option
   * @param environmentVariables API equivalent of the repeatable {@code --env-var} CLI option
   * @param externalProperties API equivalent of the repeatable {@code --property} CLI option
   * @param modulePath API equivalent of the {@code --module-path} CLI option
   * @param rootDir API equivalent of the {@code --root-dir} CLI option
   * @param timeout API equivalent of the {@code --timeout} CLI option
   * @param outputFormat API equivalent of the {@code --format} CLI option
   * @param moduleCacheDir API equivalent of the {@code --cache-dir} CLI option. Passing {@link
   *     #defaultModuleCacheDir()} is equivalent to omitting {@code --cache-dir}. Passing {@code
   *     null} is equivalent to {@code --no-cache}.
   * @param projectDir API equivalent of the {@code --project-dir} CLI option.
   */
  public ExecutorOptions(
      List<String> allowedModules,
      List<String> allowedResources,
      Map<String, String> environmentVariables,
      Map<String, String> externalProperties,
      List<Path> modulePath,
      /* @Nullable */ Path rootDir,
      /* @Nullable */ Duration timeout,
      /* @Nullable */ String outputFormat,
      /* @Nullable */ Path moduleCacheDir,
      /* @Nullable */ Path projectDir) {

    this.allowedModules = allowedModules;
    this.allowedResources = allowedResources;
    this.environmentVariables = environmentVariables;
    this.externalProperties = externalProperties;
    this.modulePath = modulePath;
    this.rootDir = rootDir;
    this.timeout = timeout;
    this.outputFormat = outputFormat;
    this.moduleCacheDir = moduleCacheDir;
    this.projectDir = projectDir;
  }

  /** API equivalent of the {@code --allowed-modules} CLI option. */
  public List<String> getAllowedModules() {
    return allowedModules;
  }

  /** API equivalent of the {@code --allowed-resources} CLI option. */
  public List<String> getAllowedResources() {
    return allowedResources;
  }

  /** API equivalent of the repeatable {@code --env-var} CLI option. */
  public Map<String, String> getEnvironmentVariables() {
    return environmentVariables;
  }

  /** API equivalent of the repeatable {@code --property} CLI option. */
  public Map<String, String> getExternalProperties() {
    return externalProperties;
  }

  /** API equivalent of the {@code --module-path} CLI option. */
  public List<Path> getModulePath() {
    return modulePath;
  }

  /** API equivalent of the {@code --root-dir} CLI option. */
  public /* @Nullable */ Path getRootDir() {
    return rootDir;
  }

  /** API equivalent of the {@code --timeout} CLI option. */
  public Duration getTimeout() {
    return timeout;
  }

  /** API equivalent of the {@code --format} CLI option. */
  public /* @Nullable */ String getOutputFormat() {
    return outputFormat;
  }

  /**
   * API equivalent of the {@code --cache-dir} CLI option. {@code null} is equivalent to {@code
   * --no-cache}.
   */
  public /* @Nullable */ Path getModuleCacheDir() {
    return moduleCacheDir;
  }

  /**
   * API equivalent of the {@code --project-dir} CLI option.
   *
   * <p>Unlike the CLI, this option only sets project dependencies. It does not set evaluator
   * settings.
   */
  public /* @Nullable */ Path getProjectDir() {
    return projectDir;
  }

  @Override
  public boolean equals(/* @Nullable */ Object obj) {
    if (this == obj) return true;
    if (obj.getClass() != ExecutorOptions.class) return false;

    var other = (ExecutorOptions) obj;
    return allowedModules.equals(other.allowedModules)
        && allowedResources.equals(other.allowedResources)
        && environmentVariables.equals(other.environmentVariables)
        && externalProperties.equals(other.externalProperties)
        && modulePath.equals(other.modulePath)
        && Objects.equals(rootDir, other.rootDir)
        && Objects.equals(timeout, other.timeout)
        && Objects.equals(outputFormat, other.outputFormat)
        && Objects.equals(moduleCacheDir, other.moduleCacheDir)
        && Objects.equals(projectDir, other.projectDir);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        allowedModules,
        allowedResources,
        environmentVariables,
        externalProperties,
        modulePath,
        rootDir,
        timeout,
        outputFormat,
        moduleCacheDir,
        projectDir);
  }

  @Override
  public String toString() {
    return "ExecutorOptions{"
        + "allowedModules="
        + allowedModules
        + ", allowedResources="
        + allowedResources
        + ", environmentVariables="
        + environmentVariables
        + ", externalProperties="
        + externalProperties
        + ", modulePath="
        + modulePath
        + ", rootDir="
        + rootDir
        + ", timeout="
        + timeout
        + ", outputFormat="
        + outputFormat
        + ", cacheDir="
        + moduleCacheDir
        + ", projectDir="
        + projectDir
        + '}';
  }

  ExecutorSpiOptions toSpiOptions() {
    return new ExecutorSpiOptions(
        allowedModules,
        allowedResources,
        environmentVariables,
        externalProperties,
        modulePath,
        rootDir,
        timeout,
        outputFormat,
        moduleCacheDir,
        projectDir);
  }
}
